package com.example.supermarket_1_0.pay_activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.pay_activity.widget.PopEnterPassword;
import com.example.supermarket_1_0.utils.DemoDataProvider;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.xui.XUI.getContext;


public class PaymentKeyBoardActivity extends AppCompatActivity {
    String[] position_list;
    int select_shop;
    double sum_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_key_board);
        position_list = getResources().getStringArray(R.array.sort_shop_list);
        Spinner mSpinnerSystem = (Spinner) findViewById(R.id.shop_spinner);
        Intent intent=getIntent();
        sum_money=intent.getDoubleExtra("MONEY",0.0);

        mSpinnerSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //show.setText("选中的颜色是："+color[position]);
                select_shop=position;
                //XToastUtils.toast(position_list[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private PopEnterPassword popEnterPassword;
    public void showPayKeyBoard(View view) {
        popEnterPassword = new PopEnterPassword(this,sum_money);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    public void clickedSubmit(View view) {

        if(popEnterPassword.check_flag){
            /**/Intent intent=getIntent();
            intent.putExtra("PAYCHECK", true);
            intent.putExtra("SHOP",select_shop);
            setResult(RESULT_OK,intent);
            finish();
            //XToastUtils.toast("AAAAAA"+position_list[select_shop]);
        }
        else{
            /**/Intent intent=getIntent();
            intent.putExtra("PAYCHECK", false);
            intent.putExtra("SHOP",select_shop);
            setResult(RESULT_OK,intent);
            finish();
            //XToastUtils.toast("BBBBBB"+position_list[select_shop]);
        }

    }
}
