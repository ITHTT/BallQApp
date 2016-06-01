package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.dialogs.LoadingProgressDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/1.
 */
public class LoginActivity extends BaseActivity{
    @Bind(R.id.etPhoneNum)
    protected EditText etPhoneNum;
    @Bind(R.id.etPassword)
    protected EditText etPassword;
    @Bind(R.id.cbPhoneNumberRight)
    protected CheckBox checkPhoneNum;
    @Bind(R.id.cbShowPassword)
    protected CheckBox showPassword;
    @Bind(R.id.tv_login)
    protected TextView tvLogin;

    protected LoadingProgressDialog loadingProgressDialog=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        setTitle("手机登录");
        etPhoneNum.requestFocus();
        checkPhoneNum.setVisibility(View.INVISIBLE);
        showPassword.setChecked(false);
        tvLogin.setEnabled(false);
        setViewListener();
    }

    private void setViewListener(){
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String text = s.toString();
                checkPhoneNum.setVisibility(text.length() == 11 ? View.VISIBLE : View.INVISIBLE);
                checkLogin();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkLogin();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etPassword.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
                etPassword.setSelection(etPassword.getText().toString().length());
            }
        });
    }

    private void checkLogin(){
        String phone=etPhoneNum.getText().toString();
        String password=etPassword.getText().toString();
        boolean isCanLogin=!TextUtils.isEmpty(phone)&&phone.length()>=11&&!TextUtils.isEmpty(password)&&password.length()>=6;
        tvLogin.setEnabled(isCanLogin);
    }

    @Override
    protected void getIntentData(Intent intent) {

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

    /**
     * 缓存用户信息
     * @param data
     */
    private void cacheUserInfo(JSONObject data){
        UserInfoUtil.setUserRank(this,data.getIntValue("rank"));
        UserInfoUtil.setUserAccount(this, data.getString("user"));
        UserInfoUtil.setUserAccount(this, data.getString("nickname"));
        UserInfoUtil.setUserToken(this, data.getString("token"));
        UserInfoUtil.setUserPortrait(this,data.getString("portrait"));
    }

    @OnClick(R.id.bt_login)
    protected void login(View view){
        String phone=etPhoneNum.getText().toString();
        String password=etPassword.getText().toString();
        if(TextUtils.isEmpty(phone)){
            return;
        }
        if(TextUtils.isEmpty(password)){
            return;
        }
        Map<String,String>params=new HashMap<>(2);
        params.put("username",phone);
        params.put("password",password);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.USER_PHONE_LOGIN_URL, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                if(loadingProgressDialog==null){
                    loadingProgressDialog=new LoadingProgressDialog(LoginActivity.this);
                    loadingProgressDialog.setMessage("登录中...");
                    loadingProgressDialog.setCanceledOnTouchOutside(false);
                }
                loadingProgressDialog.show();
            }
            @Override
            public void onError(Call call, Exception error) {
                KLog.e("请求失败");
                loadingProgressDialog.dismiss();
            }
            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject responseObj=JSONObject.parseObject(response);
                    if(responseObj!=null&&!responseObj.isEmpty()){
                        JSONObject data=responseObj.getJSONObject("data");
                        if(data!=null&&!data.isEmpty()){
                            cacheUserInfo(data);
                            String userId=data.getString("user");
                            UserInfoUtil.getUserInfo(LoginActivity.this,Tag,userId,loadingProgressDialog);
                        }
                    }
                }
            }
            @Override
            public void onFinish(Call call) {

            }
        });
    }

    @Override
    protected void onViewClick(View view) {

    }
}
