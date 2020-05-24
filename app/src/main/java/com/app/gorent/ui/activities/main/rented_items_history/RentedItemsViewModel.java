package com.app.gorent.ui.activities.main.rented_items_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.result.ItemLendingListQueryResult;

public class RentedItemsViewModel extends ViewModel {

    private ItemLendingRepository itemLendingRepository;
    private MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult = new MutableLiveData<>();

    public RentedItemsViewModel(ItemLendingRepository itemLendingRepository){
        this.itemLendingRepository = itemLendingRepository;
    }

    LiveData<ItemLendingListQueryResult> getItemLendingQueryResult() {
        return itemLendingQueryResult;
    }

    void getItemLendingHistory(){
        LoggedInUser loggedInUser = itemLendingRepository.getLoggedInUser();
        assert loggedInUser != null;
        User rentalUser = new User(loggedInUser.getFull_name()+"", loggedInUser.getEmail()+"");
        this.itemLendingRepository.getItemLendingHistoryByRentalUser(rentalUser, itemLendingQueryResult);
    }

}
