package com.strannik.animals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private AnimalsHelper helper;
    private SimpleCursorAdapter adapter;
    private String selection;
    private String orderBy;
    private String[] selectionArgs;
    private String likeQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        list.setEmptyView(findViewById(R.id.notfound));

        registerForContextMenu(list);

        helper = new AnimalsHelper(this);

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{AnimalsTable.COLUMN_ANIMAL},
                new int[]{
                        android.R.id.text1
                },
                0
        );
        list.setAdapter(adapter);

        updateCursor();
    }

    private void updateCursor() {
        Cursor cursor = helper.getReadableDatabase().query(
                AnimalsTable.TABLE_ANIMALS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );
        adapter.swapCursor(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_up:
                orderBy = AnimalsTable.COLUMN_ANIMAL + " asc";
                updateCursor();
                return true;
            case R.id.main_down:
                orderBy = AnimalsTable.COLUMN_ANIMAL + " desc";
                updateCursor();
                return true;
            case R.id.main_search:
                handleSearch(item);
                return true;
            case R.id.main_add:
                addAnimal();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addAnimal() {
        //поджигаем диалог
        final EditText edit = (EditText) LayoutInflater
                .from(this)
                .inflate(R.layout.dialog, null);


        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Add Animal");
        b.setView(edit);
        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String animal = edit.getText().toString();
                addAnAnimal(animal);
                dialog.dismiss();
            }
        });
        b.create().show();
    }

    private void addAnAnimal(String animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalsTable.COLUMN_ANIMAL, animal);

        helper.getWritableDatabase().insert(
                AnimalsTable.TABLE_ANIMALS,
                null,
                values
        );
        updateCursor();
    }


    private void handleSearch(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        item.expandActionView();
        searchView.setQuery(likeQuery, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    selection = AnimalsTable.COLUMN_ANIMAL + " like ?";
                    selectionArgs = new String[]{
                            "%"+ newText + "%"
                    };
                } else {
                    selectionArgs = null;
                    selection = null;
//                    return false;
                }

                likeQuery = newText;
                updateCursor();

                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //для того, чтобы понять у какого элемента меню вызвали контекстное меню
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.context_delete:
                deleteAnimal(info.position);
                return true;
            case R.id.context_update:
                updateAnimalDialog(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void updateAnimalDialog(int position) {
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);

        final String databaseId = cursor.getString(
                cursor.getColumnIndex(AnimalsTable.COLUMN_ID)
        );
        String animal = cursor.getString(
                cursor.getColumnIndex(AnimalsTable.COLUMN_ANIMAL)
        );

        //поджигаем диалог
        final EditText edit = (EditText) LayoutInflater
                .from(this)
                .inflate(R.layout.dialog, null);
        edit.setText(animal);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Update Animal");
        b.setView(edit);
        b.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String animal = edit.getText().toString();
                updateAnAnimal(databaseId, animal);
                dialog.dismiss();
            }
        });
        b.create().show();
    }

    private void updateAnAnimal(String databaseId, String animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalsTable.COLUMN_ANIMAL, animal);

        helper.getWritableDatabase().update(
                AnimalsTable.TABLE_ANIMALS,
                values,
                AnimalsTable.COLUMN_ID + " = ?",
                new String[]{databaseId}
        );
        updateCursor();
    }

    private void deleteAnimal(int position) {
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        String databaseId = cursor.getString(
                cursor.getColumnIndex(AnimalsTable.COLUMN_ID)
        );
        helper.getWritableDatabase().delete(
                AnimalsTable.TABLE_ANIMALS,
                AnimalsTable.COLUMN_ID + "=?",
                new String[]{databaseId}
        );
        //delete from animals where _id = 3;
        updateCursor();
    }
}