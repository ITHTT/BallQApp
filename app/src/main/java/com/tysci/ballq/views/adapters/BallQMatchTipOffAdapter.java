package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tysci.ballq.R;
import com.tysci.ballq.modles.BallQTipOffEntity;

import java.util.List;

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

    }

    public static final class BallQMatchTipOffViewHolder extends RecyclerView.ViewHolder{
        public BallQMatchTipOffViewHolder(View itemView) {
            super(itemView);
        }
    }
}
