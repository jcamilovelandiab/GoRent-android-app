package com.app.gorent.utils;

import androidx.annotation.Nullable;

import com.app.gorent.data.model.ItemLending;

import java.util.List;

public class ItemLendingListQueryResult {

    @Nullable
    private List<ItemLending> itemLendingList;
    @Nullable
    private Integer error;

    public ItemLendingListQueryResult(@Nullable Integer error) {
        this.error = error;
    }

    public ItemLendingListQueryResult(@Nullable List<ItemLending> itemLendingList) {
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
