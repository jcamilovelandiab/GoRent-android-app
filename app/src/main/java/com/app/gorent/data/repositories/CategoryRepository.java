package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.DataSourceSQLite;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.result.CategoryQueryResult;

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
        getDataSourceCache().getCategories(categoryListQueryResult);
    }

    public void getCategoryByName(String nameCategory, MutableLiveData<CategoryQueryResult> categoryQueryResult){
        getDataSourceCache().getCategoryByName(nameCategory, categoryQueryResult);
    }

}
