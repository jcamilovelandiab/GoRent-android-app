package com.app.gorent.ui.auth.signup;

import androidx.annotation.Nullable;


/**
 * This class was made to validate the sign up form.
 * If the form is invalid the registration will not proceed.
 */
class SignUpFormState {

    @Nullable
    private Integer full_nameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirm_passwordError;
    private boolean isDataValid;

    SignUpFormState(@Nullable Integer full_nameError,
                           @Nullable Integer emailError,
                           @Nullable Integer passwordError,
                           @Nullable Integer confirm_passwordError) {
        this.full_nameError = full_nameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirm_passwordError = confirm_passwordError;
        this.isDataValid = false;
    }

    SignUpFormState(boolean isDataValid){
        this.full_nameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirm_passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getFull_nameError() {
        return full_nameError;
    }

    public void setFull_nameError(@Nullable Integer full_nameError) {
        this.full_nameError = full_nameError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    public void setEmailError(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    @Nullable
    public Integer getConfirm_passwordError() {
        return confirm_passwordError;
    }

    public void setConfirm_passwordError(@Nullable Integer confirm_passwordError) {
        this.confirm_passwordError = confirm_passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}
