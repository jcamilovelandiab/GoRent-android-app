package com.app.gorent.ui.viewmodel;

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
import com.app.gorent.ui.activities.new_item.ItemFormViewModel;
import com.app.gorent.ui.activities.rent_item_details.RentItemDetailsViewModel;
import com.app.gorent.ui.activities.main.lend.LendViewModel;
import com.app.gorent.ui.activities.main.lent_items_history.LentItemsViewModel;
import com.app.gorent.ui.activities.main.rented_items_history.RentedItemsViewModel;
import com.app.gorent.ui.activities.main.search.SearchViewModel;
import com.app.gorent.ui.activities.rental_form.RentalFormViewModel;
import com.app.gorent.ui.activities.main.rent.RentViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DataSourceCache dataSourceCache = DataSourceCache.getInstance();
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(UserRepository.getInstance(dataSourceCache));
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T) new SignUpViewModel(UserRepository.getInstance(dataSourceCache));
        } else if (modelClass.isAssignableFrom(RentViewModel.class)){
            return (T) new RentViewModel(ItemRepository.getInstance(dataSourceCache));
        } else if (modelClass.isAssignableFrom(RentItemDetailsViewModel.class)){
            return (T) new RentItemDetailsViewModel(ItemRepository.getInstance(dataSourceCache));
        } else if (modelClass.isAssignableFrom(RentalFormViewModel.class)){
            return (T) new RentalFormViewModel(ItemRepository.getInstance(dataSourceCache),
                    ItemLendingRepository.getInstance(dataSourceCache),
                    UserRepository.getInstance(dataSourceCache));
        } else if(modelClass.isAssignableFrom(LentItemsViewModel.class)){
            return (T) new LentItemsViewModel(ItemLendingRepository.getInstance(dataSourceCache));
        }  else if(modelClass.isAssignableFrom(RentedItemsViewModel.class)){
            return (T) new RentedItemsViewModel(ItemLendingRepository.getInstance(dataSourceCache));
        }else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(ItemRepository.getInstance(dataSourceCache));
        } else if(modelClass.isAssignableFrom(LendViewModel.class)){
            return (T) new LendViewModel(ItemRepository.getInstance(dataSourceCache));
        }else if(modelClass.isAssignableFrom(ItemInformationViewModel.class)){
            return (T) new ItemInformationViewModel(ItemRepository.getInstance(dataSourceCache));
        }else if(modelClass.isAssignableFrom(ItemFormViewModel.class)){
            return (T) new ItemFormViewModel(ItemRepository.getInstance(dataSourceCache),
                    CategoryRepository.getInstance(dataSourceCache));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
