package com.app.gorent.ui.activities.main.lent_items_history;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.repositories.ItemLendingRepository;

import java.util.List;

public class LentItemsViewModel extends ViewModel {

    ItemLendingRepository itemLendingRepository;

    public LentItemsViewModel(ItemLendingRepository itemLendingRepository){
        this.itemLendingRepository = itemLendingRepository;
    }

    public List<ItemLending> getItemLendingList(){
        return itemLendingRepository.getLentItemHistoryOfLoggedUser();
    }

}
