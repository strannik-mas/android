package com.student.databinding3;




public class MetroStation implements MetroObject {

    public MetroStation(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String getName() {
        return name;
    }
}