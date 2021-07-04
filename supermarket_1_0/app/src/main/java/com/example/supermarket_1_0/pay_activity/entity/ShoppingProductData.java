package com.example.supermarket_1_0.pay_activity.entity;

import android.util.Log;

import com.example.supermarket_1_0.fragments.ShoppingFragment;
import com.example.supermarket_1_0.fragments.entity.ProductAdd;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItemData;
import com.example.supermarket_1_0.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ShoppingProductData {
    List<ShoppingProduct> Lists = new ArrayList<>();
    private ManageItemData manageItemData;
    private List<ManageItem> manageItemList;
    private ProductAdd productAdd;
    private JSONObject cartInfoJson;
    private List<String[]> shoppingRecord;
    public String userid;

    public List<ShoppingProduct> ShoppingProductData1(String userid) throws JSONException {
        manageItemData = new ManageItemData("goods");
        try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.i("finish1","manageitem");
        productAdd=new ProductAdd();
        productAdd.getCart(userid);
        //Log.i("finish2","productadd");
        try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        manageItemList=manageItemData.getList();
        cartInfoJson=productAdd.getCartInfoJson();

        for(ManageItem manageItem:manageItemList){
            String id=manageItem.getId();
            if(cartInfoJson.has(id)){
                ShoppingProduct shoppingProduct=new ShoppingProduct(id,manageItem.getName(),manageItem.getOtherInfo(),manageItem.getCategoryName(),
                        manageItem.getPrice(),manageItem.getNumber(),productAdd.getNumber(id),manageItem.getimageURL());
                Lists.add(shoppingProduct);
                Log.i("shoppingProduct",String.valueOf(shoppingProduct));
            }
        }
        return Lists;
    }

    public List<ShoppingProduct> ShoppingProductData2(String userid) throws JSONException {
        manageItemData = new ManageItemData("goods");
        /*try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Utils.sleeptime(500);
        //Log.i("finish1","manageitem");
        productAdd=new ProductAdd();
        productAdd.getRecord(userid);
        Utils.sleeptime(500);
        //Log.i("finish2","productadd");
        /*try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        manageItemList=manageItemData.getList();

        shoppingRecord=productAdd.getShoppingRecord();
        Log.i("get shopping record",String.valueOf(manageItemList));

        for(String[] record:shoppingRecord){
            for(ManageItem manageItem:manageItemList){
                Log.i("check shopping record",String.valueOf(manageItem.getId())+" "+record[1]);
                if(Objects.equals(record[1], manageItem.getId())){
                    ShoppingProduct shoppingProduct=new ShoppingProduct(record[1],manageItem.getName(),manageItem.getOtherInfo(),
                            record[0],manageItem.getPrice(),manageItem.getNumber(),
                            Integer.parseInt(record[2]),manageItem.getimageURL());
                    Log.i("get shopping record",shoppingProduct.toString());
                    Lists.add(shoppingProduct);
                    Log.i("shoppingProduct",String.valueOf(shoppingProduct));
                }
            }
        }
        return Lists;
    }
}
