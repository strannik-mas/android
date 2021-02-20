package com.example.strannik.metropicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 1;
    private static final String PICK_METRO_STATION =
            "com.example.strannik.metropicker.intent.action.PICK_METRO_STATION";

    private String selectedStation;
    TextView mSelectedStation;
    Storage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectedStation = (TextView) findViewById(R.id.selectedStation);
        mStorage = new Storage(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSelectedStation.setText(mStorage.getStation());

        Intent intent = getIntent();
        String action = intent.getAction();
        Toast.makeText(this, action, Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {
        //Intent i = new Intent(this, ListViewActivity.class);
        Intent i = new Intent(PICK_METRO_STATION);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, REQ_CODE);
        } else {
            Toast.makeText(this, R.string.no_activities_msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                selectedStation = data.getStringExtra(ListViewActivity.STATION_NAME);
                    mStorage.setStation(selectedStation);
            } else {
                mStorage.setStation(null);
            }
            mSelectedStation.setText(mStorage.getStation());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear:
                mStorage.setStation(null);
                mSelectedStation.setText(mStorage.getStation());
                return true;
            case R.id.item_exit:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
