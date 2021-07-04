# Generated by Django 3.1.3 on 2021-01-03 07:27

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Login',
            fields=[
                ('ID', models.AutoField(primary_key=True, serialize=False)),
                ('userid', models.CharField(max_length=25, verbose_name='用户手机号或账号')),
                ('password', models.CharField(max_length=25, verbose_name='密码')),
                ('nickname', models.CharField(max_length=25, verbose_name='用户姓名')),
                ('email', models.CharField(max_length=100, verbose_name='用户邮箱')),
                ('category', models.CharField(max_length=1, verbose_name='用户类型M/W/C')),
            ],
        ),
        migrations.CreateModel(
            name='worker',
            fields=[
                ('ID', models.AutoField(primary_key=True, serialize=False)),
                ('userid', models.CharField(max_length=15, verbose_name='用户手机号或账号')),
                ('imageurl', models.CharField(max_length=150, verbose_name='头像存储地址')),
                ('nickname', models.CharField(max_length=5, verbose_name='用户姓名')),
                ('workplace', models.CharField(max_length=10, verbose_name='工作门店')),
                ('category', models.CharField(max_length=5, verbose_name='职位 收银员|理货员|打包员')),
                ('startworktime', models.CharField(default='', max_length=5000, verbose_name='开始工作打卡时间')),
                ('endworktime', models.CharField(default='', max_length=5000, verbose_name='结束工作打卡时间')),
            ],
        ),
    ]
