package com.app.gorent.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.ui.auth.login.LoginViewModel;
import com.app.gorent.ui.auth.signup.SignUpViewModel;
import com.app.gorent.ui.item_details.ItemDetailsViewModel;
import com.app.gorent.ui.main.lent_items_history.LentItemsViewModel;
import com.app.gorent.ui.rental_form.RentalFormViewModel;
import com.app.gorent.ui.main.rent.RentViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(UserRepository.getInstance(new DataSourceCache()));
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T) new SignUpViewModel(UserRepository.getInstance(new DataSourceCache()));
        } else if (modelClass.isAssignableFrom(RentViewModel.class)){
            return (T) new RentViewModel(ItemRepository.getInstance(new DataSourceCache()));
        } else if (modelClass.isAssignableFrom(ItemDetailsViewModel.class)){
            return (T) new ItemDetailsViewModel(ItemRepository.getInstance(new DataSourceCache()));
        } else if (modelClass.isAssignableFrom(RentalFormViewModel.class)){
            return (T) new RentalFormViewModel(ItemRepository.getInstance(new DataSourceCache()),
                    ItemLendingRepository.getInstance(new DataSourceCache()),
                    UserRepository.getInstance(new DataSourceCache()));
        } else if(modelClass.isAssignableFrom(LentItemsViewModel.class)){
            return (T) new LentItemsViewModel(ItemLendingRepository.getInstance(new DataSourceCache()));
        } else{
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
