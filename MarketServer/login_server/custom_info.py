from django.core import serializers
from django.core.exceptions import ObjectDoesNotExist
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from login_server.models import worker, goods, customers
import json
import time


# Create your views here.

@csrf_exempt
def get_cart(request):
    print("get_cart", request.method)
    data_error = {
        'Status Code': 404
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            print(userid)
            cart = customers.objects.get(userid=userid).cart
            cart = cart.replace("\'", "\"")
            data = json.loads(cart)
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def add_cart(request):
    print("add_cart", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            goodsid = request.POST.get("goodsid")
            name = request.POST.get("name")
            number = request.POST.get("number")
            userid = request.POST.get("userid")
            type = request.POST.get("type")
            print(goodsid, name, number, userid)

            workers = customers.objects.get(userid=userid)
            cart = workers.cart
            cart = cart.replace("\'", "\"")
            cart_dict = json.loads(cart)
            if cart_dict.get(goodsid, -1) == -1:
                cart_dict[goodsid] = {"name": name, "number": number}
            else:
                if type == "add":
                    cart_dict[goodsid]["number"] = str(int(cart_dict[goodsid]["number"]) + int(number))
                elif type == "set":
                    cart_dict[goodsid]["number"] = number
            print(cart_dict)

            workers.cart = str(cart_dict)
            workers.save()

            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def delete_cart(request):
    print("delete_cart", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            goodsid = request.POST.get("goodsid")
            userid = request.POST.get("userid")
            print(goodsid,userid)
            worker = customers.objects.get(userid=userid)
            carts = worker.cart
            carts = carts.replace("\'", "\"")
            data = json.loads(carts)
            print(data)
            del data[goodsid]
            worker.cart = str(data)
            print(data)
            worker.save()

            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


def clean_cart(request):
    print("clean_cart", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")

            workers = customers.objects.get(userid=userid)
            workers.cart = str({})
            workers.save()

            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_record(request):
    print("get_record", request.method)
    data_error = {
        'Status Code': 404
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            print(userid)
            record = customers.objects.get(userid=userid).record
            record = record.replace("\'", "\"")
            data = json.loads(record)
            print(data)
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')

# add_record包含在发送信息&创建任务的模块中了

@csrf_exempt
def delete_record(request):
    print("delete_record", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            time=request.POST.get("time")
            goodsid = request.POST.get("goodsid")
            userid = request.POST.get("userid")
            print(time, goodsid, userid)
            worker = customers.objects.get(userid=userid)
            records = json.loads(worker.record.replace("\'", "\""))
            record_item=records[time]
            del record_item[goodsid]
            if record_item=={}:
                del records[time]
            else:
                records[time]=record_item
            worker.record=str(records)
            worker.save()
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')
