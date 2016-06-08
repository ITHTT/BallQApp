package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;

/**
 * Created by Administrator on 2016/6/8.
 * 阵容
 */
public class BallQMatchLineupDataFragment extends BaseFragment{
    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_match_lineup_data;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
