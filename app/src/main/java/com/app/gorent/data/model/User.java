package com.app.gorent.data.model;

/**
 * @author Juan Camilo Velandia Botello
 */
public class User {

    private String userId;
    private String full_name;
    private String email;
    private String password;
    private Location home;

    public User() {
    }

    public User(String full_name, String email, String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
    }

    public User(String full_name, String email) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "-User:" + "\r\n" +
                "Name: " + full_name + "\r\n" +
                "Email: " + email;
    }
}
