package com.example.alex.contactssample;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    public TextView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = (TextView) findViewById(R.id.tv);

        getContacts();
        /*String name;
        ArrayList al = new ArrayList();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ListView contactList = (ListView) findViewById(R.id.contactList);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                al.add(Arrays.asList(name));
            }while (cursor.moveToNext());
            ArrayAdapter aa = new ArrayAdapter<String>(this, R.layout.list_item, al);
            contactList.setAdapter(aa);
        }*/
    }

    private void getContacts() {
        String phoneNumber = null;

        Uri content_uri = ContactsContract.Contacts.CONTENT_URI;
        String id = ContactsContract.Contacts._ID;
        String display_name = ContactsContract.Contacts.DISPLAY_NAME;
        String has_phone_number = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri phone_content_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String phone_contact_id = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;

        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(content_uri, null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String contact_id = cursor.getString(cursor.getColumnIndex(id));
                    String name = cursor.getString(cursor.getColumnIndex(display_name));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(has_phone_number)));

                    output.append("\nИмя: " + name);

                    if(hasPhoneNumber > 0) {
                        Cursor phoneCursor = contentResolver.query(phone_content_uri, null,
                                phone_contact_id + " = ?", new String[] {contact_id}, null);

                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(number));
                            output.append("\n Телефон: " + phoneNumber);
                        }
                    }
                    output.append("\n");
                }
                contacts.setText(output);
            }
        }
    }
}
