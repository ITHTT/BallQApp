package com.tysci.ballq.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tysci.ballq.networks.HttpClientUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by HTT on 2016/5/28.
 */
public abstract class BaseFragment extends Fragment{
    protected final String Tag=this.getClass().getSimpleName();
    protected BaseActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!isCancledEventBus()){
            EventBus.getDefault().register(this);
        }
        View view=inflater.inflate(getViewLayoutId(),container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view,savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseActivity= (BaseActivity) context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(String message){

    }

    /**获取布局ID*/
    protected abstract int getViewLayoutId();
    /**初始化控件*/
    protected abstract void initViews(View view,Bundle savedInstanceState);
    /**是否取消EventBus*/
    protected abstract boolean isCancledEventBus();

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpClientUtil.getHttpClientUtil().cancelTag(Tag);
        ButterKnife.unbind(this);
        if(!isCancledEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }
}
