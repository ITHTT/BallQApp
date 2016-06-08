package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.base.AppSwipeRefreshLoadMoreRecyclerViewFragment;
import com.tysci.ballq.modles.BallQMatchEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BallQMatchTipOffListFragment extends AppSwipeRefreshLoadMoreRecyclerViewFragment{
    private BallQMatchEntity ballQMatchEntity=null;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    private void requestDatas(int matchId,int etype,int pages,boolean isLoadMore){
        String url= HttpUrls.HOST_URL_V5 + "match/" + matchId + "/tips/?etype=" + etype + "&p=" + pages;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 30, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);

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
