package com.app.gorent.data.storage.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.Role;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.Session;
import com.app.gorent.ui.activities.auth.LoggedInUserView;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.result.ItemLendingListQueryResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;
import com.app.gorent.utils.result.ItemListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.app.gorent.utils.result.UserQueryResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSourceSQLite {

    private Context context;
    private SQLite sqlite;
    private SQLiteDatabase database;
    private static DataSourceSQLite instance;

    private DataSourceSQLite(Context context) {
        this.context = context;
        this.sqlite = new SQLite(context);
        Log.i("SQLite",
                "Opens the database connection " +
                        sqlite.getDatabaseName());
        this.database = sqlite.getWritableDatabase();
        //clearTables();
    }

    private void clearTables(){
        this.database.execSQL("delete from items");
    }

    public static DataSourceSQLite getInstance(Context context){
        if(instance==null){
            instance = new DataSourceSQLite(context);
        }
        return instance;
    }

    public void login(String email, String password, MutableLiveData<AuthResult> authResult){
        String loginStatement = String.format(StatementsSQL.login, email, password);
        Cursor cursor = database.rawQuery(loginStatement,null);
        User user = CursorUtils.cursorToUser(cursor);
        if(user!=null){
            LoggedInUser loggedInUser = new LoggedInUser(user.getEmail()+"", user.getFull_name()+"");
            new Session(context).saveLoggedInUser(loggedInUser);
            authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
        }else{
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        ContentValues cv = new ContentValues();
        cv.put("email", user.getEmail());
        cv.put("password", user.getPassword());
        cv.put("full_name", user.getFull_name());
        cv.put("role", user.getRole().toString());
        long result=-1;
        try{
            result = database.insertOrThrow(
                    "users",
                    null, cv);
        }catch(SQLException e){
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
            authResult.setValue(new AuthResult(R.string.sign_up_failed));
            return;
        }
        if(result!=-1){
            LoggedInUser loggedInUser = new LoggedInUser(user.getEmail()+"", user.getFull_name()+"");
            new Session(context).saveLoggedInUser(loggedInUser);
            authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
        }else{
            authResult.setValue(new AuthResult(R.string.sign_up_failed));
        }
    }

    public void findUserByEmail(String email, MutableLiveData<UserQueryResult> userQueryResult){
        String findUserStatement = String.format(StatementsSQL.findUserByEmail, email);
        Cursor cursor = database.rawQuery(findUserStatement,null);
        User user = CursorUtils.cursorToUser(cursor);
        if(user!=null) {
            userQueryResult.setValue(new UserQueryResult(user));
        }else{
            userQueryResult.setValue(new UserQueryResult(R.string.error_user_not_found));
        }
    }


    public void logout() {
        new Session(context).clear();
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult) {
        ContentValues cv = new ContentValues();
        cv.put("id", item.getId());
        cv.put("name", item.getName());
        cv.put("description", item.getDescription());
        cv.put("price",item.getPrice());
        cv.put("feeType", item.getFeeType());
        cv.put("isRent", item.isRent()?1:0);
        cv.put("isDeleted",item.isDeleted()?1:0);
        cv.put("uploaded", item.isUploaded()?1:0);
        cv.put("image_path", item.getImage_path());
        cv.put("itemOwnerEmail", item.getItemOwner().getEmail());
        cv.put("categoryId", item.getCategory().getId());
        long result=-1;
        try{
            result = database.insertOrThrow(
                    "items",
                    null, cv);
        }catch(SQLException e){
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
            saveItemResult.setValue(new BasicResult(R.string.error_saving_item));
            return;
        }
        if(result!=-1){
            saveItemResult.setValue(new BasicResult("Item successfully saved!"));
        }else{
            saveItemResult.setValue(new BasicResult(R.string.error_saving_item));
        }
    }

    public void getItemById(String id, MutableLiveData<ItemQueryResult> itemQueryResult){
        String queryStatement = String.format(StatementsSQL.findItemById,id);
        Cursor cursor = database.rawQuery(queryStatement,null);
        Item item = CursorUtils.cursorToItem(cursor);
        if(item==null){
            itemQueryResult.setValue(new ItemQueryResult(R.string.error_item_not_found));
        }else{
            itemQueryResult.setValue(new ItemQueryResult(item));
        }
    }

    public List<Item> checkItemsNotUploaded(){
        String queryStatement = StatementsSQL.findAllItemsNotUploaded;
        Cursor cursor = database.rawQuery(queryStatement,null);
        List<Item> items = CursorUtils.cursorToItemList(cursor);
        return items;
    }

    public void itemWasUploaded(Item item) {
        ContentValues cv = new ContentValues();
        cv.put("uploaded", 1);
        String sqlStatement = String.format("update items set uploaded=1 where id='%s'", item.getId());
        this.database.execSQL(sqlStatement);
    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult) {
        ContentValues cv = new ContentValues();
        cv.put("id", item.getId());
        cv.put("name", item.getName());
        cv.put("description", item.getDescription());
        cv.put("price",item.getPrice());
        cv.put("feeType", item.getFeeType());
        cv.put("isRent", item.isRent());
        cv.put("isDeleted",item.isDeleted());
        cv.put("uploaded", item.isUploaded());
        cv.put("image_path", item.getImage_path());
        cv.put("itemOwnerEmail", item.getItemOwner().getEmail());
        cv.put("categoryId", item.getCategory().getId());
        int result = -1;
        result = this.database.update("items", cv, "id= '?'", new String[]{item.getId()});
        if(result!=-1){
            updateItemResult.setValue(new BasicResult("Item successfully updated!"));
        }else{
            updateItemResult.setValue(new BasicResult(R.string.error_updating_item));
        }
    }

    public void deleteItem(String itemId, boolean uploaded, MutableLiveData<BasicResult> deleteItemResult) {
        ContentValues cv = new ContentValues();
        cv.put("isDeleted", 1);
        cv.put("uploaded", uploaded);
        int result = -1;
        result = this.database.update("items", cv, "id= ?", new String[]{itemId});
        if(result!=-1){
            deleteItemResult.setValue(new BasicResult("Item successfully deleted!"));
        }else{
            deleteItemResult.setValue(new BasicResult(R.string.error_deleting_item));
        }
    }

    public void getCategories(MutableLiveData<CategoryListQueryResult> categoryListQueryResult) {
        Cursor cursor = database.rawQuery(StatementsSQL.findAllCategories, null);
        List<Category> categoryList = CursorUtils.cursorToCategoryList(cursor);
        if(categoryList==null){
            categoryListQueryResult.setValue(new CategoryListQueryResult(R.string.error_retrieving_categories));
        }else{
            categoryListQueryResult.setValue(new CategoryListQueryResult(categoryList));
        }
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemListQueryResult) {
        try{
            String sqlStatement = String.format(StatementsSQL.findItemsByOwner, itemOwner.getEmail());
            Cursor cursor = this.database.rawQuery(sqlStatement, null);
            List<Item> items = CursorUtils.cursorToItemList(cursor);
            itemListQueryResult.setValue(new ItemListQueryResult(items));
        }catch (Exception e){
            itemListQueryResult.setValue(new ItemListQueryResult(R.string.error_retrieving_items));
        }
    }

    public void getItemLendingHistoryByOwner(ItemOwner itemOwner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult) {
        itemLendingQueryResult.setValue(new ItemLendingListQueryResult(R.string.error_not_supported_yet));
    }

    public void getItemLendingHistoryByRentalUser(User rentalUser, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult) {
        itemLendingQueryResult.setValue(new ItemLendingListQueryResult(R.string.error_not_supported_yet));
    }

    public void getItemLendingById(String itemLendingId, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult) {
        itemLendingQueryResult.setValue(new ItemLendingQueryResult(R.string.error_not_supported_yet));
    }

    public User findUserByEmail(String email){
        String queryStatement = String.format(StatementsSQL.findUserByEmail, email);
        Cursor cursor = database.rawQuery(queryStatement, null);
        return CursorUtils.cursorToUser(cursor);
    }

}
