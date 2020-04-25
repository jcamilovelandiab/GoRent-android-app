package com.app.gorent.data.storage;

import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSourceCache {

    Map<String, User> users;

    public DataSourceCache(){
        users = new HashMap<>();
        User user = new User("juan camilo","camilo@mail.com", "camilo123");
        users.put(user.getEmail(), user);
    }

    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            if(users.containsKey(email) && users.get(email).getPassword().equals(password)){
                User user = users.get(email);
                String first_name = user.getFull_name().split(" ")[0];
                LoggedInUser loggedInUser = new LoggedInUser(user.getUserId(), first_name);
                return new Result.Success<>(loggedInUser);
            }
            return new Result.Error(new IOException("Invalid login"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> signUp(User user){
        if(users.containsKey(user.getEmail())){
            return new Result.Error(new IOException("Email has been already taken!"));
        }
        user.setUserId(java.util.UUID.randomUUID().toString());
        users.put(user.getEmail(), user);
        String first_name = user.getFull_name().split(" ")[0];
        LoggedInUser loggedInUser = new LoggedInUser(user.getUserId(), first_name);
        return new Result.Success<>(loggedInUser);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
