package com.example.supermarket_1_0.manage_Activity.entity;

import android.util.Log;

import com.example.supermarket_1_0.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getString;

public class ManageItemData {
    List<ManageItem> Lists = new ArrayList<>();
    Date date;
    SimpleDateFormat dateFormat;
    public boolean flag;

    final String strDateFormat = "yyyy-MM-dd HH:mm:ss";
    String[] Category_Goods = {"零食", "饮料", "日用品", "其他"};
    String[] Category_Person = {"收银员", "理货员", "打包员", "理和打"};
    String[] Category_Shop = {"本部学生超市", "南区学生超市", "北区学生超市", "江湾学生超市", "枫林学生超市"};

    public ManageItemData(String type) {
        this.flag = false;
        if (type == "person") {
            ManageItemData1();
        } else if (type == "goods") {
            ManageItemData2();
        }
    }

    public List<ManageItem> makedata1(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();

            JSONObject value = new JSONObject(String.valueOf(jsonObject.get(key)));
            //Log.i("bbbb", String.valueOf(value));
            int CategoryID = Integer.parseInt((String) value.get("category"));
            String CategoryName = Category_Person[CategoryID];
            String imageURL = (String) value.get("imageurl");
            String id = (String) value.get("userid");
            String Name = (String) value.get("nickname");
            String OtherInfo = "就职门店为" + Category_Shop[Integer.parseInt((String) value.get("workplace"))];
            String str = (String) value.get("startworktime");
            String[] strs = str.split(",");
            str = strs[strs.length - 1];
            String Stat = "最近开始工作时间：" + str;
            ManageItem x = new ManageItem(CategoryID, CategoryName, Category_Person, imageURL, id, Name, OtherInfo, Stat);
            Lists.add(x);
        }
        Log.i("ManageItemPerson", String.valueOf(Lists));
        flag = true;
        return Lists;
    }

    public void ManageItemData1() {
        flag=false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("type", "person")
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/person/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    makedata1(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<ManageItem> makedata2(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();

            JSONObject value = new JSONObject(String.valueOf(jsonObject.get(key)));
            //Log.i("bbbb", String.valueOf(value));
            int CategoryID = Integer.parseInt((String) value.get("category"));
            String CategoryName = Category_Goods[CategoryID];
            String imageURL = (String) value.get("imageurl");
            String id = (String) value.get("goodsid");
            String Name = (String) value.get("name");
            String OtherInfo = (String) value.get("intro");
            String Stat = String.format("价格为%.2f,剩余货物为%d件",
                    Double.parseDouble(String.valueOf(value.get("price"))), Integer.parseInt(String.valueOf(value.get("num"))));
            ManageItem x = new ManageItem(CategoryID, CategoryName, Category_Goods, imageURL, id, Name, OtherInfo, Stat);
            x.setPrice(Double.parseDouble(String.valueOf(value.get("price"))));
            x.setNumber(Integer.parseInt(String.valueOf(value.get("num"))));
            Lists.add(x);
        }
        Log.i("ManageItemGoods", String.valueOf(Lists));
        flag = true;
        return Lists;
    }

    public void ManageItemData2() {
        flag=false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("type", "goods")
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/goods/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforGoods", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    makedata2(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<ManageItem> getList() {
        return Lists;
    }


}
