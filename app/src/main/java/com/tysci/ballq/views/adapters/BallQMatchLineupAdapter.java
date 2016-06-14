package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/14.
 */
public class BallQMatchLineupAdapter {

    public static final class BallQMatchLineupViewHolder extends RecyclerView.ViewHolder{
        TextView tvPlayerNumber;
        TextView tvPlayerName;
        TextView tvPlayerPosition;


        public BallQMatchLineupViewHolder(View itemView) {

            super(itemView);
        }
    }
}
