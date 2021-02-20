package com.example.moneytracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DiagramView extends View {
    private static final String TAG = "DiagramView";
    private int income;
    private int expense;
    private Paint expensePaint = new Paint();
    private Paint incomePaint = new Paint();

    public DiagramView(Context context) {
        this(context, null);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        incomePaint.setColor(getResources().getColor(R.color.balance_income_color));
        expensePaint.setColor(getResources().getColor(R.color.balance_expense_color));

        //если просматриваем вьюху в редакторе
        if (isInEditMode()) {
            income = 19000;
            expense = 4500;
        }
    }

    public void update(int income, int expense) {
        this.income = income;
        this.expense = expense;
        invalidate();   //перерисовка вьюхи и вызов метода onDraw
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //обработка изменения размеров вьюхи
        /*int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthValue = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightValue = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(300, 300);*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //тут создавать новые кисти нельзя!
        //каждые 16мс будет создаваться объект в памяти
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawPieDiagram(canvas);
        } else {
            drawRectDiagram(canvas);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawPieDiagram(Canvas canvas) {
        if (expense + income == 0) {
            return;
        }

        float expenseAngle = 360.f * expense / (expense + income);
        float incomeAngle = 360.f * income / (expense + income);

        int space = 10;     //space between income and expense
        int size = Math.min(getWidth(), getHeight()) - space *  2;
        final int xMargin = (getWidth() - size) / 2, yMargin = (getHeight() - size) / 2;

        canvas.drawArc(
                xMargin - space,
                yMargin,
                getWidth() - xMargin - space,
                getHeight() - yMargin,
                180 - expenseAngle/2,
                expenseAngle,
                true,
                expensePaint
        );

        canvas.drawArc(
                xMargin + space,
                yMargin,
                getWidth() - xMargin + space,
                getHeight() - yMargin,
                360 - incomeAngle/2,
                incomeAngle,
                true,
                incomePaint
        );
    }

    private void drawRectDiagram(Canvas canvas) {
        if (expense + income == 0) {
            return;
        }

        long max = Math.max(expense, income);
        long expenseHeight = canvas.getHeight() * expense / max;
        long incomeHeight = canvas.getHeight() * income / max;

        int w = getWidth() / 4;

        canvas.drawRect( w/2, canvas.getHeight() - expenseHeight, w*3 / 2, canvas.getHeight(), expensePaint);
        canvas.drawRect( 5*w/2, canvas.getHeight() - incomeHeight, w*7 / 2, canvas.getHeight(), incomePaint);
    }
}
