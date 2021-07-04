from django.core import serializers
from django.core.exceptions import ObjectDoesNotExist
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from login_server.models import worker, goods, taskItem, Notification
import json

TaskNameList = ["商品进货", "打包商品"]


# Create your views here.

@csrf_exempt
def punch_in(request):
    print("punch_in", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        time = request.POST.get('time')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid, time)
            work = worker.objects.get(userid=userid)
            list = work.startworktime.split(',')
            list.append(time)
            work.startworktime = ','.join(list)
            work.save()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def punch_out(request):
    print("punch_out", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        time = request.POST.get('time')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid, time)
            work = worker.objects.get(userid=userid)
            list = work.endworktime.split(',')
            list.append(time)
            work.endworktime = ','.join(list)
            work.save()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_punch_in(request):
    print("get_punch_in", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'time': "xx-xx-xx",
            'Status Code': 404
        }
        try:
            work = worker.objects.get(userid=userid)
            list = work.startworktime.split(',')
            time = list[-1]
            print(userid, time)
            data = {
                'time': time,
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_punch_out(request):
    print("get_punch_out", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'time': "xx-xx-xx",
            'Status Code': 404
        }
        try:
            work = worker.objects.get(userid=userid)
            list = work.endworktime.split(',')
            time = list[-1]
            print(userid, time)
            data = {
                'time': time,
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def beg_off(request):
    print("beg_off", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        time = request.POST.get('time')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid, time)
            work = worker.objects.get(userid=userid)
            list = work.begofftime.split(',')
            list.append(time)
            work.begofftime = ','.join(list)
            work.save()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_beg_off(request):
    print("get_beg_off", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'time': "xx-xx-xx",
            'Status Code': 404
        }
        try:
            work = worker.objects.get(userid=userid)
            list = work.begofftime.split(',')
            time = list[-1]
            print(userid, time)
            data = {
                'time': time,
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_task(request):
    print("get_task", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid)
            tasks = taskItem.objects.filter(userid=userid)
            json_data = serializers.serialize('json', tasks)
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
def done_task(request):
    print("done_task", request.method)
    if request.method == 'POST':
        id = request.POST.get('id')
        detail = request.POST.get('detail')
        data_error = {
            'Status Code': 404
        }
        try:
            print(id)
            task = taskItem.objects.get(ID=id)
            note = Notification()
            note.userid = task.customid
            note.details = detail
            note.save()

            work = worker.objects.get(userid=task.userid)
            print(int(work.tasknum),str(int(work.tasknum) - 1),str(int(int(work.tasknum) - 1)))
            work.tasknum=str(int(work.tasknum) - 1)
            work.save()

            task.delete()


            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def cancel_task(request):
    print("done_task", request.method)
    data_error = {
        'Code': "取消失败",
        'Status Code': 404
    }
    data = {
        'Code': "取消成功",
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            id = request.POST.get('id')
            userid = request.POST.get("userid")
            work = worker.objects.get(userid=userid)
            workplace = work.workplace
            category = work.category
            print(id, userid, workplace, category)
            # 找到所有满足门店-职位的人
            search = worker.objects.filter(workplace=workplace, category__in=[category, 3]).order_by('tasknum')
            # print(list(search))
            if search.count() == 1:
                return HttpResponse(json.dumps(data_error), content_type='application/json')
            if search.first().userid == userid:
                changeuser = search[1]
            else:
                changeuser = search[0]
            # print(search,search.count(),changeuser)
            # 本人任务-1
            work.tasknum = str(int(work.tasknum) - 1)
            work.save()
            # 他人任务-1
            changeuser.tasknum = str(int(changeuser.tasknum) + 1)
            changeuser.save()
            # 任务的userid改变
            task = taskItem.objects.get(ID=id)
            task.userid = changeuser.userid
            task.save()
            # 添加通知
            note = Notification()
            note.userid = changeuser.userid
            note.details = TaskNameList[int(task.category)] + ": " + task.detail
            note.save()
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


def add_task(request):
    print("add_task", request.method)
    if request.method == 'POST':
        category = request.POST.get('category')
        time = request.POST.get('time')
        detail = request.POST.get('detail')
        customid = request.POST.get('customid')
        data_error = {
            'Status Code': 404
        }

        try:
            print(category, time, detail)
            work = worker.objects.filter(category=[category, 3]).order_by("tasknum")[0]
            work.tasknum = str(int(work.tasknum) + 1)
            task = taskItem()
            task.userid = work.userid
            task.category = category
            task.time = time
            task.detail = detail
            task.customid = customid
            task.save()

            note = Notification()
            note.userid = work.userid
            note.details = TaskNameList[int(category)] + ": " + detail
            note.save()
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')
