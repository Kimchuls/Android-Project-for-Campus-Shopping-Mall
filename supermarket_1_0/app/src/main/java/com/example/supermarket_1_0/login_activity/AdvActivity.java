package com.example.supermarket_1_0.login_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.supermarket_1_0.R;

import java.util.Random;


public class AdvActivity extends BaseActivity_1 {
    private int[] picsLayout = {R.layout.layout_hello, R.layout.layout_hello2,
            R.layout.layout_hello3, R.layout.layout_hello4, R.layout.layout_hello5};
    private int i;
    private int count = 5;
    private Button mBtnSkip;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                mBtnSkip.setText("跳过 (" + getCount() + ")");
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
    private LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random r = new Random();
        i = r.nextInt(5);
        Log.i("test", "随机数是:" + i);
        if (i < 5) {
            //随机概率会出现广告页
            setContentView(R.layout.activity_adv);
            initView2();
        } else {
            startActivity(new Intent(AdvActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void initView2() {
        linearLayout = findViewById(R.id.advLine);
        View view = View.inflate(AdvActivity.this, picsLayout[i], null);
        linearLayout.addView(view);
        mBtnSkip = view.findViewById(R.id.btn_skip);
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdvActivity.this, LoginActivity.class));
                handler.removeMessages(0);
                finish();
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    public int getCount() {
        count--;
        if (count == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return count;
    }
}
