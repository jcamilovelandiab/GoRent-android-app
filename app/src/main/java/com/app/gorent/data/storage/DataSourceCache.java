package com.app.gorent.data.storage;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.Result;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSourceCache {

    private static Map<Long, Category> categories = new HashMap<>();
    private static Map<Long, ItemLending> itemLending = new HashMap<>();
    private static Map<Long, ItemOwner> itemOwners = new HashMap<>();
    private static Map<Long, Item> items = new HashMap<>();
    private static Map<String, User> users = new HashMap<>();
    private static User loggedUser;

    public DataSourceCache(){
        User user1 = new User("juan camilo","camilo@mail.com", "camilo123");
        User user2 = new User("juan","juan@mail.com", "juan123");
        users.put(user1.getEmail(), user1);
        users.put(user2.getEmail(), user2);
        ItemOwner itemOwner1 = new ItemOwner(1L, user1.getFull_name(),user1.getEmail());
        ItemOwner itemOwner2 = new ItemOwner(2L, user2.getFull_name(),user2.getEmail());
        itemOwners.put(itemOwner1.getId(), itemOwner1);
        itemOwners.put(itemOwner2.getId(), itemOwner2);

        Category category1 = new Category(1L, "Houses", "Luxury houses");
        Category category2 = new Category(2L, "Cars", "Luxury cars. Lamborghini");
        Category category3 = new Category(3L, "Pianos", "Grand piano");
        Category category4 = new Category(4L, "Laptops", "Lenovo Computers");
        categories.put(category1.getId(), category1);
        categories.put(category2.getId(), category2);
        categories.put(category3.getId(), category3);
        categories.put(category4.getId(), category4);

        Item item1 = new Item(1L, "Lenovo legion","Legion Y15. RAM 16GB, Almacenamiento 1TB",
                30000L, category4, itemOwner1);
        Item item2 = new Item(2L, "Rest house","Palm Beach Gardens UT, United States",
                10000000L, category1, itemOwner1);
        Item item3 = new Item(3L, "Lamborghini Gallardo Spyder","Lamborghini Gallardo Lp-560-4 Mod 2013\n",
                3700000L, category2, itemOwner2);
        Item item4 = new Item(4L, "Pristine 1927 Steinway M","Piano was built in 1976 and is an exceptional piano which needs nothing.",
                374869L, category3, itemOwner1);

        items.put(item1.getId(), item1);
        items.put(item2.getId(), item2);
        items.put(item3.getId(), item3);
        items.put(item4.getId(), item4);
    }

    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            if(users.containsKey(email) && users.get(email).getPassword().equals(password)){
                loggedUser = users.get(email);
                String first_name = loggedUser.getFull_name().split(" ")[0];
                LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), first_name);
                return new Result.Success<>(loggedInUser);
            }
            return new Result.Error(new IOException("Invalid login"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> signUp(User user){
        if(users.containsKey(user.getEmail())){
            return new Result.Error(new IOException("Email has been already taken!"));
        }
        loggedUser = user;
        loggedUser.setUserId(java.util.UUID.randomUUID().toString());
        users.put(user.getEmail(), user);
        String first_name = loggedUser.getFull_name().split(" ")[0];
        LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), first_name);
        return new Result.Success<>(loggedInUser);
    }

    public void logout() {
        loggedUser = null;
    }

    public List<Item> getAvailableItems(){
        List<Item> availableItems = new ArrayList<>();
        for (Map.Entry<Long, Item> entryItem: items.entrySet()) {
            Long itemId = entryItem.getKey();
            boolean isLentItem = true;
            for (Map.Entry<Long, ItemLending> entry: itemLending.entrySet()) {
                ItemLending element = entry.getValue();
                if(element.getItem().getId().equals(itemId)){ //it's a lent item
                    isLentItem = false;
                    break;
                }
            }
            if(!isLentItem){
                availableItems.add(entryItem.getValue());
            }
        }
        return availableItems;
    }

    public Item getItemById(Long id){
        if(items.containsKey(id)){
            return items.get(id);
        }
        return null;
    }

    public List<Item> getItemsByName(String name){
        List<Item> itemList = new ArrayList<>();
        for (Map.Entry<Long,Item> entry: items.entrySet()) {
            if(entry.getValue().getName().equals(name)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<Item> getItemsByCategory(String nameCategory){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: items.entrySet()){
            if(entry.getValue().getCategory().getName().equals(nameCategory)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<Item> getItemsByEmailOwner(String email){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: items.entrySet()){
            if(entry.getValue().getItemOwner().getEmail().equals(email)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public ItemOwner getItemOwnerById(Long id){
        if(itemOwners.containsKey(id)){
            return itemOwners.get(id);
        }
        return null;
    }

    public List<ItemLending> getListItemLendingByEmailOwner(String email){
        if(!itemOwners.containsKey(email)) return null; //the owner does not exist
        List<ItemLending> itemList = new ArrayList<>();
        for(Map.Entry<Long, ItemLending> entry: itemLending.entrySet()){
            if(entry.getValue().getItem().getItemOwner().getEmail().equals(email)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<ItemLending> getListItemLendingByEmailRenter(String email){
        if(!users.containsKey(email)) return null; //the renter does not exist
        List<ItemLending> listItemLending = new ArrayList<>();
        for(Map.Entry<Long, ItemLending> entry: itemLending.entrySet()){
            if(entry.getValue().getRenter().getEmail().equals(email)){
                listItemLending.add(entry.getValue());
            }
        }
        return listItemLending;
    }

}
