package com.app.gorent.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.ItemLendingQueryResult;

import java.util.Date;

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

    public void rentItemByUser(Date dueDate, Long totalPrice, Item item, User user,
                               String delivery_address, MutableLiveData<BasicResult> rentItemResult){
        dataSource.rentItemByUser(dueDate, totalPrice,item, user, delivery_address, rentItemResult);
    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnItemResult){
        dataSource.returnItem(itemLending, returnItemResult);
    }

    public void getItemLendingHistoryByOwner(ItemOwner itemOwner, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult){
        dataSource.getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
    }

    public void getItemLendingHistoryByRentalUser(User rentalUser, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult){
        dataSource.getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
    }

}
