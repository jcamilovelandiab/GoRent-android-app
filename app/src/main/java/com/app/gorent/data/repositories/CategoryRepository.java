package com.app.gorent.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.utils.CategoryListQueryResult;
import com.app.gorent.utils.CategoryQueryResult;

import java.util.List;

public class CategoryRepository {

    private static volatile CategoryRepository instance;
    private DataSourceCache dataSourceCache;

    private CategoryRepository(DataSourceCache dataSourceCache){
        this.dataSourceCache = dataSourceCache;
    }

    public static CategoryRepository getInstance(DataSourceCache dataSourceCache){
        if(instance == null){
            instance = new CategoryRepository(dataSourceCache);
        }
        return instance;
    }

    public void getNameCategories(MutableLiveData<CategoryListQueryResult> categoryListQueryResult){
        dataSourceCache.getCategories(categoryListQueryResult);
    }

    public void getCategoryByName(String nameCategory, MutableLiveData<CategoryQueryResult> categoryQueryResult){
        dataSourceCache.getCategoryByName(nameCategory, categoryQueryResult);
    }

}
