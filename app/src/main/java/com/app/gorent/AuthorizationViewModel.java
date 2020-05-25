package com.app.gorent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.utils.result.UserQueryResult;

public class AuthorizationViewModel extends ViewModel {

    private UserRepository userRepository;
    MutableLiveData<UserQueryResult> userQueryResult = new MutableLiveData<>();

    public AuthorizationViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserQueryResult> getUserQueryResult() {
        return userQueryResult;
    }

    public void getUser(String email){
        userRepository.findUserByEmail(email, userQueryResult);
    }

}
