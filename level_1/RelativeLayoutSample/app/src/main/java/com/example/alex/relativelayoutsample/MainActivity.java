package com.example.alex.relativelayoutsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //EditText mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mUserName = (EditText) findViewById(R.id.entry);
    }

    public void onClick(View view) {
        EditText userName = (EditText) findViewById(R.id.entry);
        if (userName != null) {
            switch (view.getId()) {
                case R.id.okButton:
                    String text = userName.getText().toString().trim();
                    if (text.length() > 0) {
                        Toast.makeText(
                                this,
                                getString(R.string.hello_pref) + text, Toast.LENGTH_LONG
                        ).show();
                    }
                    userName.setText(null);
                    break;
                case R.id.cancelButton:
                    userName.setText(null);
                    break;
                default:
                    Toast.makeText(this, R.string.oops_msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
