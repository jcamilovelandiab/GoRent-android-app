package com.app.gorent.data.model;

public class Location {

    private String country;
    private String city;
    private String address;
    private String postal_code;

    public Location(String country, String city, String address, String postal_code) {
        this.country = country;
        this.city = city;
        this.address = address;
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

}
