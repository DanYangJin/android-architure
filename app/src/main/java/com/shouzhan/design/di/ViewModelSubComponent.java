package com.shouzhan.design.di;

import com.shouzhan.design.ui.home.viewmodel.DaggerViewModel;
import dagger.Subcomponent;


/**
 * @author danbin
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        /**
         * build
         *
         * @return
         */
        ViewModelSubComponent build();
    }

    /**
     * daggerViewModel
     *
     * @return
     */
    DaggerViewModel daggerViewModel();

}
