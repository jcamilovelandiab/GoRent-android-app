package com.app.gorent.ui.main.rent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Item;

import java.util.List;

public class RentViewModel extends ViewModel {

    private MutableLiveData<List<Item>> availableItems;

    public RentViewModel() {
        availableItems = new MutableLiveData<>();
    }

    public LiveData<List<Item>> getItems(){
        return availableItems;
    }

}