package com.tysci.ballq.app;

import android.app.Application;

import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.utils.WeChatUtil;

/**
 * Created by HTT on 2016/5/28.
 */
public class BallQApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigInfo.initAppConfigInfo(this);
        AppExceptionHandler.getInstance().init(this);
        HttpClientUtil.initHttpClientUtil(this, AppConfigInfo.APP_HTTP_CACHE_PATH);
        WeChatUtil.registerWXApi(this);
    }
}
