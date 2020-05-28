package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
            getDataSourceSQLite().logout();
        }
    }

    public void login(String email, String password, MutableLiveData<AuthResult> authResult) {
        // handle login
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().login(email, password, authResult);
        }else{
            getDataSourceSQLite().login(email, password, authResult);
        }
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        if(InternetConnectivity.check(getContext())){
            MutableLiveData<AuthResult> authResultMutableLiveData = new MutableLiveData<>();
            authResultMutableLiveData.observeForever(authResultM -> {
                if(authResultM==null) return;
                if(authResultM.getError()!=null){
                    authResult.setValue(authResultM);
                }
                if(authResultM.getSuccess()!=null){
                    getDataSourceSQLite().signUp(user, authResult);
                }
            });
            getDataSourceFirebase().signUp(user, authResultMutableLiveData);
        }
    }

    public void findUserByEmail(String email, MutableLiveData<UserQueryResult> userQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().findUserByEmail(email, userQueryResult);
        }else{
            getDataSourceSQLite().findUserByEmail(email, userQueryResult);
        }
    }

}