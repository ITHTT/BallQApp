package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.modles.BallQMatchEntity;

/**
 * Created by Administrator on 2016/6/10.
 */
public class MatchForecastDataFragment extends BaseFragment{
    private BallQMatchEntity matchEntity;
    private int oddsType;

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_match_forecast_data;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    public void setMatchEntity(BallQMatchEntity matchEntity) {
        this.matchEntity = matchEntity;
    }

    public void setOddsType(int type){
        this.oddsType=type;
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
