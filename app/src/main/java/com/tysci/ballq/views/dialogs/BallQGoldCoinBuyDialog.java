package com.tysci.ballq.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.tysci.ballq.R;
import com.tysci.ballq.activitys.UserAccountActivity;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.RandomUtils;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.widgets.LoadDataLayout;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/7/6.
 */
public class BallQGoldCoinBuyDialog extends Dialog {
    private RecyclerView recyclerView;
    private LoadDataLayout loadDataLayout;
    private View layoutContent;


    public BallQGoldCoinBuyDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        initViews(context);
    }

    private void initViews(Context context){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_gold_coin_buy);

        recyclerView= (RecyclerView) this.findViewById(R.id.recyclerView);
        GridLayoutManager lm = new GridLayoutManager(getContext(), 2);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        loadDataLayout=(LoadDataLayout)this.findViewById(R.id.layout_loading_data);
        layoutContent=this.findViewById(R.id.layout_content);

        getGoldCoinBuyInfo();
    }

    private void getGoldCoinBuyInfo(){
        String url= HttpUrls.HOST_URL_V5 + "exchange_list/";
        Map<String,String> params=new HashMap<>(3);
        params.put("user", UserInfoUtil.getUserId(this.getContext()));
        params.put("token", UserInfoUtil.getUserToken(this.getContext()));
        params.put("exchange_type","1");
        HttpClientUtil.getHttpClientUtil().sendPostRequest(UserAccountActivity.class.getSimpleName(), url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                loadDataLayout.showLoading();
                layoutContent.setVisibility(View.GONE);
            }

            @Override
            public void onError(Call call, Exception error) {
                loadDataLayout.setLoadError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataLayout.showLoading();
                        getGoldCoinBuyInfo();
                    }
                });
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

    private void buyGoldCoin(int id){
        String url=HttpUrls.HOST_URL_V5+"user/cny_to_balance/";
        Map<String,String> params=new HashMap<>(3);
        params.put("user", UserInfoUtil.getUserId(this.getContext()));
        params.put("token", UserInfoUtil.getUserToken(this.getContext()));
        params.put("eid",String.valueOf(id));
        params.put("repeat_token", RandomUtils.getOnlyOneByTimeMillis(32));
        HttpClientUtil.getHttpClientUtil().sendPostRequest(UserAccountActivity.class.getSimpleName(), url, params, new HttpClientUtil.StringResponseCallBack() {
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
