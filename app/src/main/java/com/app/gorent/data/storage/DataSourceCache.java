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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSourceCache {
    private Long categoryCounter = 0L;
    private Long itemCounter = 0L;
    private Long itemLendingCounter = 0L;
    private static Map<Long, Category> categoriesMp = new HashMap<>();
    private static Map<Long, ItemLending> itemLendingMp = new HashMap<>();
    private static Map<Long, Item> itemsMp = new HashMap<>();
    private static Map<String, ItemOwner> itemOwnersMp = new HashMap<>();
    private static Map<String, User> usersMp = new HashMap<>();
    private static User loggedUser;

    public DataSourceCache(){
        User user1 = new User("juan camilo","camilo@mail.com", "camilo123");
        User user2 = new User("juan","juan@mail.com", "juan123");
        signUp(user1);
        signUp(user2);
        ItemOwner itemOwner1 = new ItemOwner(user1.getFull_name(),user1.getEmail());
        ItemOwner itemOwner2 = new ItemOwner(user2.getFull_name(),user2.getEmail());
        saveItemOwner(itemOwner1);
        saveItemOwner(itemOwner2);

        Category category1 = new Category("houses", "Luxury houses");
        Category category2 = new Category("cars", "Luxury cars. Lamborghini");
        Category category3 = new Category("pianos", "Grand piano");
        Category category4 = new Category("laptops", "Lenovo Computers");
        saveCategory(category1);
        saveCategory(category2);
        saveCategory(category3);
        saveCategory(category4);

        Item item1 = new Item("Lenovo legion","Legion Y15. RAM 16GB, Almacenamiento 1TB",
                3000L,"day", category4, itemOwner1);
        Item item2 = new Item("Rest house","Palm Beach Gardens UT, United States",
                10000000L, "month",category1, itemOwner1);
        Item item3 = new Item("Lamborghini Gallardo Spyder","Lamborghini Gallardo Lp-560-4 Mod 2013\n",
                370000L, "week", category2, itemOwner2);
        Item item4 = new Item("Pristine 1927 Steinway M","Piano was built in 1976 and is an exceptional piano which needs nothing.",
                374869L, "month",category3, itemOwner1);
        saveItem(item1);
        saveItem(item2);
        saveItem(item3);
        saveItem(item4);
    }
    // Users
    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            if(usersMp.containsKey(email) && usersMp.get(email).getPassword().equals(password)){
                loggedUser = usersMp.get(email);
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
        if(usersMp.containsKey(user.getEmail())){
            return new Result.Error(new IOException("Email has been already taken!"));
        }
        loggedUser = user;
        loggedUser.setUserId(java.util.UUID.randomUUID().toString());
        usersMp.put(user.getEmail(), user);
        String first_name = loggedUser.getFull_name().split(" ")[0];
        LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), first_name);
        return new Result.Success<>(loggedInUser);
    }

    public void logout() {
        loggedUser = null;
    }

    public User updateUser(User user){
        if(usersMp.containsKey(user.getEmail())){
            usersMp.put(user.getEmail(), user);
            return usersMp.get(user.getEmail());
        }
        return null;
    }

    // Items and ItemLending
    public List<Item> getAvailableItems(){
        List<Item> availableItems = new ArrayList<>();
        for (Map.Entry<Long, Item> entryItem: itemsMp.entrySet()) {
            Long itemId = entryItem.getKey();
            boolean isLentItem = false;
            for (Map.Entry<Long, ItemLending> entry: itemLendingMp.entrySet()) {
                ItemLending element = entry.getValue();
                if(element.getReturnDate()!=null && element.getItem().getId().equals(itemId)){ //it's a lent item
                    isLentItem = true;
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
        if(itemsMp.containsKey(id)){
            return itemsMp.get(id);
        }
        return null;
    }

    public List<Item> getItemsByName(String name){
        List<Item> itemList = new ArrayList<>();
        for (Map.Entry<Long,Item> entry: itemsMp.entrySet()) {
            if(entry.getValue().getName().equals(name)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<Item> getItemsByCategory(String nameCategory){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: itemsMp.entrySet()){
            if(entry.getValue().getCategory().getName().equals(nameCategory)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<Item> getItemsByEmailOwner(String email){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: itemsMp.entrySet()){
            if(entry.getValue().getItemOwner().getEmail().equals(email)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public ItemOwner getItemOwnerById(Long id){
        if(itemOwnersMp.containsKey(id)){
            return itemOwnersMp.get(id);
        }
        return null;
    }

    public List<ItemLending> getListItemLendingByEmailOwner(String email){
        if(!itemOwnersMp.containsKey(email)) return null; //the owner does not exist
        List<ItemLending> itemList = new ArrayList<>();
        for(Map.Entry<Long, ItemLending> entry: itemLendingMp.entrySet()){
            if(entry.getValue().getItem().getItemOwner().getEmail().equals(email)){
                itemList.add(entry.getValue());
            }
        }
        return itemList;
    }

    public List<ItemLending> getListItemLendingByEmailRenter(String email){
        if(!usersMp.containsKey(email)) return null; //the renter does not exist
        List<ItemLending> listItemLending = new ArrayList<>();
        for(Map.Entry<Long, ItemLending> entry: itemLendingMp.entrySet()){
            if(entry.getValue().getRenter().getEmail().equals(email)){
                listItemLending.add(entry.getValue());
            }
        }
        return listItemLending;
    }

    public Result<String> rentItemByUser(Date dueDate, Long totalPrice, Item item){
        User renter = new User(loggedUser.getFull_name()+"", loggedUser.getEmail()+"");
        ItemLending itemLending = new ItemLending(new Date(), dueDate,totalPrice,item, renter);
        itemLending.setId(++itemLendingCounter);
        itemLendingMp.put(itemLending.getId(), itemLending);
        if(itemLendingMp.containsKey(itemLending.getId()) && itemLendingMp.get(itemLending.getId())!=null){
            return new Result.Success<>("Item was successfully rented");
        }else{
            return new Result.Error(new IOException("Error renting item"));
        }
    }

    public Result<String> returnItem(ItemLending itemLending){
        if(itemLendingMp.containsKey(itemLending.getId())){
            ItemLending new_element = itemLendingMp.get(itemLending.getId());
            new_element.setReturnDate(itemLending.getReturnDate());
            itemLendingMp.put(itemLending.getId(), new_element);
            return new Result.Success<>("Item was successfully returned");
        }else{
            return new Result.Error(new IOException("Error returning item"));
        }
    }

    public Item saveItem(Item item){
        item.setId(++itemCounter);
        itemsMp.put(item.getId(), item);
        return itemsMp.get(item.getId());
    }

    public Item updateItem(Item item){
        if(itemsMp.containsKey(item.getId())){
            itemsMp.put(item.getId(), item);
            return itemsMp.get(item.getId());
        }
        return null;
    }

    // ItemOwner
    public ItemOwner saveItemOwner(ItemOwner itemOwner){
        itemOwnersMp.put(itemOwner.getUserId(), itemOwner);
        return itemOwnersMp.get(itemOwner.getUserId());
    }

    public Item updateItemOwner(ItemOwner itemOwner){
        if(itemOwnersMp.containsKey(itemOwner.getUserId())){
            itemOwnersMp.put(itemOwner.getUserId(), itemOwner);
        }
        return null;
    }

    //Category
    private Category saveCategory(Category category){
        category.setId(++categoryCounter);
        categoriesMp.put(category.getId(), category);
        return categoriesMp.get(category.getId());
    }

}
