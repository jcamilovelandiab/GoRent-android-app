package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.Result;

import java.util.List;

public class ItemRepository {

    private static volatile ItemRepository instance;
    private DataSourceCache dataSource;

    private ItemRepository(DataSourceCache dataSource){
        this.dataSource = dataSource;
    }

    public static ItemRepository getInstance(DataSourceCache dataSource){
        if(instance == null){
            instance = new ItemRepository(dataSource);
        }
        return instance;
    }

    public List<Item> getAvailableItems(){
        return dataSource.getAvailableItems();
    }

    public Item getItemById(Long id){
        return dataSource.getItemById(id);
    }

    public List<Item> getItemsByName(String name){
        return dataSource.getItemsByName(name);
    }

    public List<Item> getItemsByCategory(String nameCategory){
        return dataSource.getItemsByCategory(nameCategory);
    }

    public List<Item> getItemsOfLoggedUser(){
        return dataSource.getItemsOfLoggedUser();
    }

    public Result<String> saveItem(String name, String description, Long price, String feeType, Category category){
        return dataSource.saveItem(name, description, price, feeType, category);
    }

}
