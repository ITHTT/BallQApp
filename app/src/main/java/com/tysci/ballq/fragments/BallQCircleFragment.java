package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;

/**
 * Created by HTT on 2016/5/28.
 */
public class BallQCircleFragment extends BaseFragment{
    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_circle;
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }
}
