package com.student.databinding3;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.student.databinding3.data.User;

public class NameChangeHandler {

    public void nameChanged(View v) {
        Toast.makeText(v.getContext(), "Name changed!", Toast.LENGTH_SHORT).show();
    }

    public void change(View v, User u, EditText e) {
        String name = e.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            u.setLastName(name);
        }
    }
}
