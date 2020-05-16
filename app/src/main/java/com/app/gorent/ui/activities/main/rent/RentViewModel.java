package com.app.gorent.ui.activities.main.rent;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemRepository;

import java.util.List;

public class RentViewModel extends ViewModel {

    //private MutableLiveData<List<Item>> availableItems;
    private ItemRepository itemRepository;
    private List<Item> itemList;

    public RentViewModel(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.itemList = itemRepository.getAvailableItems();
    }

    /*public LiveData<List<Item>> getAvailableItems(){
        return availableItems;
    }*/

    public List<Item> getItemList(){
        return itemList;
    }

}