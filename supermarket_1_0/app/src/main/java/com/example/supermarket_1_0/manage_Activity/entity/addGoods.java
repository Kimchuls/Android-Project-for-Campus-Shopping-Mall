package com.example.supermarket_1_0.manage_Activity.entity;

import android.util.Log;

import com.example.supermarket_1_0.R;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getString;

public class addGoods {
    String id;
    String Name;
    String intro;
    int Category;
    Double price;
    int num;
    String url;

    public addGoods(String id, String Name, String intro, int Category, Double price,int num,String url) {
        this.id = id;
        this.Name = Name;
        this.intro = intro;
        this.Category = Category;
        this.price=price;
        this.num=num;
        this.url = url;
    }

    public addGoods() {

    }

    public void delete_item(String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("goodsid", userid).build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/delete_goods/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforGoods", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }


    public void send_to_db() throws JSONException {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("goodsid", id)
                .add("name", Name)
                .add("intro", intro)
                .add("category", String.valueOf(Category))
                .add("price", String.valueOf(price))
                .add("num", String.valueOf(num))
                .add("imageurl", url)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/add_goods/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforGoods", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }

    public void change_to_db(String goodsid, String key, String value) throws JSONException {
        Log.i("change_to_db",goodsid+key+value);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("goodsid", goodsid)
                .add("key", key)
                .add("value", value)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/change_goods/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }

}

