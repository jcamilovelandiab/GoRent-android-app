package com.app.gorent.utils.result;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.ItemLending;

public class ItemLendingQueryResult {

    @Nullable
    private ItemLending itemLending;
    @Nullable
    private Integer error;

    public ItemLendingQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    public ItemLendingQueryResult(@Nullable ItemLending itemLending) {
        this.itemLending = itemLending;
    }

    @Nullable
    public ItemLending getItemLending() {
        return itemLending;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
