package com.example.supermarket_1_0;

import android.app.Activity;
import android.util.Log;

import com.example.supermarket_1_0.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getResources;
import static com.xuexiang.xui.utils.ResUtils.getString;

public class NoticeActivity {
    public Activity context;
    JSONObject infoJson = new JSONObject();

    public NoticeActivity(){}

    public NoticeActivity(Activity context){
        this.context=context;
    }

    public void sendBuyingNote(String customid, String detail,String time,String select_Shop) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("customid",customid)
                .add("detail",detail)
                .add("time",time)
                .add("shop",select_Shop)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/add_buy_note")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response){
            }
        });
    }

    public void makedata1(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        //infoJson = new JSONObject();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            JSONObject value= (JSONObject) jsonObject.get(key);
            Utils.getInstance().sendNotification(context, key, getResources().getString(R.string.app_name),
                    String.valueOf(value.get("details")), Integer.parseInt(key));
            //infoJson.put(key, String.valueOf(jsonObject.get(key)));
        }
        Log.i("ProductAdd make_Cart_List", String.valueOf(infoJson));
    }


    public void getNote(String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/get_note")
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
                    deleteNote(userid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void deleteNote(String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/delete_note")
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
