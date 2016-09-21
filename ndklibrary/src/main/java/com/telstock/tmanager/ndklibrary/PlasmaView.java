package com.telstock.tmanager.ndklibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by usr_micro9 on 2/08/16.
 */
public class PlasmaView extends View{
    private Bitmap mBitmap;
    private long mStartTime;

    private static native void renderPlasma(Bitmap bitmap, long time_ms);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.RGB_565);
        mStartTime = System.currentTimeMillis();
    }

    public PlasmaView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderPlasma(mBitmap, System.currentTimeMillis() - mStartTime);
        canvas.drawBitmap(mBitmap, 0, 0, null);

        invalidate();
    }

    static {
        System.loadLibrary("plasma");
    }
}
