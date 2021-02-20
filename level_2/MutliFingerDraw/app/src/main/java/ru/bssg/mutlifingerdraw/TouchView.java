package ru.bssg.mutlifingerdraw;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class TouchView extends View {

    // Краски
    private Paint [] drawPaint = new Paint[6] ;
    // Рисунок храним в Bitmap
    private Bitmap drawBitmap;
    // Но используем Canvas,  так как у него больше
    // удобных методов для рисования
    private Canvas drawCanvas;

    // Высота и ширина вью и битмэпа
    private int h;
    private int w;

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Инициализация красок
        drawPaint[0] = new Paint();
        drawPaint[1] = new Paint();
        drawPaint[2] = new Paint();
        drawPaint[3] = new Paint();
        drawPaint[4] = new Paint();
        drawPaint[5] = new Paint();

        drawPaint[0].setStrokeWidth(20);
        drawPaint[1].setStrokeWidth(20);
        drawPaint[2].setStrokeWidth(20);
        drawPaint[3].setStrokeWidth(20);
        drawPaint[4].setStrokeWidth(20);
        drawPaint[5].setStrokeWidth(20);

        drawPaint[0].setStrokeCap(Paint.Cap.ROUND);
        drawPaint[1].setStrokeCap(Paint.Cap.ROUND);
        drawPaint[2].setStrokeCap(Paint.Cap.ROUND);
        drawPaint[3].setStrokeCap(Paint.Cap.ROUND);
        drawPaint[4].setStrokeCap(Paint.Cap.ROUND);
        drawPaint[5].setStrokeCap(Paint.Cap.ROUND);


        drawPaint[0].setColor(0xFFFF0000);
        drawPaint[1].setColor(0xFF00FF00);
        drawPaint[2].setColor(0xFF0000FF);
        drawPaint[3].setColor(0xFFFFFF00);
        drawPaint[4].setColor(0xff808080);
        drawPaint[5].setColor(0xFFFF00FF);

    }


    // Инициализация Bitmap и Canvas
    private void newCanvas()
    {
        // Создаем новый Bitmap с измененными размерами и с 8 битами
        // на каждый цвет и альфу
        drawBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(drawBitmap);

    }

    // Автоматически вызывается при изменении размера и создании
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        newCanvas();
    }

    // Вызывается при необходимости перерисовки
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(drawBitmap, 0, 0, null);
    }

    public void clearCanvas()
    {
        // Создаем новый Canvas
        newCanvas();
        // И просим перерисовать view
        invalidate();
    }

    // Координаты предыдущего события, проиндексированные по
    // идентификатору пальца
    Map<Integer, Pair<Float, Float>> lastCoordinates = new HashMap<>();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();

        switch (action) {
            // Выполняется при касании первого пальца
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                int actionIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(actionIndex);

                lastCoordinates.put(pointerId, new Pair<>(x, y));

                break;
            }
            // Выполняется при движении любого из пальцев по экрану
            case MotionEvent.ACTION_MOVE: {
                for(int pointerId : lastCoordinates.keySet()) {

                    // Log.d("happy", "pointerId: " + pointerId);

                    final int pointerIndex = ev.findPointerIndex(pointerId);

                    final float x = ev.getX(pointerIndex);
                    final float y = ev.getY(pointerIndex);
                    // Log.d("happy", "x: " + x + " y: " + y);

                    Pair<Float, Float> last = lastCoordinates.get(pointerId);
                    if(last != null) {
                        drawLine(pointerId, last.first, last.second, x, y);
                    }
                    lastCoordinates.put(pointerId, new Pair<>(x, y));
                }
                break;
            }
            // Выполняется при отрыве "крайнего" пальца от экрана
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                {
                lastCoordinates.clear();
                break;
            }

            // Выполняется при отрыве какого-либо из пальцев от экрана
            // за исключением "крайнего"
            case MotionEvent.ACTION_POINTER_UP: {
                int actionIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(actionIndex);
                lastCoordinates.remove(pointerId);
                break;
            }
            // При касании нового пальца, не первого
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                int actionIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(actionIndex);

                final float x = ev.getX(actionIndex);
                final float y = ev.getY(actionIndex);

                lastCoordinates.put(pointerId, new Pair<>(x, y));
                break;

            }
        }
        return true;
    }

    private void drawLine(int activePointerIndex, Float first, Float second, float x, float y) {

        drawCanvas.drawLine(first, second, x, y, drawPaint[activePointerIndex]);
        invalidate();
    }
}
