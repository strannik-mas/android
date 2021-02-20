package com.strannik.compas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CompasView extends View {
    private float alpha;
    private int w, h;
    private int arrow;
    private Paint paint = new Paint();

    public CompasView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        arrow = Math.min(w/2, h/2);
    }

    public void setMagnetic(float y, float x) {
        alpha = (float) Math.atan2(y, x);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float Y = (float)(h/2 - arrow*Math.sin(alpha));
        float X = (float)(w/2 - arrow*Math.cos(alpha));

        canvas.drawLine(w/2, h/2, X, Y, paint);
    }
}
