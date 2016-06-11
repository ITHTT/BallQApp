package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.modles.BallQMatchEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/10.
 */
public class MatchForecastDataFragment extends BaseFragment{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;

    private BallQMatchEntity matchEntity;
    private int oddsType;
    private int currentPages=1;

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

    private void requestDatas(){
        String url="http://apib.ballq.cn/bigdata/oroc/v1/"+matchEntity.getEid()+"/"+oddsType
                   +"/?limit=10&p="+currentPages;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {

            }

            @Override
            public void onSuccess(Call call, String response) {

            }

            @Override
            public void onFinish(Call call) {

            }
        });
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
