package com.tysci.ballq.views.widgets.chartview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class PieChartView extends View {
    private Context context=null;
    /**全部的数据*/
    private float maxValue=100f;
    /**是否开启动画*/
    private boolean isAnimated=true;
    /**圆半径*/
    private float radius;

    private float animatedSpeed=6.5f;

    private float globalCurrentAngle = 0.0f;

    private List<PieChartData> pieChartDataList=null;

    private int width;
    private int height;

    public PieChartView(Context context) {
        super(context);
        initViews(context,null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context,attrs);
    }

    private void initViews(Context context,AttributeSet attrs){
        this.context=context;
        if(attrs!=null){

        }
    }

    public void setPieChartDataList(List<PieChartData>pieChartDataList){
        this.pieChartDataList=pieChartDataList;
    }

    public void setRadius(float radius){
        this.radius=radius;
    }

    public void setMaxValue(float value){
        this.maxValue=value;
    }

    public void setAnimatedSpeed(float animatedSpeed){
        this.animatedSpeed=animatedSpeed;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(width<radius){
            width= (int) (radius+1);
        }
        if(height<radius){
            height=(int)(radius+1);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }
}
