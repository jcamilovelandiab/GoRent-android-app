package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.Session;
import com.app.gorent.data.storage.sql.DataSourceSQLite;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.app.gorent.utils.result.UserQueryResult;

import java.util.List;

public class DatabaseSynchronization {

    static void synchronize(DataSourceFirebase dataSourceFirebase, DataSourceSQLite dataSourceSQLite){

    }

    static void downloadCloudedItems(DataSourceFirebase dataSourceFirebase,
                                     DataSourceSQLite dataSourceSQLite, List<Item> itemList){
        for(Item itemToDownload: itemList){
            MutableLiveData<ItemQueryResult> itemQueryResultMutable = new MutableLiveData<>();
            itemQueryResultMutable.observeForever(itemQueryResult -> {
                if(itemQueryResult==null) return;
                if(itemQueryResult.getItem()==null){ //resource not found
                    itemToDownload.setUploaded(true);
                    dataSourceSQLite.saveItem(itemToDownload, new MutableLiveData<>());
                    String [] array = itemToDownload.getImage_path().split("/");
                    String imageFileName = array[array.length-1];
                    dataSourceFirebase.downloadImage(imageFileName);
                }
            });
            dataSourceSQLite.getItemById(itemToDownload.getId(), itemQueryResultMutable);
        }
    }

    private static void synchronizeItemLending(){

    }

    static void downloadUserInformation(String full_name, String email, String password,
                                        DataSourceFirebase dataSourceFirebase, DataSourceSQLite dataSourceSQLite){
        User userQuerySQL = dataSourceSQLite.findUserByEmail(email);
        if(userQuerySQL==null){ // it's not in the database
            //Admin or user ?
            MutableLiveData<UserQueryResult> userFBResult = new MutableLiveData<>();
            userFBResult.observeForever(userQueryResult -> {
                if(userQueryResult==null) return;
                User user = new User(full_name+"",email+"", password+"");
                if(userQueryResult.getUser()!=null){
                    user.setRole(userQueryResult.getUser().getRole());
                }
                dataSourceSQLite.signUp(user, new MutableLiveData<>());
            });
            dataSourceFirebase.findUserByEmail(email, userFBResult);
        }
    }

}
