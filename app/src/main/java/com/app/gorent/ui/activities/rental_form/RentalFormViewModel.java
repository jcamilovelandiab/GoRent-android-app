package com.app.gorent.ui.activities.rental_form;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.ItemQueryResult;
import com.app.gorent.utils.Result;
import com.app.gorent.utils.Validator;

import java.util.Date;

public class RentalFormViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private ItemLendingRepository itemLendingRepository;
    private MutableLiveData<BasicResult> rentalResult = new MutableLiveData<>();
    private MutableLiveData<RentalFormState> rentalFormState = new MutableLiveData<>();
    private MutableLiveData<ItemQueryResult> itemQueryResult = new MutableLiveData<>();

    public RentalFormViewModel(ItemRepository itemRepository, ItemLendingRepository itemLendingRepository) {
        this.itemRepository = itemRepository;
        this.itemLendingRepository = itemLendingRepository;
    }

    public LiveData<BasicResult> getRentalResult() {
        return rentalResult;
    }

    public LiveData<RentalFormState> getRentalFormState() {
        return rentalFormState;
    }

    public MutableLiveData<ItemQueryResult> getItemQueryResult() {
        return itemQueryResult;
    }

    public void retrieveItemById(Long itemId){
        itemRepository.getItemById(itemId, itemQueryResult);
    }

    public void rentItem(Date dueDate, Long totalPrice, String address){
        Item item = itemQueryResult.getValue().getItem();
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        User rentalUser = new User(loggedInUser.getFull_name()+"",loggedInUser.getEmail()+"");
        itemLendingRepository.rentItemByUser(dueDate, totalPrice, item, rentalUser, address, rentalResult);
    }

    public void rentalFormDataChanged(Date currentDate, Date dueDate, Long totalPrice, String address){
        if(!isDueDateValid(currentDate, dueDate)){
            rentalFormState.setValue(new RentalFormState(R.string.invalid_delivery_date, null));
        }else if(!Validator.isStringValid(address)){
            rentalFormState.setValue(new RentalFormState(null,R.string.invalid_address));
        }else{
            rentalFormState.setValue(new RentalFormState(true));
        }
    }

    private boolean isDueDateValid(Date currentDate, Date dueDate){
        if(dueDate.compareTo(currentDate)>0){
            return true;
        }else{
            return false;
        }
    }

}
