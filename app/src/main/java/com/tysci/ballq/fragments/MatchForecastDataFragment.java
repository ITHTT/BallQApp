package com.tysci.ballq.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.modles.BallQMatchEntity;
import com.tysci.ballq.modles.BallQMatchForecastDataEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.views.adapters.BallQMatchForecastDataAdapter;
import com.tysci.ballq.views.widgets.chartview.PieChartData;
import com.tysci.ballq.views.widgets.chartview.PieChartView;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/10.
 */
public class MatchForecastDataFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,AutoLoadMoreRecyclerView.OnLoadMoreListener{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;

    private View headerView=null;

    private BallQMatchEntity matchEntity;
    private int oddsType;
    private int currentPages=1;

    private List<BallQMatchForecastDataEntity.MatchForecastDataEntity> matchForecastDataEntityList=null;
    private BallQMatchForecastDataAdapter adapter=null;

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_match_forecast_data;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(baseActivity));
        recyclerView.setOnLoadMoreListener(this);
        headerView= LayoutInflater.from(baseActivity).inflate(R.layout.layout_match_forecast_data_header,null);
        recyclerView.addHeaderView(headerView);
        requestDatas(currentPages, false);
    }

    public void setMatchEntity(BallQMatchEntity matchEntity) {
        this.matchEntity = matchEntity;
    }

    public void setOddsType(int type){
        this.oddsType=type;
    }

    private void requestDatas(int pages, final boolean isLoadMore){
        String url="http://apib.ballq.cn/bigdata/oroc/v1/"+matchEntity.getEid()+"/"+oddsType
                   +"/?limit=10&p="+pages;
        KLog.e("url:" + url);
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        BallQMatchForecastDataEntity entity=obj.getObject("data",BallQMatchForecastDataEntity.class);
                        if(entity!=null){
                            if(!isLoadMore){
                                setPieChartViewDatas(entity);
                                if(matchForecastDataEntityList==null) {
                                    matchForecastDataEntityList = new ArrayList<BallQMatchForecastDataEntity.MatchForecastDataEntity>(10);
                                    adapter=new BallQMatchForecastDataAdapter(matchForecastDataEntityList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }

                            List<BallQMatchForecastDataEntity.MatchForecastDataEntity> matchs=entity.getMatchs();
                            if(matchs!=null&&!matchs.isEmpty()){
                                if(!isLoadMore&&!matchForecastDataEntityList.isEmpty()){
                                    matchForecastDataEntityList.clear();
                                }
                                matchForecastDataEntityList.addAll(matchs);
                                adapter.notifyDataSetChanged();
                                if(matchs.size()<10){
                                    recyclerView.setLoadMoreDataComplete("没有更多数据了");
                                }else{
                                    recyclerView.setStartLoadMore();
                                    if(isLoadMore){
                                        currentPages++;
                                    }else{
                                        currentPages=2;
                                    }
                                }
                            }else{
                                if(matchForecastDataEntityList.isEmpty()){
                                    recyclerView.setLoadMoreDataComplete();
                                    headerView.findViewById(R.id.layout_forecast_data).setVisibility(View.GONE);
                                }else{
                                    recyclerView.setLoadMoreDataComplete("没有更多数据了");
                                    headerView.findViewById(R.id.layout_forecast_data).setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFinish(Call call) {

            }
        });
    }

    private void setPieChartViewDatas(BallQMatchForecastDataEntity data){
        List<PieChartData> pieChartDataList=new ArrayList<>(3);
        if(data.getRate1()+data.getRate2()+data.getRate3()==0){
            PieChartData pie01=new PieChartData();
            pie01.setValue(1/3);
            pie01.setColor(Color.parseColor("#ca574b"));
            pieChartDataList.add(pie01);

            PieChartData pie02=new PieChartData();
            pie02.setValue(1/3);
            pie02.setColor(Color.parseColor("#c3c3c3"));
            pieChartDataList.add(pie02);

            PieChartData pie03=new PieChartData();
            pie03.setValue(1/3);
            pie03.setColor(Color.parseColor("#66b249"));
            pieChartDataList.add(pie03);

        }else{
            PieChartData pie01=new PieChartData();
            pie01.setValue(data.getRate1());
            pie01.setColor(Color.parseColor("#ca574b"));
            pieChartDataList.add(pie01);

            PieChartData pie02=new PieChartData();
            pie02.setValue(data.getRate2());
            pie02.setColor(Color.parseColor("#c3c3c3"));
            pieChartDataList.add(pie02);

            PieChartData pie03=new PieChartData();
            pie03.setValue(data.getRate3());
            pie03.setColor(Color.parseColor("#66b249"));
            pieChartDataList.add(pie03);
        }

        PieChartView pieChartView= (PieChartView) headerView.findViewById(R.id.piechartview);
        pieChartView.setPieChartDataList(pieChartDataList);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }
}
