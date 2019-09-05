/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shouzhan.design.base;

/**
 * @author danbin
 * @version BaseView.java, v 0.1 2019-01-23 上午5:26 danbin
 */
public interface IBasePresenter {

    /**
     * 注册监听
     */
    void initObserver();
    /**
     * 初始化
     */
    void initUI();
    /**
     * 销毁
     */
    void onDestroy();
    /**
     * 是否可以更新UI
     */
    boolean canUpdateUi();
}
