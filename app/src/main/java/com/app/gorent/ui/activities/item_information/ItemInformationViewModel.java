package com.app.gorent.ui.activities.item_information;


import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

public class ItemInformationViewModel extends ViewModel {

    ItemRepository itemRepository;

    public ItemInformationViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemById(Long id){
        return itemRepository.getItemById(id);
    }

}
