package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.base.AppSwipeRefreshLoadMoreRecyclerViewFragment;
import com.tysci.ballq.modles.BallQBallWarpInfoEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.adapters.BallQBallWarpInfoAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/31.
 */
public class BallQHomeBallWarpListFragment extends AppSwipeRefreshLoadMoreRecyclerViewFragment{
    private List<BallQBallWarpInfoEntity> ballQBallWarpInfoEntityList;
    private BallQBallWarpInfoAdapter adapter;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        requestDatas(currentPages,false);
    }

    private void requestDatas(int pages,final boolean isLoadMore){
        String url= HttpUrls.HOST_URL_V5+"articles/?p=" + pages;
        Map<String,String> params=null;
        if(UserInfoUtil.checkLogin(baseActivity)){
            params=new HashMap<String,String>(2);
            params.put("user",UserInfoUtil.getUserId(baseActivity));
            params.put("token",UserInfoUtil.getUserToken(baseActivity));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                
            }

            @Override
            public void onError(Call call, Exception error) {
                if(recyclerView!=null) {
                    if (!isLoadMore) {
                        recyclerView.setStartLoadMore();
                    } else {
                        recyclerView.setLoadMoreDataFailed();
                    }
                }
            }

            @Override
            public void onSuccess(Call call, String response) {
                if(!isLoadMore){
                    onRefreshCompelete();
                }
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null&&!obj.isEmpty()){
                        JSONArray objArray=obj.getJSONArray("data");
                        if(objArray!=null&&!objArray.isEmpty()){
                            if(ballQBallWarpInfoEntityList==null){
                                ballQBallWarpInfoEntityList=new ArrayList<BallQBallWarpInfoEntity>(10);
                            }
                            if(!isLoadMore&&!ballQBallWarpInfoEntityList.isEmpty()){
                                ballQBallWarpInfoEntityList.clear();
                            }
                            CommonUtils.getJSONListObject(objArray,ballQBallWarpInfoEntityList,BallQBallWarpInfoEntity.class);
                            if(adapter==null){
                                adapter=new BallQBallWarpInfoAdapter(ballQBallWarpInfoEntityList);
                                recyclerView.setAdapter(adapter);
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                            if(objArray.size()<10){
                                recyclerView.setLoadMoreDataComplete("没有更多数据了");
                            }else{
                                recyclerView.setStartLoadMore();
                                if(!isLoadMore){
                                    currentPages=2;
                                }else{
                                    currentPages++;
                                }
                            }
                            return;
                        }
                    }
                }
            }

            @Override
            public void onFinish(Call call) {
                if(!isLoadMore){
                    onRefreshCompelete();
                }
            }
        });

    }

    @Override
    protected void onLoadMoreData() {
        requestDatas(currentPages,true);

    }

    @Override
    protected void onRefreshData() {
        requestDatas(1,false);
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
