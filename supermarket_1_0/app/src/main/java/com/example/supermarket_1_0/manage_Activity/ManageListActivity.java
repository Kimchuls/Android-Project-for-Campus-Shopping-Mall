package com.example.supermarket_1_0.manage_Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItemData;
import com.example.supermarket_1_0.utils.Utils;
import com.example.supermarket_1_0.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageListActivity extends BaseActivity implements View.OnClickListener {

    public static ManageListActivity context;
    TextView tv_worklist;
    ImageView iv_tip_des;
    RecyclerView rv_work_list;
    RecycleViewManageAdapter manageAdapter;
    Button bt_add_item;

    private final String tag = "TAG-Worklist";
    private static final int REQUEST_CODE_PERSON = 1900;
    private static final int REQUEST_CODE_GOODS = 2000;

    String[] Category_Goods = {"零食", "饮料", "日用品", "其他"};
    String[] Category_Person = {"收银员", "理货员", "打包员", "理和打"};
    String type;
    ManageItemData manageItemData;
    List<ManageItem> products = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managelist);
        context = this;

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        iv_tip_des = findViewById(R.id.iv_tip_des);
        rv_work_list = findViewById(R.id.rv_work_list);
        bt_add_item = findViewById(R.id.bt_add_item);
        tv_worklist = findViewById(R.id.tv_worklist);
        bt_add_item.setOnClickListener(this);

        if (Objects.equals(type, "person")) {
            XToastUtils.toast(type);
            manageItemData = new ManageItemData("person");
            tv_worklist.setText("工作人员名录");
        }
        else if (Objects.equals(type, "goods")) {
            XToastUtils.toast(type);
            manageItemData = new ManageItemData("goods");
            tv_worklist.setText("商品名录");
        }
        Utils.sleeptime(1000);
        products = manageItemData.getList();
        Log.d("goods", String.valueOf(products));
        Log.d("goods", "getdata1: " + products.size());

        if (products.size() == 0) {
            rv_work_list.setVisibility(View.GONE);
            iv_tip_des.setVisibility(View.VISIBLE);
            return;
        }

        if (Objects.equals(type, "person"))
            manageAdapter = new RecycleViewManageAdapter(products, Category_Person, type, this);
        else if (Objects.equals(type, "goods"))
            manageAdapter = new RecycleViewManageAdapter(products, Category_Goods, type, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_work_list.setLayoutManager(manager);
        rv_work_list.setAdapter(manageAdapter);
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
        Intent intent = new Intent(ManageListActivity.this, AddItemActivity.class);
        switch (v.getId()) {
            case R.id.bt_add_item:
                intent.putExtra("TYPE", type);
                if (type.equals("person"))
                    startActivityForResult(intent, REQUEST_CODE_PERSON);
                else if (type.equals("goods"))
                    startActivityForResult(intent, REQUEST_CODE_GOODS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("activity for result", String.valueOf(requestCode));
        ManageItem manageItem = new ManageItem();
        if (requestCode == REQUEST_CODE_PERSON && resultCode == RESULT_OK) {
            manageItem.setCategory(Category_Person);
            manageItem.setCategoryID(data.getIntExtra("CategoryID", 0));
            manageItem.setimageURL(data.getStringExtra("imageURL"));
            manageItem.setId(data.getStringExtra("id"));
            manageItem.setName(data.getStringExtra("Name"));
            manageItem.setOtherInfo(data.getStringExtra("OtherInfo"));
            manageItem.setStat(data.getStringExtra("Stat"));
            products.add(manageItem);
            Log.i("add person", products.toString());
            manageAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_GOODS && resultCode == RESULT_OK) {
            manageItem.setCategory(Category_Goods);
            manageItem.setCategoryID(data.getIntExtra("CategoryID", 0));
            manageItem.setimageURL(data.getStringExtra("imageURL"));
            manageItem.setId(data.getStringExtra("id"));
            manageItem.setName(data.getStringExtra("Name"));
            manageItem.setOtherInfo(data.getStringExtra("OtherInfo"));
            manageItem.setStat(data.getStringExtra("Stat"));
            manageItem.setCategory(Category_Goods);
            products.add(manageItem);
            manageAdapter.notifyDataSetChanged();
        }
    }
}
