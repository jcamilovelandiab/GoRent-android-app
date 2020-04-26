package com.app.gorent.data.model;

public class Item {

    private Long id;
    private String name;
    private String description;
    private Long price;
    private String timeUnit;
    private ItemOwner itemOwner;
    private Category category;
    private String image_path;

    public Item(Long id, String name, String description, Long price, Category category, ItemOwner itemOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.itemOwner = itemOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
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

    @Override
    public String toString() {
        return "Item: " +
                "   Name: " + name + "\r\n" +
                "   Description: " + description + "\r\n" +
                "   Price: " + price + "\r\n" +
                "   Time unit: " + timeUnit + "\r\n" +
                "   " + itemOwner + "\r\n" +
                "   " + category;
    }
}
