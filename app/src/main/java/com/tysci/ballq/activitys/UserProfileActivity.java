package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.UserInfoUtil;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/6/16.
 */
public class UserProfileActivity extends BaseActivity{
    private int uid;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_ballq_user_profile;
    }

    @Override
    protected void initViews() {
        setTitle("用户资料");

    }

    @Override
    protected View getLoadingTargetView() {
        return this.findViewById(R.id.layout_user_info);
    }

    @Override
    protected void getIntentData(Intent intent) {
        uid=intent.getIntExtra(Tag,-1);
        if(uid!=-1){
            getUserInfo(uid);
        }
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

    private void getUserInfo(int userId){
        String url= HttpUrls.HOST_URL_V5 + "user/" + userId + "/profile/";
        HashMap<String,String> params=null;
        if(UserInfoUtil.checkLogin(this)){
            params=new HashMap<>(2);
            params.put("user",UserInfoUtil.getUserId(this));
            params.put("token",UserInfoUtil.getUserToken(this));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
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
}
