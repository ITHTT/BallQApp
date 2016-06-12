package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tysci.ballq.R;
import com.tysci.ballq.modles.BallQTipOffEntity;
import com.tysci.ballq.views.widgets.CustomRattingBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/10.
 */
public class BallQMatchTipOffAdapter extends RecyclerView.Adapter<BallQMatchTipOffAdapter.BallQMatchTipOffViewHolder>{
    private List<BallQTipOffEntity> matchTipOffList;

    public BallQMatchTipOffAdapter(List<BallQTipOffEntity> matchTipOffList) {
        this.matchTipOffList = matchTipOffList;
    }

    @Override
    public int getItemCount() {
        return matchTipOffList.size();
    }

    @Override
    public BallQMatchTipOffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_tip_off_item,parent,false);
        return new BallQMatchTipOffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallQMatchTipOffViewHolder holder, int position) {
        BallQTipOffEntity info=matchTipOffList.get(position);


    }

    @Override
    public void onViewDetachedFromWindow(BallQMatchTipOffViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ButterKnife.unbind(holder);
    }

    public static final class BallQMatchTipOffViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tvTipCreateTime)
        TextView tvTipCreatedTime;
        @Bind(R.id.tvTipCreateDate)
        TextView tvTipCreatedDate;
        @Bind(R.id.tvUserNickName)
        TextView tvUserNickName;
        @Bind(R.id.ivUserIcon)
        ImageView ivUserIcon;
        @Bind(R.id.tvCommentNum)
        TextView tvCommentNum;
        @Bind(R.id.tvTipCount)
        TextView tvTipCount;
        @Bind(R.id.tvWinPercent)
        TextView tvWinPercent;
        @Bind(R.id.tvRor)
        TextView tvRor;
        @Bind(R.id.tvBonCount)
        TextView tvBonCount;
        @Bind(R.id.tvChoice)
        TextView tvChoice;
        @Bind(R.id.tvRewardNum)
        TextView tvRewardNum;
        @Bind(R.id.layoutConfidence)
        LinearLayout layoutConfidence;
        @Bind(R.id.rating_bar)
        CustomRattingBar rattingBar;
        @Bind(R.id.tvContent)
        TextView tvContent;

        public BallQMatchTipOffViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
