package com.app.gorent.data.model;

public class ItemOwner {

    private String userId;
    private String full_name;
    private String email;
    private Location home;

    public ItemOwner(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "-Item Owner" + "\r\n" +
                "Name: " + full_name + "\r\n" +
                "Email: " + email;
    }
}
