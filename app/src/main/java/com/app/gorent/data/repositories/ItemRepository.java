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
    private DataSourceCache dataSourceCache;

    private ItemRepository(DataSourceCache dataSourceCache){
        this.dataSourceCache = dataSourceCache;
    }

    public static ItemRepository getInstance(DataSourceCache dataSource){
        if(instance == null){
            instance = new ItemRepository(dataSource);
        }
        return instance;
    }

    public void getAvailableItems(MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSourceCache.getAvailableItems(itemListQueryResult);
    }

    public void getItemById(Long id, MutableLiveData<ItemQueryResult> itemQueryResult){
        dataSourceCache.getItemById(id, itemQueryResult);
    }

    public void getItemsByName(String name, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSourceCache.getItemsByName(name, itemListQueryResult);
    }

    public void getItemsByCategory(String nameCategory, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSourceCache.getItemsByCategory(nameCategory, itemListQueryResult);
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){
        dataSourceCache.saveItem(item, saveItemResult);
    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult){
        dataSourceCache.updateItem(item, updateItemResult);
    }

    public void deleteItem(Long itemId, MutableLiveData deleteItemResult){
        dataSourceCache.deleteItem(itemId, deleteItemResult);
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSourceCache.getItemsByOwner(itemOwner, itemListQueryResult);
    }

    public void getItemsByNameOrCategory(String search_text, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        dataSourceCache.getItemsByNameOrCategory(search_text, itemListQueryResult);
    }

}
