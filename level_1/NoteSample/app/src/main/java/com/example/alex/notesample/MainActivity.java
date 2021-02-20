package com.example.alex.notesample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String[] FIELDS = {"_id", DbOpenHelper.COLUMN_NOTE, DbOpenHelper.COLUMN_TIME,};
    private static final String[] FROM = {DbOpenHelper.COLUMN_NOTE, DbOpenHelper.COLUMN_TIME,};

    //    private static final int[] TO = {R.id.noteView,};
    private static final int[] TO = {android.R.id.text1, android.R.id.text2,};

    private static final java.lang.String ORDER = DbOpenHelper.COLUMN_TIME + " DESC";
    //private ArrayAdapter<String> mArrayAdapter;

    private static String formatDate(long timeStamp) {
        try {
            Locale locale = new Locale("ru");
            SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy hh:mm", locale);
            Date date = new Date(timeStamp);
            String tmp = format.format(date);
            return format.format(date);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат строки!");
            return null;
        }
    }

    EditText mInputField;
    ListView mNotesList;
    DbOpenHelper mHelper = new DbOpenHelper(this);
    SQLiteDatabase mDb;
    SimpleCursorAdapter mAdapter;
    long mNoteId = -1;
    private String mOldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputField = (EditText) findViewById(R.id.inputField);
//        mAdapter = new SimpleCursorAdapter(this, R.layout.note, null, FROM, TO, 0);
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, FROM, TO, 0);
        mNotesList = (ListView) findViewById(R.id.notesList);
        mNotesList.setAdapter(mAdapter);

        //регистрация контекстного меню
        registerForContextMenu(mNotesList);
        registerForContextMenu(mInputField);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //проверяем для того ли элемента вызвано меню
        switch (v.getId()) {
            case R.id.notesList:
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.notes_menu, menu);
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit: {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mOldNote = getNoteById(info.id);    //save for future use
                mInputField.setText(mOldNote);      //fill in input field
                mNoteId = info.id;                  //save for future use
                return true;
            }
            case R.id.item_delete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                deleteNote(info.id);
                showNotes();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private String getNoteById(long id) {
        mDb = (mDb == null) ? mHelper.getWritableDatabase() : mDb;
        Cursor cursor = mDb.query(DbOpenHelper.DB_TABLE, FIELDS, "_id = " + id, null, null, null, null);

        String note = null;
        if (cursor != null) {
            cursor.moveToFirst();   //поставить в первую позицию в результате
            note = cursor.getString(cursor.getColumnIndexOrThrow(DbOpenHelper.COLUMN_NOTE));
            cursor.close();
        }
        return note;
    }

    private void deleteNote(long id) {
        mDb = (mDb == null) ? mHelper.getWritableDatabase() : mDb;
        mDb.delete(DbOpenHelper.DB_TABLE, "_id = " + id, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {
        /*String cDate = null;
        String note = null;
        ArrayList al = new ArrayList();*/
        mDb = (mDb == null) ? mHelper.getWritableDatabase() : mDb;
        Cursor cursor = mDb.query(DbOpenHelper.DB_TABLE, FIELDS, null, null, null, null, ORDER);
        /*if (cursor != null) {
            cursor.moveToFirst();
            do {
                cDate = formatDate(cursor.getLong(cursor.getColumnIndexOrThrow(DbOpenHelper.COLUMN_TIME)));
                note = cursor.getString(cursor.getColumnIndexOrThrow(DbOpenHelper.COLUMN_NOTE));
                al.add(Arrays.asList(cDate, note));
            }while (cursor.moveToNext());
            mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, al);
        }*/

        mAdapter.swapCursor(cursor);
        //mNotesList.setAdapter(mArrayAdapter);
    }

    public void onOkButtonClick(View view) {
        String note = mInputField.getText().toString().trim();
        if(note.length() > 0) {
            ContentValues values = new ContentValues(1);
            values.put(DbOpenHelper.COLUMN_NOTE, note);
            mDb = (mDb == null) ? mHelper.getWritableDatabase() : mDb;

            if (mNoteId >= 0) {
                mDb.update(DbOpenHelper.DB_TABLE, values, "_id = " + mNoteId, null);
            } else {
                mDb.insert(DbOpenHelper.DB_TABLE, null, values);
            }
            showNotes();
        }
        mNoteId = -1;
        mOldNote = null;
        mInputField.setText(null);
    }
}
