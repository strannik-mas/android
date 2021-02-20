package com.strannik.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    private static final String TAG = "DrawView";
    private int saved = 0;

    public Paint paint;
    public Bitmap bitmap;
    public Canvas canvas;

    int h, w;
    float prev_x, prev_y;   //координаты предыдущего нажатия

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(0xffff0000);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.BUTT);

        //this.setDrawingCacheEnabled(true);
    }

    private void newCanvas(){
        if (bitmap == null) {
            Log.i(TAG, "newCanvas: nullBitmap");
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        invalidate();
    }

    public void erase() {
        newCanvas();
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, w, h, clearPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);
        invalidate();
    }

    public void restorePicture (Bitmap oldBitmap) {
        Log.d(TAG, "restorePicture: w= " + w + " h= " + h);
        this.bitmap = oldBitmap;
        /*if (canvas != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
            invalidate();
        }*/
    }

    public Bitmap getBitmap(){
        //return this.getDrawingCache();
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged: w= " + w + " h= " + h);
        //super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        newCanvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_MOVE) {
            canvas.drawLine(prev_x, prev_y, event.getX(), event.getY(), paint);
        }

        if (action == MotionEvent.ACTION_DOWN) {
            canvas.drawPoint(event.getX(), event.getY(), paint);
        }

        prev_x = event.getX();
        prev_y = event.getY();

        invalidate();

        return true;
    }
}
