package com.tysci.ballq.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.modles.UserInfoEntity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.views.dialogs.LoadingProgressDialog;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/31.
 */
public class UserInfoUtil {
    private static final String userFileName="user_info";

    private static final String USER_TOKEN="user_token";
    private static final String USER_ACCOUNT="user_account";
    private static final String USER_ID="usr_id";
    private static final String USER_INFO="user_info";
    private static final String USER_RANK="user_rank";
    private static final String USER_PORTRAIT="user_portrait";
    private static final String USER_WECHAT_TOKEN_INFO="user_wechat_token";
    private static final String USER_WECHAT_USER_INFO="user_wecaht_user_info";

    public static void setUserToken(Context context,String token){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_TOKEN,token);
    }

    public static String getUserToken(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_TOKEN);
    }

    public static void setUserAccount(Context context ,String phone){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_ACCOUNT,phone);
    }

    public static String getUserAccount(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_ACCOUNT);
    }

    public static void setUserId(Context context,String id){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_ID,id);
    }

    public static String getUserId(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_ID);
    }

    public static void setUserInfo(Context context,UserInfoEntity userInfo){
        String info= JSONObject.toJSONString(userInfo);
        if(!TextUtils.isEmpty(info)){
            SharedPreferencesUtil.setStringValue(context,userFileName,USER_INFO,info);
        }
    }

    public static void setUserInfo(Context context,String userInfoStr){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_INFO,userInfoStr);
    }

    public static UserInfoEntity getUserInfo(Context context){
        String userInfo=SharedPreferencesUtil.getStringValue(context, userFileName, USER_INFO);
        if(!TextUtils.isEmpty(userInfo)){
            return JSONObject.parseObject(userInfo, UserInfoEntity.class);
        }
        return null;
    }

    public static void setUserRank(Context context,int rank){
        SharedPreferencesUtil.setIntValue(context,userFileName,USER_RANK,rank);
    }

    public static int getUserRank(Context context){
        return SharedPreferencesUtil.getIntValue(context,userFileName,USER_RANK);
    }

    public static void setUserPortrait(Context context,String portrait){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_PORTRAIT,portrait);
    }

    public static String getUserPortrait(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_PORTRAIT);
    }

        public static void setUserWechatTokenInfo(Context context,String info){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_WECHAT_TOKEN_INFO,info);
    }

    public static void setUserWechatTokenInfo(Context context,JSONObject info){
        if(info!=null) {
            SharedPreferencesUtil.setStringValue(context, userFileName, USER_WECHAT_TOKEN_INFO, info.toJSONString());
        }
    }

    public static JSONObject getUserWechatTokenInfo(Context context){
        String info=SharedPreferencesUtil.getStringValue(context,userFileName,USER_WECHAT_TOKEN_INFO);
        if(!TextUtils.isEmpty(info)){
            return JSONObject.parseObject(info);
        }
        return null;
    }

    public static void setWechatUserInfo(Context context,String info){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_WECHAT_USER_INFO,info);
    }

    public static void setWechatUserInfo(Context context,JSONObject info){
        if(info!=null) {
            SharedPreferencesUtil.setStringValue(context, userFileName, USER_WECHAT_USER_INFO, info.toJSONString());
        }
    }

    public static JSONObject getWechatUserInfo(Context context){
        String info=SharedPreferencesUtil.getStringValue(context,userFileName,USER_WECHAT_USER_INFO);
        if(!TextUtils.isEmpty(info)){
            return JSONObject.parseObject(info);
        }
        return null;
    }

    public static void saveUserInfo(Context context,JSONObject data){
        UserInfoUtil.setUserRank(context,data.getIntValue("rank"));
        UserInfoUtil.setUserAccount(context, data.getString("user"));
        UserInfoUtil.setUserAccount(context, data.getString("nickname"));
        UserInfoUtil.setUserToken(context, data.getString("token"));
        UserInfoUtil.setUserPortrait(context,data.getString("portrait"));
    }

    public static boolean checkLogin(Context context){
        String userId=getUserId(context);
        String token=getUserToken(context);
        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(token)){
            return true;
        }
        return false;
    }

    /**
     * 退出登录，置空缓存的用户数据
     * @param context
     */
    public static void exitLogin(Context context){
        setUserId(context,null);
        setUserAccount(context, null);
        setUserToken(context, null);
        setUserRank(context, -1);
        setUserPortrait(context,null);
        setUserInfo(context,"");
    }

    /**
     * 获取用户信息
     * @param context
     * @param tag
     * @param userId
     * @param loadingProgressDialog
     */
    public static void getUserInfo(final Activity context,String tag,String userId, final LoadingProgressDialog loadingProgressDialog){
        String url= HttpUrls.getUserInfoUrl(userId);
        KLog.e("url:"+url);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(tag,url,null, new HttpClientUtil.StringResponseCallBack() {
                    @Override
                    public void onBefore(Request request) {

                    }
                    @Override
                    public void onError(Call call, Exception error) {
                        KLog.e("请求失败...");
                    }
                    @Override
                    public void onSuccess(Call call, String response) {
                        KLog.json(response);
                        if(!TextUtils.isEmpty(response)){
                            JSONObject responseObj=JSONObject.parseObject(response);
                            if(responseObj!=null&&!responseObj.isEmpty()){
                                String userInfo =responseObj.getString("data");
                                if(!TextUtils.isEmpty(userInfo)){
                                    UserInfoUtil.setUserInfo(context,userInfo);
                                    if(loadingProgressDialog!=null){
                                        context.setResult(Activity.RESULT_OK);
                                        context.finish();

                                        EventBus.getDefault().post("登录成功...");
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish(Call call) {
                        if(loadingProgressDialog!=null){
                            loadingProgressDialog.dismiss();
                        }
                    }
                }
        );
    }
}
