package com.app.gorent.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.app.gorent.R;
import com.app.gorent.data.model.LoggedInUser;

public class Session {

    private final String EMAIL_KEY = "EMAIL_LOGGED_IN_USER";
    private final String NAME_KEY = "NAME_LOGGED_IN_USER";
    private final SharedPreferences sharedPreferences;

    public Session(Context context) {
        this.sharedPreferences =
                context.getSharedPreferences( context.getString(R.string.preference_file_key ), Context.MODE_PRIVATE );;
    }

    public LoggedInUser getLoggedInUser(){
        String email = sharedPreferences.getString(EMAIL_KEY,null);
        String name = sharedPreferences.getString(NAME_KEY, null);
        if(email!=null && name !=null){
            return new LoggedInUser(email+"", name+"");
        }
        return null;
    }

    void saveLoggedInUser(LoggedInUser loggedInUser){
        if(containsToken()) clear();
        sharedPreferences.edit().putString( EMAIL_KEY, loggedInUser.getEmail()).apply();
        sharedPreferences.edit().putString(NAME_KEY, loggedInUser.getFull_name()).apply();
    }

    private boolean containsToken(){
        return sharedPreferences.contains( EMAIL_KEY ) && sharedPreferences.contains(NAME_KEY);
    }

    void clear(){
        sharedPreferences.edit().remove( EMAIL_KEY ).apply();
        sharedPreferences.edit().remove(NAME_KEY).apply();
    }

}
