package com.app.gorent.ui.activities.rental_form;

import androidx.annotation.Nullable;

public class RentalFormState {

    @Nullable
    private Integer dueDateError;
    @Nullable
    private Integer addressError;
    private boolean isDataValid;

    public RentalFormState(@Nullable Integer dueDateError, @Nullable Integer addressError) {
        this.dueDateError = dueDateError;
        this.addressError = addressError;
        this.isDataValid = false;
    }

    public RentalFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getDueDateError() {
        return dueDateError;
    }

    @Nullable
    public Integer getAddressError() {
        return addressError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

}
