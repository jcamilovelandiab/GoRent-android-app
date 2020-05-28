package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.utils.InternetConnectivity;
import com.app.gorent.utils.result.CategoryListQueryResult;

public class CategoryRepository extends Repository{

    private static volatile CategoryRepository instance;

    private CategoryRepository(Context context){
        super(context);
    }

    public static CategoryRepository getInstance(Context context){
        if(instance == null){
            instance = new CategoryRepository(context);
        }
        return instance;
    }

    public void getCategories(MutableLiveData<CategoryListQueryResult> categoryListQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getCategories(categoryListQueryResult);
        }else{
            getDataSourceCache().getCategories(categoryListQueryResult);
        }
    }

}
