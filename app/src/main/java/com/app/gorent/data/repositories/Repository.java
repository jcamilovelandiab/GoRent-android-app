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
import com.app.gorent.utils.result.BasicResult;

import java.util.List;

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
        //synchronizeDatabases();
    }

    private void synchronizeDatabases(){
        if(InternetConnectivity.check(getContext())){
            List<Item> itemsNotUploaded = this.dataSourceSQLite.checkItemsNotUploaded();
            if(itemsNotUploaded!=null && itemsNotUploaded.size()>0){
                for(Item item :itemsNotUploaded){
                    MutableLiveData<BasicResult> loadResult = new MutableLiveData<>();
                    loadResult.observeForever(basicResult -> {
                        if(basicResult==null) return;
                        if(basicResult.getSuccess()!=null){
                            getDataSourceSQLite().itemWasUploaded(item);
                        }
                    });
                    getDataSourceFirebase().saveItem(item, loadResult);
                }
            }
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
