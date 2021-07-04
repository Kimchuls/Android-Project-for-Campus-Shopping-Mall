package com.example.supermarket_1_0.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class SplashActivity extends BaseActivity_1 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(1000);//使程序休眠一秒
                    Intent it = new Intent(getApplicationContext(), AdvActivity.class);
                    Log.i("run","a new thread");
                    startActivity(it);
                    finish();//关闭当前活动
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("err","a break");
                }
            }
        };
        myThread.start();//启动线程
    }
}
