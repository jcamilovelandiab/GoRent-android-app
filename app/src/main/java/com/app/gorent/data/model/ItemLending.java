package com.app.gorent.data.model;

public class ItemLending {

    Long id;
    String lendingDate;
    String dueDate;
    String returnDate;
    Long totalPrice;
    User renter;
    Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(String lendingDate) {
        this.lendingDate = lendingDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
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
