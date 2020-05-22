package com.app.gorent.data.storage;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.LoggedInUser;

public class Session {

    @Nullable
    private static LoggedInUser loggedInUser;

    public static LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(@Nullable LoggedInUser loggedInUser) {
        Session.loggedInUser = loggedInUser;
    }

}
