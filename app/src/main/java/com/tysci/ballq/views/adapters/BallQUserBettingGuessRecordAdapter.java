package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tysci.ballq.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 */
public class BallQUserBettingGuessRecordAdapter extends RecyclerView.Adapter<BallQUserBettingGuessRecordAdapter.BallQUserBettingGuessRecordViewHolder>{
    @Override
    public BallQUserBettingGuessRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ballq_betting_record_item,parent,false);
        return new BallQUserBettingGuessRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallQUserBettingGuessRecordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static final class BallQUserBettingGuessRecordViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.view_mark)
        View viewMark;
        @Bind(R.id.tv_betting_time)
        TextView tvBettingTime;
        @Bind(R.id.tv_betting_date)
        TextView tvBettingDate;
        @Bind(R.id.tv_betting_match_name)
        TextView tvBettingMatchName;
        @Bind(R.id.tv_betting_match_date)
        TextView tvBettingMatchDate;
        @Bind(R.id.tv_home_team_name)
        TextView tvHomeTeamName;
        @Bind(R.id.tv_match_state)
        TextView tvMatchState;
        @Bind(R.id.tv_away_team_name)
        TextView tvAwayTeamName;
        @Bind(R.id.tv_betting_result_info)
        TextView tvBettingResultInfo;
        @Bind(R.id.tvSam)
        TextView tvSam;
        @Bind(R.id.ivBetResult)
        ImageView ivBetResult;
        @Bind(R.id.tvBetResultSam)
        TextView tvBetResultSam;

        public BallQUserBettingGuessRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
