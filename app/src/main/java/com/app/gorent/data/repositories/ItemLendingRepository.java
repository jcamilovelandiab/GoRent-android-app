package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.storage.DataSourceCache;

import java.util.List;

public class ItemLendingRepository {

    private static volatile ItemLendingRepository instance;
    private DataSourceCache dataSource;

    private ItemLendingRepository(DataSourceCache dataSource){
        this.dataSource = dataSource;
    }

    public static ItemLendingRepository getInstance(DataSourceCache dataSource){
        if(instance == null){
            instance = new ItemLendingRepository(dataSource);
        }
        return instance;
    }

    public List<ItemLending> getListItemLendingByEmailOwner(String email){
        return null;
    }

    public List<ItemLending> getListItemLendingByEmailRenter(String email){
        return null;
    }

}
