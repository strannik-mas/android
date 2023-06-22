package com.example.databinding2023.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.databinding2023.BR;

public class User extends BaseObservable {
    private static final String TAG = "happy";
    private String firstName;
    private String lastName;
    private int age;

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }


    public boolean isAdult()
    {
        return age >= 16;
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
        Log.d(TAG, "user " + this);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
        Log.d(TAG, "user " + this);
    }

    public void firstNameChanged (CharSequence s) {
        String first = s.toString();
        if (!TextUtils.isEmpty(first) && !first.equals(firstName)) {
            setFirstName(first);
        }
    }

    public void lastNameChanged (CharSequence s) {
        String last = s.toString();
        if (!TextUtils.isEmpty(last) && !last.equals(lastName)) {
            setLastName(last);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
