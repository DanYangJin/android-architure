package com.shouzhan.design.ui.home;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.fshows.android.stark.utils.FsLogUtil;
import com.google.common.collect.Lists;
import com.shouzhan.design.R;
import com.shouzhan.design.adapter.GridGroupAdapter;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.compontent.recyclerview.FsRecyclerView;
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter;
import com.shouzhan.design.compontent.recyclerview.OnLoadRefreshListener;
import com.shouzhan.design.databinding.ActivityGridBinding;

import java.util.List;

/**
 * @author danbin
 * @version GridActivity.java, v 0.1 2019-10-20 19:59 danbin
 */
public class GridActivity extends BaseActivity<ActivityGridBinding> {

    private FsRecyclerView mFsRecyclerView;
    private GridGroupAdapter mGridAdapter;
    private FsRecyclerViewAdapter mFsRecyclerViewAdapter;

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_grid;
    }

    @Override
    public void initView() {
        super.initView();
        mFsRecyclerView = findViewById(R.id.recycler_view);
        mFsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mGridAdapter = new GridGroupAdapter();
        mFsRecyclerViewAdapter = new FsRecyclerViewAdapter(mGridAdapter);
        mFsRecyclerView.setAdapter(mFsRecyclerViewAdapter);
        mFsRecyclerView.setRefreshEnabled(false);
        mFsRecyclerView.setLoadMoreEnabled(true);
        mFsRecyclerView.setOnLoadRefreshListener(new OnLoadRefreshListener() {
            @Override
            public void onRefresh() {
                FsLogUtil.error("Catch", "onRefresh");
            }

            @Override
            public void onLoadMore() {
                FsLogUtil.error("Catch", "onLoadMore");
                mFsRecyclerView.refreshComplete();
            }
        });
        List<String> dataList = Lists.newArrayList();
        dataList.add("1");
        mGridAdapter.addData(dataList);
    }

    @Override
    public void getData() {

    }

}
