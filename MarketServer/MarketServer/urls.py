"""MarketServer URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from login_server import views, manage_item, worklist, custom_info, notification

urlpatterns = [
    path('admin/', admin.site.urls),
    path('login/',views.login_view),
    path('add_user/',views.add_user),
    path('delete_user/',views.delete_user),
    path('change_user/',views.change_user),

    path('worklist/punch_in/',worklist.punch_in),
    path('worklist/punch_out/',worklist.punch_out),
    path('worklist/get_punch_in/',worklist.get_punch_in),
    path('worklist/get_punch_out/',worklist.get_punch_out),
    path('worklist/beg_off/',worklist.beg_off),
    path('worklist/get_beg_off/',worklist.get_beg_off),

    path('worklist/add_task/',worklist.add_task),
    path('worklist/get_task/',worklist.get_task),
    path('worklist/done_task/',worklist.done_task),
    path('worklist/cancel_task/',worklist.cancel_task),


    path('manage_item/person/',manage_item.get_person),
    path('manage_item/add_person/',manage_item.add_person),
    path('manage_item/change_person/',manage_item.change_person),
    path('manage_item/delete_person/',manage_item.delete_person),

    path('manage_item/goods/', manage_item.get_goods),
    path('manage_item/add_goods/', manage_item.add_goods),
    path('manage_item/change_goods/', manage_item.change_goods),
    path('manage_item/delete_goods/', manage_item.delete_goods),

    path('custom/cart', custom_info.get_cart),
    path('custom/add_cart', custom_info.add_cart),
    path('custom/delete_cart', custom_info.delete_cart),
    path('custom/clean_cart', custom_info.clean_cart),

    path('custom/record', custom_info.get_record),
    path('custom/delete_record', custom_info.delete_record),

    path('custom/add_buy_note', notification.add_buy_note),
    path('custom/get_note', notification.get_note),
    path('custom/delete_note', notification.delete_note),
]
