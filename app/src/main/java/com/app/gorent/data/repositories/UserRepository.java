package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.User;
import com.app.gorent.utils.InternetConnectivity;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.result.UserQueryResult;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository extends Repository{

    private static volatile UserRepository instance;

    // private constructor : singleton access
    private UserRepository(Context context) {
        super(context);
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void logout() {
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().logout();
        }else{
            getDataSourceCache().logout();
        }
    }

    public void login(String email, String password, MutableLiveData<AuthResult> authResult) {
        // handle login
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().login(email, password, authResult);
        }else{
            getDataSourceCache().login(email, password, authResult);
        }
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().signUp(user, authResult);
        }else{
            getDataSourceCache().signUp(user, authResult);
        }
    }

    public void findUserByEmail(String email, MutableLiveData<UserQueryResult> userQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().findUserByEmail(email, userQueryResult);
        }else{
            getDataSourceCache().findUserByEmail(email, userQueryResult);
        }
    }

}