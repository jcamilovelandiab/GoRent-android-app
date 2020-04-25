package com.app.gorent.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.data.repositories.UserRepository;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.R;
import com.app.gorent.ui.auth.AuthResult;
import com.app.gorent.ui.auth.LoggedInUserView;
import com.app.gorent.utils.Result;
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

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = userRepository.login(username, password);
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new AuthResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new AuthResult(R.string.login_failed));
        }
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
