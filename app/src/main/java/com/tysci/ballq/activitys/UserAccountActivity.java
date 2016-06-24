package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.views.widgets.SegmentTabLayout;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/24.
 */
public class UserAccountActivity extends BaseActivity{
    @Bind(R.id.layout_segment_tab)
    protected SegmentTabLayout tabLayout;
    @Bind(R.id.view_pager)
    protected ViewPager viewPager;
    private String[] titles={"交易记录","金币"};

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_account;
    }

    @Override
    protected void initViews() {
        this.setTitle("我的资产");
        tabLayout.setTabData(titles);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
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
