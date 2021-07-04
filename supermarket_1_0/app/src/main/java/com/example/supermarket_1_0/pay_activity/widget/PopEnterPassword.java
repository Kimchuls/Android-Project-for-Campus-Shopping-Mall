package com.example.supermarket_1_0.pay_activity.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.pay_activity.OnPasswordInputFinish;

import java.util.Objects;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;

    public boolean check_flag = false;
    public String standard_pwd = "987654";

    public PopEnterPassword(final Activity context,double sum_money) {

        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);
        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);
        pwdView.changeMoney(String.format("￥%.2f",sum_money));
        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(final String password) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // 模拟耗时的操作。
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mContext.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dismiss();
                                //Toast.makeText(mContext, "支付成功，密码为：" + password, Toast.LENGTH_SHORT).show();
                                if (Objects.equals(password, standard_pwd)){
                                    Toast.makeText(mContext, "支付成功，密码为：" + password, Toast.LENGTH_SHORT).show();
                                    check_flag=true;
                                }
                                else{
                                    Toast.makeText(mContext, "支付失败，密码为：" + password, Toast.LENGTH_SHORT).show();
                                    check_flag=false;
                                }
                            }
                        });
                    }

                }).start();
            }
        });
        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }
}
