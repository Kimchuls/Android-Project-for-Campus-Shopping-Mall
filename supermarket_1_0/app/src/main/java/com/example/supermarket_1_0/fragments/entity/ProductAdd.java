package com.example.supermarket_1_0.fragments.entity;

import android.util.Log;

import com.example.supermarket_1_0.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xuexiang.xui.utils.ResUtils.getString;

public class ProductAdd {
    private JSONObject cartInfoJson=new JSONObject();
    private List<String[]> shoppingRecord = new ArrayList<>();
    public boolean flag;
    public static boolean putCartFlag = true, deleteCartFlag = true, cleanCartFlag = true;

    public ProductAdd() {
    }

    public List<String[]> getShoppingRecord(){return shoppingRecord;}

    public boolean flagTrue() {
        return putCartFlag & deleteCartFlag & cleanCartFlag;
    }

    public JSONObject getCartInfoJson() {
        return cartInfoJson;
    }

    public void setCartInfoJson(JSONObject cartInfoJson) {
        this.cartInfoJson = cartInfoJson;
    }

    public int getNumber(String key) throws JSONException {
        return cartInfoJson.getInt(key);
    }

    public void setNumber(String goodsid, int num) throws JSONException {
        cartInfoJson.put(goodsid, num);
    }

    public static void putCart(String goodsid, String userid, String name, int num, String type) {
        putCartFlag = false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("goodsid", goodsid)
                .add("userid", userid)
                .add("name", name)
                .add("number", String.valueOf(num))
                .add("type", type)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/add_cart")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                putCartFlag = true;
            }
        });
    }

    public static void deleteCart(String goodsid, String userid) {
        deleteCartFlag = false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("goodsid", goodsid)
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/delete_cart")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                deleteCartFlag = true;
            }
        });
    }

    public static void cleanCart(String userid) {
        cleanCartFlag = false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/clean_cart")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                cleanCartFlag = true;
            }
        });
    }

    public void makedata1(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        cartInfoJson = new JSONObject();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            JSONObject value = new JSONObject(String.valueOf(jsonObject.get(key)));
            cartInfoJson.put(key, (String) value.get("number"));
        }
        Log.i("ProductAdd make_Cart_List", String.valueOf(cartInfoJson));
        flag = true;
    }

    public void getCart(String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/cart")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    makedata1(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*******************************************************************************************************************************************/

    public void makedata2(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        shoppingRecord = new ArrayList<>();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();//时间
            JSONObject value = new JSONObject(String.valueOf(jsonObject.get(key)));//当次购物记录

            Iterator<String> that = value.keys();
            while(that.hasNext()){
                String that_key=that.next();//商品id
                JSONObject that_value = new JSONObject(String.valueOf(value.get(that_key)));//商品名称和购买数量
                String[] newString = {key,that_key,(String) that_value.get("number")};
                Log.i("ProductAdd make_Record_List_4", String.valueOf(newString));
                shoppingRecord.add(newString);
            }
        }
        Log.i("ProductAdd make_Record_List", String.valueOf(shoppingRecord));
        flag = true;
    }

    public void getRecord(String userid) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/record")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                try {
                    makedata2(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteRecord(String time,String goodsid, String userid) {
        deleteCartFlag = false;
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("time",time)
                .add("goodsid", goodsid)
                .add("userid", userid)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.mysql_url) + "custom/delete_record")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure in ManageItemforPerson", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                deleteCartFlag = true;
            }
        });
    }
}
