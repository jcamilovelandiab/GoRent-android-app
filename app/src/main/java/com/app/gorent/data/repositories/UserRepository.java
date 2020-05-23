package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.DataSourceSQLite;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.CheckInternetConnectivity;
import com.app.gorent.utils.result.AuthResult;

import javax.sql.DataSource;

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
        getDataSourceCache().logout();
    }

    public void login(String username, String password, MutableLiveData<AuthResult> authResult) {
        // handle login
        getDataSourceCache().login(username, password, authResult);
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        getDataSourceCache().signUp(user, authResult);
    }

}