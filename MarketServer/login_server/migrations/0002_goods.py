# Generated by Django 3.1.3 on 2021-01-03 13:33

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('login_server', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='goods',
            fields=[
                ('ID', models.AutoField(primary_key=True, serialize=False)),
                ('goodsid', models.CharField(max_length=15, verbose_name='商品编号')),
                ('name', models.CharField(max_length=5, verbose_name='商品名称')),
                ('intro', models.CharField(max_length=100, verbose_name='简介')),
                ('category', models.CharField(max_length=5, verbose_name='类别 零食等4个')),
                ('imageurl', models.CharField(max_length=150, verbose_name='头像存储地址')),
                ('price', models.CharField(default='', max_length=10, verbose_name='开始工作打卡时间')),
                ('num', models.CharField(default='', max_length=10, verbose_name='结束工作打卡时间')),
            ],
        ),
    ]