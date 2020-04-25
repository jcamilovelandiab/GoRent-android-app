package com.app.gorent.utils;

import android.util.Patterns;

public class Validator {

    public static boolean isEmailValid(String email){
        if(email==null){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password){
        return password!=null && password.trim().length()>5;
    }

    public static boolean isConfirmPasswordValid(String password, String confirm_password){
        boolean passwordValid = isPasswordValid(password);
        if(!passwordValid) return false;

        boolean confirmPasswordValid = isPasswordValid(confirm_password);
        if(!confirmPasswordValid) return false;
        return password.equals(confirm_password);
    }

    public static boolean isNameValid(String name){
        // \\p{L} is a Unicode Character Property that matches any kind of letter from any language
        String regex = "^[\\p{L} .'-]+$";
        return name.matches(regex);
    }

}
