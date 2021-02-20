package com.example.strannik.metropicker;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends ListActivity implements AdapterView.OnItemClickListener {

    static final String STATION_NAME = "Station name";
    String[] mStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources r = getResources();
        mStations = r.getStringArray(R.array.stations);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item, mStations);
        setListAdapter(aa);
        ListView lv = getListView();

        lv.setOnItemClickListener(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        //Toast.makeText(this, action, Toast.LENGTH_LONG).show();

        //регистрация контекстного меню для строки странции
        registerForContextMenu(lv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundColor(Color.GRAY);
        CharSequence station = ((TextView) view).getText();
        sendStation(station);

        Toast.makeText(this, station, Toast.LENGTH_LONG).show();
    }

    private void sendStation(CharSequence station) {
        Intent result = new Intent(this, MainActivity.class);
        result.putExtra(STATION_NAME, station);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, R.string.menu_2);
        Toast.makeText(this, R.string.menu_2, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(
            ContextMenu menu,
            View v,
            ContextMenu.ContextMenuInfo menuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_station_pick, menu);
        //Log.e("onCreateContextMenu", v.toString());
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        CharSequence stationSelected = getStationOnMenuInfo(info);
        menu.setHeaderTitle(stationSelected);
    }

    private CharSequence getStationOnMenuInfo(AdapterView.AdapterContextMenuInfo info) {
        return ((TextView)info.targetView).getText();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_send:
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                CharSequence stationSelected = getStationOnMenuInfo(info);
                sendStation(stationSelected);
                return true;
            case R.id.item_exit:
                finish();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
