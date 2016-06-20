package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.views.adapters.UserRewardMoneyAdapter;
import com.tysci.ballq.views.widgets.CircleImageView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/20.
 */
public class UserRewardActivity extends BaseActivity{
    @Bind(R.id.ivUserIcon)
    protected CircleImageView ivUserIcon;
    @Bind(R.id.isV)
    protected ImageView isV;
    @Bind(R.id.gridView)
    protected GridView gvRewards;

    private List<String> rewardMoneys;
    private UserRewardMoneyAdapter adapter=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_reward;
    }

    @Override
    protected void initViews() {
        setTitle("用户打赏");
        rewardMoneys=Arrays.asList(this.getResources().getStringArray(R.array.reward_moneys));
        adapter=new UserRewardMoneyAdapter(rewardMoneys);
        gvRewards.setAdapter(adapter);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
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
    protected void onViewClick(View view) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
