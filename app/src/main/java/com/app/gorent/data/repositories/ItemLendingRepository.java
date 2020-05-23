package com.app.gorent.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirestore;
import com.app.gorent.data.storage.DataSourceSQLite;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.ItemLendingListQueryResult;
import com.app.gorent.utils.ItemLendingQueryResult;

import java.util.Date;

public class ItemLendingRepository {

    private static volatile ItemLendingRepository instance;
    private DataSourceCache dataSourceCache;
    private DataSourceFirestore dataSourceFirestore;
    private DataSourceSQLite dataSourceSQLite;

    private ItemLendingRepository(DataSourceCache dataSourceCache){
        this.dataSourceCache = dataSourceCache;
    }

    public static ItemLendingRepository getInstance(DataSourceCache dataSourceCache){
        if(instance == null){
            instance = new ItemLendingRepository(dataSourceCache);
        }
        return instance;
    }

    public void rentItemByUser(Date dueDate, Long totalPrice, Item item, User user,
                               String delivery_address, MutableLiveData<BasicResult> rentItemResult){
        dataSourceCache.rentItemByUser(dueDate, totalPrice,item, user, delivery_address, rentItemResult);
    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnItemResult){
        dataSourceCache.returnItem(itemLending, returnItemResult);
    }

    public void getItemLendingHistoryByOwner(ItemOwner itemOwner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        dataSourceCache.getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
    }

    public void getItemLendingHistoryByRentalUser(User rentalUser, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        dataSourceCache.getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
    }

    public void getItemLendingById(Long itemLending, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult){
        dataSourceCache.getItemLendingById(itemLending, itemLendingQueryResult);
    }

}
