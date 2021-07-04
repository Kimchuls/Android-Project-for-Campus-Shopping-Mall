package com.example.supermarket_1_0.login_activity;
import com.google.gson.annotations.SerializedName;

public class Login_cs {
    /**
     * username : admin
     * adminname : 李东阳
     * code : 成功
     * Status Code : 200
     */
    private String nickname;
    private String userid;
    private String code;
    @SerializedName("Status Code")
    private int _$StatusCode185; // FIXME check this code

    public String getuserid() {
        return userid;
    }
    public void setuserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String adminname) {
        this.nickname = nickname;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public int get_$StatusCode185() {
        return _$StatusCode185;
    }
    public void set_$StatusCode185(int _$StatusCode185) {
        this._$StatusCode185 = _$StatusCode185;
    }
}
