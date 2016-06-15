package com.tysci.ballq.views.widgets.chartview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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

    private float radius;

    private float animatedSpeed=6.5f;

    private float globalCurrentAngle = 0.0f;

    private List<PieChartData> pieChartDataList=null;

    private int width;
    private int height;
    private RectF rectF=null;
    private Paint paint=null;

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
        rectF=new RectF();
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2f);
        paint.setAntiAlias(true);
    }

    public void setPieChartDataList(List<PieChartData>pieChartDataList){
        this.pieChartDataList=pieChartDataList;
        postInvalidate();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size=Math.min(width,height);
        radius=(float)size/2;
        float centerX=(float)width/2;
        float centerY=(float)height/2;

        rectF.top=centerY-radius;
        rectF.left=centerX-radius;
        rectF.right=centerX+radius;
        rectF.bottom=centerY+radius;
        drawPies(pieChartDataList,canvas,paint);
    }

    protected void drawPies(List<PieChartData>datas,Canvas canvas,Paint paint){
        float startAngle=-90f;
        float percentAngle=0f;
        int size=datas.size();
        for(int i=0;i<size;i++){
            PieChartData data=datas.get(i);
            paint.setColor(data.getColor());
            percentAngle=data.getValue()*360;
            canvas.drawArc(rectF,startAngle,percentAngle,true,paint);
            startAngle+=percentAngle;
        }
    }




}
