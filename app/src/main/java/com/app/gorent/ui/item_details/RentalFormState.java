package com.app.gorent.ui.item_details;

import androidx.annotation.Nullable;

public class RentalFormState {

    @Nullable
    private Integer dueDateError;
    private boolean isDataValid;

    public RentalFormState(@Nullable Integer dueDateError) {
        this.dueDateError = dueDateError;
        this.isDataValid = false;
    }

    public RentalFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getDueDateError() {
        return dueDateError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
