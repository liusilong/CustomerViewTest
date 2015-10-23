package com.lsl.cusview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hughes on 15/10/23.
 */
public class CusBitmap extends View {
    Bitmap bitmap;
    float changingY = 0;
    String TAG = this.getClass().getName();

    public CusBitmap(Context context) {
        super(context);
    }

    public CusBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.drawBitmap(bitmap, (canvas.getWidth() / 2), changingY, null);
        if (changingY < canvas.getHeight()) {
            changingY += 10;
        } else {
            changingY = 0;
        }
        Log.i(TAG, "canvas 的高：" + canvas.getHeight());
        Log.i(TAG, "ChangingY 的值：" + changingY);
        invalidate();

    }
}
