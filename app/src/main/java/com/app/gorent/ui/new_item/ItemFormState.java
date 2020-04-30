package com.app.gorent.ui.new_item;

import androidx.annotation.Nullable;

public class ItemFormState {

    @Nullable
    private Integer nameError;
    @Nullable
    private Integer descriptionError;
    @Nullable
    private Integer priceError;
    @Nullable
    private Integer feeTypeError;
    @Nullable
    private Integer categoryError;

    private boolean isDataValid;

    public ItemFormState(@Nullable Integer nameError, @Nullable Integer descriptionError, @Nullable Integer priceError, @Nullable Integer feeTypeError, @Nullable Integer categoryError) {
        this.nameError = nameError;
        this.descriptionError = descriptionError;
        this.priceError = priceError;
        this.feeTypeError = feeTypeError;
        this.categoryError = categoryError;
        this.isDataValid = false;
    }

    public ItemFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.nameError = null;
        this.descriptionError = null;
        this.priceError = null;
        this.feeTypeError = null;
        this.categoryError = null;
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    public void setNameError(@Nullable Integer nameError) {
        this.nameError = nameError;
    }

    @Nullable
    public Integer getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(@Nullable Integer descriptionError) {
        this.descriptionError = descriptionError;
    }

    @Nullable
    public Integer getPriceError() {
        return priceError;
    }

    public void setPriceError(@Nullable Integer priceError) {
        this.priceError = priceError;
    }

    @Nullable
    public Integer getFeeTypeError() {
        return feeTypeError;
    }

    public void setFeeTypeError(@Nullable Integer feeTypeError) {
        this.feeTypeError = feeTypeError;
    }

    @Nullable
    public Integer getCategoryError() {
        return categoryError;
    }

    public void setCategoryError(@Nullable Integer categoryError) {
        this.categoryError = categoryError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }

}
