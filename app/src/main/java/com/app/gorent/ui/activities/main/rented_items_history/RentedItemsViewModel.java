package com.app.gorent.ui.activities.main.rented_items_history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.ItemLendingQueryResult;

import java.util.List;

public class RentedItemsViewModel extends ViewModel {

    private ItemLendingRepository itemLendingRepository;
    private MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult = new MutableLiveData<>();

    public RentedItemsViewModel(ItemLendingRepository itemLendingRepository){
        this.itemLendingRepository = itemLendingRepository;
    }

    public MutableLiveData<ItemLendingQueryResult> getItemLendingQueryResult() {
        return itemLendingQueryResult;
    }

    public void getItemLendingHistory(){
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        User rentalUser = new User(loggedInUser.getFull_name()+"", loggedInUser.getEmail()+"");
        this.itemLendingRepository.getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
    }

}
