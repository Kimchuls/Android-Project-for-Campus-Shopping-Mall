package com.example.supermarket_1_0.work_activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.utils.Utils;
import com.example.supermarket_1_0.work_activity.Adapter.RecycleViewWorklistAdapter;
import com.example.supermarket_1_0.work_activity.entity.AddItem;
import com.example.supermarket_1_0.work_activity.entity.AddItemData;

import java.util.ArrayList;
import java.util.List;

public class WorkListActivity extends BaseActivity implements View.OnClickListener {

    public static WorkListActivity context;
    ImageView iv_tip_des;
    RecyclerView rv_work_list;
    RecycleViewWorklistAdapter worklistAdapter;
    AddItemData addItemData;
    private final String tag = "TAG-Worklist";
    private static final int REQUEST_CODE = 0102;
    String username;
    String userid;

    List<AddItem> products = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklist);
        context = this;

        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        userid = intent.getStringExtra("userid");
        Log.i("aaa", username + userid);

        iv_tip_des = findViewById(R.id.iv_tip_des);
        rv_work_list = findViewById(R.id.rv_work_list);

        addItemData = new AddItemData(userid);
        addItemData.AddItemData1();
        //while (!addItemData.flag);
        Utils.sleeptime(1000);
        products = addItemData.getLists();

        Log.d(tag, "getdata1: " + products.size());
        if (products.size() == 0) {
            rv_work_list.setVisibility(View.GONE);
            iv_tip_des.setVisibility(View.VISIBLE);
            return;
        }

        worklistAdapter = new RecycleViewWorklistAdapter(products, username, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_work_list.setLayoutManager(manager);
        rv_work_list.setAdapter(worklistAdapter);
        //给RecycleView item设置分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.my_divider);
        if (drawable != null) {
            itemDecoration.setDrawable(drawable);
        }
        rv_work_list.addItemDecoration(itemDecoration);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //物理返回键关闭本页，也需要回传数据
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }

    @Override
    public void onClick(View v) {

    }

}
