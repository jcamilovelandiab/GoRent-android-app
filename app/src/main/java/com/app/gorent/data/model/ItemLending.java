package com.app.gorent.data.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class ItemLending {

    Long id;
    Date lendingDate;
    Date dueDate;
    @Nullable
    Date returnDate;
    Long totalPrice;
    User renter;
    Item item;

    public ItemLending(Date lendingDate, Date dueDate, Long totalPrice, Item item,User renter) {
        this.lendingDate = lendingDate;
        this.dueDate = dueDate;
        this.totalPrice = totalPrice;
        this.renter = renter;
        this.item = item;
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

    @Override
    public String toString() {
        return "ItemLending: " + "\r\n" +
                "   Id: " + id + "\r\n" +
                "   Lending date: " + lendingDate + "\r\n" +
                "   Due date: " + dueDate + "\r\n" +
                "   Return date: " + returnDate + "\r\n" +
                "   Rental " + renter + "\r\n" +
                "   " + item;
    }
}
