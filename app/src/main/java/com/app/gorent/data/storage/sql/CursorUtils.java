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
                item.setId(cursor.getString(cursor.getColumnIndex("itemId")));
                item.setName(cursor.getString(cursor.getColumnIndex("itemName")));
                item.setDescription(cursor.getString(cursor.getColumnIndex("itemDescription")));
                item.setFeeType(cursor.getString(cursor.getColumnIndex("itemFeeType")));
                item.setPrice(cursor.getLong(cursor.getColumnIndex("itemPrice")));
                item.setImage_path(cursor.getString(cursor.getColumnIndex("itemImagePath")));
                item.setRent(cursor.getInt(cursor.getColumnIndex("itemIsRent")) == 1);
                item.setDeleted(cursor.getInt(cursor.getColumnIndex("itemIsDeleted"))==1);
                item.setUploaded(cursor.getInt(cursor.getColumnIndex("itemUploaded"))==1);
                category.setId(cursor.getString(cursor.getColumnIndex("categoryId")));
                category.setName(cursor.getString(cursor.getColumnIndex("categoryName")));
                category.setDescription(cursor.getString(cursor.getColumnIndex("categoryDescription")));
                itemOwner.setEmail(cursor.getString(cursor.getColumnIndex("userEmail")));
                itemOwner.setFull_name(cursor.getString(cursor.getColumnIndex("userFullName")));
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
                item.setId(cursor.getString(cursor.getColumnIndex("itemId")));
                item.setName(cursor.getString(cursor.getColumnIndex("itemName")));
                item.setDescription(cursor.getString(cursor.getColumnIndex("itemDescription")));
                item.setFeeType(cursor.getString(cursor.getColumnIndex("itemFeeType")));
                item.setPrice(cursor.getLong(cursor.getColumnIndex("itemPrice")));
                item.setImage_path(cursor.getString(cursor.getColumnIndex("itemImagePath")));
                item.setRent(cursor.getInt(cursor.getColumnIndex("itemIsRent")) == 1);
                item.setDeleted(cursor.getInt(cursor.getColumnIndex("itemIsDeleted"))==1);
                item.setUploaded(cursor.getInt(cursor.getColumnIndex("itemUploaded"))==1);
                category.setId(cursor.getString(cursor.getColumnIndex("categoryId")));
                category.setName(cursor.getString(cursor.getColumnIndex("categoryName")));
                category.setDescription(cursor.getString(cursor.getColumnIndex("categoryDescription")));
                itemOwner.setEmail(cursor.getString(cursor.getColumnIndex("userEmail")));
                itemOwner.setFull_name(cursor.getString(cursor.getColumnIndex("userFullName")));
                item.setCategory(category);
                item.setItemOwner(itemOwner);
                System.out.println(item);
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
