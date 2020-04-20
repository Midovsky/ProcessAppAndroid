package com.example.processapp.model;

public class Wkf {

    private String id;
    private String name;

    public Wkf(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Wkf{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
