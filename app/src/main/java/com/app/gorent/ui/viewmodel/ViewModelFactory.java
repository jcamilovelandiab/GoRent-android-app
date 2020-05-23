package com.app.gorent.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.app.gorent.data.repositories.CategoryRepository;
import com.app.gorent.data.repositories.ItemLendingRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.ui.activities.auth.login.LoginViewModel;
import com.app.gorent.ui.activities.auth.signup.SignUpViewModel;
import com.app.gorent.ui.activities.item_information.ItemInformationViewModel;
import com.app.gorent.ui.activities.item_form.ItemFormViewModel;
import com.app.gorent.ui.activities.main.MainActivityViewModel;
import com.app.gorent.ui.activities.rent_item_details.RentItemDetailsViewModel;
import com.app.gorent.ui.activities.main.lend.LendViewModel;
import com.app.gorent.ui.activities.main.lent_items_history.LentItemsViewModel;
import com.app.gorent.ui.activities.main.rented_items_history.RentedItemsViewModel;
import com.app.gorent.ui.activities.main.search.SearchViewModel;
import com.app.gorent.ui.activities.rental_form.RentalFormViewModel;
import com.app.gorent.ui.activities.main.rent.RentViewModel;
import com.app.gorent.ui.activities.return_item.ReturnItemViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(UserRepository.getInstance(context));
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T) new SignUpViewModel(UserRepository.getInstance(context));
        } else if(modelClass.isAssignableFrom(MainActivityViewModel.class)){
            return (T) new MainActivityViewModel(UserRepository.getInstance(context));
        } else if (modelClass.isAssignableFrom(RentViewModel.class)){
            return (T) new RentViewModel(ItemRepository.getInstance(context));
        } else if (modelClass.isAssignableFrom(RentItemDetailsViewModel.class)){
            return (T) new RentItemDetailsViewModel(ItemRepository.getInstance(context));
        } else if (modelClass.isAssignableFrom(RentalFormViewModel.class)){
            return (T) new RentalFormViewModel(ItemRepository.getInstance(context),
                    ItemLendingRepository.getInstance(context));
        } else if(modelClass.isAssignableFrom(LentItemsViewModel.class)){
            return (T) new LentItemsViewModel(ItemLendingRepository.getInstance(context));
        }  else if(modelClass.isAssignableFrom(RentedItemsViewModel.class)){
            return (T) new RentedItemsViewModel(ItemLendingRepository.getInstance(context));
        }else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(ItemRepository.getInstance(context));
        } else if(modelClass.isAssignableFrom(LendViewModel.class)){
            return (T) new LendViewModel(ItemRepository.getInstance(context));
        }else if(modelClass.isAssignableFrom(ItemInformationViewModel.class)){
            return (T) new ItemInformationViewModel(ItemRepository.getInstance(context),
                    CategoryRepository.getInstance(context));
        }else if(modelClass.isAssignableFrom(ItemFormViewModel.class)){
            return (T) new ItemFormViewModel(ItemRepository.getInstance(context),
                    CategoryRepository.getInstance(context));
        } else if (modelClass.isAssignableFrom(ReturnItemViewModel.class)){
            return (T) new ReturnItemViewModel(ItemLendingRepository.getInstance(context));
        } else{
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
