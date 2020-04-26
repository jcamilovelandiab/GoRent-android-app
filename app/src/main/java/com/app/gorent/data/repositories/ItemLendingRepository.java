package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.Result;

import java.util.Date;
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

    public Result<String> rentItemByUser(Date dueDate, Long totalPrice, Item item){
        return dataSource.rentItemByUser(dueDate, totalPrice,item);
    }

    public Result<String> returnItem(ItemLending itemLending){
        return dataSource.returnItem(itemLending);
    }

    public List<ItemLending> getListItemLendingByEmailOwner(String email){
        return dataSource.getListItemLendingByEmailOwner(email);
    }

    public List<ItemLending> getListItemLendingByEmailRenter(String email){
        return dataSource.getListItemLendingByEmailRenter(email);
    }

}
