package com.example.alex.animsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ship = (ImageView) findViewById(R.id.shipView);
        Animation shipAnim = AnimationUtils.loadAnimation(this, R.anim.ship_anim);
        if (ship != null) {
            ship.startAnimation(shipAnim);
        }
    }
}
