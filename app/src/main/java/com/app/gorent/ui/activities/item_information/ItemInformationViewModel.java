package com.app.gorent.ui.activities.item_information;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.ItemQueryResult;

public class ItemInformationViewModel extends ViewModel {

    ItemRepository itemRepository;
    MutableLiveData<ItemQueryResult> itemQueryResult = new MutableLiveData<>();

    public ItemInformationViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public MutableLiveData<ItemQueryResult> getItemQueryResult() {
        return itemQueryResult;
    }

    public void findItemById(Long id){
        itemRepository.getItemById(id, itemQueryResult);
    }

}
