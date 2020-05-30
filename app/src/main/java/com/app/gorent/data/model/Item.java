package com.app.gorent.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item {

    private String id;
    private String name;
    private String description;
    private Long price;
    private String feeType;
    private ItemOwner itemOwner;
    private Category category;
    private String image_path;
    private boolean isRent;
    private boolean isDeleted;
    private boolean uploaded;

    public Item() {
        this.isDeleted = false;
        this.isRent = false;
        this.uploaded = false;
        this.id = UUID.randomUUID().toString();
    }

    public Item(String name, String description, Long price, String feeType, Category category, ItemOwner itemOwner) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.feeType = feeType;
        this.category = category;
        this.itemOwner = itemOwner;
        this.isDeleted = false;
        this.isRent = false;
        this.uploaded = false;
        this.id = UUID.randomUUID().toString();
    }

    public Item(String name, String description, Long price, String feeType,
                Category category, ItemOwner itemOwner, String image_path) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.feeType = feeType;
        this.category = category;
        this.itemOwner = itemOwner;
        this.image_path = image_path;
        this.isDeleted = false;
        this.isRent = false;
        this.uploaded = false;
        this.id = UUID.randomUUID().toString();
    }

    public Item(String id, String name, String description, Long price, String feeType,
                Category category, ItemOwner itemOwner, String image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.feeType = feeType;
        this.category = category;
        this.itemOwner = itemOwner;
        this.image_path = image_path;
        this.isDeleted = false;
        this.isRent = false;
        this.uploaded = false;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public ItemOwner getItemOwner() {
        return itemOwner;
    }

    public void setItemOwner(ItemOwner itemOwner) {
        this.itemOwner = itemOwner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }

    @Override
    public String toString() {
        return "-Item" + "\r\n" +
                "  Name: " + name + "\r\n" +
                "  Description: " + description + "\r\n" +
                "  Price: $" + price + "\r\n" +
                "  Fee type: " + feeType + "\r\n" +
                itemOwner + "\r\n" +
                category;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }


}
