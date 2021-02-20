package com.strannik.currencyexchange_retrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_CHAR_CODE = "CharCode";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_NOMINAL = "Nominal";
    private static final String KEY_NAME = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        ListView listView = findViewById(R.id.list);

        NetworkService.getInstance()
                .getXMLApi()
                .getRatesForCurrentDay()
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Map<String, String> m;
                            Result result = response.body();
                            if (result != null) {
                                String date = result.getDate();
                                Log.i(TAG, "onResponse: " + date);
                                setTitle(getTitle() + " на " + date);

                                List<Valute> valutes = result.getValuteList();
                                if (valutes != null && valutes.size() > 0) {
                                    for(Valute valute: valutes) {
                                        m = new HashMap<String, String>();
                                        String charCode = valute.getCharCode();
                                        String value = valute.getValue();
                                        String nominal = "за " + valute.getNominal();
                                        String name = valute.getName();
                                        /*Log.d(TAG, "onResponse: charCode = " + charCode);
                                        Log.d(TAG, "onResponse: value = " + value);*/

                                        m.put(KEY_CHAR_CODE, charCode);
                                        m.put(KEY_VALUE, value);
                                        m.put(KEY_NOMINAL, nominal);
                                        m.put(KEY_NAME, name);
                                        list.add(m);
                                    }
                                }
                            }
                            String[] from = {KEY_CHAR_CODE, KEY_VALUE, KEY_NOMINAL, KEY_NAME};
                            int[] to = {R.id.charCodeView, R.id.valueView, R.id.nominalView, R.id.nameView};
                            SimpleAdapter sa = new SimpleAdapter(getBaseContext(), list, R.layout.item_view, from, to);

                            listView.setAdapter(sa);
                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable throwable) {
                        Log.e(TAG, "onFailure: " + throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }
}