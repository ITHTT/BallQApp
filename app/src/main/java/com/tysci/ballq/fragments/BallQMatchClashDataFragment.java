package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.base.AppSwipeRefreshLoadMoreRecyclerViewFragment;

/**
 * Created by Administrator on 2016/6/8.
 * 对阵
 */
public class BallQMatchClashDataFragment extends AppSwipeRefreshLoadMoreRecyclerViewFragment{
    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
