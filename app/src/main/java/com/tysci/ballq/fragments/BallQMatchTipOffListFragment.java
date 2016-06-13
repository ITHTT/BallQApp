package com.tysci.ballq.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.modles.BallQMatchEntity;
import com.tysci.ballq.modles.BallQTipOffEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.views.adapters.BallQMatchTipOffAdapter;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BallQMatchTipOffListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,AutoLoadMoreRecyclerView.OnLoadMoreListener{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;

    private BallQMatchEntity ballQMatchEntity=null;
    private int currentPages=1;
    private List<BallQTipOffEntity>matchTipOffList=null;
    private BallQMatchTipOffAdapter adapter=null;

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_match_tip_off_list;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(baseActivity));
        recyclerView.setOnLoadMoreListener(this);
        Bundle data=getArguments();
        if(data!=null){
            ballQMatchEntity=data.getParcelable("match_data");
            setRefreshing();
            requestDatas(ballQMatchEntity.getEid(),ballQMatchEntity.getEtype(),currentPages,false);
        }
    }

    private void setRefreshing(){
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    private void onRefreshCompelete(){
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeRefresh != null)
                    swipeRefresh.setRefreshing(false);
            }
        }, 1000);
    }

    private void requestDatas(int matchId,int etype,int pages, final boolean isLoadMore){
        String url= HttpUrls.HOST_URL_V5 + "match/" + matchId + "/tips/?etype=" + etype + "&p=" + pages;
        KLog.e("url:"+url);
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 30, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {
                if (!isLoadMore) {
                    recyclerView.setStartLoadMore();
                } else {
                    recyclerView.setLoadMoreDataFailed();
                }
            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if (!TextUtils.isEmpty(response)) {
                    JSONObject obj = JSONObject.parseObject(response);
                    if (obj != null) {
                        JSONArray arrays = obj.getJSONArray("data");
                        if (arrays != null && !arrays.isEmpty()) {
                            if (matchTipOffList == null) {
                                matchTipOffList = new ArrayList<BallQTipOffEntity>(10);
                            }
                            if (!isLoadMore && !matchTipOffList.isEmpty()) {
                                matchTipOffList.clear();
                            }
                            CommonUtils.getJSONListObject(arrays, matchTipOffList, BallQTipOffEntity.class);
                            if (adapter == null) {
                                adapter = new BallQMatchTipOffAdapter(matchTipOffList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                            if (arrays.size() < 10) {
                                recyclerView.setLoadMoreDataComplete("没有更多数据了...");
                            } else {
                                recyclerView.setStartLoadMore();
                                if (isLoadMore) {
                                    currentPages++;
                                } else {
                                    currentPages = 2;
                                }
                                return;
                            }
                        }
                    }
                }

            }

            @Override
            public void onFinish(Call call) {
                if (!isLoadMore) {
                    onRefreshCompelete();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        if(recyclerView.isLoadMoreing()){
            onRefreshCompelete();
        }else{
            requestDatas(ballQMatchEntity.getEid(),ballQMatchEntity.getEtype(),currentPages,false);
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }

}
