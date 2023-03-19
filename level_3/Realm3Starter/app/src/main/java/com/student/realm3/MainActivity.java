package com.student.realm3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        RealmChangeListener<RealmResults<User>> {

    private static final String TAG = "happy";
    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private ListView list;
    private Button create;

    private UserAdapter adapter;

    private Realm realm = Realm.getDefaultInstance();

    private RealmResults<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        create = findViewById(R.id.add);
        list = findViewById(R.id.list);

        create.setOnClickListener(this);

        //users = realm.where(User.class).findAll();
        users = realm.where(User.class).greaterThan("age", 14).findAll();

        adapter = new UserAdapter(users);
        list.setAdapter(adapter);

        users.addChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //отписка т.к. активность будет засорять память
        users.removeAllChangeListeners();
        users = null;
        realm.close();
    }

    @Override
    public void onClick(View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        int a = 12;

        try {
            String aText = age.getText().toString();
            a = Integer.parseInt(aText);
        }catch (Exception e) {

        }

        User u = new User(first, last, a);

        realm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(u));
    }


    @Override
    public void onChange(RealmResults<User> users) {
        adapter.update(users);
        /*for(User u : users) {
            Log.d(TAG, "onChange: user: " + u);
        }*/
    }
}
