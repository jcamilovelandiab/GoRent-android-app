package com.app.gorent.ui.activities.main_admin.item_lending_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.utils.result.ItemLendingListQueryResult;

public class ItemLendingHistoryViewModel extends ViewModel {

    private ItemLendingRepository itemLendingRepository;
    MutableLiveData<ItemLendingListQueryResult> itemLendingListQueryResult = new MutableLiveData<>();

    public ItemLendingHistoryViewModel(ItemLendingRepository itemLendingRepository) {
        this.itemLendingRepository = itemLendingRepository;
        this.itemLendingRepository.getAllItemLending(itemLendingListQueryResult);
    }

    LiveData<ItemLendingListQueryResult> getItemLendingListQueryResult() {
        return itemLendingListQueryResult;
    }
}
