package com.lsl.cusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by hughes on 15/10/23.
 * 本自定义View思路：
 * 两个空心圆弧相互交替的转
 * 分解效果：
 * 先画出底部额圆环形，在画上面的圆环，圆环的进度（圆弧扫过的角度）为构造方法中的mProgress
 */
public class CusCircle extends View {
    //第一圈颜色
    private int mFirstColor;
    //第二圈颜色
    private int mSecondColor;
    //圆环的宽度
    private int mCircleWidth;
    //速度
    private int mSpeed;
    //当前进度 确切的说时圆弧扫过的角度，360°为一圈
    private int mProgress;
    //画笔
    private Paint mPaint;
    //是否开始下一个
    private boolean isNext = false;

    private String TAG = this.getClass().getName();

    public CusCircle(Context context) {
        this(context, null);
    }

    public CusCircle(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);

    }

    public CusCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr, 20);//默认速度为20
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        //绘图线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;//获取圆心X坐标
        //获取半径  用x坐标减去圆环的款的一半
        int radius = center - mCircleWidth / 2;
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        //用于定义圆弧的形状和大小的界限
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        int left = center - radius;
        int right = center + radius;
//        Log.i(TAG, "getWidth:" + getWidth() + "getHeight:" + getHeight() + "mCircleWidth:" + mCircleWidth + "radius:" + radius);
//        Log.i(TAG, "left:" + left + "top:" + left + "right:" + right + "bottom:" + right);
        if (!isNext) {
            //第一圈颜色完整，第二圈跑
            mPaint.setColor(mFirstColor);
            //画出圆环
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            //根据进度画圆弧  在圆环上画弧
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        } else {
            /**
             * 画底部的圆环
             */
            //第一圈颜色完整，第二圈跑
            mPaint.setColor(mSecondColor);
            //画出圆环
            canvas.drawCircle(center, center, radius, mPaint);
            /**
             * 画圆弧
             */
            mPaint.setColor(mFirstColor);
            //根据进度画圆弧
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
        super.onDraw(canvas);
    }
}
