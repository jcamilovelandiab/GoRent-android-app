package com.app.gorent.ui.activities.main.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.result.ItemListQueryResult;

public class SearchViewModel extends ViewModel {

    private ItemRepository itemRepository;

    private MutableLiveData<ItemListQueryResult> itemListQueryResult = new MutableLiveData<>();

    public SearchViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public MutableLiveData<ItemListQueryResult> getItemListQueryResult() {
        return itemListQueryResult;
    }

    public void searchItems(String keyword){
        itemRepository.getItemsByNameOrCategory(keyword, itemListQueryResult);
    }

    public void filter(String filter){

    }

}