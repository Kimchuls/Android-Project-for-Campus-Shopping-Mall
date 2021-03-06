package com.example.supermarket_1_0.pay_activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.NoticeActivity;
import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.pay_activity.Adapter.RecycleViewShoppingAdapter;
import com.example.supermarket_1_0.pay_activity.entity.ShoppingProduct;
import com.example.supermarket_1_0.pay_activity.entity.ShoppingProductData;
import com.example.supermarket_1_0.utils.Utils;
import com.example.supermarket_1_0.utils.XToastUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xuexiang.xui.utils.ResUtils.getResources;

public class CartActivity extends BaseActivity implements View.OnClickListener {

    public CartActivity context;
    TextView tv_settlement, tv_sum_money;
    ImageView iv_tip_des;
    RecyclerView rv_shopping_cart_list;
    RecycleViewShoppingAdapter shoppingAdapter;
    private final String tag = "TAG-ShoppingCart";
    String[] position_list;
    String userid;
    public static final int REQUEST_CODE = 1231;

    List<ShoppingProduct> products = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        //Log.i(tag,userid);
        initViews();
    }

    public void initViews() {
        context = this;
        tv_sum_money = findViewById(R.id.tv_sum_money);
        tv_settlement = findViewById(R.id.tv_settlement);
        iv_tip_des = findViewById(R.id.iv_tip_des);
        tv_settlement.setOnClickListener(this);
        rv_shopping_cart_list = findViewById(R.id.rv_shopping_cart_list);
        position_list = getResources().getStringArray(R.array.sort_shop_list);

        try {
            queryTotals();//??????product
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (products.size() == 0) {
            return;
        }
        shoppingAdapter = new RecycleViewShoppingAdapter(products, userid, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_shopping_cart_list.setLayoutManager(manager);
        rv_shopping_cart_list.setAdapter(shoppingAdapter);
        //???RecycleView item???????????????
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.my_divider);
        if (drawable != null) {
            itemDecoration.setDrawable(drawable);
        }
        rv_shopping_cart_list.addItemDecoration(itemDecoration);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //???????????????????????????????????????????????????
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
        switch (v.getId()) {
            case R.id.tv_settlement:
                String str = tv_sum_money.getText().toString().split("???")[0];
                Log.i("sum_money", str);
                Double sum_money = Double.parseDouble(str.replace("???", ""));

                //XToastUtils.toast(String.format("?????????,%f", sum_money + 0.5));
                if (sum_money == 0) {
                    Utils.getInstance().tips(CartActivity.this, "????????????????????????");
                    return;
                }
                //XToastUtils.toast("?????????????????????0");
                //showPayKeyBoard(v);
                Intent intent = new Intent(CartActivity.this, PaymentKeyBoardActivity.class);
                intent.putExtra("MONEY", sum_money);
                startActivityForResult(intent, REQUEST_CODE);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            boolean checkflag = data.getBooleanExtra("PAYCHECK", false);
            int select_Shop = data.getIntExtra("SHOP", 0);
            if (checkflag) {
                //XToastUtils.toast("?????????????????????1_" + position_list[select_Shop]);

                String note = "";
                final String strDateFormat = "yyyy-MM-dd HH:mm:ss";
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                String sim = dateFormat.format(date);
                int i;
                for (i = 0; i < products.size() - 1; i++) {
                    ShoppingProduct shoppingProduct = products.get(i);
                    note += String.format("%s-%s-%s,", shoppingProduct.getProductID(), shoppingProduct.getProductName(), shoppingProduct.getBuyNum());
                }
                ShoppingProduct shoppingProduct = products.get(i);
                note += String.format("%s-%s-%s", shoppingProduct.getProductID(), shoppingProduct.getProductName(), shoppingProduct.getBuyNum());
                Log.i("finish buying",userid+"\n"+note+"\n"+sim+"\n"+select_Shop);
                new NoticeActivity().sendBuyingNote(userid, note, sim, String.valueOf(select_Shop));

                products.clear();//??????list
                shoppingAdapter.notifyDataSetChanged();
                XToastUtils.toast("?????????????????????");
                if (products.size() == 0) {//????????????????????????????????????????????????
                    rv_shopping_cart_list.setVisibility(View.GONE);
                    iv_tip_des.setVisibility(View.VISIBLE);
                    tv_sum_money.setText("???0.0");
                }

                Utils.getInstance().sendNotification(context, "buy_suc", getResources().getString(R.string.app_name), "????????????????????????", 400);
            } else {
                XToastUtils.toast("????????????");

            }
        }
    }

    //???????????????????????????????????????
    public void queryTotals() throws JSONException {
        products = new ShoppingProductData().ShoppingProductData1(userid);
        Log.d(tag, "queryTotals1: " + products.size());
        Double total = 0.0;
        if (products.size() != 0) {
            //Log.i("product","aaaa");
            for (ShoppingProduct shoppingProduct : products) {
                total += (shoppingProduct.getPrice() * shoppingProduct.getBuyNum());
            }
        } else {
            //????????????????????????????????????????????????
            rv_shopping_cart_list.setVisibility(View.GONE);
            iv_tip_des.setVisibility(View.VISIBLE);
        }
        Log.d(tag, "queryTotals2: " + total);
        tv_sum_money.setText(String.format("???%.2f????????????0.5????????????", total));
        Log.d(tag, "queryTotals3");
    }
}
