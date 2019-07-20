package com.shouzhan.design.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityDaggerBinding;
import com.shouzhan.design.datasource.db.UserRoomDatabase;
import com.shouzhan.design.model.db.User;
import com.shouzhan.design.ui.home.viewmodel.DaggerViewModel;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.List;


/**
 * @author danbin
 * @version DraggerActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public class DaggerActivity extends BaseActivity<ActivityDaggerBinding> {

    private static final String TAG = DaggerActivity.class.getSimpleName();

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private UserRoomDatabase userRoomDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        userRoomDatabase = UserRoomDatabase.getInstance(mContext);
        DaggerViewModel viewModel = viewModelFactory.create(DaggerViewModel.class);
        viewModel.requestDaggerData();
        getData();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger;
    }

    @Override
    public void getData() {
        User user = new User(1, "danyangjin", "123456", "hahahah");
        userRoomDatabase.userDao().insert(user);
        List<User> userList = userRoomDatabase.userDao().getUserList();
        FsLogUtil.error(TAG, userList.toString());
    }

}
