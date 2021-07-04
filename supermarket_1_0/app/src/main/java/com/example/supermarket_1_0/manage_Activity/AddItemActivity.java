package com.example.supermarket_1_0.manage_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.login_activity.RandomNumber;
import com.example.supermarket_1_0.manage_Activity.entity.addGoods;
import com.example.supermarket_1_0.manage_Activity.entity.addPerson;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.validation.RegexpValidator;

import org.json.JSONException;

import java.util.Objects;

public class AddItemActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_worklist;
    TextView tv_additem_1, tv_additem_2, tv_additem_3, tv_additem_4, tv_additem_5, tv_additem_6;
    MaterialEditText et_studentid, et_name, et_email, et_pwd, et_category, et_imageurl;
    Button bt_check_studentid, bt_check_name, bt_check_email, bt_check_pwd, bt_check_category;
    Button bt_submit_item;
    String intentType;
    Intent intent;

    String[] Category_Goods = {"零食", "饮料", "日用品", "其他"};
    String[] Category_Person = {"收银员", "理货员", "打包员", "理和打"};
    String[] Category_Shop = {"本部学生超市", "南区学生超市", "北区学生超市", "江湾学生超市", "枫林学生超市"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additemlist);
        tv_worklist = findViewById(R.id.tv_worklist);

        tv_additem_1 = findViewById(R.id.tv_additem_1);
        tv_additem_2 = findViewById(R.id.tv_additem_2);
        tv_additem_3 = findViewById(R.id.tv_additem_3);
        tv_additem_4 = findViewById(R.id.tv_additem_4);
        tv_additem_5 = findViewById(R.id.tv_additem_5);
        tv_additem_6 = findViewById(R.id.tv_additem_6);

        et_studentid = findViewById(R.id.et_studentid);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        et_category = findViewById(R.id.et_category);
        et_imageurl = findViewById(R.id.et_imageurl);

        et_studentid.setText("100005");
        et_imageurl.setText("https://Kimchuls.github.io/Newinfo/image/logo.png");
        et_name.setText("金辰哲");
        et_email.setText("0");
        et_pwd.setText("1.5&6000");
        et_category.setText("1");

        bt_check_studentid = findViewById(R.id.bt_check_studentid);
        bt_check_name = findViewById(R.id.bt_check_name);
        bt_check_email = findViewById(R.id.bt_check_email);
        bt_check_pwd = findViewById(R.id.bt_check_pwd);
        bt_check_category = findViewById(R.id.bt_check_category);

        bt_submit_item = findViewById(R.id.bt_submit_item);

        intent = getIntent();
        intentType = intent.getStringExtra("TYPE");

        switch (intentType) {
            case "person":
                tv_additem_1.setText("请输入11位学号：");
                tv_additem_2.setText("请输入姓名：");
                tv_additem_3.setText("请输入就职门店：");
                tv_additem_4.setText("请点击生成随机密码：");
                tv_additem_5.setText("请输入类别：");
                tv_additem_6.setText("请输入图片地址：");
                bt_check_studentid.setText("手动校验");
                bt_check_studentid.setOnClickListener(this);
                bt_check_name.setText("手动校验");
                bt_check_name.setOnClickListener(this);
                bt_check_email.setText("手动校验");
                bt_check_email.setOnClickListener(this);
                bt_check_pwd.setText("手动校验");
                bt_check_pwd.setOnClickListener(this);
                bt_check_category.setText("手动校验");
                bt_check_category.setOnClickListener(this);
                bt_submit_item.setOnClickListener(this);

                et_studentid.addValidator(new RegexpValidator("只能输入0+11位学号!", "0[0-9]{11}"));
                et_name.addValidator(new RegexpValidator("只能2-4位名字!", "[\\u4e00-\\u9fa5]+"));
                et_email.addValidator(new RegexpValidator("只能选择0-4号门店", "[0-4]"));
                et_pwd.addValidator(new RegexpValidator("只能输入数字、字母和下划线", "[a-zA-Z0-9_]+"));
                et_category.addValidator(new RegexpValidator("只能输入四类工作人员0-3", "[0-3]"));
                break;
            case "goods":
                tv_additem_1.setText("请输入商品编码：");
                tv_additem_2.setText("请输入商品名称：");
                tv_additem_3.setText("请输入商品简介：");
                tv_additem_4.setText("请输入商品数目&价格：");
                tv_additem_5.setText("请输入商品类别：");
                tv_additem_6.setText("请输入图片地址：");
                bt_check_studentid.setText("手动校验");
                bt_check_studentid.setOnClickListener(this);
                bt_check_name.setText("手动校验");
                bt_check_name.setOnClickListener(this);
                bt_check_email.setText("手动校验");
                bt_check_email.setOnClickListener(this);
                bt_check_pwd.setText("手动校验");
                bt_check_pwd.setOnClickListener(this);
                bt_check_category.setText("手动校验");
                bt_check_category.setOnClickListener(this);
                bt_submit_item.setOnClickListener(this);

                et_studentid.addValidator(new RegexpValidator("只能输入数字!", "\\d+"));
                et_name.addValidator(new RegexpValidator("只能1-17位名字!", "[\\u4e00-\\u9fa5]{1,17}"));
                et_email.addValidator(new RegexpValidator("只能1-35位简介!", "[\\u4e00-\\u9fa5]{1,35}"));
                et_pwd.addValidator(new RegexpValidator("只能输入数字&数字!", "(\\d+)&(\\d+)"));
                et_category.addValidator(new RegexpValidator("只能输入0-3!", "[0-3]"));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_check_studentid:
                //XToastUtils.toast(intentType+Objects.equals(intentType, "person"));
                if (Objects.equals(intentType, "person")) {
                    //XToastUtils.toast(et_studentid.getText());
                    et_pwd.setText(String.valueOf(new RandomNumber().getRandomNumber(6)));
                    et_category.setText("0");
                }
                et_studentid.validate();
                break;
            case R.id.bt_check_name:
                et_name.validate();
                break;
            case R.id.bt_check_email:
                et_email.validate();
                break;
            case R.id.bt_check_pwd:
                et_pwd.validate();
                break;
            case R.id.bt_check_category:
                et_category.validate();
                break;
            case R.id.bt_submit_item:
                //XToastUtils.toast("person add?");
                if (Objects.equals(intentType, "person")) {
                    //XToastUtils.toast("person add?");
                    addPerson addperson = new addPerson(String.valueOf(et_studentid.getText()), String.valueOf(et_name.getText()),
                            String.valueOf(et_email.getText()), String.valueOf(et_pwd.getText()),
                            Integer.parseInt(String.valueOf(et_category.getText())), String.valueOf(et_imageurl.getText()));
                    try {
                        addperson.send_to_db();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("additemac", "makeintent");
                    intent.putExtra("CategoryID", Integer.parseInt(String.valueOf(et_category.getText())));
                    //intent.putExtra("CategoryName",Category_Person[Integer.parseInt(String.valueOf(et_category.getText()))]);
                    intent.putExtra("imageURL", String.valueOf(et_imageurl.getText()));
                    intent.putExtra("id", String.valueOf(et_studentid.getText()));
                    intent.putExtra("Name", String.valueOf(et_name.getText()));
                    intent.putExtra("OtherInfo", "就职门店为" + Category_Shop[Integer.parseInt(String.valueOf(et_email.getText()))]);
                    intent.putExtra("Stat", "他还未打过卡");
                    setResult(RESULT_OK, intent);
                    finish();

                }
                else if (Objects.equals(intentType, "goods")) {
                    String[] str = String.valueOf(et_pwd.getText()).split("&");
                    Double price = Double.valueOf(str[0]);
                    int num = Integer.parseInt(str[1]);
                    addGoods addgoods = new addGoods(String.valueOf(et_studentid.getText()), String.valueOf(et_name.getText()),
                            String.valueOf(et_email.getText()), Integer.parseInt(String.valueOf(et_category.getText())),
                            price, num, String.valueOf(et_imageurl.getText()));
                    try {
                        addgoods.send_to_db();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("additemac", "makeintent");
                    intent.putExtra("CategoryID", Integer.parseInt(String.valueOf(et_category.getText())));
                    intent.putExtra("imageURL", String.valueOf(et_imageurl.getText()));
                    intent.putExtra("id", String.valueOf(et_studentid.getText()));
                    intent.putExtra("Name", String.valueOf(et_name.getText()));
                    intent.putExtra("OtherInfo", String.valueOf(et_email.getText()));
                    intent.putExtra("Stat", String.format("价格为%.2f,剩余货物为%d件",price,num));
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
            default:
                break;
        }

    }
}
