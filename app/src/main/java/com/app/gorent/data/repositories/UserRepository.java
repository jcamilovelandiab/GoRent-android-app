package com.app.gorent.data.repositories;

import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.Result;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;
    private DataSourceCache dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private UserRepository(DataSourceCache dataSource) {
        this.dataSource = dataSource;
    }

    public static UserRepository getInstance(DataSourceCache dataSource) {
        if (instance == null) {
            instance = new UserRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public LoggedInUser getLoggedUser() {
        return user;
    }

    public void setUser(LoggedInUser user) {
        this.user = user;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            // the user could logged in successfully
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public Result<LoggedInUser> signup(User user){
        Result<LoggedInUser> result = dataSource.signUp(user);
        if(result instanceof Result.Success){
            // the user could signed up and logged in successfully
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

}