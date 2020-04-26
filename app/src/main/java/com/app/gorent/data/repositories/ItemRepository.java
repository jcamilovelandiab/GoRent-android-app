package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.storage.DataSourceCache;

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
        return null;
    }

    public Item getItemById(){
        return null;
    }

    public List<Item> getItemsByName(String name){
        return null;
    }

    public List<Item> getItemsByCategory(String nameCategory){
        return null;
    }

}
