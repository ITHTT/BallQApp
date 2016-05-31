package com.tysci.ballq.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/31.
 */
public abstract class AppSwipeRefreshLoadMoreRecyclerViewFragment extends BaseFragment implements AutoLoadMoreRecyclerView.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;

    private String refreshTip="刷新数据中...";

    protected int currentPages=1;

    @Override
    protected int getViewLayoutId() {
        return R.layout.layout_swiperefresh_recyclerview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setOnLoadMoreListener(this);
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
                swipeRefresh.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        if(!swipeRefresh.isRefreshing()){
            onLoadMoreData();
        }else{
            recyclerView.setLoadMoreDataComplete(refreshTip);
        }
    }

    @Override
    public void onRefresh() {
        if(!recyclerView.isLoadMoreing()){
            onRefreshData();
        }else{
            onRefreshCompelete();
        }
    }

    protected abstract void onLoadMoreData();

    protected abstract void onRefreshData();


}
