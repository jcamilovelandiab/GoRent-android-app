package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.DataSourceFirebase;
import com.app.gorent.data.storage.Session;
import com.app.gorent.data.storage.sql.DataSourceSQLite;
import com.app.gorent.utils.InternetConnectivity;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.app.gorent.utils.result.UserQueryResult;

import java.util.List;

public class DatabaseSynchronization {

    static void downloadCloudedItems(DataSourceFirebase dataSourceFirebase,
                                     DataSourceSQLite dataSourceSQLite, List<Item> itemList){
        for(Item itemToDownload: itemList){
            Item itemFound = dataSourceSQLite.findItemByIdWithDeleted(itemToDownload.getId());
            if(itemFound==null){
                dataSourceFirebase.saveItem(itemToDownload, new MutableLiveData<>());
            }
        }
    }

    static void uploadUncloudedItems(DataSourceFirebase dataSourceFirebase,
                                     DataSourceSQLite dataSourceSQLite){
        List<Item> itemList = dataSourceSQLite.checkItemsNotUploaded();
        for(Item item:  itemList){
            MutableLiveData<ItemQueryResult> itemQueryResultMutable  =new MutableLiveData<>();
            itemQueryResultMutable.observeForever(itemQueryResult -> {
                if(itemQueryResult==null) return;
                if(itemQueryResult.getError()!=null){ //item doesn't exists. Need to be uploaded
                    saveUncloudedItem(dataSourceFirebase, dataSourceSQLite, item);
                }
                if(itemQueryResult.getItem()!=null){ //item already exists. Needs to be updated
                    updateUncloudedItem(dataSourceFirebase, dataSourceSQLite, item);
                }
            });
            dataSourceFirebase.getItemById(item.getId(), itemQueryResultMutable);
        }
    }

    private static void saveUncloudedItem(DataSourceFirebase dataSourceFirebase,
                                          DataSourceSQLite dataSourceSQLite, Item item){
        MutableLiveData<BasicResult> saveItemResult = new MutableLiveData<>();
        saveItemResult.observeForever(basicResult -> {
            if(basicResult==null) return;
            if(basicResult.getSuccess()!=null){
                dataSourceSQLite.itemWasUploaded(item);
            }
        });
        dataSourceFirebase.saveItem(item, saveItemResult);
    }

    private static void updateUncloudedItem(DataSourceFirebase dataSourceFirebase,
                                          DataSourceSQLite dataSourceSQLite, Item item){
        MutableLiveData<BasicResult> uploadItemResult = new MutableLiveData<>();
        uploadItemResult.observeForever(basicResult -> {
            if(basicResult==null) return;
            if(basicResult.getSuccess()!=null){
                dataSourceSQLite.itemWasUploaded(item);
            }
        });
        dataSourceFirebase.updateItem(item, uploadItemResult);
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
