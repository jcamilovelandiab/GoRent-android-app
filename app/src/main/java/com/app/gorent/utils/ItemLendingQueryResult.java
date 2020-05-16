package com.app.gorent.utils;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.ItemLending;

import java.util.List;

public class ItemLendingQueryResult {

    @Nullable
    private List<ItemLending> itemLendingList;
    @Nullable
    private Integer error;

    public ItemLendingQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    public ItemLendingQueryResult(@Nullable List<ItemLending> itemLendingList) {
        this.itemLendingList = itemLendingList;
    }

    @Nullable
    public List<ItemLending> getItemLendingList() {
        return itemLendingList;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
