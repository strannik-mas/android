package com.example.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddItemActivity extends AppCompatActivity {
    public static final String TYPE_KEY = "type";
    private static final String TAG = "AddItemActivity";
    private EditText name;
    private EditText price;
    private Button addBtn;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

//        setTitle(R.string.add_item_title);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.add_item_screen_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.add_btn);

        type = getIntent().getStringExtra(TYPE_KEY);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + s);
                String priceText = price.getText().toString();
                addBtn.setEnabled(!TextUtils.isEmpty(name.getText().toString()) &&
                        !TextUtils.isEmpty(priceText));
                if (!TextUtils.isEmpty(priceText)) {
                    if (!priceText.contains("₴")) {
                        String newPriceText = priceText  + " ₴";
                        price.setText(newPriceText);
                        price.setSelection(1);
                    }
                }
            }
        };

        name.addTextChangedListener(textWatcher);
        price.addTextChangedListener(textWatcher);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = name.getText().toString();
                String itemPrice = price.getText().toString();

                Item item = new Item(itemPrice, itemName, type);

                Intent intent = new Intent();
                /*intent.putExtra("name", itemName);
                intent.putExtra("price", itemPrice);*/
                intent.putExtra("item", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //чтобы просто закрыть экран без интента
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
