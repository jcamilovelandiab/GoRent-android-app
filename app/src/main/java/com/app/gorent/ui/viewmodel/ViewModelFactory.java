package com.app.gorent.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.ui.auth.login.LoginViewModel;
import com.app.gorent.ui.auth.signup.SignUpViewModel;
import com.app.gorent.ui.item_information.ItemInfoActivity;
import com.app.gorent.ui.item_information.ItemInfoViewModel;
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
        }else if (modelClass.isAssignableFrom(ItemInfoViewModel.class)){
            return (T) new ItemInfoViewModel(ItemRepository.getInstance(new DataSourceCache()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
