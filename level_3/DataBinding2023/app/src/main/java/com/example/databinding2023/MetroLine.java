package com.example.databinding2023;

public class MetroLine implements MetroObject {
    private String name;

    public MetroLine(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
