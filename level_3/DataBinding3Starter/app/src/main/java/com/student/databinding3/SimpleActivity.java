package com.student.databinding3;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.student.databinding3.data.User;
import com.student.databinding3.databinding.ActivitySimpleBinding;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_simple);
        com.student.databinding3.databinding.ActivitySimpleBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_simple);

        User u = new User("Иван", "Перепухов", 23); // , false);
        NameChangeHandler h = new NameChangeHandler();

        binding.setUser(u);
        binding.setHandler(h);
        binding.setEdit(binding.newname);

        String s = "https://s9.travelask.ru/system/images/files/001/480/243/wysiwyg_jpg/2.jpg?1625518462";
        binding.setUrl(s);

        /*binding.changename.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );*/

        /*binding.changename.setOnClickListener(
                v -> {
                    String name = binding.newname.getText().toString();
                    if (!TextUtils.isEmpty(name)) {
                        u.setLastName(name);
                        //binding.invalidateAll();
                    }
                }
        );*/


    }
}
