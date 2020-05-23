package com.app.gorent.utils.result;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.Item;

public class ItemQueryResult {

    @Nullable
    private Item item;
    @Nullable
    private Integer error;

    public ItemQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    public ItemQueryResult(@Nullable Item item) {
        this.item = item;
    }

    @Nullable
    public Item getItem() {
        return item;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
