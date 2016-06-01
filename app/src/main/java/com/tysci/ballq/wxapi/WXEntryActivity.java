package com.tysci.ballq.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tysci.ballq.R;
import com.tysci.ballq.activitys.LoginActivity;
import com.tysci.ballq.activitys.RegisterActivity;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.WeChatUtil;
import com.tysci.ballq.views.dialogs.LoadingProgressDialog;
import com.tysci.ballq.views.widgets.convenientbanner.ConvenientBanner;
import com.tysci.ballq.views.widgets.convenientbanner.holder.CBViewHolderCreator;
import com.tysci.ballq.views.widgets.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/31.
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final int REQUEST_CODE_LOGIN=0x0001;
    private static final int REQUEST_CODE_REGISTER=0x0002;

    @Bind(R.id.view_pager)
    protected ConvenientBanner convenientBanner;
    private List<Integer> localImages=null;

    private LoadingProgressDialog loadingProgressDialog=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_or_register;
    }

    @Override
    protected void initViews() {
        setTitle("登录/注册");
        getLocalImages();
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages);
    }

    private void getLocalImages(){
        localImages=new ArrayList<>(2);
        localImages.add(R.mipmap.icon_login_or_register_bg_1);
        localImages.add(R.mipmap.icon_login_or_register_bg_2);
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

    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        if(WeChatUtil.wxApi != null) {
            KLog.e("注册回调方法...");
            WeChatUtil.wxApi.handleIntent(intent, this);
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dimssProgressDialog();
    }

    private void showProgressDialog(String msg){
        if(loadingProgressDialog==null){
            loadingProgressDialog=new LoadingProgressDialog(this);
            loadingProgressDialog.setCanceledOnTouchOutside(false);
        }
        loadingProgressDialog.setMessage(msg);
        loadingProgressDialog.show();
    }

    private void dimssProgressDialog(){
        if(loadingProgressDialog!=null&&loadingProgressDialog.isShowing()){
            loadingProgressDialog.dismiss();
        }
    }


    /**
     * 微信登录
     * @param view
     */
    @OnClick(R.id.layout_login_weixin)
    protected void weChatLogin(View view){
        boolean isSuccess=WeChatUtil.weChatLogin(this);
        if(isSuccess){
            showProgressDialog("加载中...");
        }
    }

    /**
     * 手机登录
     * @param view
     */
    @OnClick(R.id.layout_login_phone)
    protected void phoneLogin(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    /**
     * 注册
     * @param view
     */
    @OnClick(R.id.tv_register)
    protected void register(View view){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        KLog.e("错误码:"+baseResp.errCode);
        switch(baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                KLog.e("授权成功");
                SendAuth.Resp resp= (SendAuth.Resp) baseResp;
                getWeChatToken(resp.code);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                KLog.e("微信拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                KLog.e("用户取消授权");
                break;
        }
    }

    /**
     * 获取微信Token
     * @param code
     */
    private void getWeChatToken(String code){
        String url= HttpUrls.GET_WECHAT_TOKEN_URL+"?appid="+WeChatUtil.APP_ID_WECHAT
                +"&secret="+WeChatUtil.APP_SECRET_WECHAT
                +"&code="+code
                +"&grant_type=authorization_code";
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 120 * 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                showProgressDialog("登录中...");
            }

            @Override
            public void onError(Call call, Exception error) {
                dimssProgressDialog();
            }

            @Override
            public void onSuccess(Call call, String response) {


            }

            @Override
            public void onFinish(Call call) {

            }
        });
    }

    /**
     * 获取微信用户信息
     * @param userInfoObj
     */
    protected void getWeChatUserInfo(JSONObject userInfoObj){
        String token= userInfoObj.getString("access_token");
        String openId=userInfoObj.getString("openid");
        String url=HttpUrls.GET_WECHAT_USER_IFNO_URL+"?access_token="+token+"&openid="+openId;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {
                dimssProgressDialog();
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
    protected void onViewClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE_LOGIN){

            }else if(requestCode==REQUEST_CODE_REGISTER){

            }
        }
    }

    public static final class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
