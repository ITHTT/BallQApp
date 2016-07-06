package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.fragments.UserAccountGoldRecordFragment;
import com.tysci.ballq.fragments.UserAccountTradeRecordFragment;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.adapters.BallQFragmentPagerAdapter;
import com.tysci.ballq.views.widgets.SegmentTabLayout;
import com.tysci.ballq.views.widgets.SlidingTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/24.
 */
public class UserAccountActivity extends BaseActivity{
    @Bind(R.id.layout_segment_tab)
    protected SegmentTabLayout tabLayout;
    @Bind(R.id.view_pager)
    protected ViewPager viewPager;
    @Bind(R.id.tvUserBalance)
    protected TextView tvUserBalance;
    @Bind(R.id.tvUserScore)
    protected TextView tvUserScore;
    @Bind(R.id.tvUserGoldCoin)
    protected TextView tvUserGoldCoin;

    private String[] titles={"交易记录","金币"};

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_account;
    }

    @Override
    protected void initViews() {
        this.setTitle("我的资产");
        tabLayout.setTabData(titles);
        tabLayout.setOnTabSelectListener(new SlidingTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        showLoading();
        getUserAccountInfo();
    }

    @Override
    protected View getLoadingTargetView() {
        return this.findViewById(R.id.layout_account_info);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    private void addFragments(){
       if(viewPager.getAdapter()==null) {
           List<BaseFragment> fragments = new ArrayList<>(2);
           fragments.add(new UserAccountTradeRecordFragment());
           fragments.add(new UserAccountGoldRecordFragment());
           BallQFragmentPagerAdapter adapter=new BallQFragmentPagerAdapter(getSupportFragmentManager(),fragments);
           viewPager.setAdapter(adapter);

       }

    }

    private void getUserAccountInfo(){
        String url= HttpUrls.HOST_URL_V5+"my_account/";
        HashMap<String,String> params=new HashMap<>(2);
        if (UserInfoUtil.checkLogin(this)) {
            params.put("user", UserInfoUtil.getUserId(this));
            params.put("token", UserInfoUtil.getUserToken(this));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {
                showErrorInfo(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoading();
                        getUserAccountInfo();
                    }
                });
            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null&&!obj.isEmpty()){
                        if(obj.getIntValue("status")==0){
                            JSONArray datas=obj.getJSONArray("data");
                            if(datas!=null&&!datas.isEmpty()){
                                JSONObject data=datas.getJSONObject(0);
                                if(data!=null&&!data.isEmpty()){
                                    hideLoad();
                                    tvUserBalance.setText(String.format(Locale.getDefault(),"%.2f",data.getFloat("rmb")/100));
                                    tvUserGoldCoin.setText(String.format(Locale.getDefault(),"%.2f",data.getFloat("gold")/100));
                                    tvUserScore.setText(data.getString("points"));
                                    addFragments();
                                    return;
                                }
                            }
                        }
                    }
                }
                showEmptyInfo();
            }
            @Override
            public void onFinish(Call call) {

            }
        });
    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }

    @OnClick(R.id.tvGetScoreByCompleteTask)
    protected void onTaskPointsRecord(View view){

    }
}
