package com.app.gorent.ui.activities.main.rent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.ItemListQueryResult;

import java.util.List;

public class RentViewModel extends ViewModel {

    private MutableLiveData<ItemListQueryResult> availableItems = new MutableLiveData<>();
    private ItemRepository itemRepository;

    public RentViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public MutableLiveData<ItemListQueryResult> getAvailableItems() {
        return availableItems;
    }

    public void retrieveAvailableItems(){
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        User user = new User(loggedInUser.getFull_name()+"", loggedInUser.getEmail()+"");
        this.itemRepository.getAvailableItems(user, availableItems);
    }

}