package com.example.gactranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TranslateActivity extends AppCompatActivity implements Observer<TranslateResult> {
    private EditText source;
    private TextView destination;
    private TranslateViewModel translateViewModel;
    public static final String LANGPAIR = "en|ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);

        translateViewModel = new ViewModelProvider(this).get(TranslateViewModel.class);
        /*LiveData<TranslateResult> result = translateViewModel.getTranslate(
                source.getText().toString(),
                LANGPAIR
        );
        result.observe(this, aTrRes -> destination.setText(aTrRes.getResult()));*/
    }

    public void translate(View view) {
        String sentence = source.getText().toString();
        if (!TextUtils.isEmpty(sentence)) {
            translateViewModel.getTranslate(sentence, LANGPAIR).observe(this, this);
        }
    }

    @Override
    public void onChanged(TranslateResult result) {
        if(result != null) {
            destination.setText(result.getResult());
        }
    }
}