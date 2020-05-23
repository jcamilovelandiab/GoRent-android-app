package com.app.gorent.ui.activities.main;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.UserRepository;

public class MainActivityViewModel extends ViewModel {

    private UserRepository userRepository;

    public MainActivityViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void logout(){
        userRepository.logout();
    }

}
