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
 * Created by Administrator on 2016/6/8.
 * 阵容
 */
public class BallQMatchLineupDataFragment extends AppSwipeRefreshLoadMoreRecyclerViewFragment{
    private BallQMatchEntity matchEntity;

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        getDatas();
    }

    private void getDatas(){
        Bundle data=getArguments();
        if(data!=null){
            matchEntity=data.getParcelable("match_data");
            if(matchEntity!=null){
                requestDatas(matchEntity.getEid());
            }
        }
    }

    private void requestDatas(int matchId){
       String url= HttpUrls.HOST_URL_V3 + "stats/match/" + matchId + "/lineups/";
        KLog.e("url:"+url);
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 30, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.e(response);
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
