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
    //当前进度
    private int mProgress;
    //画笔
    private Paint mPaint;
    //是否开始下一个
    private boolean isNext = false;

    public CusCircle(Context context) {
        super(context);
    }

    public CusCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        if (!isNext) {
            //第一圈颜色完整，第二圈跑
            mPaint.setColor(mFirstColor);
            //画出圆环
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            //根据进度画圆弧
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        } else {
            //第一圈颜色完整，第二圈跑
            mPaint.setColor(mSecondColor);
            //画出圆环
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            //根据进度画圆弧
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
        super.onDraw(canvas);
    }
}
