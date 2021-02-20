package com.example.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Parcelable {
    public static final String TYPE_UNCNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";

    public  int id;
    public String price;
    /*@SerializedName("name")         //для переназначения json полей
    public String title;*/
    public String name;
    public String type;

    public Item(String price, String name, String type) {
        this.price = price;
        this.name = name;
        this.type = type;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        price = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(price);
        dest.writeString(name);
        dest.writeString(type);
    }
 /*
    public Item(String title, int price) {
        this.price = price;
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }*/
}
