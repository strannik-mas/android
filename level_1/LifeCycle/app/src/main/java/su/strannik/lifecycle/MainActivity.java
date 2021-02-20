package su.strannik.lifecycle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String NAME = "NAME";
    public static final int NAME_CODE = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: aaa");
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

    public void startEdit(View view) {
        Intent intent = new Intent(this, EditActivity.class);

        intent.putExtra(NAME, "sasha");
        intent.putExtra("user", new User());

        //startActivity(intent);
        startActivityForResult(intent, NAME_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: reqCode = " + requestCode);
        if (requestCode == NAME_CODE) {
            String name = data.getStringExtra(NAME);
            Log.d(TAG, "onActivityResult: name2-" + name);
            if (name != null) {
                Toast.makeText(this, "Name: " + name, Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}