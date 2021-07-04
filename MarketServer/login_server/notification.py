from django.core import serializers
from django.core.exceptions import ObjectDoesNotExist
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from login_server.models import worker, goods, customers, Notification, taskItem
import json
import time


# Create your views here.

@csrf_exempt
def add_buy_note(request):
    print("add_buy_note", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            customid = request.POST.get("customid")
            detail = request.POST.get("detail")
            time = request.POST.get("time")
            shop = request.POST.get("shop")
            print(customid,detail,time,shop)
            search = worker.objects.filter(workplace=shop, category__in=[2, 3]).order_by('tasknum')
            if search.count() == 0:
                return HttpResponse(json.dumps(data_error), content_type='application/json')
            searchs=search.first()
            workerid = searchs.userid

            # 发送打包任务
            task = taskItem()
            task.userid = workerid
            task.category = "1"
            task.time = time
            task.detail = detail
            task.customid = customid
            task.save()
            # 打包任务的细节加上任务编号
            task = taskItem.objects.get(customid=customid, time=time)
            moredetail = str(task.ID+1000000)[1:] + "," + task.detail
            task.detail=moredetail
            task.save()
            # 发送打包通知
            notes = Notification()
            notes.userid = workerid
            notes.details = "打包商品:" + moredetail
            notes.save()
            # 商品存货减少
            details=detail.split(",")
            for stri in details:
                strs=stri.split("-")
                good=goods.objects.get(goodsid=strs[0])
                print(strs[0],strs[1],strs[2])
                good.num=str(int(int(good.num)-int(strs[2])))
                number = int(int(good.num)-int(strs[2]))
                if(number<20):
                    search1 = worker.objects.filter(workplace=shop, category__in=[1, 3]).order_by('tasknum').first()
                    workerid1=search1.userid
                    moredetail1=strs[0]+"-"+strs[1]+"-1000"
                    task1=taskItem()
                    task1.userid=workerid1
                    task1.category=0
                    task1.time=time
                    task1.detail=moredetail1
                    task1.customid="000001"
                    task1.save()

                    notes1 = Notification()
                    notes1.userid = workerid1
                    notes1.details = "进货商品:" + moredetail1
                    notes1.save()
                good.save()
            # 购物记录添加
            customer=customers.objects.get(userid=customid)
            record=customer.record.replace("\'","\"")
            record_dict=json.loads(record)
            custom_cart=json.loads(customer.cart.replace("\'","\""))
            record_dict[time]=custom_cart
            customer.record=str(record_dict)
            # 购物车清空
            customer.cart=str({})
            customer.save()
            # 员工任务数目加一
            searchs.tasknum=str(int(searchs.tasknum)+1)
            searchs.save()

            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_note(request):
    print("get_note", request.method)
    data_error = {
        'Status Code': 404
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            notes = Notification.objects.filter(userid=userid)
            json_data = serializers.serialize('json', notes)
            json_data = json.loads(json_data)
            data = {}
            for item in json_data:
                # print(type(item),item)
                data[item['pk']] = item['fields']
            print(data)
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def delete_note(request):
    print("delete_note", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            Notification.objects.filter(userid=userid).delete()
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')
