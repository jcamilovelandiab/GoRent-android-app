package com.app.gorent.ui.activities.main.rented_items_history;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.repositories.ItemLendingRepository;

import java.util.List;

public class RentedItemsViewModel extends ViewModel {
    ItemLendingRepository itemLendingRepository;

    public RentedItemsViewModel(ItemLendingRepository itemLendingRepository){
        this.itemLendingRepository = itemLendingRepository;
    }

    public List<ItemLending> getItemLendingList(){
        return itemLendingRepository.getRentedItemHistoryOfLoggedUser();
    }

}
