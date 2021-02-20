package su.strannik.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printBtnNumber(View view) {
        AppCompatButton btn = (AppCompatButton) view;
        String newText = "";
        //Log.d(TAG, "printBtnNumber: " + btn.getText());
        //Toast.makeText(this, "adf", Toast.LENGTH_SHORT).show();
        TextView screen = findViewById(R.id.screen);
        String oldText = screen.getText().toString();
        String btnText = btn.getText().toString();
        if (btnText.equals("CE")) {
            //Log.i(TAG, "printBtnNumber: " + btnText);
            newText = null;
        } else {
            newText = oldText + btnText;
        }
        screen.setText(newText);
    }

    @SuppressLint("DefaultLocale")
    public void countExpression(View view) {
        double result = 0;
        String tmpZnak;
        int i = 0;
        TextView screen = findViewById(R.id.screen);
        String expression = screen.getText().toString();
        Pattern pattern = Pattern.compile("\\d+");
        Pattern pattern2 = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(expression);
        Matcher matcher2 = pattern2.matcher(expression);
        Log.d(TAG, String.format("countExpression: matshes: " + matcher2.matches()));
        while (matcher.find()) {
            if (result == 0) {
                result = Double.parseDouble(matcher.group());
            } else {
                Log.i(TAG, "countExpression: resTmp index = " + i);
                if (i <= 2) {
                    matcher2.find(i);
                } else {
                    matcher2.find(i + 1);
                }
                tmpZnak = matcher2.group();
                switch (tmpZnak) {
                    case "/":
                        result /= Double.parseDouble(matcher.group());
                        break;
                    case "x":
                        result *= Double.parseDouble(matcher.group());
                        break;
                    case "+":
                        result += Double.parseDouble(matcher.group());
                        break;
                    case "-":
                        result -= Double.parseDouble(matcher.group());
                        break;
                }
                Log.i(TAG, "countExpression: resTmp znak = " + tmpZnak);
                Log.d(TAG, "countExpression: resTmp = " + result);
            }
            i++;
        }

        screen.setText(String.format("%s\n=\n%f", expression, result));
    }
}