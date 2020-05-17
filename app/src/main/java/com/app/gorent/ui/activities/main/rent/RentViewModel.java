package com.app.gorent.ui.activities.main.rent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.ItemListQueryResult;

import java.util.List;

public class RentViewModel extends ViewModel {

    private MutableLiveData<ItemListQueryResult> availableItems = new MutableLiveData<>();
    private ItemRepository itemRepository;

    public RentViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.itemRepository.getAvailableItems(availableItems);
    }

    public MutableLiveData<ItemListQueryResult> getAvailableItems() {
        return availableItems;
    }
}