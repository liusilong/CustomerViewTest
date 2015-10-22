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
import android.widget.TextView;

/**
 * Created by hughes on 15/10/22.
 */
public class CusTextView_01 extends TextView{
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
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomTitleView);
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
    }


    /**
     * 获得自定义样式的属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CusTextView_01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        Log.i(tag, "获取的宽度:" + getWidth());
        Log.i(tag, "获取的高度:" + getHeight());
        Log.i(tag, "获取的Rect高度:" + mBound.height());
        Log.i(tag, "获取的Rect高度:" + mBound.width());
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2,
                getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
