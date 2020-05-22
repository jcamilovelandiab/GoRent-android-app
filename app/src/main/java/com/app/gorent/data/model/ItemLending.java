package com.app.gorent.data.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class ItemLending implements Serializable {

    Long id;
    Date lendingDate;
    Date dueDate;
    @Nullable
    Date returnDate;
    Long totalPrice;
    User renter;
    Item item;
    String delivery_address;

    public ItemLending(Date lendingDate, Date dueDate, Long totalPrice, Item item,User renter, String delivery_address) {
        this.lendingDate = lendingDate;
        this.dueDate = dueDate;
        this.totalPrice = totalPrice;
        this.renter = renter;
        this.item = item;
        this.delivery_address = delivery_address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Nullable
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    @Override
    public String toString() {
        return "ItemLending: " + "\r\n" +
                "   Id: " + id + "\r\n" +
                "   Lending date: " + lendingDate + "\r\n" +
                "   Due date: " + dueDate + "\r\n" +
                "   Return date: " + returnDate + "\r\n" +
                "   Rental " + renter + "\r\n" +
                "   Delivery address: "+delivery_address+"\r\n"+
                "   " + item;
    }
}
