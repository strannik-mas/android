package com.example.alex.controlssample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS = "PREFS";
    static final String KEY_CHECK = "checkbox";
    static final String KEY_TOGGLE = "toggleButton";
    static final String KEY_RADIO = "radioButton";
    static final String KEY_NAME = "editField";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        final SharedPreferences.Editor editor = getEditor();

        assert userName != null;
        userName.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //сохранение состояния приложения
                    editor.putString(KEY_NAME, String.valueOf(userName.getText()));
                    editor.commit();
                    Toast.makeText(
                            getApplicationContext(),
                            userName.getText(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EditText userName = (EditText) findViewById(R.id.user_name);
        String name = prefs.getString(KEY_NAME, null);
        if (userName != null) {
            userName.setText(prefs.getString(KEY_NAME, null));
        }

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        if (checkBox != null) {
            checkBox.setChecked(prefs.getBoolean(KEY_CHECK, false));
        }

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle);
        if (toggleButton != null) {
            toggleButton.setChecked(prefs.getBoolean(KEY_TOGGLE, false));
        }

        int radioId = prefs.getInt(KEY_RADIO, 0);
        Log.e("getRadioId", String.valueOf(radioId));
        if (radioId > 0) {
            RadioButton radioButton = (RadioButton) findViewById(radioId);
            if (radioButton != null) {
                radioButton.setChecked(true);
                Toast.makeText(this, "kuku", Toast.LENGTH_SHORT).show();
            }
        }
        //Toast.makeText(this, radioId, Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        SharedPreferences.Editor editor = getEditor();
        if (((CheckBox) view).isChecked()) {
            //сохранение состояния приложения
            editor.putBoolean(KEY_CHECK, true);
            editor.commit();
            Toast.makeText(this, "Отмечено", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Не отмечено", Toast.LENGTH_SHORT).show();
            //сохранение состояния приложения
            editor.putBoolean(KEY_CHECK, false);
            editor.commit();
        }
    }

    public void onToggleClicked(View view) {
        SharedPreferences.Editor editor = getEditor();
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(this, "Включено", Toast.LENGTH_SHORT).show();
            //сохранение состояния приложения
            editor.putBoolean(KEY_TOGGLE, true);
            editor.commit();
        } else {
            Toast.makeText(this, "Выключено", Toast.LENGTH_SHORT).show();
            //сохранение состояния приложения
            editor.putBoolean(KEY_TOGGLE, false);
            editor.commit();
        }
    }

    private SharedPreferences.Editor getEditor() {
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        return prefs.edit();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton rb = (RadioButton) view;
        SharedPreferences.Editor editor = getEditor();
        int id = view.getId();
        Log.e("setRadioId", String.valueOf(id));
        editor.putInt(KEY_RADIO, id);
        editor.apply();

        Toast.makeText(this, "Выбрано животное: " + rb.getText(), Toast.LENGTH_SHORT).show();
    }
}
