package su.strannik.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditActivity";
    private EditText edit;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        Log.d(TAG, "onCreate: aaa");

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.NAME);
        if (name != null) {
            edit.setText(name);
        }

        button.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: bbb");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: cc");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ddd");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ppp");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: eee");
    }

    @Override
    public void onClick(View v) {
        String name = edit.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(MainActivity.NAME, name);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
}