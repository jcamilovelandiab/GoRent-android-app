package com.app.gorent.ui.activities.return_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;

import java.util.Date;

public class ReturnItemViewModel extends ViewModel {

    ItemLendingRepository itemLendingRepository;
    MutableLiveData<BasicResult> returnItemResult = new MutableLiveData<>();
    MutableLiveData<ItemLendingQueryResult> itemLendingQuery = new MutableLiveData<>();

    public ReturnItemViewModel(ItemLendingRepository itemLendingRepository) {
        this.itemLendingRepository = itemLendingRepository;
    }

    LiveData<ItemLendingQueryResult> getItemLendingQuery() {
        return itemLendingQuery;
    }

    LiveData<BasicResult> getReturnItemResult() {
        return returnItemResult;
    }

    /*public void setItemLending(ItemLending itemLending) {
        this.itemLendingQuery.setValue(itemLending);
    }*/

    void getItemLending(String itemLendingId){
        this.itemLendingRepository.getItemLendingById(itemLendingId, itemLendingQuery);
    }

    void returnItem(){
        ItemLending itemLending = itemLendingQuery.getValue().getItemLending();
        assert itemLending != null;
        itemLending.setReturnDate(new Date());
        itemLendingRepository.returnItem(itemLending, returnItemResult);
    }

}
