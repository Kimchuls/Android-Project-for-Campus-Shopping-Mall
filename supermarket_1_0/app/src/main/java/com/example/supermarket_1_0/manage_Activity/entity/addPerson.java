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

public class addPerson {
    String id;
    String Name;
    String Shop;
    String pwd;
    int Category;
    String url;

    public addPerson(String id, String Name, String Shop, String pwd, int Category, String url) {
        this.id = id;
        this.Name = Name;
        this.Shop = Shop;
        this.pwd = pwd;
        this.Category = Category;
        this.url = url;
    }

    public addPerson() {

    }

    public void delete_item(String userid) {
        OkHttpClient client1 = new OkHttpClient();
        FormBody body1 = new FormBody.Builder().add("userid", userid).build();
        final Request request1 = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/delete_person/")
                .post(body1)
                .build();
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });

        OkHttpClient client2 = new OkHttpClient();
        FormBody body2 = new FormBody.Builder().add("userid", userid).build();
        final Request request2 = new Request.Builder()
                .url(getString(R.string.mysql_url) + "delete_user/")
                .post(body2)
                .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }


    public void send_to_worker() throws JSONException {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", id)
                .add("imageurl", url)
                .add("nickname", Name)
                .add("workplace", Shop)
                .add("category", String.valueOf(Category))
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/add_person/")
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

    public void send_to_person() throws JSONException {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", id)
                .add("password", pwd)
                .add("nickname", Name)
                .add("email", String.format("%s@fudan.edu.cn", id.substring(1)))
                .add("category", "W")
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "add_user/")
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

    public void send_to_db() throws JSONException {
        send_to_worker();
        send_to_person();
    }

    public void change_to_db(String userid, String key, String value) throws JSONException {
        Log.i("change_to_db", userid + key + value);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", userid)
                .add("key", key)
                .add("value", value)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "manage_item/change_person/")
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

