package com.app.gorent.data.repositories;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.storage.DataSourceCache;

import java.util.List;

public class CategoryRepository {

    private static volatile CategoryRepository instance;
    private DataSourceCache dataSource;

    private CategoryRepository(DataSourceCache dataSource){
        this.dataSource = dataSource;
    }

    public static CategoryRepository getInstance(DataSourceCache dataSource){
        if(instance == null){
            instance = new CategoryRepository(dataSource);
        }
        return instance;
    }

    public List<String> getNameCategories(){
        return dataSource.getNameCategories();
    }

    public Category getCategoryById(){
        return null;
    }

}
