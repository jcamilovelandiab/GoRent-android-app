package com.app.gorent.utils;

import androidx.annotation.Nullable;

public class BasicResult {

    @Nullable
    private String success;
    @Nullable
    private Integer error;

    public BasicResult(@Nullable Integer error) {
        this.error = error;
    }

    public BasicResult(@Nullable String success) {
        this.success = success;
    }

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
