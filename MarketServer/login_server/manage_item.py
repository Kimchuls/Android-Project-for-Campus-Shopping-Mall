from django.core import serializers
from django.core.exceptions import ObjectDoesNotExist
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from login_server.models import worker, goods
import json
import time


# Create your views here.

@csrf_exempt
def get_person(request):
    print("get_person", request.method)
    data_error = {
        'data': '',
        'code': '读取数据出错',
        'Status Code': 404
    }
    print("get_person", request.method)
    if request.method == 'POST':
        try:
            workers = worker.objects.all()
            json_data = serializers.serialize('json', workers)
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
def add_person(request):
    print("add_person", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            imageurl = request.POST.get("imageurl")
            nickname = request.POST.get("nickname")
            workplace = request.POST.get("workplace")
            category = request.POST.get("category")
            work = worker()
            work.userid = userid
            work.nickname = nickname
            work.imageurl = imageurl
            work.workplace = workplace
            work.category = category
            work.startworktime = ""
            work.endworktime = ""
            work.begofftime = ""
            work.tasknum = "0"
            work.save()
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def change_person(request):
    print("change_person", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            userid = request.POST.get("userid")
            key = request.POST.get("key")
            value = request.POST.get("value")
            work = worker.objects.get(userid=userid)
            if key == "workplace":
                work.workplace = value
            if key == "category":
                work.category = value
            work.save()
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


def delete_person(request):
    print("delete_person", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid)
            worker.objects.filter(userid=userid).delete()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def get_goods(request):
    print("get_goods", request.method)
    data_error = {
        'data': '',
        'code': '读取数据出错',
        'Status Code': 404
    }
    if request.method == 'POST':
        try:
            good = goods.objects.all()
            json_data = serializers.serialize('json', good)
            json_data = json.loads(json_data)

            data = {}
            for item in json_data:
                # print(type(item),item)
                data[item['pk']] = item['fields']
            # print(data)
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def add_goods(request):
    print("add_goods", request.method)
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
            intro = request.POST.get("intro")
            category = request.POST.get("category")
            price = request.POST.get("price")
            num = request.POST.get("num")
            imageurl = request.POST.get("imageurl")
            good = goods()
            good.goodsid = goodsid
            good.name = name
            good.intro = intro
            good.category = category
            good.price = price
            good.num = num
            good.imageurl = imageurl
            good.save()
            return HttpResponse(json.dumps(data), content_type='application/json')

        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def change_goods(request):
    print("change_goods", request.method)
    data_error = {
        'Status Code': 404
    }
    data = {
        'Status Code': 100
    }
    if request.method == 'POST':
        try:
            goodsid = request.POST.get("goodsid")
            key = request.POST.get("key")
            value = request.POST.get("value")
            print(goodsid, key, value)
            good = goods.objects.get(goodsid=goodsid)
            if key == "price":
                good.price = value
            if key == "num":
                good.num = value
            good.save()
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


def delete_goods(request):
    print("delete_goods", request.method)
    if request.method == 'POST':
        goodsid = request.POST.get('goodsid')
        data_error = {
            'Status Code': 404
        }
        try:
            print(goodsid)
            goods.objects.filter(goodsid=goodsid).delete()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')
