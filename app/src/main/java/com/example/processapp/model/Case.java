package com.example.processapp.model;

public class Case {

    private String name;
    private String state;

    public Case(String name, String state) {
        this.name = name;
        this.state = state;

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Case{" +
                "state='" + state + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
