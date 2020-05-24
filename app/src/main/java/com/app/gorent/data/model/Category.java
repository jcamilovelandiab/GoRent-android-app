package com.app.gorent.data.model;

import java.util.List;

public class Category {

    private String id;
    private String name;
    private String description;

    public Category() {
    }

    public Category(String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        /*return "-Category" + "\r\n" +
                "Name: " + name + "\r\n" +
                "Description: " + description;*/
        return "-Category: "+name;
    }
}
