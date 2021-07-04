package com.example.supermarket_1_0.work_activity.entity;

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
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getString;

public class AddItemData {
    List<AddItem> Lists = new ArrayList<>();
    Date date;
    public boolean flag;
    SimpleDateFormat dateFormat;
    String userid;

    final String strDateFormat = "yyyy-MM-dd HH:mm:ss";
    String[] TaskNameList = {"商品进货", "打包商品"};

    public AddItemData() {
    }

    public AddItemData(String userid) {
        this.flag = false;
        this.userid = userid;
    }

    public List<AddItem> getLists() {
        return Lists;
    }

    public void AddItemData1() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/get_task/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in AddItemData", e.getMessage());
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

    private void makedata1(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();

            JSONObject value = new JSONObject(String.valueOf(jsonObject.get(key)));

            String id = key;
            String WorkerID = (String) value.get("userid");
            int TaskCode = Integer.parseInt((String) value.get("category"));
            String StartTime = (String) value.get("time");
            String TaskDetail = (String) value.get("detail");

            if (TaskCode == 1) {
                id = id + "-" + TaskDetail.split(",")[0];
            }

            AddItem x = new AddItem(id, WorkerID, TaskCode, StartTime, TaskDetail);
            Lists.add(x);
        }
        Log.i("bbbb", String.valueOf(Lists));
        flag = true;
    }

    public static void FinishTask(String id, Map<String, Integer> chooseList) {
        String detail = "";
        int num1 = 0, num2 = 0;
        for (String key : chooseList.keySet()) {
            if (chooseList.get(key) == 0)
                num2++;
            else
                num1++;
        }
        if (num2 == 0)
            detail = "所有商品都已配齐！请及时去学生超市取货！";
        else if (num1 == 0)
            detail = "所有商品都缺货！请去学生超市办理退款。";
        else {
            detail = "下列商品缺货：";
            for (String key : chooseList.keySet()) {
                if (chooseList.get(key) == 0)
                    detail += key + "; ";
            }
            detail += "请去学生超市取货并退款！";
        }
        if (id.split("-").length == 2) {
            detail = id.split("-")[1] + detail;
        }
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("id", id.split("-")[0])
                .add("detail", detail)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/done_task/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in AddItemDataFinishTask", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
            }
        });
    }

    public static void CancelTask(String id, String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("id", id.split("-")[0])
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/cancel_task/")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in AddItemDataFinishTask", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }
}
