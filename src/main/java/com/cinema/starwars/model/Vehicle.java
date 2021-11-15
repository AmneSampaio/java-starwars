package com.cinema.starwars.model;

import java.lang.String;

public class Vehicle {

    private int id;
    private String name;
    private String model;

    public Vehicle(String ... args) {
        System.out.println(args);
    }

    public Vehicle(int id) {
        this.id = id;
    }

    public Vehicle(int id, String name, String model) {
        this.id = id;
        this.name = name;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
