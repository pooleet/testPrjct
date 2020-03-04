package org.example.model;

public class Company {
    private int id;
    private String schet;
    private String name;

    public Company(int id, String schet, String name) {
        this.id = id;
        this.schet = schet;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchet() {
        return schet;
    }

    public void setSchet(String schet) {
        this.schet = schet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
