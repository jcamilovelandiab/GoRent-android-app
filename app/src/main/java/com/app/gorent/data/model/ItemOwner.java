package com.app.gorent.data.model;

public class ItemOwner {

    private String full_name;
    private String email;

    public ItemOwner() {
    }

    public ItemOwner(String email, String full_name) {
        this.full_name = full_name;
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "-Item Owner" + "\r\n" +
                "  Name: " + full_name + "\r\n" +
                "  Email: " + email;
    }
}
