package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.views.adapters.BallQFragmentPagerAdapter;
import com.tysci.ballq.views.widgets.SegmentTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/8.
 */
public class BallQMatchForecastDataFragment extends BaseFragment{
    @Bind(R.id.tab_layout_match_data)
    protected SegmentTabLayout tabLayout;
    @Bind(R.id.view_pager)
    protected ViewPager viewPager;

    private String[] titles={"亚盘","大小球"};

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_match_forecast_data;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        tabLayout.setTabData(titles);
        Bundle data=getArguments();
        if(data!=null){
            List<BaseFragment> fragments=new ArrayList<>(2);
            BaseFragment baseFragment=new MatchForecastDataFragment();
            BallQFragmentPagerAdapter adapter=null;





        }


    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
