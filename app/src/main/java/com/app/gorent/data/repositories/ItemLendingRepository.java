package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.InternetConnectivity;
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
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().rentItemByUser(dueDate, totalPrice, item, user, delivery_address, rentItemResult);
        }else{
            getDataSourceCache().rentItemByUser(dueDate, totalPrice,item, user, delivery_address, rentItemResult);
        }
    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnItemResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().returnItem(itemLending, returnItemResult);
        }else{
            getDataSourceCache().returnItem(itemLending, returnItemResult);
        }
    }

    public void getItemLendingHistoryByOwner(ItemOwner itemOwner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
        }else{
            getDataSourceCache().getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
        }
    }

    public void getItemLendingHistoryByRentalUser(User rentalUser, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
        }else{
            getDataSourceCache().getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
        }
    }

    public void getItemLendingById(String itemLendingId, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getItemLendingById(itemLendingId, itemLendingQueryResult);
        } else {
            getDataSourceCache().getItemLendingById(itemLendingId, itemLendingQueryResult);
        }
    }

}
