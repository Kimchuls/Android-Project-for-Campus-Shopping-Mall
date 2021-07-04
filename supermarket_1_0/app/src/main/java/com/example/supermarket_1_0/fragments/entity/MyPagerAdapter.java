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

package com.example.supermarket_1_0.fragments.entity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.example.supermarket_1_0.R;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.annotation.Router;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.common.RandomUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author xuexiang
 * @since 2020/4/21 12:24 AM
 */

public class MyPagerAdapter extends PagerAdapter {
    private View mCurrentView;

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return ContentPage.size();
    }

    private List<View> list ;
    public MyPagerAdapter( List<View> list){
        this.list = list;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        /*ContentPage page = ContentPage.getPage(position);
        View view = initView(position);
        view.setTag(position);
        //(Button)view.getid(R.id.button)
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
        return view;*/
        container.addView(list.get(position));
        return list.get(position);
    }

    /*private View initView(int pos) {
        //View view = getLayoutInflater().inflate(R.layout.fg_page1, null, false);

            t = (TextView) view.findViewById(R.id.textView);
            t.setText(String.format("%d", RandomUtils.getRandom(10, 100)));
            b = (Button) view.findViewById(R.id.button2);
            b.setOnClickListener(this);
        return view;
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getItemPosition(Object object) {
        if ("changed".equals(((View) object).getTag())) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View)object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

}
