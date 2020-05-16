package com.app.gorent.ui.activities.main.search;

import androidx.lifecycle.ViewModel;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private ItemRepository itemRepository;

    public SearchViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<Item> getItems(String search_text){
        return itemRepository.getAvailableItems();
    }

}