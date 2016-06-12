package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.modles.BallQTipOffEntity;
import com.tysci.ballq.modles.BallQUserCommentEntity;
import com.tysci.ballq.networks.GlideImageLoader;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.MatchBettingInfoUtil;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.adapters.BallQUserCommentAdapter;
import com.tysci.ballq.views.widgets.CircleImageView;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/6/6.
 */
public class BallQTipOffDetailActivity extends BaseActivity {
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;
    @Bind(R.id.editText)
    protected EditText etComment;
    @Bind(R.id.ivLike)
    protected ImageView ivLike;
    @Bind(R.id.btnPublish)
    protected Button btPublish;

    private View headerView;
    private BallQTipOffEntity tipOffInfo=null;
    private int currentPages=1;

    private List<BallQUserCommentEntity> userCommentEntityList;
    private BallQUserCommentAdapter userCommentAdapter=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ballq_tip_off_detail;
    }

    @Override
    protected void initViews() {
        setTitle("爆料详情");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        titleBar.setRightMenuIcon(R.mipmap.icon_share_gold, this);
        headerView= LayoutInflater.from(this).inflate(R.layout.layout_ballq_tip_off_header,null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.addHeaderView(headerView);
    }

    @Override
    protected void getIntentData(Intent intent) {
        tipOffInfo=intent.getParcelableExtra(Tag);
        if(tipOffInfo!=null){
            KLog.e("加载数据...");
            getTipOffInfo(tipOffInfo.getEid(), tipOffInfo.getId());
        }

    }

    /**
     * 获取爆料信息
     * @param matchId
     * @param tipId
     */
    private void getTipOffInfo(int matchId,int tipId){
        String url= HttpUrls.HOST_URL_V5 + "match/" + matchId + "/tip/" + tipId + "/";
        KLog.e("Url:"+url);
        Map<String,String>params=null;
        if(UserInfoUtil.checkLogin(this)){
            params=new HashMap<>(2);
            params.put("user", UserInfoUtil.getUserId(this));
            params.put("token", UserInfoUtil.getUserToken(this));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {
                KLog.e("加载失败...");

            }
            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                       BallQTipOffEntity data= obj.getObject("data", BallQTipOffEntity.class);
                        if(data!=null){
                            tipOffInfo=data;
                            initBallQTipOffInfo(headerView,data);
                            getTipOffBounties(tipOffInfo.getId());
                            requestTipOffComments(1, tipOffInfo.getId(), false);
                        }
                    }
                }
            }
            @Override
            public void onFinish(Call call) {

            }
        });
    }

    private void initBallQTipOffInfo(View view,BallQTipOffEntity data){
        CircleImageView ivUserIcon= (CircleImageView) view.findViewById(R.id.ivUserIcon);
        ImageView isV=(ImageView)view.findViewById(R.id.isV);
        TextView tvUserName=(TextView)view.findViewById(R.id.tv_user_name);
        ImageView ivAchievement01=(ImageView)view.findViewById(R.id.iv_user_achievement01);
        ImageView ivAchievement02=(ImageView)view.findViewById(R.id.iv_user_achievement02);
        TextView tvCreatedTime=(TextView)view.findViewById(R.id.tv_create_time);
        TextView tvUserTipCount=(TextView)view.findViewById(R.id.tv_user_tip_count);
        TextView tvUserWinRate=(TextView)view.findViewById(R.id.tv_user_tip_win_rate);
        TextView tvUserTrend=(TextView)view.findViewById(R.id.tv_user_tip_trend);
        TextView tvUserReward=(TextView)view.findViewById(R.id.tv_user_tip_reward);

        ImageView ivHomeTeam=(ImageView)view.findViewById(R.id.iv_home_team_icon);
        TextView tvHomeTeamName=(TextView)view.findViewById(R.id.tv_home_team_name);
        TextView tvMatchState=(TextView)view.findViewById(R.id.tv_game_time);
        TextView tvMatchDate=(TextView)view.findViewById(R.id.tv_game_date);
        ImageView ivAwayTeam=(ImageView)view.findViewById(R.id.iv_away_team_icon);
        TextView tvAwayTeamName=(TextView)view.findViewById(R.id.tv_away_team_name);
        TextView tvMatchLeague=(TextView)view.findViewById(R.id.tv_game_league_name);

        TextView tvChoice=(TextView)view.findViewById(R.id.tvChoice);
        TextView tvSam=(TextView)view.findViewById(R.id.tvSam);
        ImageView ivBettingResult=(ImageView)view.findViewById(R.id.ivBetResult);
        TextView tvTipContent=(TextView)view.findViewById(R.id.tv_tip_content);
        LinearLayout layoutOtherTipInfo=(LinearLayout)view.findViewById(R.id.layout_other_tips);
        TextView tvOtherTipCount=(TextView)view.findViewById(R.id.tvOtherTipNum);

        GlideImageLoader.loadImage(this, data.getPt(), R.mipmap.icon_user_default, ivUserIcon);
        UserInfoUtil.setUserHeaderVMark(data.getIsv(), isV, ivUserIcon);
        UserInfoUtil.setUserAchievementInfo(this, data.getTitle1(), ivAchievement01, data.getTitle2(), ivAchievement02);
        tvUserName.setText(data.getFname());
        Date date= CommonUtils.getDateAndTimeFromGMT(data.getCtime());
        if(date!=null){
            tvCreatedTime.setText(CommonUtils.getDateAndTimeFormatString(date));
        }
        tvUserTipCount.setText(String.valueOf(data.getTipcount()));
        tvUserWinRate.setText(String.format(Locale.getDefault(), "%.2f", data.getWins() * 100) + "%");
        tvUserTrend.setText(String.format(Locale.getDefault(), "%.2f", data.getRor()) + "%");
        tvUserReward.setText(String.valueOf(data.getBtyc()));

        GlideImageLoader.loadImage(this, data.getHtlogo(), 0, ivHomeTeam);
        tvHomeTeamName.setText(data.getHtname());
        GlideImageLoader.loadImage(this, data.getAtlogo(), 0, ivAwayTeam);
        tvAwayTeamName.setText(data.getAtname());
        tvMatchLeague.setText(data.getTourname());
        String choice=MatchBettingInfoUtil.getBettingResultInfo(data.getChoice(), data.getOtype(), data.getOdata());
        if(!TextUtils.isEmpty(choice)) {
            tvChoice.setText(choice);
        }else{
            tvChoice.setText("");
        }

        tvTipContent.setText(Html.fromHtml(data.getCont()));
        tvSam.setText(String.valueOf(data.getSam() / 100));

        if(data.getMtcount()>1){
            layoutOtherTipInfo.setVisibility(View.VISIBLE);
            tvOtherTipCount.setText(String.valueOf(data.getMtcount()));
        }else{
            layoutOtherTipInfo.setVisibility(View.GONE);
        }

        if(userCommentEntityList==null){
            userCommentEntityList=new ArrayList<>(10);
            userCommentAdapter=new BallQUserCommentAdapter(userCommentEntityList);
            recyclerView.setAdapter(userCommentAdapter);
        }






    }


    private void getTipOffBounties(int id){
       String url= HttpUrls.HOST_URL_V5 + "bounties/?etype=38&eid=" +id;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 10, new HttpClientUtil.StringResponseCallBack() {
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


    private void requestTipOffComments(int pages,int id, final boolean isLoadMore){
        String url=HttpUrls.HOST_URL_V5 + "comments/?etype=38&eid=" + id + "&p=" + pages;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 10, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onError(Call call, Exception error) {
                recyclerView.setLoadMoreDataFailed();

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        JSONArray array=obj.getJSONArray("data");
                        if(array!=null&&!array.isEmpty()){
                            if(!isLoadMore&&!userCommentEntityList.isEmpty()){
                                userCommentEntityList.clear();
                            }
                            CommonUtils.getJSONListObject(array,userCommentEntityList,BallQUserCommentEntity.class);
                            userCommentAdapter.notifyDataSetChanged();
                            if(array.size()<10){
                                recyclerView.setLoadMoreDataComplete("没有更多数据了");
                            }else {
                                recyclerView.setStartLoadMore();
                                if (isLoadMore) {
                                    currentPages++;
                                } else {
                                    currentPages=2;
                                }
                            }
                            return;
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
