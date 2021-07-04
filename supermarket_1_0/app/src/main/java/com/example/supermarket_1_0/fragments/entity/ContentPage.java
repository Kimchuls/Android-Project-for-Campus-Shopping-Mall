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

/**
 * 页面枚举
 */
public enum ContentPage {

    新闻(0),
    教育(1),
    体育(2),
    娱乐(3);

    private final int position;

    ContentPage(int pos) {
        position = pos;
    }

    public static com.example.supermarket_1_0.fragments.entity.ContentPage getPage(int position) {
       return com.example.supermarket_1_0.fragments.entity.ContentPage.values()[position];
    }

    public static int size() {
       return com.example.supermarket_1_0.fragments.entity.ContentPage.values().length;
    }

    public static String[] getPageNames() {
        com.example.supermarket_1_0.fragments.entity.ContentPage[] pages = com.example.supermarket_1_0.fragments.entity.ContentPage.values();
        String[] pageNames = new String[pages.length];
        for (int i = 0; i < pages.length; i++) {
            pageNames[i] = pages[i].name();
        }
        return pageNames;
    }

    public int getPosition() {
        return position;
    }
}
