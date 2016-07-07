package com.tysci.ballq.views.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tysci.ballq.R;
import com.tysci.ballq.modles.UserInfoEntity;
import com.tysci.ballq.networks.GlideImageLoader;
import com.tysci.ballq.utils.UserInfoUtil;

/**
 * Created by Administrator on 2016/6/9.
 */
public class BallQAuthorAnalystsView extends LinearLayout{
    private CircleImageView ivUserHeader;
    private ImageView iV;
    private TextView tvAuthorName;
    private TextView tvAuthorBrief;
    private CircleProgressView winProgress;
    private CircleProgressView profitProgress;
    private TextView tvRankInfo;
    private TextView tvProfitInfo;

    public BallQAuthorAnalystsView(Context context) {
        super(context);
        initViews(context);
    }

    public BallQAuthorAnalystsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BallQAuthorAnalystsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BallQAuthorAnalystsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_ballq_analysts_item,this,true);
        ivUserHeader= (CircleImageView) this.findViewById(R.id.ivUserIcon);
        iV=(ImageView)this.findViewById(R.id.isV);
        tvAuthorName=(TextView)this.findViewById(R.id.tv_author_name);
        tvAuthorBrief=(TextView)this.findViewById(R.id.tv_author_brief);
        winProgress=(CircleProgressView)this.findViewById(R.id.cpv_wins);
        profitProgress=(CircleProgressView)this.findViewById(R.id.cpv_profit);
        tvRankInfo=(TextView)this.findViewById(R.id.tv_rank_info);
        tvProfitInfo=(TextView)this.findViewById(R.id.tv_profit_info);
    }

    public void setUserInfo(String name,String brief){
        tvAuthorName.setText(name);
        tvAuthorBrief.setText(brief);
    }

    public void setUserHeader(String img,int isV){
        GlideImageLoader.loadImage(getContext(),img,R.mipmap.icon_user_default,ivUserHeader);
        UserInfoUtil.setUserHeaderVMark(isV,iV,ivUserHeader);
    }

    public void setWinValue(int value){
        winProgress.setTitleLable("胜率");
        winProgress.setProgressValue(value);
    }

    public void setProfitValue(int value){
        profitProgress.setTitleLable("盈利率");
        profitProgress.setProgressValue(value);
    }

    public void setAuthorRankInfo(String info){
        tvRankInfo.setText(info);
    }

    public void setAuthorProfitInfo(String info){
        tvProfitInfo.setText(info);

    }

    public void setOnClickUserIconListener(OnClickListener onClickUserIconListener){
        ivUserHeader.setOnClickListener(onClickUserIconListener);
    }

    public void setUserId(String id){
        ivUserHeader.setTag(R.id.user_id,id);
    }
}
