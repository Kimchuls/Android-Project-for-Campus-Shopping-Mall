/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.supermarket_1_0.fragments.entity;

/**
 * @author xuexiang
 * @since 2018/12/26 下午11:49
 */
public enum MultiPage {

    零食(0),
    饮料(1),
    日用品(2),
    其他(3);

    private final int position;

    MultiPage(int pos) {
        position = pos;
    }

    public static MultiPage getPage(int position) {
        return MultiPage.values()[position];
    }

    public static int size() {
        return com.example.supermarket_1_0.fragments.entity.MultiPage.values().length;
    }

    public static String[] getPageNames() {
        com.example.supermarket_1_0.fragments.entity.MultiPage[] pages = com.example.supermarket_1_0.fragments.entity.MultiPage.values();
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
