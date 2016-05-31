package com.tysci.ballq.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.modles.UserInfoEntity;

/**
 * Created by Administrator on 2016/5/31.
 */
public class UserInfoUtil {
    private static final String userFileName="user_info";

    private static final String USER_TOKEN="user_token";
    private static final String USER_ACCOUNT="user_account";
    private static final String USER_ID="usr_id";
    private static final String USER_INFO="user_info";

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

    public static UserInfoEntity getUserInfo(Context context){
        String userInfo=SharedPreferencesUtil.getStringValue(context, userFileName, USER_INFO);
        if(!TextUtils.isEmpty(userInfo)){
            return JSONObject.parseObject(userInfo, UserInfoEntity.class);
        }
        return null;
    }


}
