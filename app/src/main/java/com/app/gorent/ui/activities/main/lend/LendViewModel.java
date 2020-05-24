package com.app.gorent.ui.activities.main.lend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.result.ItemListQueryResult;

public class LendViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private MutableLiveData<ItemListQueryResult> itemListQueryResult =  new MutableLiveData<>();

    public LendViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    LiveData<ItemListQueryResult> getItemListQueryResult() {
        return itemListQueryResult;
    }

    void findMyItems(){
        LoggedInUser loggedInUser = itemRepository.getLoggedInUser();
        assert loggedInUser != null;
        ItemOwner itemOwner = new ItemOwner(loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        itemRepository.getItemsByOwner(itemOwner, itemListQueryResult);
    }

}
