package com.app.gorent.ui.activities.main.lent_items_history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.ItemLendingListQueryResult;

public class LentItemsViewModel extends ViewModel {

    ItemLendingRepository itemLendingRepository;
    MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult = new MutableLiveData<>();

    public LentItemsViewModel(ItemLendingRepository itemLendingRepository){
        this.itemLendingRepository = itemLendingRepository;
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        ItemOwner itemOwner = new ItemOwner(loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        this.itemLendingRepository.getItemLendingHistoryByOwner(itemOwner, itemLendingQueryResult);
    }

    public MutableLiveData<ItemLendingListQueryResult> getItemLendingQueryResult() {
        return itemLendingQueryResult;
    }
}
