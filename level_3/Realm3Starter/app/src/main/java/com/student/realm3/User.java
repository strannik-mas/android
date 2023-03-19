package com.student.realm3;


import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class User  extends RealmObject{
    private String firstName;
    private String lastName;
    @Index
    private int age;

    @PrimaryKey
    private String pk;

    private void generatePK() {
        pk = String.format(
                "%s%s%d",
                Objects.toString(firstName, ""),
                Objects.toString(lastName, ""),
                age
        );
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        generatePK();
    }


    public String getFirstName() {
        return firstName;

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        generatePK();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        generatePK();

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        generatePK();
    }

    public User() {
        generatePK();
    }

    public String getPk() {
        return pk;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}