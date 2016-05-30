package com.tysci.ballq.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tysci.ballq.R;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.views.widgets.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * Created by HTT on 2016/5/28.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected final String Tag=this.getClass().getSimpleName();
    protected TitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        titleBar= (TitleBar) this.findViewById(R.id.title_bar);
        setTitleBarLeftIcon(R.mipmap.icon_back_gold);
        if(!isCanceledEventBus()){
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
        initViews();
        if(getIntent()!=null){
            getIntentData(this.getIntent());
        }
        handleInstanceState(savedInstanceState);
    }

    protected void setTitle(String title){
        if(titleBar!=null){
            titleBar.setTitleBarTitle(title);
        }
    }

    protected void setTitleBarLeftIcon(int res){
        if(titleBar!=null){
            titleBar.setTitleBarLeftIcon(res,this);
        }
    }

    /**获取界面布局文件的ID*/
    protected abstract int getContentViewId();
    /**初始化控件*/
    protected abstract void initViews();
    /**获取Intent中的数据*/
    protected abstract void getIntentData(Intent intent);
    /**是否取消EventBus*/
    protected abstract boolean isCanceledEventBus();
    /**保存异常时的数据*/
    protected abstract void saveInstanceState(Bundle outState);
    /**处理异常时的情况*/
    protected abstract void handleInstanceState(Bundle outState);
    /**控件点击事件*/
    protected abstract void onViewClick(View view);

    protected void back(){
        this.finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_titlebar_left){
            back();
        }
        onViewClick(v);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(!isCanceledEventBus()){
            EventBus.getDefault().unregister(this);
        }
        /**取消网络请求*/
        HttpClientUtil.getHttpClientUtil().cancelTag(Tag);
    }
}
