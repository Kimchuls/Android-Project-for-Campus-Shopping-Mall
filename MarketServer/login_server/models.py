from django.db import models

# Create your models here.
class Login(models.Model):
    ID=models.AutoField(primary_key=True)
    userid=models.CharField(max_length=25,verbose_name='用户手机号或账号')
    password=models.CharField(max_length=25,verbose_name='密码')
    nickname=models.CharField(max_length=25,verbose_name='用户姓名')
    email=models.CharField(max_length=100,verbose_name="用户邮箱")
    category=models.CharField(max_length=1,verbose_name="用户类型M/W/C")

class worker(models.Model):
    ID = models.AutoField(primary_key=True)
    userid = models.CharField(max_length=15, verbose_name='用户手机号或账号')
    imageurl=models.CharField(max_length=150,verbose_name="头像存储地址")
    nickname = models.CharField(max_length=5, verbose_name='用户姓名')
    workplace = models.CharField(max_length=10, verbose_name="工作门店")
    category = models.CharField(max_length=5, verbose_name="职位 收银员|理货员|打包员")
    startworktime = models.CharField(max_length=5000,verbose_name="开始工作打卡时间",default="")
    endworktime = models.CharField(max_length=5000, verbose_name="结束工作打卡时间",default="")
    begofftime = models.CharField(max_length=5000,verbose_name="请假的时间",default="")
    tasknum= models.CharField(max_length=15, verbose_name='任务数目',default="0")

class goods(models.Model):
    ID = models.AutoField(primary_key=True)
    goodsid = models.CharField(max_length=15, verbose_name='商品编号')
    name = models.TextField(max_length=30, verbose_name='商品名称')
    intro = models.TextField(max_length=100, verbose_name="简介")
    category = models.TextField(max_length=5, verbose_name="类别 零食等4个")
    imageurl=models.CharField(max_length=150,verbose_name="头像存储地址")
    price = models.CharField(max_length=10,verbose_name="开始工作打卡时间",default="")
    num = models.CharField(max_length=10, verbose_name="结束工作打卡时间",default="")

class taskItem(models.Model):
    ID = models.AutoField(primary_key=True)
    userid = models.TextField(max_length=30, verbose_name='工作人员id')
    category = models.TextField(max_length=10, verbose_name="类别 上货等几个")
    time = models.TextField(max_length=50, verbose_name="时间")
    detail = models.TextField(max_length=400, verbose_name="简介")
    customid = models.TextField(max_length=20,verbose_name="客户id，系统任务为全0",default="")

class Notification(models.Model):
    ID=models.AutoField(primary_key=True)
    userid = models.TextField(max_length=30, verbose_name='任务id')
    details=models.TextField(max_length=1000,verbose_name="细节",default="")

class customers(models.Model):
    ID = models.AutoField(primary_key=True)
    userid = models.CharField(max_length=15, verbose_name='用户手机号或账号')
    nickname = models.CharField(max_length=20, verbose_name='用户姓名')
    cart = models.TextField(max_length=5000, verbose_name="购物车")
    record = models.TextField(max_length=5000, verbose_name="购物记录")
