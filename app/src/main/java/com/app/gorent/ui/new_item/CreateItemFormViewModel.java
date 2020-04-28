package com.app.gorent.ui.new_item;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

public class CreateItemFormViewModel extends ViewModel {

    ItemRepository itemRepository;

    public CreateItemFormViewModel(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item){

    }

}
