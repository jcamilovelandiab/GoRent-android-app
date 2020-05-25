package com.app.gorent.utils.result;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.User;

public class UserQueryResult {

    @Nullable
    private User user;

    @Nullable
    private Integer error;

    public UserQueryResult(@Nullable User user) {
        this.user = user;
    }

    public UserQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
