package com.app.gorent.ui.activities.rent_item_details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.result.ItemQueryResult;

public class RentItemDetailsViewModel extends ViewModel {

    private MutableLiveData<ItemQueryResult> itemQueryResult = new MutableLiveData<>();
    private ItemRepository itemRepository;

    public RentItemDetailsViewModel(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public MutableLiveData<ItemQueryResult> getItemQueryResult() {
        return itemQueryResult;
    }

    public void retrieveItemById(Long id){
        itemRepository.getItemById(id, itemQueryResult);
    }

}
