package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.UserInfoUtil;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/20.
 */
public class UserBettingGuessRecordActivity extends BaseActivity{
    private String uid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ballq_user_betting_record;
    }

    @Override
    protected void initViews() {
        setTitle("竞猜记录");
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getIntentData(Intent intent) {
        uid=intent.getStringExtra(Tag);
        if(TextUtils.isEmpty(uid)){
            uid=UserInfoUtil.getUserId(this);
        }
        getUserInfo(uid);
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

    /**
     * 获取用户信息
     * @param uid
     */
    private void getUserInfo(final String uid){
        String url= HttpUrls.HOST_URL_V5 + "user/" + uid + "/profile/";
        HashMap<String,String> params=null;
        if(UserInfoUtil.checkLogin(this)){
            params=new HashMap<>(2);
            params.put("user",UserInfoUtil.getUserId(this));
            params.put("token",UserInfoUtil.getUserToken(this));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag,url,params,new HttpClientUtil.StringResponseCallBack(){

            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {
                showErrorInfo(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoading();
                        getUserInfo(uid);
                    }
                });

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        JSONObject dataObj=obj.getJSONObject("data");
                        if(dataObj!=null){
                            requestUserBettingRecordInfos(1,uid,null,null,null,false);
//                            GlideImageLoader.loadImage(UserTrendStatisticActivity.this, dataObj.getString("pt"), R.mipmap.icon_user_default, ivUserIcon);
//                            UserInfoUtil.setUserHeaderVMark(dataObj.getIntValue("isv"),isV,ivUserIcon);
//                            tvUserName.setText(dataObj.getString("fname"));
//                            tvWinRate.setText("亚盘胜率:"+String.format(Locale.getDefault(),"%.2f",dataObj.getFloat("wins")*100)+"%");
//                            float allProfit=dataObj.getFloat("tearn")/100;
//                            String allProfitInfo="总盈亏:";
//                            if(allProfit>=0){
//                                allProfitInfo=allProfitInfo+"+"+allProfit;
//                            }else{
//                                allProfitInfo=allProfitInfo+allProfit;
//                            }
//                            tvAllProfit.setText(allProfitInfo);
//                            tvProfitRate.setText("投资回报:"+String.format(Locale.getDefault(),"%.2f",dataObj.getFloat("ror"))+"%");
//                            getUserTrendStatisticInfo(uid);
                            return;
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

    private void requestUserBettingRecordInfos(int pages,String uid, String bet, String query, String etype,boolean isLoadMore){
        String url=HttpUrls.HOST_URL_V5 + "user/" + uid + "/bets" + (TextUtils.isEmpty(bet)?"/":"/"+bet) + "/?p=" + pages + (TextUtils.isEmpty(query)?"":"&query=" + query) + (!TextUtils.isEmpty(etype)? "&etype=" + etype : "");
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
}
