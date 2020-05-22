package com.app.gorent.ui.activities.return_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.utils.BasicResult;

public class ReturnItemViewModel extends ViewModel {

    ItemLendingRepository itemLendingRepository;
    MutableLiveData<BasicResult> returnItemResult = new MutableLiveData<>();
    MutableLiveData<ItemLending> itemLendingQuery = new MutableLiveData<>();

    public ReturnItemViewModel(ItemLendingRepository itemLendingRepository) {
        this.itemLendingRepository = itemLendingRepository;
    }

    public LiveData<ItemLending> getItemLendingQuery() {
        return itemLendingQuery;
    }

    public LiveData<BasicResult> getReturnItemResult() {
        return returnItemResult;
    }

    public void setItemLending(ItemLending itemLending) {
        this.itemLendingQuery.setValue(itemLending);
    }

    public void returnItem(){
        itemLendingRepository.returnItem(itemLendingQuery.getValue(), returnItemResult);
    }

}
