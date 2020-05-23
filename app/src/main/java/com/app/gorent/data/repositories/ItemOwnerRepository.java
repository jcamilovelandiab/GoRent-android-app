package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirestore;
import com.app.gorent.data.storage.DataSourceSQLite;

import java.util.List;

public class ItemOwnerRepository {

    private static volatile ItemOwnerRepository instance;
    private DataSourceCache dataSource;
    private DataSourceFirestore dataSourceFirestore;
    private DataSourceSQLite dataSourceSQLite;

    private ItemOwnerRepository(DataSourceCache dataSource){
        this.dataSource = dataSource;
    }

    public static ItemOwnerRepository getInstance(DataSourceCache dataSource){
        if(instance == null){
            instance = new ItemOwnerRepository(dataSource);
        }
        return instance;
    }

    public ItemOwner save(ItemOwner itemOwner){
        return dataSource.saveItemOwner(itemOwner);
    }

    public ItemOwner getItemOwnerById(Long id){
        return null;
    }
    public List<Item> getItemsByEmailOwner(String email){
        return null;
    }

}
