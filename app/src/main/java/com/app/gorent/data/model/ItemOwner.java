package com.app.gorent.data.model;

public class ItemOwner {

    private Long id;
    private String full_name;
    private String email;
    private Location home;

    public ItemOwner(Long id, String full_name, String email) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
