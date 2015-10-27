package com.lsl.cusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hughes on 15/10/26.
 */
public class CusTest_01 extends View {
    private String TAG = "CusTest_01";
    private Paint mPaint;
    //内圆中间的文字
    private String centerText;

    public CusTest_01(Context context) {
        this(context, null);
    }

    public CusTest_01(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusTest_01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CusTest_01);
        centerText = a.getString(R.styleable.CusTest_01_centerText);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        //画内圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 3, mPaint);
        //内圆中的文字
        mPaint.setColor(Color.RED);
        Rect r = new Rect();
        //返回包围整个字符串的最小的一个Rect区域
        mPaint.getTextBounds(centerText, 0, centerText.length(), r);
        Log.i(TAG, "centerText的长：" + r.width() / 2 + "");
        canvas.drawText(centerText, getWidth() / 2 - (r.width() / 2), getHeight() / 2 + (r.height() / 2), mPaint);
        //画圆环
        int radius = getWidth() / 3 + 20;
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        canvas.drawCircle(center, center, radius, mPaint);
        //画圆弧
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF, -90, 180, false, mPaint);

    }
}
