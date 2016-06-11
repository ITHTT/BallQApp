package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tysci.ballq.R;
import com.tysci.ballq.modles.BallQMatchForecastDataEntity;
import com.tysci.ballq.modles.BallQMatchForecastDataEntity.MatchForecastDataEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HTT on 2016/6/10.
 */
public class BallQMatchForecastDataAdapter extends RecyclerView.Adapter<BallQMatchForecastDataAdapter.BallQMatchForecastDataViewHolder>{
    private List<BallQMatchForecastDataEntity.MatchForecastDataEntity> matchForecastDataEntityList;

    public BallQMatchForecastDataAdapter(List<MatchForecastDataEntity> matchForecastDataEntityList) {
        this.matchForecastDataEntityList = matchForecastDataEntityList;
    }

    @Override
    public BallQMatchForecastDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ballq_match_forecast_data,parent,false);
        return new BallQMatchForecastDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallQMatchForecastDataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return matchForecastDataEntityList.size();
    }

    public static final class BallQMatchForecastDataViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_match_date_info)
        protected TextView tvMatchDateInfo;
        @Bind(R.id.tv_match_team_info)
        protected TextView tvMatchTeamInfo;
        @Bind(R.id.tv_match_percent)
        protected TextView tvMatchPercent;
        @Bind(R.id.tv_match_state)
        protected TextView tvMatchState;

        public BallQMatchForecastDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
