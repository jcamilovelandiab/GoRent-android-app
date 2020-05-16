package com.app.gorent.data.storage;

import com.app.gorent.data.model.LoggedInUser;

public class Session {

    private static LoggedInUser loggedInUser;

    public static LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(LoggedInUser loggedInUser) {
        Session.loggedInUser = loggedInUser;
    }

}
