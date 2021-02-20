package com.example.alex.listviewsample;

import android.app.ListActivity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

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

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundColor(Color.GRAY);
        /*TextView textView = (TextView) view;
        textView.setBackgroundColor(Color.RED);*/
        Toast.makeText(this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
    }
}
