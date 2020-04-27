package com.app.gorent.ui.rental_form;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.ui.rental_form.RentalFormState;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.Result;

import java.util.Date;

public class RentalFormViewModel extends ViewModel {

    private ItemRepository itemRepository;
    private ItemLendingRepository itemLendingRepository;
    private UserRepository userRepository;
    private MutableLiveData<BasicResult> rentalResult = new MutableLiveData<>();
    private MutableLiveData<RentalFormState> rentalFormState = new MutableLiveData<>();
    private Item item;

    public RentalFormViewModel(ItemRepository itemRepository, ItemLendingRepository itemLendingRepository,
                               UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemLendingRepository = itemLendingRepository;
        this.userRepository = userRepository;
    }

    public LiveData<BasicResult> getRentalResult() {
        return rentalResult;
    }

    public LiveData<RentalFormState> getRentalFormState() {
        return rentalFormState;
    }

    public Item retrieveItemById(Long id){
        this.item = itemRepository.getItemById(id);
        return item;
    }

    public Item getItem(){
        return item;
    }

    public void rentItem(Date dueDate, Long totalPrice){
        Result<String> result = itemLendingRepository.rentItemByUser(dueDate, totalPrice, item);
        if(result instanceof Result.Success){
            String data = (String) ((Result.Success) result).getData();
            rentalResult.setValue(new BasicResult(data));
        }else{
            rentalResult.setValue(new BasicResult(R.string.rental_failed));
        }
    }

    public void rentalFormDataChanged(Date currentDate, Date dueDate, Long totalPrice){
        if(isDueDateValid(currentDate, dueDate)){
            rentalFormState.setValue(new RentalFormState(R.string.invalid_delivery_date));
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
