package com.app.gorent.data.model;

/**
 * @author Juan Camilo Velandia Botello
 */
public class User {

    private String full_name;
    private String email;
    private String password;
    private Role role;

    public User() {
        this.role = Role.user;
    }

    public User(String full_name, String email, String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.role = Role.user;
    }

    public User(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;
        this.role = Role.user;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "-User:" + "\r\n" +
                "Name: " + full_name + "\r\n" +
                "Email: " + email;
    }

}
