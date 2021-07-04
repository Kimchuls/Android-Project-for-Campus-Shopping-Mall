package com.example.supermarket_1_0.pay_activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.fragments.entity.ProductAdd;
import com.example.supermarket_1_0.pay_activity.Adapter.ViewPagerAdapter;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 展示商品详情的界面
 */
//public class ProductActivity extends BaseActivity implements View.OnClickListener {
@Page
public class ProductActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_product)
    ViewPager vp_product;
    @BindView(R.id.bt_reduce)
    ImageView bt_reduce;
    @BindView(R.id.bt_add)
    ImageView bt_add;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_detail)
    TextView tv_product_detail;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_inventory)
    TextView tv_inventory;
    @BindView(R.id.tv_shopping_car)
    TextView tv_shopping_car;
    @BindView(R.id.tv_image_tips)
    TextView tv_image_tips;
    @BindView(R.id.tv_buyNum)
    EditText tv_buyNum;
    @BindView(R.id.ll_go_to_shopping_car)
    LinearLayout ll_go_to_shopping_car;

    List<View> views = new ArrayList<>();   //存放展示图片的list
    public  ProductActivity context ;

    String userid, datastr, id, ImageURL, CatrgoryName, Name, detail;
    String[] datas;
    Double price;
    int Number;

    private String tag = "TAG_ProductActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    private String imageURLAdd(String url) {
        String newURL = url.substring(0, url.length() - 4) + "_1.png";
        Log.i("url", newURL);
        return newURL;
    }

    private void initViews() {
        context = this;
        bt_reduce.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        tv_shopping_car.setOnClickListener(this);
        ll_go_to_shopping_car.setOnClickListener(this);

        //获取传过来的具体商品
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        datastr = intent.getStringExtra("data");
        Log.d(tag,userid+"\n"+datastr);

        datas = datastr.split(",");
        id = datas[0].split("=")[1];
        ImageURL = datas[1].split("=")[1];
        price = Double.valueOf(datas[2].split("=")[1]);
        CatrgoryName = datas[3].split("=")[1];
        Name = datas[4].split("=")[1];
        detail = datas[5].split("=")[1];
        Number = Integer.parseInt(datas[6].split("=")[1]);
        //XToastUtils.toast(id);

        tv_product_name.setText(String.format("[%s] %s", CatrgoryName, Name));
        tv_product_detail.setText(detail);
        tv_price.setText("￥" + price);
        tv_inventory.setText(String.valueOf(Number));

        //商品信息初始化
        //initPicture(ImageURL);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
        ImageView proImageView = new ImageView(this);
        ImageLoader.get().loadImage(proImageView, ImageURL);
        proImageView.setLayoutParams(layoutParams);
        views.add(proImageView);

        ImageView proImageView2 = new ImageView(this);
        ImageLoader.get().loadImage(proImageView2, imageURLAdd(ImageURL));
        proImageView2.setLayoutParams(layoutParams);
        views.add(proImageView2);
        tv_image_tips.setText(1 + "/" + views.size());

        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        vp_product.setAdapter(adapter);
        //viewpager 页面滑动监测
        vp_product.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //当前item滑动过程中会一直回调.i 当前item的定位.v 当前页面偏移的百分比.i1 当前页面偏移的像素位置

            }

            @Override
            public void onPageSelected(int i) {
                //当前的item
                tv_image_tips.setText((i + 1) + "/" + views.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //此方法 在当前item状态改变的时候调用。i=1：表示正在滑动。 i=2：表示滑动结束。 i=0：表示啥都没做
            }
        });
    }

    private void initListeners() {
    }

    @Override
    public void onClick(View v) {
        int buyNum;     //购买数量
        switch (v.getId()) {
            case R.id.bt_reduce:
                buyNum = Integer.parseInt(tv_buyNum.getText().toString());
                buyNum--;
                if (buyNum <= 0) {
                    buyNum = 0;
                }
                tv_buyNum.setText(String.valueOf(buyNum));
                buyNum = Integer.parseInt(tv_buyNum.getText().toString());
                Log.d(tag, "buyNum_reduce" + buyNum);
                break;
            case R.id.bt_add:
                buyNum = Integer.parseInt(tv_buyNum.getText().toString());
                buyNum++;
                if (buyNum > Number) {
                    buyNum--;
                }
                tv_buyNum.setText(String.valueOf(buyNum));
                buyNum = Integer.parseInt(tv_buyNum.getText().toString());
                Log.d(tag, "buyNum_add:" + buyNum);
                break;
            case R.id.tv_shopping_car:
                //加入购物车
                buyNum = Integer.parseInt(tv_buyNum.getText().toString());
                Log.d(tag, "buyNum_shopping_cart:" + buyNum);
                ProductAdd productAdd = new ProductAdd();
                productAdd.putCart(id, userid, Name, buyNum, "add");
                //while (!productAdd.putCartFlag) ;
                //保存数据
                //saveProductData(buyNum);
                Toast.makeText(ProductActivity.this, "加入成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_go_to_shopping_car:
                //跳转到购物车界面
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                intent.putExtra("userid", userid);
                //Log.i("aaaa",userid+String.valueOf(intent));
                startActivity(intent);
                finish();
                break;
        }
    }
}
