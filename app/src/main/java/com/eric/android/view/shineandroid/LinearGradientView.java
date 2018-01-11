package com.eric.android.view.shineandroid;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * author : Eric
 * e-mail : yuanshuai@bertadata.com
 * time   : 2018/01/08
 * desc   : xxxx描述
 * version: 1.0
 */

public class LinearGradientView extends FrameLayout {
    private Paint mPaint;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private Bitmap mMaskBitmap;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    Canvas maskCanvas;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        if (mMaskBitmap == null && (w > 0 && h > 0)) {
            mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            maskCanvas = new Canvas(mMaskBitmap);
            Canvas tempCanvas = new Canvas(temp);
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            tempCanvas.save();
            tempCanvas.rotate(-40, w / 2, h / 2);
            int padding = (int) (Math.sqrt(2) * Math.max(w, h)) / 2;
            Paint tempPaint=new Paint();
            tempPaint.setColor(Color.parseColor("#bbffffff"));
            tempCanvas.drawRect(w/2, -padding, w/2+20, h + padding, tempPaint);
            tempCanvas.drawRect(w/2+40, -padding, w/2+50, h + padding, tempPaint);
//            tempCanvas.drawRect(w/2, -padding, 40, h + padding, tempPaint);
            tempCanvas.restore();
            maskCanvas.drawBitmap(temp,0,0,mPaint);
            mPaint.setXfermode(null);
        }
        x0ff = -w;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mGlobalLayoutListener == null) {
            mGlobalLayoutListener = getLayoutListener();
        }
        getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener getLayoutListener() {
        return this::startAnim;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mGlobalLayoutListener != null) {
            getViewTreeObserver().removeGlobalOnLayoutListener(mGlobalLayoutListener);
            mGlobalLayoutListener = null;
        }
        super.onDetachedFromWindow();
    }

    float x0ff;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShineMask(canvas);
    }

    protected void drawShineMask(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, -x0ff, 0, null);
    }

    public void startAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "xoff", -getWidth(), 0, getWidth());
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.start();
    }

    private void setXoff(float off) {
        x0ff = off;
        invalidate();
    }
}
