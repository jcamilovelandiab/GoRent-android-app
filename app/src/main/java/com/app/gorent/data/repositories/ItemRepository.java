package com.app.gorent.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.InternetConnectivity;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;

public class ItemRepository extends Repository{

    private static volatile ItemRepository instance;

    private ItemRepository(Context context){
        super(context);
    }

    public static ItemRepository getInstance(Context context){
        if(instance == null){
            instance = new ItemRepository(context);
        }
        return instance;
    }

    public void getAvailableItems(User loggedInUser, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getAvailableItems(loggedInUser, itemListQueryResult);
        }else{
            itemListQueryResult.setValue(new ItemListQueryResult(R.string.error_internet_connection));
        }
    }

    public void getItemById(String id, MutableLiveData<ItemQueryResult> itemQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getItemById(id, itemQueryResult);
        }else{
            getDataSourceSQLite().getItemById(id, itemQueryResult);
        }
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){
        if(InternetConnectivity.check(getContext())){
            MutableLiveData<BasicResult> itemResultMutable = new MutableLiveData<>();
            itemResultMutable.observeForever(basicResult -> {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    saveItemResult.setValue(basicResult);
                }
                if(basicResult.getSuccess()!=null){
                    item.setUploaded(true);
                    getDataSourceSQLite().saveItem(item, saveItemResult);
                }
            });
            getDataSourceFirebase().saveItem(item, itemResultMutable);
        }else{
            item.setUploaded(false);
            getDataSourceSQLite().saveItem(item, saveItemResult);
        }
    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult){
        if(InternetConnectivity.check(getContext())){
            MutableLiveData<BasicResult> itemResultMutable = new MutableLiveData<>();
            itemResultMutable.observeForever(basicResult -> {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    updateItemResult.setValue(basicResult);
                }
                if(basicResult.getSuccess()!=null){
                    item.setUploaded(true);
                    getDataSourceSQLite().updateItem(item, updateItemResult);
                }
            });
            getDataSourceFirebase().updateItem(item, itemResultMutable);
        }else{
            item.setUploaded(false);
            getDataSourceSQLite().updateItem(item, updateItemResult);
        }
    }

    public void deleteItem(String itemId, MutableLiveData<BasicResult> deleteItemResult){
        if(InternetConnectivity.check(getContext())){
            MutableLiveData<BasicResult> itemResultMutable = new MutableLiveData<>();
            itemResultMutable.observeForever(basicResult -> {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    deleteItemResult.setValue(basicResult);
                }
                if(basicResult.getSuccess()!=null){
                    getDataSourceSQLite().deleteItem(itemId, true, deleteItemResult);
                }
            });
            getDataSourceFirebase().deleteItem(itemId, itemResultMutable);
        }else{
            getDataSourceSQLite().deleteItem(itemId,false, deleteItemResult);
        }
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        if(InternetConnectivity.check(getContext())){
            MutableLiveData<ItemListQueryResult> itemListResultFB = new MutableLiveData<>();
            itemListResultFB.observeForever(itemListQueryResultFirebase -> {
                if(itemListQueryResultFirebase == null) return;
                if(itemListQueryResultFirebase.getError()!=null){
                    itemListQueryResult.setValue(itemListQueryResultFirebase);
                }
                if(itemListQueryResultFirebase.getItems()!=null){
                    DatabaseSynchronization.downloadCloudedItems(getDataSourceFirebase(), getDataSourceSQLite(),
                            itemListQueryResultFirebase.getItems());
                    itemListQueryResult.setValue(itemListQueryResultFirebase);
                }
            });
            getDataSourceFirebase().getItemsByOwner(itemOwner, itemListResultFB);
        }else{
            getDataSourceSQLite().getItemsByOwner(itemOwner, itemListQueryResult);
        }
    }

    public void getItemsByNameOrCategory(String search_text, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getItemsByNameOrCategory(search_text, itemListQueryResult);
        }
    }

    public void getAllItemsAdmin(MutableLiveData<ItemListQueryResult> itemListQueryResult){
        if(InternetConnectivity.check(getContext())){
            getDataSourceFirebase().getAllItems(itemListQueryResult);
        }
    }

}
