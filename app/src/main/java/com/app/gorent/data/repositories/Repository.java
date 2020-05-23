package com.app.gorent.data.repositories;

import android.content.Context;

import com.app.gorent.data.storage.DataSourceCache;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.DataSourceSQLite;

public abstract class Repository {

    private Context context;
    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;
    private DataSourceSQLite dataSourceSQLite;

    Repository(Context context){
        this.context = context;
        this.dataSourceCache = DataSourceCache.getInstance();
        this.dataSourceFirebase = DataSourceFirebase.getInstance();
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
}
