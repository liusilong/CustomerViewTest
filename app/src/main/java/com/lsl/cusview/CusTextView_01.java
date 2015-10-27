package com.lsl.cusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by hughes on 15/10/22.
 */
public class CusTextView_01 extends TextView {
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleSize;
    private Rect mBound;
    //绘制时空之文本绘制的范围
    private Paint mPaint;
    private final static String tag = "CusTextView_01";

    public CusTextView_01(Context context) {
        super(context);
    }

    public CusTextView_01(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    //默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    //默热门设置为16sp,TypeValue也可以把sp转化为px
                    mTitleSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()
                    ));
                    break;
            }
        }
        a.recycle();
        //获得绘制文本的宽和高
        mPaint = new Paint();
        mPaint.setTextSize(mTitleSize);
        mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
//                postInvalidate();
                //重新测量布局
                requestLayout();
                invalidate();
            }

        });
    }

    //产生四个随机数
    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 5) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return sb.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景黄色矩形
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        mPaint.setAntiAlias(true);
        Log.i(tag, "getMeasuredWidth():" + getMeasuredWidth());
        Log.i(tag, "getMeasuredHeight():" + getMeasuredHeight());
        Log.i(tag, "获取的宽度:" + getWidth());
        Log.i(tag, "获取的高度:" + getHeight());
        Log.i(tag, "获取的Rect高度:" + mBound.height());
        Log.i(tag, "获取的Rect高度:" + mBound.width());
        //画文字
        mPaint.setTextSize(mTitleSize);
        mPaint.setAlpha(200);
        mPaint.setUnderlineText(true); // 文字下划线
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2,
                getHeight() / 2 + mBound.height() / 2, mPaint);
        //画点的背景
//        mPaint.setColor(Color.RED);
//        mPaint.setAlpha(180);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        //画点(模仿验证码上的噪点)
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(255);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        for (int i = 0; i < 100; i++) {
            canvas.drawPoint(new Random().nextInt(getMeasuredWidth()), new Random().nextInt(getMeasuredHeight()), mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //Match_parent
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            width = desired;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    //用于在代码中修改自定义属性的值
    public void setTitleText(String str) {
        mTitleText = str;
    }

    public void setTitleTExtColor(int color) {
        mTitleTextColor = color;
    }
}
