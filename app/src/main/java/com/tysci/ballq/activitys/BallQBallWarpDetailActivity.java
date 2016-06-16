package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.modles.BallQBallWarpInfoEntity;
import com.tysci.ballq.modles.BallQUserCommentEntity;
import com.tysci.ballq.modles.BallQUserRewardHeaderEntity;
import com.tysci.ballq.modles.UserInfoEntity;
import com.tysci.ballq.networks.GlideImageLoader;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.utils.WebViewUtil;
import com.tysci.ballq.views.adapters.BallQUserCommentAdapter;
import com.tysci.ballq.views.adapters.BallQUserRewardHeaderAdapter;
import com.tysci.ballq.views.widgets.CircleImageView;
import com.tysci.ballq.views.widgets.loadmorerecyclerview.AutoLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/6.
 */
public class BallQBallWarpDetailActivity extends BaseActivity{
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view)
    protected AutoLoadMoreRecyclerView recyclerView;
    @Bind(R.id.et_comment_infos)
    protected EditText etUserCommentInfo;
    @Bind(R.id.ivLike)
    protected ImageView ivLike;
    @Bind(R.id.btnPublish)
    protected Button btPublish;

    private View headerView=null;
    private BallQBallWarpInfoEntity ballWarpInfo=null;
    private int currentPages=1;

    private List<BallQUserRewardHeaderEntity>userRewardHeaderEntityList=null;
    private BallQUserRewardHeaderAdapter userRewardHeaderAdapter=null;

    private List<BallQUserCommentEntity> userCommentEntityList=null;
    private BallQUserCommentAdapter userCommentAdapter;

    private int isLike=0;
    private int isCollect=0;
    private int fid=0;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_ballq_ball_warp_detail;
    }

    @Override
    protected void initViews() {
        setTitle("球经详情");
        titleBar.setRightMenuIcon(R.mipmap.icon_share_gold,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerView= LayoutInflater.from(this).inflate(R.layout.layout_ballq_ball_warp_header,null);
        recyclerView.addHeaderView(headerView);
        ivLike.setOnClickListener(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getIntentData(Intent intent) {
        KLog.e("获取数据。。。");
        ballWarpInfo=intent.getParcelableExtra(Tag);
        if(ballWarpInfo!=null) {
            getBallQBallWarpInfo(ballWarpInfo.getId());
        }
    }

    /**
     * 获取球经信息
     * @param id
     */
    private void getBallQBallWarpInfo(int id){
        String url= HttpUrls.HOST_URL_V5+"article/" + id + "/";
        Map<String,String> params=null;
        if(UserInfoUtil.checkLogin(this)){
            params=new HashMap<>(2);
            params.put("user",UserInfoUtil.getUserId(this));
            params.put("token",UserInfoUtil.getUserToken(this));
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {

            }
            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        BallQBallWarpInfoEntity data=obj.getObject("data",BallQBallWarpInfoEntity.class);
                        if(data!=null){
                            ballWarpInfo=data;
                            initBallWarpInfo(headerView,data);
                            getBallWarpBounties(data.getId());
                            requestBallWarpComments(data.getId(),1,false);
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

    private void initBallWarpInfo(View view,BallQBallWarpInfoEntity data){
        RelativeLayout layoutUserInfo=(RelativeLayout)view.findViewById(R.id.layout_user_info);
        CircleImageView ivUserHeader= (CircleImageView) view.findViewById(R.id.ivUserIcon);
        ImageView isV=(ImageView)view.findViewById(R.id.isV);
        TextView tvUserName=(TextView)view.findViewById(R.id.tv_user_name);
        ImageView ivAchievement01=(ImageView)view.findViewById(R.id.iv_user_achievement_01);
        ImageView ivAchievement02=(ImageView)view.findViewById(R.id.iv_user_achievement_02);
        TextView tvWriteCounts=(TextView)view.findViewById(R.id.tv_user_write_article_counts);
        TextView tvCreateDate=(TextView)view.findViewById(R.id.tv_create_date);
        ImageView ivUserCollect=(ImageView)view.findViewById(R.id.iv_user_collection);
        ivUserCollect.setOnClickListener(this);
        TextView tvTitle=(TextView)view.findViewById(R.id.tvTitle);
        FrameLayout webLayout=(FrameLayout)view.findViewById(R.id.webLayout);

        GlideImageLoader.loadImage(this, data.getPt(), R.mipmap.icon_user_default, ivUserHeader);
        UserInfoUtil.setUserHeaderVMark(data.getIsv(), isV, ivUserHeader);
        tvUserName.setText(data.getFname());
        UserInfoUtil.setUserAchievementInfo(this, data.getTitle1(), ivAchievement01, data.getTitle2(), ivAchievement02);
        tvWriteCounts.setText(String.valueOf(data.getArtcount()));
         Date date= CommonUtils.getDateAndTimeFromGMT(data.getCtime());
         if(date!=null){
           tvCreateDate.setText(CommonUtils.getDateAndTimeFormatString(date));
         }
        KLog.e("显示数据xxx");
        isCollect=data.getIsc();
        isLike=data.getIs_like();
        fid=data.getFid();
        if(data.getIsc()==1){
            ivUserCollect.setSelected(true);
        }else{
            ivUserCollect.setSelected(false);
        }
        tvTitle.setText(data.getTitle());
        WebView webView= WebViewUtil.getHtmlWebView(this, data.getCont());
        if(webView!=null){
            if(webLayout.getChildCount()==0){
                webLayout.addView(webView);
            }
        }
        if(data.getIs_like()==1){
            ivLike.setSelected(true);
        }else{
            ivLike.setSelected(false);
        }
        KLog.e("初始化数据...");
        if(userCommentEntityList==null||userCommentAdapter==null){
            KLog.e("添加适配器...");
            userCommentEntityList=new ArrayList<>(10);
            userCommentAdapter=new BallQUserCommentAdapter(userCommentEntityList);
            recyclerView.setAdapter(userCommentAdapter);
        }
    }

    /**
     * 获取打赏记录
     * @param id
     */
    private void getBallWarpBounties(int id){
        String url=HttpUrls.HOST_URL_V5 + "bounties/?etype=49&eid=" + id;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 60, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {

            }
            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        JSONArray jsonArray=obj.getJSONArray("data");
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            if(userRewardHeaderEntityList==null){
                                userRewardHeaderEntityList=new ArrayList<BallQUserRewardHeaderEntity>(10);
                            }
                            if(userRewardHeaderEntityList.size()>0){
                                userRewardHeaderEntityList.clear();
                            }
                            CommonUtils.getJSONListObject(jsonArray,userRewardHeaderEntityList,BallQUserRewardHeaderEntity.class);
                            if(userRewardHeaderAdapter==null){
                                userRewardHeaderAdapter=new BallQUserRewardHeaderAdapter(BallQBallWarpDetailActivity.this,userRewardHeaderEntityList);
                                GridView gridView= (GridView) headerView.findViewById(R.id.gridView);
                                gridView.setAdapter(userRewardHeaderAdapter);
                            }else{
                                userRewardHeaderAdapter.notifyDataSetChanged();
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

    /**
     * 获取评论数据
     * @param id
     * @param pages
     * @param isLoadMore
     */
    private void requestBallWarpComments(int id, int pages, final boolean isLoadMore){
        String url= HttpUrls.HOST_URL_V5 + "comments/?etype=49&eid=" +id + "&p=" + pages;
        HttpClientUtil.getHttpClientUtil().sendGetRequest(Tag, url, 30, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {

            }
            @Override
            public void onError(Call call, Exception error) {
                if(isLoadMore){
                    recyclerView.setLoadMoreDataFailed();
                }else{
                    recyclerView.setStartLoadMore();
                }
            }
            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    if(obj!=null){
                        JSONArray objs=obj.getJSONArray("data");
                        if(objs!=null&&!objs.isEmpty()) {
                            if(!isLoadMore&&!userCommentEntityList.isEmpty()){
                                userCommentEntityList.clear();
                            }
                            CommonUtils.getJSONListObject(objs,userCommentEntityList,BallQUserCommentEntity.class);
                            userCommentAdapter.notifyDataSetChanged();
                            if(objs.size()<10){
                                recyclerView.setLoadMoreDataComplete("没有更多数据");
                            }else{
                                if(isLoadMore){
                                    currentPages++;
                                }else{
                                    currentPages=2;
                                }
                                recyclerView.setStartLoadMore();
                            }
                        }
                    }
                }
            }
            @Override
            public void onFinish(Call call) {

            }
        });
    }

    /**
     * 用户点赞
     */
    private void userLike(){
        if(!UserInfoUtil.checkLogin(this)){
            UserInfoUtil.userLogin(this);
        }else{
            String url=HttpUrls.HOST_URL_V5+"likes/";
            HashMap<String, String> params = new HashMap<>();
            params.put("user", UserInfoUtil.getUserId(this));
            params.put("token", UserInfoUtil.getUserToken(this));
            params.put("object_type", "article");
            params.put("object_id", String.valueOf(ballWarpInfo.getId()));
            if (isLike == 0) {
                params.put("action", "add");
            } else {
                params.put("action", "cancel");
            }
            HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
                @Override
                public void onBefore(Request request) {

                }
                @Override
                public void onError(Call call, Exception error) {

                }
                @Override
                public void onSuccess(Call call, String response) {
                    KLog.e(response);
                    if (!TextUtils.isEmpty(response)) {
                        JSONObject object=JSONObject.parseObject(response);
                        if (object != null) {
                            //ToastUtil2.show(ArticleDetailActivity.this, object.getString("message"));
                            int status = object.getIntValue("status");
                            if (status == 8400 && isLike == 0) {
                                //postRefreshEvent("user_like", 1);
                                isLike = 1;
                                ivLike.setSelected(true);
                            } else if (status == 8401 && isLike == 1) {
                                //postRefreshEvent("user_like", -1);
                                isLike = 0;
                                ivLike.setSelected(false);
                            }
                        }
                    }
                }
                @Override
                public void onFinish(Call call) {

                }
            });
        }
    }

    private void userCollect(){
        if(!UserInfoUtil.checkLogin(this)){
            UserInfoUtil.userLogin(this);
        }else {
            String url = HttpUrls.HOST_URL_V5 + "user/favorites/add/?etype=1&eid=" + ballWarpInfo.getId();
            String delUrl = HttpUrls.HOST_URL_V5 + "user/favorites/del/?etype=1&fid=" + fid;
            HashMap<String, String> params = new HashMap<>();
            params.put("user", UserInfoUtil.getUserId(this));
            params.put("token", UserInfoUtil.getUserToken(this));
            if (isCollect == 1 && fid>=0) {
                url = delUrl;
            }
            HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, params, new HttpClientUtil.StringResponseCallBack() {
                @Override
                public void onBefore(Request request) {

                }
                @Override
                public void onError(Call call, Exception error) {

                }
                @Override
                public void onSuccess(Call call, String response) {
                    KLog.json(response);
                    if (!TextUtils.isEmpty(response)) {
                        ImageView ivUserCollect=(ImageView)headerView.findViewById(R.id.iv_user_collection);
                        if (isCollect == 1) {
                            if (response.contains("OK")) {
                                isCollect = 0;
                                ivUserCollect.setSelected(false);
                                //refreshCollectionList();
                                return;
                            }
                        } else {
                            JSONObject obj=JSONObject.parseObject(response);
                            if (obj != null) {
                                fid = obj.getIntValue("data");
                                isCollect = 1;
                                ivUserCollect.setSelected(true);
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
    }

    @Override
    protected void userLogin(UserInfoEntity userInfoEntity) {
        getBallQBallWarpInfo(ballWarpInfo.getId());
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
        switch(view.getId()){
            case R.id.ivLike:
                userLike();
                break;
            case R.id.iv_user_collection:
                userCollect();
                break;
        }
    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
