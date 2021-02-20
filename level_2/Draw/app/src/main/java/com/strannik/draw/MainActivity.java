package com.strannik.draw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DrawView drawView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = findViewById(R.id.hostCanvas);

        if(savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("picture");
            drawView.restorePicture(bitmap);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        Bitmap b = drawView.getBitmap();
        Log.d(TAG, "onSaveInstanceState: " + b);
        outState.putParcelable("picture", b);
    }

    public void colorClick(View view) {
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        int colorId = colorDrawable.getColor();

        drawView.paint.setColor(colorId);
    }

    public void canvasReset(View view) {
        drawView.erase();
    }
}