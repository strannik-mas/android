package com.example.student1.allsaints;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "MainActivity";
    // Константы для передачи данных через Intent
    // Должны быть доступны "детальной" Activity
    public static final String SAINT_NAME =    "SAINT_NAME";
    public static final String SAINT_ID =      "SAINT_ID";
    public static final String SAINT_RATING =  "SAINT_RATING";

    public static final int    RATING_REQUEST = 777;

    private ListView list;

    private List<Saint> saints = new ArrayList<>();
    private SaintAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);

        // Источник данных для парсера XML из ресурсов
        InputSource mySaints = new InputSource(getResources().openRawResource(R.raw.saints));
        // Новый XPath запрос
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        // Подробно об XPath
        // http://www.w3schools.com/xsl/xpath_syntax.asp

        // Собственно запрос 
        String  expression = "/saints/saint";

        NodeList nodes;
        try {
            // Результат XPath запроса - набор узлов
            nodes = (NodeList) xPath.evaluate(expression, mySaints, XPathConstants.NODESET);
            if(nodes != null) {
                int numSaints = nodes.getLength();
                // Для каждого из узлов 
                for (int i = 0; i < numSaints; i++)
                {
                    // Узел
                    Node saint = nodes.item(i);
                    ///
//                    String name = saint.getFirstChild().getTextContent();
                    String name = saint.getChildNodes().item(1).getTextContent();
                    String dob = saint.getChildNodes().item(3).getTextContent();
                    String dod = saint.getChildNodes().item(5).getTextContent();

                    //Log.d("happy", "dob: " + dob);
                    Saint s = new Saint(name, dob, dod, 0f);


                    saints.add(s);

                    //Log.d("happy", "dod: " + dod);

                }
            }
        }
        catch (Exception e)
        {

        }
        Collections.sort(saints);

        adapter = new SaintAdapter(this, R.layout.listviewitem, saints);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);

        //для удаления элементов
        list.setOnItemLongClickListener(this);

    }

    // Вызывается при создании контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // Вызывается при выборе элемента контекстного меню
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ///
        return super.onContextItemSelected(item);
    }


    // Вызывается при создании меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //при выделенных элементах - только корзина
        if (adapter.hasSelected()) {
            getMenuInflater().inflate(R.menu.delete, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    // Вызывается при выборе элемента меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //сортировка по имени
        switch (item.getItemId())
        {
            case R.id.menu_down:
                Collections.sort(saints, Collections.<Saint>reverseOrder());
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_up:
                Collections.sort(saints);
                adapter.notifyDataSetChanged();
                return false;
            case R.id.menu_add:
                showAddDialog();
                return true;
            case R.id.main_delete:
                adapter.deleteSelected();
                //перестройка меню
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog() {
        View dialog = getLayoutInflater().inflate(R.layout.dialog_add,  null);
        final EditText text = (EditText) dialog.findViewById(R.id.dialog_add);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setView(dialog)
                .setTitle("Add a Saint!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = text.getText().toString();
                        saints.add(
                                new Saint(name, "", "", 0f)
                        );
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(! adapter.hasSelected()) {
            Intent intent = new Intent(this, SaintDetail.class);
            Saint s = saints.get(position);
            Log.d(TAG, "onItemClick: nameIntent = " + s.getName());
            intent.putExtra(SAINT_ID, position);
            intent.putExtra(SAINT_NAME, s.getName());
            intent.putExtra(SAINT_RATING, s.getRating());

            startActivityForResult(intent, RATING_REQUEST);
        } else {
            toggleSelection(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RATING_REQUEST && resultCode == RESULT_OK) {
            float rating = data.getFloatExtra(SAINT_RATING, -1f);
            int id = data.getIntExtra(SAINT_ID, -1);
            if (rating >= 0 && id >= 0) {
                Saint s = saints.get(id);
                s.setRating(rating);
                adapter.notifyDataSetChanged();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        toggleSelection(position);
        return true;
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        //изменение меню - только корзина должна остаться
        invalidateOptionsMenu();
    }
}
