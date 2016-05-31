package com.tysci.ballq.utils;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

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



}
