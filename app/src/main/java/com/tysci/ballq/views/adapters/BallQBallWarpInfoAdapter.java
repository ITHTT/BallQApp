package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tysci.ballq.R;
import com.tysci.ballq.modles.BallQBallWarpInfoEntity;
import com.tysci.ballq.views.widgets.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class BallQBallWarpInfoAdapter extends RecyclerView.Adapter<BallQBallWarpInfoAdapter.BallQBallWarpInfoViewHolder>{
    private List<BallQBallWarpInfoEntity> ballQInfoListItemEntityList;

    public BallQBallWarpInfoAdapter(List<BallQBallWarpInfoEntity> list){
        this.ballQInfoListItemEntityList=list;
    }
    @Override
    public BallQBallWarpInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ballq_home_ball_warp_item,parent,false);
        return new BallQBallWarpInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BallQBallWarpInfoViewHolder holder, int position) {
        final BallQBallWarpInfoEntity info=ballQInfoListItemEntityList.get(position);
        holder.tvUserName.setText(info.getFname());
        holder.tvLikeCounts.setText(String.valueOf(info.getLike_count()));
        holder.tvCommentCounts.setText(String.valueOf(info.getComcount()));
        holder.tvReadCounts.setText(String.valueOf(info.getReading_count()));
        holder.tvRewardCounts.setText(String.valueOf(info.getBoncount()));
        holder.tvTitle.setText(info.getTitle());
//        try {
//            holder.tvCreateDate.setText(CalendarUtils.parseStringTZ(info.getCtime()).toString(CalendarUtils.ToString.MM_dd__HHmm));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Glide.with(holder.itemView.getContext())
//                .load(ApiUtils.imgUrl(info.getCover()))
//                .asBitmap()
//                .placeholder(R.mipmap.article_def_img)
//                .into(holder.ivInfoConver);
//
//        Glide.with(holder.itemView.getContext())
//                .load(ApiUtils.imgUrl(info.getPt()))
//                .asBitmap()
//                .placeholder(R.mipmap.user_icon_default)
//                .into(holder.ivUserHeader);

//        if(info.getIsv()==1){
//            holder.isV.setVisibility(View.VISIBLE);
//            holder.ivUserHeader.setBorderColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.c_ffc90c));
//        }else{
//            holder.isV.setVisibility(View.GONE);
//            holder.ivUserHeader.setBorderColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.c_e6e6e6));
//        }

//        if(!TextUtils.isEmpty(info.getTitle1())){
//            holder.ivUserAchievement01.setVisibility(View.VISIBLE);
//            Glide.with(holder.itemView.getContext())
//                    .load(ApiUtils.imgUrl(info.getTitle1()))
//                    .asBitmap()
//                    .placeholder(R.color.gray)
//                    .into(holder.ivUserAchievement01);
//        }else{
//            holder.ivUserAchievement01.setVisibility(View.GONE);
//        }
//
//        if(!TextUtils.isEmpty(info.getTitle2())){
//            holder.ivUserAchievement02.setVisibility(View.VISIBLE);
//            Glide.with(holder.itemView.getContext())
//                    .load(ApiUtils.imgUrl(info.getTitle2()))
//                    .asBitmap()
//                    .placeholder(R.color.gray)
//                    .into(holder.ivUserAchievement02);
//        }else{
//            holder.ivUserAchievement02.setVisibility(View.GONE);
//        }

        if(position==0){
            holder.divider.setVisibility(View.VISIBLE);
        }else{
            holder.divider.setVisibility(View.GONE);
        }
    }

//    public void notifyDatas(String type,String id,int value){
//        int index=getItemById(id);
//        if(index>=0){
//            BallQInfoEntity info=ballQInfoListItemEntityList.get(index);
//            if(type.equals("user_like")){
//                info.setLike_count(info.getLike_count()+value);
//            }else if(type.equals("user_comment")){
//                info.setComcount(info.getComcount()+value);
//            }else if(type.equals("user_read")){
//                info.setReading_count(info.getReading_count()+value);
//            }
//            notifyItemChanged(index);
//        }
//    }
//
//    private int getItemById(String id){
//        if(ballQInfoListItemEntityList!=null&&ballQInfoListItemEntityList.size()>0){
//            int size=ballQInfoListItemEntityList.size();
//            for(int i=0;i<size;i++){
//                if(id.equals(String.valueOf(ballQInfoListItemEntityList.get(i).getId()))){
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }

    @Override
    public int getItemCount() {
        return ballQInfoListItemEntityList.size();
    }

    public static final class BallQBallWarpInfoViewHolder extends RecyclerView.ViewHolder{
        ImageView ivInfoConver;
        TextView tvUserName;
        CircleImageView ivUserHeader;
        ImageView isV;
        ImageView ivUserAchievement01;
        ImageView ivUserAchievement02;
        TextView tvCreateDate;
        TextView tvReadCounts;
        TextView tvTitle;
        TextView tvLikeCounts;
        TextView tvCommentCounts;
        TextView tvRewardCounts;
        View divider;

        public BallQBallWarpInfoViewHolder(View itemView) {
            super(itemView);
            ivInfoConver=(ImageView)itemView.findViewById(R.id.iv_ballq_info_cover);
            tvUserName=(TextView)itemView.findViewById(R.id.tv_ballq_info_author_name);
            ivUserHeader=(CircleImageView)itemView.findViewById(R.id.ivUserIcon);
            isV=(ImageView)itemView.findViewById(R.id.isV);
            ivUserAchievement01=(ImageView)itemView.findViewById(R.id.iv_ballq_info_author_achievement01);
            ivUserAchievement02=(ImageView)itemView.findViewById(R.id.iv_ballq_info_author_achievement02);
            tvCreateDate=(TextView)itemView.findViewById(R.id.tv_ballq_info_create_date);
            tvReadCounts=(TextView)itemView.findViewById(R.id.tv_ballq_info_read_counts);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_ballq_info_title);
            tvLikeCounts=(TextView)itemView.findViewById(R.id.tv_ballq_info_like_count);
            tvCommentCounts=(TextView)itemView.findViewById(R.id.tv_ballq_info_comments_count);
            tvRewardCounts=(TextView)itemView.findViewById(R.id.tv_ballq_info_reward_counts);
            divider=itemView.findViewById(R.id.divider);
        }
    }

}
