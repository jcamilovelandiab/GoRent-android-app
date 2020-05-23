package com.app.gorent.data.storage;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.utils.AuthResult;
import com.app.gorent.ui.activities.auth.LoggedInUserView;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.CategoryListQueryResult;
import com.app.gorent.utils.CategoryQueryResult;
import com.app.gorent.utils.ItemLendingListQueryResult;
import com.app.gorent.utils.ItemLendingQueryResult;
import com.app.gorent.utils.ItemListQueryResult;
import com.app.gorent.utils.ItemQueryResult;

import java.util.ArrayList;
import java.util.Calendar;
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

    private static DataSourceCache instance=null;

    public static DataSourceCache getInstance(){
        if(instance==null){
            instance = new DataSourceCache();
        }
        return instance;
    }

    private DataSourceCache(){
        User user1 = new User("Juan Felipe","juan@mail.com", "juan123");
        User user2 = new User("Juan Camilo","camilo@mail.com", "camilo123");
        usersMp.put(user1.getEmail(), user1);
        usersMp.put(user2.getEmail(), user2);
        ItemOwner itemOwner1 = new ItemOwner(user1.getEmail(),user1.getFull_name());
        ItemOwner itemOwner2 = new ItemOwner(user2.getEmail(),user2.getFull_name());
        saveItemOwner(itemOwner1);
        saveItemOwner(itemOwner2);

        Category category1 = new Category("houses", "Luxury houses");
        Category category2 = new Category("cars", "Luxury cars. Lamborghini");
        Category category3 = new Category("pianos", "Grand piano");
        Category category4 = new Category("laptops", "High-quality Computers");
        MutableLiveData<BasicResult> basicResult = new MutableLiveData<>();
        saveCategory(category1, basicResult);
        saveCategory(category2, basicResult);
        saveCategory(category3, basicResult);
        saveCategory(category4, basicResult);

        Item item1 = new Item("Lenovo legion","Legion Y15. RAM 16GB, Almacenamiento 1TB",
                3000L,"Daily fee", category4, itemOwner1);
        Item item2 = new Item("Rest house","Palm Beach Gardens UT, United States",
                10000000L, "Monthly fee",category1, itemOwner1);
        Item item3 = new Item("Lamborghini Gallardo Spyder","Lamborghini Gallardo Lp-560-4 Mod 2013",
                370000L, "Weekly fee", category2, itemOwner2);
        Item item4 = new Item("Pristine 1927 Steinway M","Piano was built in 1976 and is an exceptional piano which needs nothing.",
                374869L, "Monthly fee",category3, itemOwner1);
        saveItem(item1, basicResult);
        saveItem(item2, basicResult);
        saveItem(item3, basicResult);
        saveItem(item4, basicResult);

        Calendar c1= Calendar.getInstance();
        c1.add(Calendar.DATE, 30);
        Date dueDate1=c1.getTime();

        Calendar c2= Calendar.getInstance();
        c2.add(Calendar.DATE, 10);
        Date dueDate2=c2.getTime();

        rentItemByUser(dueDate1,10000L, item1, user2, "Alameda, Bogotá, Colombia.", basicResult);
        rentItemByUser(dueDate2,15000L, item2,user2, "El poblado, Medellín, Antioquía",basicResult);
        //rentItemByUser(dueDate1,20000L, item4, user2,"Alameda, Bogotá, Colombia.", basicResult);
        //rentItemByUser(dueDate2,15000L,item3, user1, basicResult);
    }
    /* -------------------------------------------------------------------------- */
    /*                                USERS                                */
    /* -------------------------------------------------------------------------- */
    public void login(String email, String password, MutableLiveData<AuthResult> authResult) {
        try {
            // TODO: handle loggedInUser authentication
            if(usersMp.containsKey(email) && usersMp.get(email).getPassword().equals(password)){
                User loggedUser = usersMp.get(email);
                //String first_name = loggedUser.getFull_name().split(" ")[0];
                LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), loggedUser.getFull_name());
                Session.setLoggedInUser(loggedInUser);
                authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
            }else{
                authResult.setValue(new AuthResult(R.string.login_failed));
            }
        } catch (Exception e) {
            authResult.setValue(new AuthResult(R.string.error_logging_in));
        }
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        if(usersMp.containsKey(user.getEmail())){
            authResult.setValue(new AuthResult(R.string.error_email_already_taken));
            return;
        }
        usersMp.put(user.getEmail(), user);
        LoggedInUser loggedInUser = new LoggedInUser(user.getEmail()+"", user.getFull_name()+"");
        //String first_name = loggedUser.getFull_name().split(" ")[0];
        Session.setLoggedInUser(loggedInUser);
        authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
    }

    public void logout() {
        Session.setLoggedInUser(null);
    }

    /* -------------------------------------------------------------------------- */
    /*                                ITEM                                */
    /* -------------------------------------------------------------------------- */
    public void getAvailableItems(User loggedInUser, MutableLiveData<ItemListQueryResult> itemListQueryResult){
        List<Item> availableItems = new ArrayList<>();
        for (Map.Entry<Long, Item> entryItem: itemsMp.entrySet()) {
            if(!entryItem.getValue().isRent() &&
                    !entryItem.getValue().getItemOwner().getEmail().equals(loggedInUser.getEmail())){
                availableItems.add(entryItem.getValue());
            }
        }
        itemListQueryResult.setValue(new ItemListQueryResult(availableItems));
    }

    public void getItemById(Long id, MutableLiveData<ItemQueryResult> itemQueryResult){
        if(itemsMp.containsKey(id)){
            itemQueryResult.setValue(new ItemQueryResult(itemsMp.get(id)));
        }else{
            itemQueryResult.setValue(new ItemQueryResult(R.string.error_item_not_found));
        }
    }

    public void getItemsByName(String name, MutableLiveData<ItemListQueryResult> itemsQueryResult){
        List<Item> itemList = new ArrayList<>();
        for (Map.Entry<Long,Item> entry: itemsMp.entrySet()) {
            if(entry.getValue().getName().equals(name)){
                itemList.add(entry.getValue());
            }
        }
        itemsQueryResult.setValue(new ItemListQueryResult(itemList));
    }

    public void getItemsByCategory(String nameCategory, MutableLiveData<ItemListQueryResult> itemsQueryResult){
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: itemsMp.entrySet()){
            if(entry.getValue().getCategory().getName().equals(nameCategory)){
                itemList.add(entry.getValue());
            }
        }
        itemsQueryResult.setValue(new ItemListQueryResult(itemList));
    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemsQueryResult){
        if(!itemOwnersMp.containsKey(itemOwner.getEmail())){
            itemsQueryResult.setValue(new ItemListQueryResult(R.string.error_item_owner_not_found));
        }else {
            List<Item> itemList = new ArrayList<>();
            for (Map.Entry<Long, Item> entry : itemsMp.entrySet()) {
                if (entry.getValue().getItemOwner().getEmail().equals(itemOwner.getEmail())) {
                    itemList.add(entry.getValue());
                }
            }
            itemsQueryResult.setValue(new ItemListQueryResult(itemList));
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                                ITEM LENDING                                */
    /* -------------------------------------------------------------------------- */
    public void getItemLendingHistoryByOwner(ItemOwner owner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        if(!itemOwnersMp.containsKey(owner.getEmail())) {
            itemLendingQueryResult.setValue(new ItemLendingListQueryResult(R.string.error_item_owner_not_found));
        }else {
            List<ItemLending> itemLendingList = new ArrayList<>();
            for (Map.Entry<Long, ItemLending> entry : itemLendingMp.entrySet()) {
                if (entry.getValue().getItem().getItemOwner().getEmail().equals(owner.getEmail())) {
                    itemLendingList.add(entry.getValue());
                }
            }
            itemLendingQueryResult.setValue(new ItemLendingListQueryResult(itemLendingList));
        }
    }

    /* -------------------------------------------------------------------------- */

    public void getItemLendingHistoryByRentalUser(User user, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){
        if(!usersMp.containsKey(user.getEmail())){
            itemLendingQueryResult.setValue(new ItemLendingListQueryResult(R.string.error_item_owner_not_found));
        }else {
            List<ItemLending> itemLendingList = new ArrayList<>();
            for (Map.Entry<Long, ItemLending> entry : itemLendingMp.entrySet()) {
                if (entry.getValue().getRenter().getEmail().equals(user.getEmail())) {
                    itemLendingList.add(entry.getValue());
                }
            }
            itemLendingQueryResult.setValue(new ItemLendingListQueryResult(itemLendingList));
        }
    }

    public void getItemLendingById(Long itemLendingId, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult) {
        if(itemLendingMp.containsKey(itemLendingId)){
            ItemLending itemLending = itemLendingMp.get(itemLendingId);
            itemLendingQueryResult.setValue(new ItemLendingQueryResult(itemLending));
        }else{
            itemLendingQueryResult.setValue(new ItemLendingQueryResult(R.string.error_item_lending_not_found));
        }
    }

    public void rentItemByUser(Date dueDate, Long totalPrice, Item item, User user,
                               String delivery_address,
                               MutableLiveData<BasicResult> rentalResult){
        if(item.isRent()){
            rentalResult.setValue(new BasicResult(R.string.error_item_is_already_rented));
        }else{
            item.setRent(true);
            User renter = new User(user.getFull_name()+"", user.getEmail()+"");
            ItemLending itemLending = new ItemLending(new Date(), dueDate,totalPrice,item, renter, delivery_address);
            itemLending.setId(++itemLendingCounter);
            itemLendingMp.put(itemLending.getId(), itemLending);
            if(itemLendingMp.containsKey(itemLending.getId()) && itemLendingMp.get(itemLending.getId())!=null){
                rentalResult.setValue(new BasicResult("Item was successfully rented!"));
                itemsMp.put(item.getId(),item); //update item
            }else{
                rentalResult.setValue(new BasicResult(R.string.error_renting_item));
            }
        }
    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnResult){
        if(itemLendingMp.containsKey(itemLending.getId())){
            ItemLending new_element = itemLendingMp.get(itemLending.getId());
            new_element.setReturnDate(itemLending.getReturnDate());
            Item item = itemLending.getItem();
            item.setRent(false);
            itemsMp.put(item.getId(), item);
            itemLendingMp.put(itemLending.getId(), new_element);
            returnResult.setValue(new BasicResult("Item was successfully returned!"));
        }else{
            returnResult.setValue(new BasicResult(R.string.error_returning_item));
        }
    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){
        item.setId(++itemCounter);
        itemsMp.put(item.getId(), item);
        saveItemResult.setValue(new BasicResult("Item successfully saved"));
    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult){
        if(itemsMp.containsKey(item.getId())){
            itemsMp.put(item.getId(), item);
            updateItemResult.setValue(new BasicResult("Item successfully updated"));
        }else{
            updateItemResult.setValue(new BasicResult(R.string.error_item_not_found));
        }
    }

    // ItemOwner
    public ItemOwner saveItemOwner(ItemOwner itemOwner){
        itemOwnersMp.put(itemOwner.getEmail(), itemOwner);
        return itemOwnersMp.get(itemOwner.getEmail());
    }

    public Item updateItemOwner(ItemOwner itemOwner){
        if(itemOwnersMp.containsKey(itemOwner.getEmail())){
            itemOwnersMp.put(itemOwner.getEmail(), itemOwner);
        }
        return null;
    }

    //Category
    private void saveCategory(Category category, MutableLiveData<BasicResult> saveCategoryResult){
        category.setId(++categoryCounter);
        categoriesMp.put(category.getId(), category);
        saveCategoryResult.setValue(new BasicResult("Category successfully saved"));
    }

    public void getCategories(MutableLiveData<CategoryListQueryResult> categoryListQueryResult){
        List<Category> categoryList = new ArrayList<>();
        for(Map.Entry<Long, Category> entry: categoriesMp.entrySet()){
            categoryList.add(entry.getValue());
        }
        categoryListQueryResult.setValue(new CategoryListQueryResult(categoryList));
    }

    public void getCategoryByName(String nameCategory, MutableLiveData<CategoryQueryResult> categoryQueryResult){
        for (Map.Entry<Long, Category> entry: categoriesMp.entrySet()){
            if(entry.getValue().getName().equals(nameCategory)){
                categoryQueryResult.setValue(new CategoryQueryResult(entry.getValue()));
                return;
            }
        }
        categoryQueryResult.setValue(new CategoryQueryResult(R.string.error_category_not_found));
    }

    public void getItemsByNameOrCategory(String search_text, MutableLiveData<ItemListQueryResult> itemListQueryResult) {
        search_text = search_text.toLowerCase();
        List<Item> itemList = new ArrayList<>();
        for(Map.Entry<Long, Item> entry: itemsMp.entrySet()){
            if(entry.getValue().getName().toLowerCase().equals(search_text) ||
                entry.getValue().getCategory().getName().toLowerCase().equals(search_text)){
                itemList.add(entry.getValue());
            }
        }
        itemListQueryResult.setValue(new ItemListQueryResult(itemList));
    }


    public void deleteItem(Long itemId, MutableLiveData<BasicResult> deleteItemResult) {
        if(itemsMp.containsKey(itemId)){
            boolean hasHistory = false;
            for(Map.Entry<Long, ItemLending> entry: itemLendingMp.entrySet()){
                if(entry.getValue().getItem().getId().equals(itemId)){
                    hasHistory = true; break;
                }
            }
            if(hasHistory){
                deleteItemResult.setValue(new BasicResult(R.string.error_item_has_history));
            }else{
                itemsMp.remove(itemId);
                deleteItemResult.setValue(new BasicResult("Item successfully deleted"));
            }
        }else{
            deleteItemResult.setValue(new BasicResult(R.string.error_item_not_found));
        }
    }


}
