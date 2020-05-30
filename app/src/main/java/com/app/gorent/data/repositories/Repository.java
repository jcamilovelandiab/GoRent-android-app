package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.sql.DataSourceSQLite;
import com.app.gorent.data.storage.sql.SQLite;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.InternetConnectivity;

public abstract class Repository {

    private Context context;
    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;
    private DataSourceSQLite dataSourceSQLite;

    Repository(Context context){
        this.context = context;
        this.dataSourceCache = DataSourceCache.getInstance(context);
        this.dataSourceFirebase = DataSourceFirebase.getInstance(context);
        this.dataSourceSQLite = DataSourceSQLite.getInstance(context);
        synchronizeItems();
    }

    void synchronizeItems(){
        if(InternetConnectivity.check(context)){
            DatabaseSynchronization.uploadUncloudedItems(getDataSourceFirebase(), getDataSourceSQLite());
        }
    }

    Context getContext() {
        return context;
    }

    DataSourceCache getDataSourceCache() {
        return dataSourceCache;
    }

    DataSourceFirebase getDataSourceFirebase() {
        return dataSourceFirebase;
    }

    DataSourceSQLite getDataSourceSQLite() {
        return dataSourceSQLite;
    }

    public LoggedInUser getLoggedInUser(){
        return new Session(context).getLoggedInUser();
    }

}
