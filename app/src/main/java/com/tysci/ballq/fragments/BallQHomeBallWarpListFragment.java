package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.base.AppSwipeRefreshLoadMoreRecyclerViewFragment;

/**
 * Created by Administrator on 2016/5/31.
 */
public class BallQHomeBallWarpListFragment extends AppSwipeRefreshLoadMoreRecyclerViewFragment{
    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }
}
