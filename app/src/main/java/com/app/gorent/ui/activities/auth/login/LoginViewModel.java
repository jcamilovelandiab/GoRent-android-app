package com.app.gorent.ui.activities.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.R;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.Validator;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> loginResult = new MutableLiveData<>();
    private UserRepository userRepository;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        userRepository.login(email, password, loginResult);
    }

    public void loginDataChanged(String email, String password) {
        if (!Validator.isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!Validator.isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }


}
