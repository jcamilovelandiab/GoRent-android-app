package com.app.gorent.data.storage.sql;

import android.database.Cursor;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.Role;
import com.app.gorent.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class CursorUtils {

    static List<Item> cursorToItemList(Cursor cursor){
        List<Item> itemList = itemList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                Category category = new Category();
                ItemOwner itemOwner = new ItemOwner();
                item.setId(cursor.getString(cursor.getColumnIndex("items.id")));
                item.setName(cursor.getString(cursor.getColumnIndex("items.name")));
                item.setDescription(cursor.getString(cursor.getColumnIndex("items.description")));
                item.setFeeType(cursor.getString(cursor.getColumnIndex("items.feeType")));
                item.setPrice(cursor.getLong(cursor.getColumnIndex("items.price")));
                item.setImage_path(cursor.getString(cursor.getColumnIndex("items.image_path")));
                item.setRent(cursor.getInt(cursor.getColumnIndex("items.isRent")) == 1);
                item.setDeleted(cursor.getInt(cursor.getColumnIndex("items.isDeleted"))==1);
                item.setUploaded(cursor.getInt(cursor.getColumnIndex("items.uploaded"))==1);
                category.setId(cursor.getString(cursor.getColumnIndex("items.categoryId")));
                category.setName(cursor.getString(cursor.getColumnIndex("categories.name")));
                category.setDescription(cursor.getString(cursor.getColumnIndex("categories.description")));
                itemOwner.setEmail(cursor.getString(cursor.getColumnIndex("items.itemOwnerEmail")));
                itemOwner.setFull_name(cursor.getString(cursor.getColumnIndex("users.full_name")));
                item.setCategory(category);
                item.setItemOwner(itemOwner);
                System.out.println(item);
                itemList.add(item);
            }while(cursor.moveToNext());
        }
        return itemList;
    }

    static Item cursorToItem(Cursor cursor){
        Item item = null;
        if(cursor.moveToFirst()){
            item = new Item();
            do{
                Category category = new Category();
                ItemOwner itemOwner = new ItemOwner();
                item.setId(cursor.getString(cursor.getColumnIndex("items.id")));
                item.setName(cursor.getString(cursor.getColumnIndex("items.name")));
                item.setDescription(cursor.getString(cursor.getColumnIndex("items.description")));
                item.setFeeType(cursor.getString(cursor.getColumnIndex("items.feeType")));
                item.setPrice(cursor.getLong(cursor.getColumnIndex("items.price")));
                item.setImage_path(cursor.getString(cursor.getColumnIndex("items.image_path")));
                item.setRent(cursor.getInt(cursor.getColumnIndex("items.isRent")) == 1);
                item.setDeleted(cursor.getInt(cursor.getColumnIndex("items.isDeleted"))==1);
                item.setUploaded(cursor.getInt(cursor.getColumnIndex("items.uploaded"))==1);
                category.setId(cursor.getString(cursor.getColumnIndex("items.categoryId")));
                category.setName(cursor.getString(cursor.getColumnIndex("categories.name")));
                category.setDescription(cursor.getString(cursor.getColumnIndex("categories.description")));
                itemOwner.setEmail(cursor.getString(cursor.getColumnIndex("items.itemOwnerEmail")));
                itemOwner.setFull_name(cursor.getString(cursor.getColumnIndex("users.full_name")));
                item.setCategory(category);
                item.setItemOwner(itemOwner);
            }while(cursor.moveToNext());
        }
        return item;
    }

    static List<Category> cursorToCategoryList(Cursor cursor){
        List<Category> categoryList = null;
        if(cursor.moveToFirst()){
            categoryList=new ArrayList<>();
            do{
                Category category = new Category();
                category.setId(cursor.getString(cursor.getColumnIndex("id")));
                category.setName(cursor.getString(cursor.getColumnIndex("name")));
                category.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                categoryList.add(category);
            }while(cursor.moveToNext());
        }
        return categoryList;
    }

    static List<User> cursorToUserList(Cursor cursor){
        List<User> userList= null;
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setFull_name(cursor.getString(cursor.getColumnIndex("full_name")));
                user.setRole(Role.valueOf(cursor.getString(cursor.getColumnIndex("role"))));
                userList.add(user);
            }while(cursor.moveToNext());
        }
        return userList;
    }

    static User cursorToUser(Cursor cursor){
        User user = null;
        if(cursor.moveToFirst()){
            do{
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setFull_name(cursor.getString(cursor.getColumnIndex("full_name")));
                user.setRole(Role.valueOf(cursor.getString(cursor.getColumnIndex("role"))));
                break;
            }while(cursor.moveToNext());
        }
        return user;
    }


}
