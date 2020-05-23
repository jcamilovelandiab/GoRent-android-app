package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.DataSourceSQLite;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemLendingListQueryResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;

import java.util.Date;

public class ItemLendingRepository extends Repository{

    private static volatile ItemLendingRepository instance;

    private ItemLendingRepository(Context context){
        super(context);
    }

    public static ItemLendingRepository getInstance(Context context){
        if(instance == null){
            instance = new ItemLendingRepository(context);
        }
        return instance;
    }

    public void rentItemByUser(Date dueDate, Long totalPrice, Item item, User user,
                               String delivery_address, MutableLiveData<BasicResult> rentItemResult){
        getDataSourceCache().rentItemByUser(dueDate, totalPrice,item, user, delivery_address, rentItemResult);
    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnItemResult){
        getDataSourceCache().returnItem(itemLending, returnItemResult);
    }

    public void getItemLendingHistoryByOwner(ItemOwner itemOwner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        getDataSourceCache().getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
    }

    public void getItemLendingHistoryByRentalUser(User rentalUser, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        getDataSourceCache().getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
    }

    public void getItemLendingById(Long itemLending, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult){
        getDataSourceCache().getItemLendingById(itemLending, itemLendingQueryResult);
    }

}
