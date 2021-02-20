package com.example.student1.fragmentdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyDialog.MyDialogHost
{
    public static final String DIALOG_KEY = "DIALOG_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // При нажатии на "положительную" или "отрицательную"
    // кнопки в диалоге, показывать соответствующий "тост".


    // Обработка нажатия на виджет
    public void showDialog(View view) {
        // Создаем диалог
        MyDialog dialog = new MyDialog();

        // Показываем диалог
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void positive() {
        Toast.makeText(this, "Файл удлён", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void negative() {
        Toast.makeText(this, "Операция отменена", Toast.LENGTH_SHORT).show();
    }
}







