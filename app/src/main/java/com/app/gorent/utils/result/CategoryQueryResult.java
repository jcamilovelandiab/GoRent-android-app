package com.app.gorent.utils.result;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.Category;

public class CategoryQueryResult {

    @Nullable
    private Category category;

    @Nullable
    private Integer error;

    public CategoryQueryResult(@Nullable Category category) {
        this.category = category;
    }

    public CategoryQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
