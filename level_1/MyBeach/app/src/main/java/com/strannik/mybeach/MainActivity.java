package com.strannik.mybeach;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void orderTrip(View view) {
        //Toast.makeText(this, "You buy me!", Toast.LENGTH_SHORT).show();
        Snackbar.make(findViewById(R.id.coordinator), "Ваше путешествие заказано!", Snackbar.LENGTH_LONG).show();
    }
}