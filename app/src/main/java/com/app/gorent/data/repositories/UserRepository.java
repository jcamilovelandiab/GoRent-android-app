package com.app.gorent.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.AuthResult;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;
    private DataSourceCache dataSourceCache;

    // private constructor : singleton access
    private UserRepository(DataSourceCache dataSourceCache) {
        this.dataSourceCache = dataSourceCache;
    }

    public static UserRepository getInstance(DataSourceCache dataSourceCache) {
        if (instance == null) {
            instance = new UserRepository(dataSourceCache);
        }
        return instance;
    }

    public void logout() {
        dataSourceCache.logout();
    }

    public void login(String username, String password, MutableLiveData<AuthResult> authResult) {
        // handle login
        dataSourceCache.login(username, password, authResult);
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        dataSourceCache.signUp(user, authResult);
    }

}