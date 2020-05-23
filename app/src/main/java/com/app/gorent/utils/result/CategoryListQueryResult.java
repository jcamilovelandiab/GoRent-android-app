package com.app.gorent.utils.result;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.Category;

import java.util.List;

public class CategoryListQueryResult {

    @Nullable
    private List<Category> categoryList;

    @Nullable
    private Integer error;

    public CategoryListQueryResult(@Nullable List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryListQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public List<Category> getCategoryList() {
        return categoryList;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
