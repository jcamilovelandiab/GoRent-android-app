package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;

public class ItemRepository extends Repository{

    private static volatile ItemRepository instance;

    private ItemRepository(Context context){
        super(context);
    }

    public static ItemRepository getInstance(Context context){
        if(instance == null){
            instance = new ItemRepository(context);
        }
        return instance;
    }

    public void getAvailableItems(User loggedInUser, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        getDataSourceCache().getAvailableItems(loggedInUser, itemListQueryResult);
    }

    public void getItemById(String id, MutableLiveData<ItemQueryResult> itemQueryResult){
        getDataSourceCache().getItemById(id, itemQueryResult);
    }

    public void getItemsByName(String name, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        getDataSourceCache().getItemsByName(name, itemListQueryResult);
    }

    public void getItemsByCategory(String nameCategory, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        getDataSourceCache().getItemsByCategory(nameCategory, itemListQueryResult);
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){
        getDataSourceCache().saveItem(item, saveItemResult);
    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult){
        getDataSourceCache().updateItem(item, updateItemResult);
    }

    public void deleteItem(String itemId, MutableLiveData deleteItemResult){
        getDataSourceCache().deleteItem(itemId, deleteItemResult);
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        getDataSourceCache().getItemsByOwner(itemOwner, itemListQueryResult);
    }

    public void getItemsByNameOrCategory(String search_text, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        getDataSourceCache().getItemsByNameOrCategory(search_text, itemListQueryResult);
    }

}
