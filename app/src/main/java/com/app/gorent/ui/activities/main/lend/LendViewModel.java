package com.app.gorent.ui.activities.main.lend;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.ItemListQueryResult;

import java.util.List;

public class LendViewModel extends ViewModel {

    public ItemRepository itemRepository;
    public MutableLiveData<ItemListQueryResult> itemListQueryResult =  new MutableLiveData<>();

    public LendViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        findMyItems();
    }

    public MutableLiveData<ItemListQueryResult> getItemListQueryResult() {
        return itemListQueryResult;
    }

    public void findMyItems(){
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        ItemOwner itemOwner = new ItemOwner(loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        itemRepository.getItemsByOwner(itemOwner, itemListQueryResult);
    }

}
