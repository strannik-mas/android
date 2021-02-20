package com.example.student1.allsaints;

import android.support.annotation.NonNull;

public class Saint implements Comparable<Saint> {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Saint(String name, String dob, String dod, float rating) {
        this.name = name;
        this.dob = dob;
        this.dod = dod;
        this.rating = rating;
    }

    private String name;
    private String dob;
    private String dod;
    private float rating = 0f;

    @Override
    public int compareTo(@NonNull Saint o) {
        return getName().compareTo(o.getName());
    }
}
