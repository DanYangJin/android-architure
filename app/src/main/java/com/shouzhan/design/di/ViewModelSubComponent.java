package com.shouzhan.design.di;

import com.shouzhan.design.ui.home.viewmodel.DraggerViewModel;
import dagger.Subcomponent;

/**
 * @author danbin
 * @version ViewModelSubComponent.java, v 0.1 2019-02-24 下午4:19 danbin
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        /**
         * 构造器
         *
         * @return
         */
        ViewModelSubComponent build();
    }

    /**
     * 首页ViewModel
     *
     * @return HomeViewModel
     */
    DraggerViewModel homeViewModel();

}
