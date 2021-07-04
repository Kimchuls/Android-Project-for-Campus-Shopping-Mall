package com.example.supermarket_1_0.login_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.supermarket_1_0.MainActivity;
import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.manage_Activity.ManageActivity;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.example.supermarket_1_0.work_activity.WorkActivity;
import com.google.gson.Gson;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewPassword extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnVertify;
    private EditText PhoneNumber, EmailAccont, VertifyCode, FirstPassword, SecondPassword;
    private TextView btnUser, btnPrivacy;

    private long verificationCode = 0; //生成的验证码
    private String email; //邮箱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        PhoneNumber = (EditText) findViewById(R.id.phone_number);
        EmailAccont = (EditText) findViewById(R.id.email_account);
        VertifyCode = (EditText) findViewById(R.id.verify_code);
        FirstPassword = (EditText) findViewById(R.id.fst_password_code);
        SecondPassword = (EditText) findViewById(R.id.sec_password_code);

        PhoneNumber.setText("18912345678");
        EmailAccont.setText("jincz2000@126.com");
        //VertifyCode.setText("123456");
        FirstPassword.setText("654321");
        SecondPassword.setText("654321");
        //verificationCode=123456;

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnVertify = (Button) findViewById(R.id.btn_get_verify_code);
        btnUser = (TextView) findViewById(R.id.tv_user_protocol);
        btnPrivacy = (TextView) findViewById(R.id.tv_privacy_protocol);
        btnLogin.setOnClickListener(this);
        btnVertify.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        btnPrivacy.setOnClickListener(this);
        btnVertify.setText("获取验证码");
    }
    private int count=60;
    public int getCount() {
        count--;
        if (count == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return count;
    }
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verify_code:
                email=EmailAccont.getText().toString();
                sendVerificationCode(email); //发送验证码
                XToastUtils.info("验证码已发送");
                btnVertify.setEnabled(false);
                new CountDownTimer(60000, 1000) {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btnVertify.setEnabled(false);
                        btnVertify.setText(String.format("%ds后重新发送验证码",millisUntilFinished/1000));
                    }

                    @Override
                    public void onFinish() {
                        btnVertify.setEnabled(true);
                        btnVertify.setText("发送验证码");
                    }
                }.start();
                break;
            case R.id.btn_login:
                submit();
                break;
            case R.id.tv_user_protocol:
                //XToastUtils.info("用户协议");
                new MaterialDialog.Builder(this)
                        .iconRes(R.drawable.icon_tip)
                        .title("用户协议")
                        .content(R.string.content_privacy_explain_again)
                        .positiveText(R.string.lab_privacy_name)
                        .show();
                break;
            case R.id.tv_privacy_protocol:
                //XToastUtils.info("隐私政策");
                new MaterialDialog.Builder(this)
                        .iconRes(R.drawable.icon_tip)
                        .title("隐私政策")
                        .content(R.string.content_privacy_explain_again)
                        .positiveText(R.string.lab_privacy_name)
                        .show();
                break;
            default:
                break;
        }
    }


    private void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        RandomNumber rn = new RandomNumber();
                        verificationCode = rn.getRandomNumber(6);
                        SendEmail se = new SendEmail(email);
                        se.sendHtmlEmail(verificationCode);//发送html邮件
                        Toast.makeText(NewPassword.this,"发送成功",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        // validate
        String phonenum = PhoneNumber.getText().toString().trim();
        String emailstr = EmailAccont.getText().toString().trim();
        String code = VertifyCode.getText().toString();
        String firstpwd = FirstPassword.getText().toString();
        String secondpwd  = SecondPassword.getText().toString();
        Log.i(firstpwd,secondpwd);
        if(Integer.parseInt(code)!=verificationCode){ //验证码和输入一致
            XToastUtils.error("验证失败"+code+Long.toString(verificationCode));
            return;
        }
        if (!Objects.equals(firstpwd, secondpwd)){
            XToastUtils.error("两次输入密码不同");
            return;
        }
        //Toast.makeText(LoginActivity.this, "hello!" + tvUserNameString + tvPasswordString, Toast.LENGTH_LONG).show();
        // TODO validate success, do something
        InternetSever(phonenum, emailstr,firstpwd.trim());
    }

    private void InternetSever(String phonenum, String email, String password) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", phonenum)
                .add("email",email)
                .add("password", password)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url)+"add_user/")
                .post(body)
                .build();
        Toast.makeText(NewPassword.this, request.toString(), Toast.LENGTH_LONG).show();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("frost_connection", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String date = response.body().string();
                Log.e("onresponse", date);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Login_cs login_cs = gson.fromJson(date, Login_cs.class);
                        if (login_cs.get_$StatusCode185() == 200) {
                            Toast.makeText(NewPassword.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(NewPassword.this, MainActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else if (login_cs.get_$StatusCode185() == 204) {
                            Toast.makeText(NewPassword.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(NewPassword.this, WorkActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else if (login_cs.get_$StatusCode185() == 208) {
                            Toast.makeText(NewPassword.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(NewPassword.this, ManageActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else{
                            Toast.makeText(NewPassword.this, login_cs.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}