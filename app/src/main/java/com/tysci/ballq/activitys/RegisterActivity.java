package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/1.
 */
public class RegisterActivity extends BaseActivity{
    @Bind(R.id.etPhoneNum)
    protected EditText etPhoneNum;
    @Bind(R.id.cbPhoneNumberRight)
    protected CheckBox checkPhoneNum;
    @Bind(R.id.etVCode)
    protected EditText etVCode;
    @Bind(R.id.tvGetVerify)
    protected TextView tvGetVCode;
    @Bind(R.id.etPassword)
    protected EditText etPassword;
    @Bind(R.id.cbShowPassword)
    protected CheckBox showPassword;
    @Bind(R.id.cbAgree)
    protected CheckBox cbAgree;
    @Bind(R.id.tv_commit)
    protected TextView tvCommit;

    private boolean isModifyPassword=false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        setTitle("手机注册");
        checkPhoneNum.setVisibility(View.INVISIBLE);
        tvGetVCode.setEnabled(false);
        showPassword.setChecked(false);
        tvCommit.setEnabled(false);
        setViewListener();
    }

    @Override
    protected void getIntentData(Intent intent) {
        if(intent!=null){
            isModifyPassword=intent.getBooleanExtra(Tag,false);
            if(isModifyPassword){
                setTitle("忘记密码");
            }
        }
    }

    private void setViewListener(){
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String text = s.toString();
                boolean isGood=!TextUtils.isEmpty(text)&&text.length()==11;
                checkPhoneNum.setVisibility(isGood ? View.VISIBLE : View.INVISIBLE);
                tvGetVCode.setEnabled(isGood);
                checkRegister();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etVCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRegister();
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
                checkRegister();
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

    private void checkRegister(){
        String phone=etPhoneNum.getText().toString();
        String vcode=etVCode.getText().toString();
        String password=etPassword.getText().toString();
        boolean isRegister=!TextUtils.isEmpty(phone)&&phone.length()>=11
                           &&!TextUtils.isEmpty(vcode)&&vcode.length()>=6
                           &&!TextUtils.isEmpty(password)&&password.length()>=6;
        tvCommit.setEnabled(isRegister);
    }

    /**
     * 点击获取验证码
     * @param view
     */
    @OnClick(R.id.tvGetVerify)
    protected void onClickGetVCode(View view){

    }

    /**
     * 获取验证码
     */
    private void getVCode(String phone){

    }

    /**
     * 检查手机号
     */
    private void checkPhone(String phone){
        Map<String,String>params=new HashMap<>(2);
        params.put("username",phone);
        params.put("check_type","phone_number");
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.CHECK_USER_PHONE_URL, params, new HttpClientUtil.StringResponseCallBack() {
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

    public class TimeCount extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

        }
    }
}
