package com.tysci.ballq.utils;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.views.dialogs.LoadingProgressDialog;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/31.
 */
public class WeChatUtil {
    public static final String APP_ID_WECHAT = "wx764a44d6325f4975";
    public static final String APP_SECRET_WECHAT = "35758ebd06710d202acf6270ce1be146";
    public static final String APP_MCH_ID_WECHAT = "1235168302";

    public static IWXAPI wxApi;

    public static void registerWXApi(Context context){
        wxApi= WXAPIFactory.createWXAPI(context,APP_ID_WECHAT, true);
        wxApi.registerApp(APP_ID_WECHAT);
    }

    /**
     * 微信登录
     * @param context
     * @return
     */
    public static boolean weChatLogin(Context context) {
        if(wxApi!=null) {
            boolean isInstalled = wxApi.isWXAppInstalled() && wxApi.isWXAppSupportAPI();
            if (!isInstalled) {
                Toast.makeText(context, "请先安装微信APP",Toast.LENGTH_SHORT);
                return false;
            }
            // send oauth request
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            wxApi.sendReq(req);
            return true;
        }
        return false;
    }

    /**
     * 获取微信Token
     * @param context
     * @param tag
     * @param code
     * @param loadingProgressDialog
     */
    public static void getWeChatToken(Context context,String tag,String code, final LoadingProgressDialog loadingProgressDialog){
        String url= HttpUrls.GET_WECHAT_TOKEN_URL+"?appid="+APP_ID_WECHAT
                +"&secret="+APP_SECRET_WECHAT
                +"&code="+code
                +"&grant_type=authorization_code";
        HttpClientUtil.getHttpClientUtil().sendGetRequest(tag, url, 120 * 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {
                if(loadingProgressDialog!=null) {
                    loadingProgressDialog.dismiss();
                }
            }
            @Override
            public void onSuccess(Call call, String response) {


            }
            @Override
            public void onFinish(Call call) {

            }
        });
    }

    public static void getWeChatUserInfo(Context context,String tag,JSONObject userInfoObj){
        String token= userInfoObj.getString("access_token");
        String openId=userInfoObj.getString("openid");
        String url=HttpUrls.GET_WECHAT_USER_IFNO_URL+"?access_token="+token+"&openid="+openId;
    }



}
