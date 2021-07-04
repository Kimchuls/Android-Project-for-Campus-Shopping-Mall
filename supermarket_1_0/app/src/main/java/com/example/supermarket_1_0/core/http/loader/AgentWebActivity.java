package com.example.supermarket_1_0.core.http.loader;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.internal.$Gson$Types;

import java.io.Serializable;

public class AgentWebActivity extends AppCompatActivity {

    public static final String KEY_URL = "webview.key_url";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String weburi = (String)bundle.getSerializable(KEY_URL);
        Log.i("url for web",weburi);

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(weburi));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(getPackageManager());
            // 打印Log   ComponentName到底是什么
            Log.e("TAG", "componentName = " + componentName.getClassName());
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(getApplicationContext(), "没有匹配的程序", Toast.LENGTH_SHORT).show();
        }/**/
    }
}
