package com.example.supermarket_1_0.manage_Activity;

import android.app.Activity;
import android.text.InputType;
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
import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;
import com.example.supermarket_1_0.manage_Activity.entity.addGoods;
import com.example.supermarket_1_0.manage_Activity.entity.addPerson;
import com.example.supermarket_1_0.utils.Utils;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class RecycleViewManageAdapter extends RecyclerView.Adapter<RecycleViewManageAdapter.MyViewHolder> {
    private List<ManageItem> workLists;
    private Activity context;
    private MyViewHolder viewHolder;
    private String[] Category;
    private String type;

    private final String tag = "TAG_RVShoppingAdapter";


    public RecycleViewManageAdapter(List<ManageItem> workLists, String[] Category, String type, Activity context) {
        this.workLists = workLists;
        this.context = context;
        this.Category = Category;
        this.type = type;
        Log.i("create adapter", type);
        Log.i("create adapter", String.valueOf(Category[0]));
    }

    String[] Category_Shop = {"本部学生超市", "南区学生超市", "北区学生超市", "江湾学生超市", "枫林学生超市"};

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //加载子view视图，创建viewholder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_manage_card_view_item, viewGroup, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        //当前商品
        ManageItem product = workLists.get(i);
        viewHolder.tv_category.setText(product.getCategoryName());

        ImageLoader.get().loadImage(viewHolder.iv_imageItem, product.getimageURL());
        viewHolder.tv_id.setText(String.format("[ID:%s]", product.getId()));
        viewHolder.tv_name.setText(String.format("名称:%s", product.getName()));
        if(Objects.equals(type, "person"))
            viewHolder.tv_other_info.setText(product.getOtherInfo());
        else if (Objects.equals(type, "goods"))
            viewHolder.tv_other_info.setText(product.getOtherInfo());
        viewHolder.tv_stat.setText(product.getStat());

        PositionListener positionListener = new PositionListener(i);
        //点击事件监听
        viewHolder.vt_deleteItem.setOnClickListener(positionListener);
        viewHolder.tv_category.setOnClickListener(positionListener);
        viewHolder.iv_imageItem.setOnClickListener(positionListener);
        viewHolder.tv_id.setOnClickListener(positionListener);
        viewHolder.tv_name.setOnClickListener(positionListener);
        viewHolder.tv_other_info.setOnClickListener(positionListener);
        viewHolder.tv_stat.setOnClickListener(positionListener);
    }

    @Override
    public int getItemCount() {
        return workLists.size();
    }

    public int getPosition() {
        return 1;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button vt_deleteItem;
        TextView tv_category, tv_id, tv_name, tv_other_info, tv_stat;
        LinearLayout rl_shopping_product;
        ImageView iv_imageItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_shopping_product = itemView.findViewById(R.id.rl_shopping_product);
            iv_imageItem = itemView.findViewById(R.id.iv_imageItem);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);

            tv_other_info = itemView.findViewById(R.id.tv_other_info);
            tv_stat = itemView.findViewById(R.id.tv_stat);
            vt_deleteItem = itemView.findViewById(R.id.vt_deleteItem);
        }
    }

    class PositionListener implements View.OnClickListener {
        public int position;
        List<String> choiceList;
        Map<String, Integer> chooseList = new HashMap<>();

        public PositionListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ManageItem product = workLists.get(position);
            switch (v.getId()) {
                case R.id.vt_deleteItem:
                    Log.d(tag, "删除信息");
                    new MaterialDialog.Builder(context)
                            .content("确认删除此条目？")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //XToastUtils.toast("点击了确认按钮");
                                    if (Objects.equals(type, "person")) {
                                        new addPerson().delete_item(workLists.get(position).getId());
                                    } else if (Objects.equals(type, "goods")) {
                                        new addGoods().delete_item(workLists.get(position).getId());
                                    }
                                    workLists.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .positiveText(R.string.lab_yes)
                            .negativeText(R.string.lab_no)
                            .show();
                    Log.i("worklist_changed", workLists.toString());
                    break;
                case R.id.tv_category:
                    if(Objects.equals(type, "person")){
                        new MaterialDialog.Builder(context)
                                .title("修改类别")
                                .content("请输入类别：")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT)
                                .input(
                                        "请输入类别名", "", false,
                                        ((dialog, input) -> {
                                            //XToastUtils.toast(input.toString());
                                        }))
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive((dialog, which) -> {
                                    int id = Integer.parseInt(dialog.getInputEditText().getText().toString());
                                    if (id >= 0 && id <= 3) {
                                        viewHolder.tv_category.setText(Category[id]);
                                        workLists.get(position).setCategoryName(Category[id]);
                                        try {
                                            new addPerson().change_to_db(workLists.get(position).getId(), "category", String.valueOf(id));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    notifyDataSetChanged();
                                })
                                .cancelable(false)
                                .show();
                    }
                    else if (Objects.equals(type, "goods")){
                        new MaterialDialog.Builder(context)
                                .title("修改价格")
                                .content("请输入价格：")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT)
                                .input(
                                        "请输入价格", "", false,
                                        ((dialog, input) -> {
                                            //XToastUtils.toast(input.toString());
                                        }))
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive((dialog, which) -> {
                                    Double price = Double.valueOf(dialog.getInputEditText().getText().toString());
                                    String str=Utils.makeformat(product.getStat());
                                    //XToastUtils.toast(String.format(str,price));
                                    viewHolder.tv_stat.setText(String.format(str,price));
                                    workLists.get(position).setStat(String.format(str,price));
                                    try {
                                        new addGoods().change_to_db(product.getId(), "price", String.valueOf(price));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    notifyDataSetChanged();
                                })
                                .cancelable(false)
                                .show();
                    }
                    break;
                case R.id.tv_other_info:
                    if(Objects.equals(type, "person")){
                        new MaterialDialog.Builder(context)
                                .title("修改就职门店")
                                .content("请输入就职门店编号：")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT)
                                .input(
                                        "请输入0-4编号",
                                        "",
                                        false,
                                        ((dialog, input) -> {
                                            //XToastUtils.toast(input.toString());
                                            //ImageLoader.get().loadImage(viewHolder.iv_imageItem, input.toString());
                                        }))
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive((dialog, which) -> {
                                    int id = Integer.parseInt(dialog.getInputEditText().getText().toString());
                                    if (id >= 0 && id <= 4) {
                                        viewHolder.tv_other_info.setText("就职门店为" + Category_Shop[id]);
                                        workLists.get(position).setOtherInfo("就职门店为" + Category_Shop[id]);
                                        try {
                                            new addPerson().change_to_db(workLists.get(position).getId(), "workplace", String.valueOf(id));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        notifyDataSetChanged();
                                    }
                                })
                                .cancelable(false)
                                .show();
                    }
                    else if(Objects.equals(type, "goods")){
                        new MaterialDialog.Builder(context)
                                .title("修改数量")
                                .content("请输入数量：")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT)
                                .input(
                                        "请输入数量",
                                        "",
                                        false,
                                        ((dialog, input) -> {
                                            //XToastUtils.toast(input.toString());
                                        }))
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive((dialog, which) -> {
                                    int num = Integer.parseInt(dialog.getInputEditText().getText().toString());
                                    String str=Utils.reverseformat(product.getStat());
                                    //XToastUtils.toast(str);
                                    viewHolder.tv_stat.setText(String.format(str,num));
                                    workLists.get(position).setStat(String.format(str,num));
                                    try {
                                        new addGoods().change_to_db(product.getId(), "num", String.valueOf(num));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    notifyDataSetChanged();
                                })
                                .cancelable(false)
                                .show();
                    }
                    break;
                default:
                    break;

            }
        }
    }
}
