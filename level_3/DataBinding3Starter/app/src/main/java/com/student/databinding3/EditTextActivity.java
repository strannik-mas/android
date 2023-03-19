package com.student.databinding3;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.student.databinding3.data.User;
import com.student.databinding3.databinding.ActivityEditTextBinding;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.student.databinding3.databinding.ActivityEditTextBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_edit_text);

        User u = new User("Иван", "Ivanov", 25);

        binding.setUser(u);
    }
}