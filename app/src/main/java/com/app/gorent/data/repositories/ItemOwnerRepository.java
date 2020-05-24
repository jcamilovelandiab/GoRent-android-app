package com.app.gorent.data.repositories;

import android.content.Context;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;

import java.util.List;

public class ItemOwnerRepository extends Repository{

    private static volatile ItemOwnerRepository instance;

    private ItemOwnerRepository(Context context){
        super(context);
    }

    public static ItemOwnerRepository getInstance(Context context){
        if(instance == null){
            instance = new ItemOwnerRepository(context);
        }
        return instance;
    }

    public ItemOwner save(ItemOwner itemOwner){
        return getDataSourceCache().saveItemOwner(itemOwner);
    }

    public ItemOwner getItemOwnerById(Long id){
        return null;
    }
    public List<Item> getItemsByEmailOwner(String email){
        return null;
    }

}
