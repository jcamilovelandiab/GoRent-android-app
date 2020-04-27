package com.app.gorent.ui.item_details;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

public class ItemDetailsViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private Item item;

    public ItemDetailsViewModel(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item retrieveItemById(Long id){
        this.item = itemRepository.getItemById(id);
        return item;
    }

    public Item getItem(){
        return item;
    }

}
