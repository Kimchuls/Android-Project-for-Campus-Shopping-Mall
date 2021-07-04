package com.example.supermarket_1_0.record_Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.pay_activity.entity.ShoppingProduct;
import com.example.supermarket_1_0.pay_activity.entity.ShoppingProductData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity implements View.OnClickListener {

    public RecordActivity context;
    ImageView iv_tip_des;
    RecyclerView rv_shopping_cart_list;
    RecycleViewRecordAdapter shoppingAdapter;
    private final String tag = "TAG-RecordActivity";
    String[] position_list;
    String userid;
    public static final int REQUEST_CODE = 1231;

    List<ShoppingProduct> products = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        initViews();
    }

    public void initViews() {
        context = this;
        iv_tip_des = findViewById(R.id.iv_tip_des);
        rv_shopping_cart_list = findViewById(R.id.rv_shopping_cart_list);
        try {
            products = new ShoppingProductData().ShoppingProductData2(userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (products.size() == 0) {
            return;
        }
        shoppingAdapter = new RecycleViewRecordAdapter(products, userid, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_shopping_cart_list.setLayoutManager(manager);
        rv_shopping_cart_list.setAdapter(shoppingAdapter);
        //给RecycleView item设置分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.my_divider);
        if (drawable != null) {
            itemDecoration.setDrawable(drawable);
        }
        rv_shopping_cart_list.addItemDecoration(itemDecoration);
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
