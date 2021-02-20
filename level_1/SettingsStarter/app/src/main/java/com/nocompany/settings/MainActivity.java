package com.nocompany.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String LOGIN_KEY = "LOGIN_KEY";
    private SharedPreferences prefs;
    private EditText text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(
                this,
                R.xml.preferences,
                false
        );

        text = (EditText) findViewById(R.id.some_text);
        button = (Button) findViewById(R.id.button);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String login = prefs.getString(LOGIN_KEY, "");

        text.setText(login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToSave = text.getText().toString();

                SharedPreferences.Editor editor = prefs.edit().putString(LOGIN_KEY, textToSave);
                editor.apply();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            case R.id.menu_main_login:
                String l = prefs.getString("pref_accountLogin", "empty");
                Toast.makeText(this, "login: " + l, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
