package com.tysci.ballq.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.modles.BallQBannerImageEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.views.widgets.BallQAuthorAnalystsView;
import com.tysci.ballq.views.widgets.BannerNetworkImageView;
import com.tysci.ballq.views.widgets.TitleBar;
import com.tysci.ballq.views.widgets.convenientbanner.ConvenientBanner;
import com.tysci.ballq.views.widgets.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/7/7.
 */
public class BallQHomePageFragment extends BaseFragment{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.convenientBanner)
    protected ConvenientBanner banner;
    @Bind(R.id.author01)
    protected BallQAuthorAnalystsView authorAnalystsView01;
    @Bind(R.id.author02)
    protected BallQAuthorAnalystsView authorAnalystsView02;
    @Bind(R.id.tv_tip_info)
    protected TextView tvTipInfo;
    @Bind(R.id.tv_game_info)
    protected TextView tvGameInfo;
    @Bind(R.id.tv_ballq_go)
    protected TextView tvBallQGo;
    @Bind(R.id.tv_ballq_circle)
    protected TextView tvBallQCircle;

    private List<BallQBannerImageEntity> bannerImageEntityList=null;


    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_ballq_home_page;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        setTitle();
        banner.setBackgroundColor(Color.parseColor("#f6f6f6"));
        banner.getViewPager().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (swipeRefresh != null)
                            swipeRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (swipeRefresh != null) {
                            swipeRefresh.setEnabled(true);
                        }
                }
                return false;
            }
        });
        getHomePageInfo();

    }

    private void setTitle(){
        TitleBar titleBar=baseActivity.getTitleBar();
        if(titleBar!=null) {
            titleBar.resetTitle();
            titleBar.setTitleBarTitle("首页");
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return swipeRefresh;
    }

    @Override
    protected boolean isCancledEventBus() {
        return false;
    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }

    private void getHomePageInfo(){
        String url= HttpUrls.HOST_URL_V5+"home/";
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
                    if(obj!=null&&!obj.isEmpty()&&obj.getIntValue("status")==0){
                        JSONObject dataObj=obj.getJSONObject("data");
                        if(dataObj!=null&&!dataObj.isEmpty()){
                            JSONArray picArray=dataObj.getJSONArray("pics");
                            if(picArray!=null&&!picArray.isEmpty()){
                                setBannerPictures(picArray);
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

    private void setBannerPictures(JSONArray picArray){
        if(bannerImageEntityList==null||bannerImageEntityList.isEmpty()){
            if(bannerImageEntityList==null){
                bannerImageEntityList=new ArrayList<>(picArray.size());
            }
            CommonUtils.getJSONListObject(picArray,bannerImageEntityList,BallQBannerImageEntity.class);
            banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new BannerNetworkImageView();
                }
            }, bannerImageEntityList)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可不设
                    .setPageIndicator(new int[]{R.drawable.guide_gray, R.drawable.guide_red});
            banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.setPointViewVisible(true);
            banner.setManualPageable(true);
        }else{
            bannerImageEntityList.clear();
            CommonUtils.getJSONListObject(picArray,bannerImageEntityList,BallQBannerImageEntity.class);
            banner.notifyDataSetChanged();
        }
    }
}
