package su.strannik.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.edit);
    }

    public void start(View view) {
        String data = edit.getText().toString();
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(data)
        );
        PackageManager manager = getPackageManager();
        if (intent.resolveActivity(manager) != null) {
            startActivity(intent);
        }
    }
}