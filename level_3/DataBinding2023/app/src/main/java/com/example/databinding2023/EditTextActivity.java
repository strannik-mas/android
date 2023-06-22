package com.example.databinding2023;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding2023.data.User;
import com.example.databinding2023.databinding.ActivityEditTextBinding;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        ActivityEditTextBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_text);

        User u = new User("Иван", "Suka", 40);
        binding.setUser(u);
    }
}