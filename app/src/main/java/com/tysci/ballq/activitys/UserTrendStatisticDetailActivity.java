package com.tysci.ballq.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;
import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.modles.BallQTrendProfitStatisticEntity;
import com.tysci.ballq.networks.GlideImageLoader;
import com.tysci.ballq.networks.HttpClientUtil;
import com.tysci.ballq.networks.HttpUrls;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.KLog;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.widgets.BallQTrendProfitStatisticLayout;
import com.tysci.ballq.views.widgets.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/6/17.
 */
public class UserTrendStatisticDetailActivity extends BaseActivity{
    @Bind(R.id.ivUserIcon)
    protected CircleImageView ivUserIcon;
    @Bind(R.id.isV)
    protected ImageView isV;
    @Bind(R.id.tv_user_name)
    protected TextView tvUserName;
    @Bind(R.id.tvTrendProfit)
    protected TextView tvTrendProfit;
    @Bind(R.id.tvTrendProfitably)
    protected TextView tvTrendProfitabley;
    @Bind(R.id.textView1)
    protected TextView textView1;
    @Bind(R.id.textView2)
    protected TextView textView2;
    @Bind(R.id.textView3)
    protected TextView textView3;
    @Bind(R.id.textView4)
    protected TextView textView4;
    @Bind(R.id.textView5)
    protected TextView textView5;
    @Bind(R.id.textView6)
    protected TextView textView6;
    @Bind(R.id.layout_trend_ahc_profit)
    protected BallQTrendProfitStatisticLayout trendAhc;
    @Bind(R.id.layout_trend_league_profit)
    protected BallQTrendProfitStatisticLayout trendLeague;
    @Bind(R.id.layout_trend_month_profit)
    protected BallQTrendProfitStatisticLayout trendMonth;
    @Bind(R.id.layout_trend_to_profit)
    protected BallQTrendProfitStatisticLayout trendTo;
    @Bind(R.id.layout_trend_gold_profit)
    protected BallQTrendProfitStatisticLayout trendGold;
    @Bind(R.id.layout_trend_week_profit)
    protected BallQTrendProfitStatisticLayout trendWeek;
    @Bind(R.id.lineChartView)
    protected LineChartView lineChartView;

