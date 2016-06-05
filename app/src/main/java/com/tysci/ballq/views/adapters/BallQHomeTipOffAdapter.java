package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tysci.ballq.R;
import com.tysci.ballq.modles.BallQTipOffEntity;
import com.tysci.ballq.networks.GlideImageLoader;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.views.widgets.CircleImageView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HTT on 2016/6/4.
 */
public class BallQHomeTipOffAdapter extends RecyclerView.Adapter<BallQHomeTipOffAdapter.BallQHomeTipOffViewHolder>{
    private List<BallQTipOffEntity> ballQTipOffEntityList;

    public BallQHomeTipOffAdapter(List<BallQTipOffEntity>list){
        this.ballQTipOffEntityList=list;
    }

    @Override
    public BallQHomeTipOffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ballq_home_tip_off_item,parent,false);
        return new BallQHomeTipOffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallQHomeTipOffViewHolder holder, int position) {
        BallQTipOffEntity info=ballQTipOffEntityList.get(position);
        holder.tvMatchName.setText(info.getTourname());
        Date matchDate= CommonUtils.getDateAndTimeFromGMT(info.getMtime());
        if(matchDate!=null) {
            holder.tvMatchDate.setText(CommonUtils.getMM_ddString(matchDate));
            holder.tvMatchTime.setText(CommonUtils.getTimeOfDay(matchDate));
        }else{
            holder.tvMatchDate.setText("");
            holder.tvMatchTime.setText("");
        }
        GlideImageLoader.loadImage(holder.itemView.getContext(),info.getHtlogo(),R.mipmap.ic_ballq_logo,holder.ivHtLogo);
        holder.tvHtName.setText(info.getHtname());
        holder.tvHtScore.setText(String.valueOf(info.getHtscore()));
        GlideImageLoader.loadImage(holder.itemView.getContext(),info.getAtlogo(),R.mipmap.ic_ballq_logo,holder.ivAtLogo);
        holder.tvHtName.setText(info.getAtname());
        holder.tvAtScore.setText(String.valueOf(info.getAtscore()));

        String bettingInfo=getBettingInfo(info.getChoice(),info.getOtype(),info.getOdata());
        if(!TextUtils.isEmpty(bettingInfo)){
            holder.tvChoice.setText(bettingInfo);
        }else{
            holder.tvChoice.setText("");
        }

        Date tipDate=CommonUtils.getDateAndTimeFromGMT(info.getCtime());
        if(tipDate!=null){
            holder.tvTipCreateTime.setText(CommonUtils.getDateAndTimeFormatString(tipDate));
        }else{
            holder.tvTipCreateTime.setText("");
        }

    }

    private String getBettingInfo(String choice, String otype, String odata){
        if(!TextUtils.isEmpty(choice)&&!TextUtils.isEmpty(otype)&&!TextUtils.isEmpty(odata)){
            JSONObject obj=JSONObject.parseObject(odata);
            if(obj!=null&&!obj.isEmpty()){
                String team = "";
                String oddsType;
                switch (otype) {
                    case "1":
                        oddsType = "胜平负 ";
                        break;
                    case "2":
                        oddsType = "大小球 ";
                        break;
                    case "3":// SP
                        oddsType = "亚盘";
                        break;
                    case "4":
                        oddsType = "竞彩让球(" + obj.getString("HC") + ")";
                        break;
                    case "5":
                        oddsType = "亚盘 ";
                        break;
                    default:
                        return null;
                }
//            if ("HC".equals(bq.odds_type)) {
//                oddsType = "竞彩让球(" + oddsInfo.optString("HC") + ")";
//            } else if ("AHC".endsWith(bq.odds_type)) {
//                oddsType = "亚盘 ";
//            } else if ("3W".equals(bq.odds_type)) {
//                oddsType = "胜平负 ";
//            } else if ("TO".equals(bq.odds_type)) {
//                oddsType = "大小球 ";
//            }
                switch (choice) {
                    case "DO":
                        team = "平@";
                        break;
                    case "HO":
                        team = "主胜 @";
                        break;
                    case "AO":
                        team = "客胜 @";
                        break;
                    case "OO":
                        team = "高于" + obj.getString("T") + "@";
                        break;
                    case "UO":
                        team = "低于" + obj.getString("T") + "@";
                        break;
                    case "MLH":
                        if (Float.parseFloat(obj.getString("HCH")) > 0) {
                            team = "主队+" + obj.getString("HCH") + "@";
                        } else {
                            team = "主队" + obj.getString("HCH") + "@";
                        }
                        break;
                    case "MLA":
                        if (Float.parseFloat(obj.getString("HCA")) > 0) {
                            team = "客队+" + obj.getString("HCA") + "@";
                        } else {
                            team = "客队" + obj.getString("HCA") + "@";
                        }
                        break;
                }
                if(!TextUtils.isEmpty(oddsType)&&!TextUtils.isEmpty(team)){
                    return oddsType + team + obj.getString(choice);
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return ballQTipOffEntityList.size();
    }

    @Override
    public void onViewDetachedFromWindow(BallQHomeTipOffViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ButterKnife.unbind(holder);
    }

    public static final class BallQHomeTipOffViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tvMatchTime)
        protected TextView tvMatchTime;
        @Bind(R.id.tvMatchName)
        protected TextView tvMatchName;
        @Bind(R.id.tvMatchDate)
        protected TextView tvMatchDate;
        @Bind(R.id.ivHtLogo)
        protected ImageView ivHtLogo;
        @Bind(R.id.tvHtName)
        protected TextView tvHtName;
        @Bind(R.id.ivAtLogo)
        protected ImageView ivAtLogo;
        @Bind(R.id.tvAtName)
        protected TextView tvAtName;
        @Bind(R.id.tvHtScore)
        protected TextView tvHtScore;
        @Bind(R.id.tvAtScore)
        protected TextView tvAtScore;
        @Bind(R.id.tvGameState)
        protected TextView tvGameState;
        @Bind(R.id.ivCheckCollection)
        protected ImageView ivCheckCollection;
        @Bind(R.id.tvChoice)
        protected TextView tvChoice;
        @Bind(R.id.tvTipCreateTime)
        protected TextView tvTipCreateTime;
        @Bind(R.id.tvContent)
        protected TextView tvContent;
        @Bind(R.id.tvSam)
        protected TextView tvSam;
        @Bind(R.id.tvReadingCount)
        protected TextView tvReadingCount;
        @Bind(R.id.tvCommentNum)
        protected TextView tvCommentNum;
        @Bind(R.id.tvLikeCount)
        protected TextView tvLikeCount;
        @Bind(R.id.tvTipCount)
        protected TextView tvTipCount;
        @Bind(R.id.tvWinPercent)
        protected TextView tvWinPercent;
        @Bind(R.id.tvRor)
        protected TextView tvRor;
        @Bind(R.id.tvBonCount)
        protected TextView tvBonCount;
        @Bind(R.id.tvUserNickName)
        protected TextView tvUserNickName;
        @Bind(R.id.ivUserIcon)
        protected CircleImageView ivUserIcon;
        @Bind(R.id.isV)
        protected ImageView isV;
        @Bind(R.id.ivBetResult)
        protected ImageView ivBetResult;






        public BallQHomeTipOffViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}