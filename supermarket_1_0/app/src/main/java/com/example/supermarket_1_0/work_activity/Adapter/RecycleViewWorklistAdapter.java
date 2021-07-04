package com.example.supermarket_1_0.work_activity.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItemData;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.example.supermarket_1_0.work_activity.entity.AddItem;
import com.example.supermarket_1_0.work_activity.entity.AddItemData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.xuexiang.xpage.core.CoreConfig.getContext;


public class RecycleViewWorklistAdapter extends RecyclerView.Adapter<RecycleViewWorklistAdapter.MyViewHolder> {
    private List<AddItem> workLists;
    private Activity context;
    private String username;
    private MyViewHolder viewHolder;

    private final String tag = "TAG_RVShoppingAdapter";


    public RecycleViewWorklistAdapter(List<AddItem> workLists, String username, Activity context) {
        this.workLists = workLists;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //加载子view视图，创建viewholder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_work_list_item, viewGroup, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        //当前商品
        AddItem product = workLists.get(i);
        viewHolder.tv_user_name.setText(String.format("%s [ID: %s]", username, product.getWorkerID()));
        viewHolder.tv_worktitle.setText(String.format("任务：%s", product.getTaskName()));
        switch (product.getTaskCode()){
            case 0:
                viewHolder.tv_workdetail.setText(String.format("任务序号：%s\t\t\t提示时间%s\n请尽快申请进货", product.getId(), product.getStartTime()));
                break;
            case 1:
                viewHolder.tv_workdetail.setText(String.format("购买单号：%s\t\t\t开始时间%s\n请在30分钟内完成打包", product.getId(), product.getStartTime()));
                break;
            default:
                break;
        }

        List<String> choiceList = null;
        try {
            choiceList = product.getTaskDetailEncode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("choiseList",choiceList.toString());

        PositionListener positionListener = new PositionListener(i, choiceList);
        //点击事件监听
        viewHolder.bt_cancel_task.setOnClickListener(positionListener);
        viewHolder.bt_show_detail.setOnClickListener(positionListener);
        viewHolder.bt_finish_package.setOnClickListener(positionListener);
    }

    @Override
    public int getItemCount() {
        return workLists.size();
    }

    public int getPosition() {
        return 1;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button bt_cancel_task, bt_show_detail, bt_finish_package;
        TextView tv_user_name, tv_worktitle, tv_workdetail;
        LinearLayout rl_shopping_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_shopping_product = itemView.findViewById(R.id.rl_shopping_product);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_worktitle = itemView.findViewById(R.id.tv_worktitle);
            tv_workdetail = itemView.findViewById(R.id.tv_workdetail);
            bt_cancel_task = itemView.findViewById(R.id.bt_cancel_task);
            bt_show_detail = itemView.findViewById(R.id.bt_show_detail);
            bt_finish_package = itemView.findViewById(R.id.bt_finish_package);
        }
    }

    class PositionListener implements View.OnClickListener {
        public int position;
        List<String> choiceList;
        Map<String, Integer> chooseList = new HashMap<>();

        public PositionListener(int position, List<String> choiceList) {
            this.position = position;
            this.choiceList = choiceList;
            for (String str : choiceList) {
                chooseList.put(str, 0);
            }
        }

        @Override
        public void onClick(View v) {
            AddItem product = workLists.get(position);

            switch (v.getId()) {
                case R.id.bt_cancel_task:
                    Log.d(tag, "取消任务点击");
                    new MaterialDialog.Builder(context)
                            .content("确认取消此任务？")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //XToastUtils.toast("点击了确认按钮");
                                    workLists.remove(position);
                                    AddItemData.CancelTask(product.getId(),product.getWorkerID());
                                    notifyDataSetChanged();
                                }
                            })
                            .positiveText(R.string.lab_yes)
                            .negativeText(R.string.lab_no)
                            .show();
                    Log.i("worklist_changed",workLists.toString());
                    break;
                case R.id.bt_show_detail:
                    Log.d(tag, "显示细节");
                    new MaterialDialog.Builder(context)
                            .title("任务清单")
                            .items(choiceList)
                            .itemsCallbackMultiChoice(
                                    new Integer[]{},
                                    (dialog, which, text) -> {
                                        for (String str : choiceList) {
                                            chooseList.put(str, 0);
                                        }
                                        StringBuilder sb = new StringBuilder("选中：\n");
                                        Log.i("textarrlen", String.valueOf(which.length));
                                        for (int i = 0; i < which.length; i++) {

                                            chooseList.put((String) text[i],1);
                                            sb.append(which[i]).append(":").append(text[i]).append("\n");
                                        }
                                        //XToastUtils.toast(sb.toString());
                                        return true;
                                    })
                            .positiveText("选择")
                            .negativeText("取消")
                            .show();
                    //notifyDataSetChanged();
                    break;
                case R.id.bt_finish_package:
                    //XToastUtils.toast("点击了确认按钮");
                    Log.d(tag, "完成任务点击");
                    new MaterialDialog.Builder(context)
                            .content("确认完成此任务？\n未选择的选项将视为缺货，并通知顾客")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //XToastUtils.toast("点击了完成按钮");
                                    workLists.remove(position);
                                    AddItemData.FinishTask(product.getId(),chooseList);
                                    notifyDataSetChanged();
                                }
                            })
                            .positiveText(R.string.lab_yes)
                            .negativeText(R.string.lab_no)
                            .show();

                    break;
                default:
                    break;
            }
        }
    }


}
