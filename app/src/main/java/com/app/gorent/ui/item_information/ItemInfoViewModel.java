package com.app.gorent.ui.item_information;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

public class ItemInfoViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private Item item;

    public ItemInfoViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(Long id){
        this.item = itemRepository.getItemById(id);
        return item;
    }

}
