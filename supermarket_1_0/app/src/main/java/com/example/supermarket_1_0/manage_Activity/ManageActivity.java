package com.example.supermarket_1_0.manage_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItemData;
import com.example.supermarket_1_0.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManageActivity extends BaseActivity implements View.OnClickListener {
    private String phonenum, nickname;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.check_person_button)
    Button mCheckPersonButton;
    @BindView(R.id.check_item_button)
    Button mCheckItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage;
    }

    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        Intent intent = getIntent();
        phonenum = intent.getStringExtra("userid");
        nickname = intent.getStringExtra("nickname");
        //XToastUtils.info("otherlogin " + phonenum + " " + nickname);
        toolbar.setTitle("祝你今天工作顺利，管理员" + nickname);
    }

    protected void initListeners() {
        mCheckPersonButton.setOnClickListener(this);
        mCheckItemButton.setOnClickListener(this);
    }

    ManageItemData manageItemData;
    List<ManageItem> products = new ArrayList<>();

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ManageActivity.this, ManageListActivity.class);
        switch (v.getId()) {
            case R.id.check_person_button:
                intent.putExtra("type", "person");
                startActivity(intent);
                break;
            case R.id.check_item_button:
                intent.putExtra("type", "goods");
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
