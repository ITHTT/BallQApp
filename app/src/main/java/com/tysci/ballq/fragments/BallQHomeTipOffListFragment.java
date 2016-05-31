package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;

/**
 * Created by Administrator on 2016/5/31.
 */
public class BallQHomeTipOffListFragment extends BaseFragment {

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_home_tip_off_list;
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }
}
