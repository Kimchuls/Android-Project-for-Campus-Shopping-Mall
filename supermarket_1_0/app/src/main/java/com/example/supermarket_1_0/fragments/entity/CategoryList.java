package com.example.supermarket_1_0.fragments.entity;

import android.util.Log;

import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {
    List<ManageItem> A=new ArrayList<>();
    public List<ManageItem> ListManageItem0= new ArrayList<>();
    public List<ManageItem> ListManageItem1= new ArrayList<>();
    public List<ManageItem> ListManageItem2= new ArrayList<>();
    public List<ManageItem> ListManageItem3= new ArrayList<>();
    public List<ManageItem> initListManageItem;

    public CategoryList(List<ManageItem> products){
        this.initListManageItem=products;

        for(ManageItem manageItem:initListManageItem){
            Log.i("product",String.valueOf(manageItem));
            switch (manageItem.getCategoryID()){
                case 0:
                    ListManageItem0.add(manageItem);
                    break;
                case 1:
                    ListManageItem1.add(manageItem);
                    break;
                case 2:
                    ListManageItem2.add(manageItem);
                    break;
                case 3:
                    ListManageItem3.add(manageItem);
                    break;
                default:
                    break;
            }
        }
    }
    public List<ManageItem> getListManageItem(int pos) {
        switch (pos){
            case 0:
                return ListManageItem0;
            case 1:
                return ListManageItem1;
            case 2:
                return ListManageItem2;
            case 3:
                return ListManageItem3;
            default:
                return new ArrayList<>();
        }
    }
}
