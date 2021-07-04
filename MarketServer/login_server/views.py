from django.core.exceptions import ObjectDoesNotExist
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from login_server.models import Login, customers
import json
import time


# Create your views here.

@csrf_exempt
def login_view(request):
    print("login_view", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        password = request.POST.get('password')
        email = request.POST.get('email')
        print(userid, email, password)
        data_error = {
            'userid': userid,
            'nickname': '',
            'code': '错误，用户不存在',
            'Status Code': 404
        }
        try:
            if Login.objects.filter(userid=userid).exists():
                user = Login.objects.get(userid=userid)
            elif Login.objects.filter(email=email).exists():
                user = Login.objects.get(email=email)
            else:
                return HttpResponse(json.dumps(data_error), content_type='application/json')
            if user.password == password:
                data = {
                    'userid': user.userid,
                    'nickname': user.nickname,
                    'code': '成功',
                    'Status Code': 200
                }
                if user.category == 'M':
                    data['Status Code'] = 208  # 管理账号
                elif user.category == 'W':
                    data['Status Code'] = 204  # 员工账号
                elif user.category == 'C':
                    data['Status Code'] = 200  # 客户账号
                return HttpResponse(json.dumps(data), content_type='application/json')
            else:
                data_error = {
                    'userid': userid,
                    'nickname': '',
                    'code': '用户名或密码错误',
                    'Status Code': 404
                }
                print(data_error)
                return HttpResponse(json.dumps(data_error), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def add_user(request):
    print("add_user", request.method)
    nickname = ""
    category = "M"
    if request.method == 'POST':
        userid = request.POST.get('userid')
        password = request.POST.get('password')
        email = request.POST.get('email')
        category = request.POST.get('category')
        nickname = request.POST.get('nickname')
        data_error = {
            'userid': userid,
            'nickname': '',
            'code': '错误，用户不存在',
            'Status Code': 404
        }
        try:
            print("value", userid, email)
            if Login.objects.filter(email=email).exists():
                user = Login.objects.get(email=email)
                if user.userid != userid:
                    print("userid error", userid, user.userid)
                    data_error = {
                        'userid': userid,
                        'nickname': '',
                        'code': '错误，用户名与邮箱不符',
                        'Status Code': 404
                    }
                    return HttpResponse(json.dumps(data_error), content_type='application/json')
                user.password = password
                user.save()
                data = {
                    'userid': user.userid,
                    'nickname': user.nickname,
                    'code': '成功',
                    'Status Code': 200
                }
                if user.category == 'M':
                    data['Status Code'] = 208  # 管理账号
                elif user.category == 'W':
                    data['Status Code'] = 204  # 员工账号
                elif user.category == 'C':
                    data['Status Code'] = 200  # 客户账号
                return HttpResponse(json.dumps(data), content_type='application/json')
            else:
                user = Login()
                user.userid = userid
                user.password = password
                user.email = email
                user.nickname = userid if nickname == None else nickname
                user.category = 'C' if category == None else category
                print("value_register", userid, password, email, nickname, category)
                user.save()

                if(category==None):
                    customer = customers()
                    customer.userid = userid
                    customer.nickname = userid
                    customer.cart=str({})
                    customer.record = str({})
                    customer.save()

                data = {
                    'userid': userid,
                    'nickname': userid,
                    'code': '成功',
                    'Status Code': 200
                }
                return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


@csrf_exempt
def change_user(request):
    print("change_user", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        nickname = request.POST.get('nickname')
        data_error = {
            'userid': userid,
            'nickname': '',
            'code': '用户昵称修改出错',
            'Status Code': 404
        }
        try:
            print(userid)
            if Login.objects.filter(userid=userid).exists():
                user = Login.objects.get(userid=userid)
                user.nickname = nickname
                user.save()
                data = {
                    'userid': user.userid,
                    'nickname': user.nickname,
                    'code': '成功修改昵称',
                    'Status Code': 100
                }
                return HttpResponse(json.dumps(data), content_type='application/json')
            else:
                return HttpResponse(json.dumps(data_error), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')


def delete_user(request):
    print("delete_user", request.method)
    if request.method == 'POST':
        userid = request.POST.get('userid')
        data_error = {
            'Status Code': 404
        }
        try:
            print(userid)
            Login.objects.filter(userid=userid).delete()
            customers.objects.filter(userid=userid).delete()
            data = {
                'Status Code': 100
            }
            return HttpResponse(json.dumps(data), content_type='application/json')
        except ObjectDoesNotExist:
            return HttpResponse(json.dumps(data_error), content_type='application/json')
    else:
        return HttpResponse('GET请求无效')
