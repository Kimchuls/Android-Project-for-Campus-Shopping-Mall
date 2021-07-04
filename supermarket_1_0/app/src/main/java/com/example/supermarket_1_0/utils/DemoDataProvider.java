/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.supermarket_1_0.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.adapter.entity.NewInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 演示数据
 *
 */
public class DemoDataProvider {

    /*public boolean goodsInfo = false;
    public List<JsonObject> goodsObject;

    public JsonObject parcels(int pos) {
        if (goodsInfo == true)
            return goodsObject.get(pos);
        List<JsonObject> goodsObject = new ArrayList<>();
        goodsInfo=true;

        int length = 5;
        for (int i = 0; i < length; i++) {
            JsonObject item = new JsonObject();
            item.addProperty();
            goodsObject.add(item);
        }
        return goodsObject.get(pos);

    };*/

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    @MemoryCache
    public static List<BannerItem> getBannerList() {
        List<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];

            list.add(item);
        }
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    public static String adv_logo_url = "https://Kimchuls.github.io/Newinfo/image/logo.png";
    @MemoryCache
    public static List<NewInfo> getDemoNewInfos(int times) {
        List<NewInfo> list = new ArrayList<>();
        for (int i=0;i<=times;i++){
            list.add(new NewInfo("进口零食", "汤姆农场坚果零食10袋")
                    .setSummary("汤姆农场蜂蜜黄油扁桃仁等零食10袋\n颠覆原味坚果概念，促销价 ¥ 77.90")
                    .setDetailUrl("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.7.5b6e2373yB2fbf&id=553417223433&skuId=4307207316181&areaId=310100&user_id=3299560040&cat_id=2&is_b=1&rn=cdeb4cde3708552c4514152e14d17726")
                    .setImageUrl("https://Kimchuls.github.io/Newinfo/image/newspic_1_nut.png"));
        }


        list.add(new NewInfo("蛋糕", "巧克力蛋糕")
                .setSummary("21cake黑白巧克力慕斯生日蛋糕切块\n 黑白巧克力慕斯蛋糕, 价格 ¥298.00")
                .setDetailUrl("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.6.d007fd0bfEvEwr&id=38455975786&skuId=3858440471331&areaId=310100&user_id=2058864678&cat_id=2&is_b=1&rn=430ec39392e681df98fef7a6783cfc2d")
                .setImageUrl("https://Kimchuls.github.io/Newinfo/image/newspic_2_cake.png"));

        list.add(new NewInfo("红酒", "拉菲罗斯柴尔德红酒")
                .setSummary("拉菲罗斯柴尔德红酒进口6支装\n" +
                        "ASC三重防伪可查，狂欢价 ¥ 599.00")
                .setDetailUrl("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.6.51b824d5eRr8Y5&id=520985736485&skuId=4436548069919&areaId=310100&user_id=509138323&cat_id=2&is_b=1&rn=c1cec0586231f1c2df31ebd76c931e91")
                .setImageUrl("https://Kimchuls.github.io/Newinfo/image/newspic_3_wine.png"));

        list.add(new NewInfo("茶叶", "西湖龙井茶叶礼盒")
                .setSummary("2020新茶西湖龙井茶叶礼盒装250g\n" +
                        "陶瓷罐礼盒装, 狂欢价 ¥ 688.00")
                .setDetailUrl("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.16.2f63364acFIEoy&id=537638423700&areaId=310100&user_id=2386840837&cat_id=2&is_b=1&rn=cd09eaeed52b0db21a2f0de02fe2e156")
                .setImageUrl("https:///Kimchuls.github.io/Newinfo/image/newspic_4_tea.png"));

        list.add(new NewInfo("酸奶", "安慕希希腊风味酸牛奶")
                .setSummary("安慕希酸牛奶原味205g*16盒\n" +
                        "常温发酵酸牛奶整箱批发, 狂欢价 ¥49.80")
                .setDetailUrl("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.36.47a17cdcVCpQYe&id=608240393788&skuId=4518703112459&areaId=310100&user_id=1799996758&cat_id=2&is_b=1&rn=d2d1ae428f62a73848ea5f19886ef9a3")
                .setImageUrl("https://kimchuls.github.io/Newinfo/image/newspic_5_yogurt.png"));
        return list;
    }

    public static List<AdapterItem> getGridItems(Context context) {
        return getGridItems(context, R.array.grid_titles_entry, R.array.grid_icons_entry);
    }


    private static List<AdapterItem> getGridItems(Context context, int titleArrayId, int iconArrayId) {
        List<AdapterItem> list = new ArrayList<>();
        String[] titles = ResUtils.getStringArray(titleArrayId);
        Drawable[] icons = ResUtils.getDrawableArray(context, iconArrayId);
        for (int i = 0; i < titles.length; i++) {
            list.add(new AdapterItem(titles[i], icons[i]));
        }
        return list;
    }

    /**
     * 获取时间段
     *
     * @param interval 时间间隔（分钟）
     * @return
     */
    public static String[] getTimePeriod(int interval) {
        return getTimePeriod(24, interval);
    }

    /**
     * 获取时间段
     *
     * @param interval 时间间隔（分钟）
     * @return
     */
    public static String[] getTimePeriod(int totalHour, int interval) {
        String[] time = new String[totalHour * 60 / interval];
        int point, hour, min;
        for (int i = 0; i < time.length; i++) {
            point = i * interval;
            hour = point / 60;
            min = point - hour * 60;
            time[i] = (hour < 9 ? "0" + hour : "" + hour) + ":" + (min < 9 ? "0" + min : "" + min);
        }
        return time;
    }

}
