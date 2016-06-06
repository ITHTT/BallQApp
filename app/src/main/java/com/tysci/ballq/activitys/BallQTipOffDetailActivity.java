package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import butterknife.Bind;

/**
 * Created by HTT on 2016/6/6.
 */
public class BallQTipOffDetailActivity extends BaseActivity {
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ballq_tip_off_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
