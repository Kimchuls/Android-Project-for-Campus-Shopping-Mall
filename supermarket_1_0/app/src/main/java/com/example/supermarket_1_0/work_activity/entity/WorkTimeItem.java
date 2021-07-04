package com.example.supermarket_1_0.work_activity.entity;

import android.util.Log;

import com.example.supermarket_1_0.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getString;

public class WorkTimeItem {
    String starttime = null, endtime = null, begofftime = null;
    String userid;

    public String getstarttime() {
        return starttime;
    }

    public void setstarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getendtime() {
        return endtime;
    }

    public void setendtime(String endtime) {
        this.endtime = endtime;
    }

    public String getbegofftime() {
        return begofftime;
    }

    public void setbegofftime(String begofftime) {
        this.begofftime = begofftime;
    }

    public WorkTimeItem(String userid) {
        this.userid=userid;
        OkHttpClient client1 = new OkHttpClient();
        FormBody body1 = new FormBody.Builder()
                .add("userid",userid)
                .build();
        final Request request1 = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/get_punch_in/")
                .post(body1)
                .build();
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    starttime = (String) jsonObject.get("time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        OkHttpClient client2 = new OkHttpClient();
        FormBody body2 = new FormBody.Builder()
                .add("userid",userid)
                .build();
        final Request request2 = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/get_punch_out/")
                .post(body2)
                .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    endtime = (String) jsonObject.get("time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        OkHttpClient client3 = new OkHttpClient();
        FormBody body3 = new FormBody.Builder()
                .add("userid",userid)
                .build();
        final Request request3 = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/get_beg_off/")
                .post(body3)
                .build();
        client3.newCall(request3).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    begofftime = (String) jsonObject.get("time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addPunchInTime() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .add("time", starttime)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/punch_in/")
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

    public void addPunchOutTime() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .add("time", endtime)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/punch_out/")
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

    public void addBegOffTime() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .add("time", begofftime)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/beg_off/")
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

    public void getWorkItem() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid",userid)
                .add("time", begofftime)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "worklist/beg_off/")
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
