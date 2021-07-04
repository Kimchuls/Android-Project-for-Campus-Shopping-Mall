package com.example.supermarket_1_0.login_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Page(anim = CoreAnim.none)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tvUserName;
    private EditText tvPassword;
    private Button btnLogin;
    private TextView btnPassword;
    private TextView btnOther;
    private TextView btnUser;
    private TextView btnPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tvUserName = (EditText) findViewById(R.id.et_phone_number);
        tvPassword = (EditText) findViewById(R.id.et_password_code);
        /*tvUserName.setText("012345678901");
        tvPassword.setText("123456789");*/

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnPassword = (TextView) findViewById(R.id.tv_forget_password);
        btnPassword.setOnClickListener(this);
        btnOther = (TextView) findViewById(R.id.tv_other_login);
        btnOther.setOnClickListener(this);
        btnUser = (TextView) findViewById(R.id.tv_user_protocol);
        btnPrivacy = (TextView) findViewById(R.id.tv_privacy_protocol);
        btnUser.setOnClickListener(this);
        btnPrivacy.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //XToastUtils.info("login");
                Log.i("info", "Login");
                submit();
                break;
            case R.id.tv_other_login:
                //XToastUtils.info("otherlogin");
                Log.i("info", "otherLogin");
                startActivity(new Intent(LoginActivity.this, EmailLoginActivity.class));
                break;
            case R.id.tv_forget_password:
                //XToastUtils.info("pwd");
                Log.i("info", "pwd");
                startActivity(new Intent(LoginActivity.this, NewPassword.class));
                break;
            case R.id.tv_user_protocol:
                //XToastUtils.info("用户协议");
                new MaterialDialog.Builder(LoginActivity.this)
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

    private void submit() {
        // validate
        String tvUserNameString = tvUserName.getText().toString().trim();
        if (TextUtils.isEmpty(tvUserNameString)) {
            Toast.makeText(this, "请输入手机号或ID", Toast.LENGTH_SHORT).show();
            return;
        }
        String tvPasswordString = tvPassword.getText().toString().trim();
        if (TextUtils.isEmpty(tvPasswordString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(LoginActivity.this, "hello!" + tvUserNameString + tvPasswordString, Toast.LENGTH_LONG).show();
        // TODO validate success, do something
        InternetSever(tvUserNameString, tvPasswordString);
    }

    private void InternetSever(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", username)
                .add("password", password)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url)+"login/")
                .post(body)
                .build();
        //Toast.makeText(LoginActivity.this, request.toString(), Toast.LENGTH_LONG).show();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in LoginActivity", e.getMessage());
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
                            Toast.makeText(LoginActivity.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else if (login_cs.get_$StatusCode185() == 204) {
                            Toast.makeText(LoginActivity.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, WorkActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else if (login_cs.get_$StatusCode185() == 208) {
                            Toast.makeText(LoginActivity.this, "欢迎登录，" + login_cs.getNickname(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, ManageActivity.class);
                            intent.putExtra("nickname",login_cs.getNickname());
                            intent.putExtra("userid",login_cs.getuserid());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, login_cs.getCode(), Toast.LENGTH_SHORT).show();
                            tvPassword.setText("");
                            tvUserName.setText("");
                        }
                    }
                });

            }
        });
    }
}