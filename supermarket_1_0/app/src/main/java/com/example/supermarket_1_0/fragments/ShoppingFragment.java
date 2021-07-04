/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.example.supermarket_1_0.fragments;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.supermarket_1_0.MainActivity;
import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.adapter.base.delegate.SimpleDelegateAdapter;
import com.example.supermarket_1_0.core.BaseFragment;
import com.example.supermarket_1_0.fragments.entity.CategoryList;
import com.example.supermarket_1_0.fragments.entity.MultiPage;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItem;
import com.example.supermarket_1_0.manage_Activity.entity.ManageItemData;
import com.example.supermarket_1_0.utils.DemoDataProvider;
import com.example.supermarket_1_0.utils.Utils;
import com.example.supermarket_1_0.utils.UtilsForNet;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;

/**
 *
 */
@Page(anim = CoreAnim.none)
public class ShoppingFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SimpleDelegateAdapter<ManageItem> mNewsAdapter;

    ManageItemData manageItemData;
    List<ManageItem> products = new ArrayList<>();
    CategoryList categoryList;
    String userid;


    //返回为 null意为不需要导航栏
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    //布局的资源id
    protected int getLayoutId() {
        return R.layout.fragment_shopping;
    }

    private void initcategory(){
        manageItemData = new ManageItemData("goods");
        Utils.sleeptime(1000);
        products = manageItemData.getList();
        categoryList = new CategoryList(products);
    }
    //初始化控件
    @Override
    protected void initViews() {

        initcategory();
        userid = ((MainActivity) Objects.requireNonNull(getActivity())).getPhonenum();


        for (String page : MultiPage.getPageNames()) {
            mTabLayout.addTab(mTabLayout.newTab().setText(page));
        }
        mTabLayout.setTabMode(MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(this);

        int pos = 0;
        mTabLayout.setScrollPosition(0, 0, false);
        WidgetUtils.setTabLayoutTextFont(mTabLayout);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        //资讯
        mNewsAdapter = new SimpleDelegateAdapter<ManageItem>(R.layout.adapter_shopping_card_list_item, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, ManageItem model) {
                if (model != null) {
                    holder.text(R.id.tv_user_name, model.getName());
                    holder.text(R.id.tv_tag, model.getCategoryName());
                    holder.text(R.id.tv_summary, model.getOtherInfo() + "\n" + model.getOtherInfo());
                    holder.text(R.id.tv_stat, model.getStat());
                    holder.image(R.id.iv_image, model.getimageURL());
                    holder.image(R.id.iv_avatar, DemoDataProvider.adv_logo_url);
                    //holder.click(R.id.card_view, v -> UtilsForNet.goWeb(getContext(), model.getDetailUrl()));
                    Log.i("aaaa",userid+"\n"+model.toString());
                    holder.click(R.id.card_view, v -> UtilsForNet.goProduct(getContext(), userid, model.toString()));
                }
            }
        };

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(mNewsAdapter);
        recyclerView.setAdapter(delegateAdapter);
        mNewsAdapter.refresh(categoryList.getListManageItem(pos));
    }


    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
                initcategory();
                //int pos = mNewsAdapter.getSelectPosition();
                //Log.i("position", String.valueOf(pos));
                mNewsAdapter.refresh(categoryList.getListManageItem(mTabLayout.getSelectedTabPosition()));
                mNewsAdapter.setSelectPosition(0);
                refreshLayout.finishRefresh();
            }, 1000);
        });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.i("change", String.format("%d", tab.getPosition()));
        initcategory();
        mNewsAdapter.refresh(categoryList.getListManageItem(tab.getPosition()));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
