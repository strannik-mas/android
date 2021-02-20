package com.strannik.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    public static final int REQUEST_READ_CONTACTS = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        showContactsWrapper();
    }

    private void showContactsWrapper() {
        //проверяем версию android API и разрешения
        if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                                PackageManager.PERMISSION_GRANTED
        ) {
            //запрос разрешений чтения контактов
            requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS
            );
        } else {
            //показать контакты (версия < 6 или разрешение уже есть
            Cursor cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );

            ListAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    //массив данных для подстановки в layout в 2 текстовых поля
                    new String[]{
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    },
                    new int[] {
                            android.R.id.text1,
                            android.R.id.text2
                    },
                    0
            );
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            //поскольку только один запрос - то 0 элемент
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showContactsWrapper();
            } else {
                Toast.makeText(this, "day razr", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}