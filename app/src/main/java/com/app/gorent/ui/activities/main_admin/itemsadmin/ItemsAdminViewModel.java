package com.app.gorent.ui.activities.main_admin.itemsadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.result.ItemListQueryResult;

public class ItemsAdminViewModel extends ViewModel {

    private MutableLiveData<ItemListQueryResult> itemListQueryResult = new MutableLiveData<>();


    public ItemsAdminViewModel(ItemRepository itemRepository) {
        itemRepository.getAllItemsAdmin(itemListQueryResult);
    }

    public LiveData<ItemListQueryResult> getItemListQueryResult() {
        return itemListQueryResult;
    }

}
