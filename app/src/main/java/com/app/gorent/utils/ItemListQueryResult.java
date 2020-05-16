package com.app.gorent.utils;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.Item;

import java.util.List;

public class ItemListQueryResult {
    @Nullable
    private List<Item> items;
    @Nullable
    private Integer error;

    public ItemListQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    public ItemListQueryResult(@Nullable List<Item> items) {
        this.items = items;
    }

    @Nullable
    public List<Item> getItems() {
        return items;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
