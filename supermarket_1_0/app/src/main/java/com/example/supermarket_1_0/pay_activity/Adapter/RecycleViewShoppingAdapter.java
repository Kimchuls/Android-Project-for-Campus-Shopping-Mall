package com.example.supermarket_1_0.pay_activity.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.fragments.entity.ProductAdd;
import com.example.supermarket_1_0.pay_activity.CartActivity;
import com.example.supermarket_1_0.pay_activity.ProductActivity;
import com.example.supermarket_1_0.pay_activity.entity.ShoppingProduct;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class RecycleViewShoppingAdapter extends RecyclerView.Adapter<RecycleViewShoppingAdapter.MyViewHolder> {
    private List<ShoppingProduct> productLists;
    private Activity context;
    private MyViewHolder viewHolder;

    private final String tag = "TAG_RVShoppingAdapter";
    private String userid;


    public RecycleViewShoppingAdapter(List<ShoppingProduct> productLists, String userid, Activity context) {
        this.productLists = productLists;
        this.context = context;
        this.userid = userid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //加载子view视图，创建viewholder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_goods_card_view_list_item, viewGroup, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        //当前商品
        ShoppingProduct product = productLists.get(i);
        //viewHolder.iv_shopping_product_image.setImageResource(ResourceUtils.getMipmapId(context,product.getExtension()));
        ImageLoader.get().loadImage(viewHolder.iv_shopping_product_image, product.getProductUrl());
        viewHolder.tv_shopping_product_name.setText(product.getProductName());
        viewHolder.tv_shopping_product_description.setText(product.getProductDescription());
        viewHolder.tv_shopping_buyNum.setText(String.valueOf(product.getBuyNum()));
        viewHolder.tv_shopping_product_price.setText(String.format("￥%s", product.getPrice()));
        viewHolder.tv_category_name.setText(product.getType());

        int curBuyNum = Integer.parseInt(viewHolder.tv_shopping_buyNum.getText().toString());
        //点击事件监听
        viewHolder.iv_shopping_reduce.setOnClickListener(new PositionListener(i));
        viewHolder.iv_shopping_add.setOnClickListener(new PositionListener(i));
        viewHolder.bt_shopping_delete.setOnClickListener(new PositionListener(i));
        viewHolder.rl_shopping_product.setOnClickListener(new PositionListener(i));

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public int getPosition() {
        return 1;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button bt_shopping_delete;
        ImageView iv_shopping_product_image, iv_shopping_add, iv_shopping_reduce;
        TextView tv_shopping_product_name, tv_shopping_product_description, tv_shopping_product_price, tv_shopping_buyNum, tv_category_name;
        LinearLayout rl_shopping_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_shopping_product = itemView.findViewById(R.id.rl_shopping_product);
            bt_shopping_delete = itemView.findViewById(R.id.vt_delete);
            iv_shopping_reduce = itemView.findViewById(R.id.bt_reduceItem);
            iv_shopping_add = itemView.findViewById(R.id.bt_addItem);
            tv_shopping_buyNum = itemView.findViewById(R.id.tv_buyItemNum);

            iv_shopping_product_image = itemView.findViewById(R.id.iv_imageItem);
            tv_shopping_product_name = itemView.findViewById(R.id.tv_titleItem);
            tv_shopping_product_description = itemView.findViewById(R.id.tv_summaryItem);
            tv_shopping_product_price = itemView.findViewById(R.id.tv_priceItem);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
        }
    }

    class PositionListener implements View.OnClickListener {
        public int position;
        public int curBuyNum;
        public ShoppingProduct product;

        public PositionListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ProductAdd productAdd=new ProductAdd();
            product = productLists.get(position);
            curBuyNum = product.getBuyNum();
            switch (v.getId()) {
                case R.id.bt_reduceItem:
                    //点击减
                    Log.d(tag, "减-购买数量：" + curBuyNum);
                    curBuyNum--;
                    if (curBuyNum <= 0) {
                        curBuyNum = 0;
                    }
                    viewHolder.tv_shopping_buyNum.setText(String.valueOf(curBuyNum));
                    Log.d(tag, "减-购买数量修改后：" + curBuyNum);
                    productLists.get(position).setBuyNum(curBuyNum);

                    productAdd.putCart(product.getProductID(), userid, product.getProductName(), curBuyNum, "set");
                    //while(!productAdd1.putCartFlag);

                    for (ShoppingProduct shoppingProduct : productLists) {
                        Log.i("reduce", shoppingProduct.toString());
                    }
                    updateTotal();
                    notifyDataSetChanged();
                    break;
                case R.id.bt_addItem://点击加
                    Log.d(tag, "加-购买数量：" + curBuyNum);
                    if (curBuyNum < productLists.get(position).getInventory()) {
                        curBuyNum++;
                    }
                    viewHolder.tv_shopping_buyNum.setText(String.valueOf(curBuyNum));
                    Log.d(tag, "加-购买数量修改后：" + curBuyNum);
                    productLists.get(position).setBuyNum(curBuyNum);

                    productAdd.putCart(product.getProductID(), userid, product.getProductName(), curBuyNum, "set");
                    //while(!productAdd2.putCartFlag);

                    for (ShoppingProduct shoppingProduct : productLists) {
                        Log.i("add", shoppingProduct.toString());
                    }
                    notifyDataSetChanged();
                    updateTotal();
                    break;
                case R.id.vt_delete://点击删除
                    Log.d(tag, "del_product:" + product.toString());
                    productLists.remove(position);

                    productAdd.deleteCart(product.getProductID(), userid);
                    //while (!productAdd3.deleteCartFlag) ;

                    for (ShoppingProduct shoppingProduct : productLists) {
                        Log.i("delete", shoppingProduct.toString());
                    }
                    notifyDataSetChanged();
                    updateTotal();
                    break;
                case R.id.rl_shopping_product:
                    //点击item跳转
                    Log.d(tag, "cur_product:" + product.toString());
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("userid", userid);
                    intent.putExtra("data", product.toString());
                    //while(!productAdd.flagTrue());
                    context.startActivity(intent);
                    context.finish();
                    break;
                default:
                    break;
            }
        }
    }

    //修改总金额数据
    public void updateTotal() {
        int total = 0;
        if (productLists.size() != 0) {
            for (ShoppingProduct shoppingProduct : productLists) {
                total += (shoppingProduct.getPrice() * shoppingProduct.getBuyNum());
            }
        } else {
            ImageView iv_tip_des = context.findViewById(R.id.iv_tip_des);
            RecyclerView rv_shopping_cart_list = context.findViewById(R.id.rv_shopping_cart_list);
            rv_shopping_cart_list.setVisibility(View.GONE);
            iv_tip_des.setVisibility(View.VISIBLE);
        }
        Log.d(tag, "updateTotal: " + total);
        //通过context设置显示总额的textview
        TextView tv_sum_money = context.findViewById(R.id.tv_sum_money);

        if (tv_sum_money != null) {
            tv_sum_money.setText("￥" + total);
        }
    }
}
