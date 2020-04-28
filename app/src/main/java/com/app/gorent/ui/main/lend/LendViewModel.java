package com.app.gorent.ui.main.lend;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

import java.util.List;

public class LendViewModel extends ViewModel {

    public ItemRepository itemRepository;

    public LendViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsOfLoggedUser(){
        return itemRepository.getItemsOfLoggedUser();
    }

}
