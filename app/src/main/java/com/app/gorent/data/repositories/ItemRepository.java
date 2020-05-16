package com.app.gorent.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.ItemListQueryResult;
import com.app.gorent.utils.ItemQueryResult;

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

    public void getAvailableItems(MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSource.getAvailableItems(itemListQueryResult);
    }

    public void getItemById(Long id, MutableLiveData<ItemQueryResult> itemQueryResult){
        dataSource.getItemById(id, itemQueryResult);
    }

    public void getItemsByName(String name, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSource.getItemsByName(name, itemListQueryResult);
    }

    public void getItemsByCategory(String nameCategory, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSource.getItemsByCategory(nameCategory, itemListQueryResult);
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){
        dataSource.saveItem(item, saveItemResult);
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSource.getItemsByOwner(itemOwner, itemListQueryResult);
    }

}