    private String uid=null;
    private int etype=-1;
    private boolean isOldUser=false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_trend_statistics_detail;
    }

    @Override
    protected void initViews() {
        trendAhc.setTrendProfitTitle("按亚盘统计盈利");
        trendLeague.setTrendProfitTitle("按赛事统计盈利");
        trendMonth.setTrendProfitTitle("按月份统计盈利");
        trendTo.setTrendProfitTitle("按大小球统计盈利");
        trendGold.setTrendProfitTitle("按球币统计盈利");
        trendWeek.setTrendProfitTitle("按天统计盈利");
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getIntentData(Intent intent) {
        uid=intent.getStringExtra("user_id");
        etype=intent.getIntExtra("etype",-1);
        if(!TextUtils.isEmpty(uid)){
            getTrendStatisticDetailInfo(etype,uid);
        }

    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    private void getTrendStatisticDetailInfo(int etype,String uid){
        String url= HttpUrls.HOST_URL_V5+(isOldUser?"old/":"")+"user/"+uid+"/betting_stats/"+(etype>=0?"?etype="+etype:"");
        KLog.e("url:"+url);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, url, null, new HttpClientUtil.StringResponseCallBack() {
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
                        JSONObject dataObj=obj.getJSONObject("data");
                        if(dataObj!=null){
                            JSONObject generalObj=dataObj.getJSONObject("general");
                            if(generalObj!=null){
                                setTrendStataisticInfo(generalObj);
                            }

                           JSONArray ahcArrays= dataObj.getJSONArray("ahc");
                            if(ahcArrays!=null&&!ahcArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>ahcs=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(ahcArrays,ahcs,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(1,ahcs);
                                trendAhc.setTrendProfitStatistValue(ahcs);
                            }

                            JSONArray tournArrays=dataObj.getJSONArray("tourn");
                            if(tournArrays!=null&&!tournArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>tourns=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(tournArrays,tourns,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(2,tourns);
                                trendLeague.setTrendProfitStatistValue(tourns);
                            }

                            JSONArray monthArrays=dataObj.getJSONArray("month");
                            if(monthArrays!=null&&!monthArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>months=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(monthArrays,months,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(3,months);
                                trendMonth.setTrendProfitStatistValue(months);
                                setLineChartViewData(months);
                            }

                            JSONArray toArrays=dataObj.getJSONArray("to");
                            if(toArrays!=null&&!toArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>tos=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(toArrays,tos,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(4,tos);
                                trendTo.setTrendProfitStatistValue(tos);
                            }

                            JSONArray amountArrays=dataObj.getJSONArray("amount");
                            if(amountArrays!=null&&!amountArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>amounts=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(amountArrays,amounts,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(5,amounts);
                                trendGold.setTrendProfitStatistValue(amounts);
                            }

                            JSONArray weekArrays=dataObj.getJSONArray("weekday");
                            if(weekArrays!=null&&!weekArrays.isEmpty()){
                                List<BallQTrendProfitStatisticEntity>weeks=new ArrayList<BallQTrendProfitStatisticEntity>(ahcArrays.size());
                                CommonUtils.getJSONListObject(weekArrays,weeks,BallQTrendProfitStatisticEntity.class);
                                setTrendProfitType(6,weeks);
                                trendWeek.setTrendProfitStatistValue(weeks);
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

    private void setTrendStataisticInfo(JSONObject general){
        String tempStr;

        tempStr = general.getString("fname");
        if (!TextUtils.isEmpty(tempStr))
            tvUserName.setText(tempStr);

        tempStr = general.getString("pt");
        if (!TextUtils.isEmpty(tempStr)) {
            GlideImageLoader.loadImage(this,tempStr,R.mipmap.icon_user_default,ivUserIcon);
            UserInfoUtil.setUserHeaderVMark(general.getIntValue("isv"),isV,ivUserIcon);
        }

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("tearn") / 100F);
        if (tempStr.equalsIgnoreCase("NaN")) tempStr = "0.00";
        tvTrendProfit.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("ror")) + "%";
        if (tempStr.equalsIgnoreCase("NaN%")) tempStr = "0.00%";
        tvTrendProfitabley.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.0f", general.getFloat("wins") * 100F) + "%";
        if (tempStr.equalsIgnoreCase("NaN%")) tempStr = "0.00%";
        textView1.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("avgamt") / 100F);
        if (tempStr.equalsIgnoreCase("NaN")) tempStr = "0.00";
        textView2.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("avgrate"));
        if (tempStr.equalsIgnoreCase("NaN")) tempStr = "0.00";
        textView3.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("betamt") / 100F);
        if (tempStr.equalsIgnoreCase("NaN")) tempStr = "0.00";
        textView4.setText(tempStr);

        tempStr = String.format(Locale.getDefault(), "%.2f", general.getFloat("retamt") / 100F);
        if (tempStr.equalsIgnoreCase("NaN")) tempStr = "0.00";
        textView5.setText(tempStr);
        setTrendInfo(textView6,general.getString("trends"));
    }

    /**
     * 这个方法用来添加走势里面的数据，赢用红色＋号表示，输用绿色－号表示，走用灰色0表示
     * params tv:  显示走势的TextView
     * params s :  服务端传过来的走势数据，赢为1，输为2，走为3
     */
    private void setTrendInfo(TextView tv, String s) {
        try {
            char c;
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if ((int) c == (int) '1') {
                    ssb.append('+');
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ce483d")), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
                if ((int) c == (int) '2') {
                    ssb.append('-');
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#469c4a")), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
                if ((int) c == (int) '3') {
                    ssb.append('0');
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#9b9b9b")), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
            tv.setText(ssb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTrendProfitType(int type,List<BallQTrendProfitStatisticEntity>datas){
        if(datas!=null&&!datas.isEmpty()){
            int size=datas.size();
            for(int i=0;i<size;i++){
                datas.get(i).setType(type);
            }
        }
    }

    private void setLineChartViewData(List<BallQTrendProfitStatisticEntity>datas){
        if(datas!=null&&datas.size()>0){
            lineChartView.setVisibility(View.VISIBLE);
            LineSet lineSet=new LineSet();
            String[]dates=datas.get(0).getMonth().split("-");
            final int year = Integer.parseInt(dates[0]);
            String endMonth=String.valueOf(year + "/" + dates[dates.length-1]);
            String startMonth=String.valueOf((year - 1) + "/" + dates[dates.length-1]);

            int size=datas.size();
            int count=13-size;
            float min=0f;
            float max=0f;
            for(int i=0;i<count;i++){
                if(i==0) {
                    lineSet.addPoint(startMonth,0f);
                }else{
                    lineSet.addPoint("",0f);
                }
            }
            for(int i=size-1;i>=0;i--){
                float value=0f;
                if(i+1<size){
                    value=(float) (datas.get(i).getEarn() + datas.get(i + 1).getEarn()) / 100f;
                    if(i==0) {
                        lineSet.addPoint(endMonth, value);
                    }else{
                        lineSet.addPoint("", value);
                    }
                }else{
                    value=(float)datas.get(i).getEarn()/100f;
                    lineSet.addPoint("",value);
                }
                KLog.e("value:"+value);
                if(min>value){
                    min=value;
                }
                if(max<value){
                    max=value;
                }
            }

            lineSet.setSmooth(true);
            lineSet
                    .setDotsColor(Color.parseColor("#ffc755"))
                    .setThickness(4).beginAt(0);
            lineChartView.addData(lineSet);
            KLog.e("max:"+max);
            KLog.e("min:"+min);
            int maxValue= (int) Math.ceil(max);
            int minValue=(int)Math.floor(min);
            KLog.e("MaxValue:"+maxValue);
            KLog.e("MinValue:"+minValue);
            lineChartView.setBorderSpacing(Tools.fromDpToPx(0))
                    .setAxisBorderValues(minValue-10,maxValue+10,(maxValue-minValue)/4+20)
                    .setYLabels(AxisController.LabelPosition.OUTSIDE)
                    .setXLabels(AxisController.LabelPosition.OUTSIDE)
                    .setLabelsColor(Color.parseColor("#3a3a3a"))
                    .setXAxis(true)
                    .setYAxis(false);
            lineChartView.show();
        }else{
            lineChartView.setVisibility(View.GONE);
        }
    }


    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
