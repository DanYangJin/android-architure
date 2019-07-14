package com.shouzhan.design.ui.home.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.repository.DraggerRepository;

import javax.inject.Inject;

/**
 * @author danbin
 * @version HomeViewModel.java, v 0.1 2019-07-14 08:56 danbin
 */
public class DraggerViewModel extends AndroidViewModel {

    private static final String TAG = DraggerViewModel.class.getSimpleName();

    private DraggerRepository repository;

    @Inject
    public DraggerViewModel(@NonNull DraggerRepository repository, @NonNull Application application) {
        super(application);
        this.repository = repository;
    }

    public void requestHomeData() {
        FsLogUtil.error(TAG, "requestHomeData");
    }

}
