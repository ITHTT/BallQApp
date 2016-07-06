package com.tysci.ballq.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.tysci.ballq.R;

/**
 * Created by HTT on 2016/7/6.
 */
public class BallQGoldCoinBuyAdapter extends RecyclerView.Adapter<BallQGoldCoinBuyAdapter.BallQGoldCoinBuyViewHolder>{

    @Override
    public BallQGoldCoinBuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buy_gold_coin_item,parent,false);
        return new BallQGoldCoinBuyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallQGoldCoinBuyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static final class BallQGoldCoinBuyViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;
        public BallQGoldCoinBuyViewHolder(View itemView) {
            super(itemView);
            itemView=(CheckBox)itemView.findViewById(R.id.gold_coin_item);
        }
    }
}
