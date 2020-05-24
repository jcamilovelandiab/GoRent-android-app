package com.app.gorent.data.storage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.ui.activities.auth.LoggedInUserView;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.result.CategoryQueryResult;
import com.app.gorent.utils.result.ItemLendingListQueryResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;
import com.app.gorent.utils.result.ItemListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class DataSourceFirebase {

    private FirebaseFirestore fireStoreDB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser loggedUser;
    private StorageReference mStorageRef;

    private static DataSourceFirebase instance = null;

    private DataSourceFirebase(){
        fireStoreDB = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        //Offline configuration
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        this.fireStoreDB.setFirestoreSettings(settings);
    }

    public static DataSourceFirebase getInstance(){
        if(instance == null){
            instance = new DataSourceFirebase();
        }
        return instance;
    }

    /* -------------------------------------------------------------------------- */
    /*                                USERS                                */
    /* -------------------------------------------------------------------------- */
    public void login(String email, String password,
                      MutableLiveData<com.app.gorent.utils.result.AuthResult> result) {
        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        loggedUser = firebaseAuth.getCurrentUser();
                        assert loggedUser != null;

                        LoggedInUser loggedInUser = new LoggedInUser(
                                loggedUser.getEmail()+"", loggedUser.getDisplayName()+"");
                        Session.setLoggedInUser(loggedInUser);
                        result.setValue(new com.app.gorent.utils.result.AuthResult(
                                new LoggedInUserView(loggedUser.getDisplayName())));
                    }else{
                        result.setValue(new com.app.gorent.utils.result.AuthResult(
                                R.string.login_failed
                        ));
                    }
                });
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       loggedUser = firebaseAuth.getCurrentUser();
                       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                               .setDisplayName(user.getFull_name()).build();
                       loggedUser.updateProfile(profileUpdates);
                       LoggedInUser loggedInUser = new LoggedInUser(
                               user.getEmail()+"", user.getFull_name()+"");
                       Session.setLoggedInUser(loggedInUser);
                       authResult.setValue(new AuthResult(
                               new LoggedInUserView(user.getFull_name())));
                   }else{
                       authResult.setValue(new AuthResult(R.string.sign_up_failed));
                   }
                });
    }

    public void logout() {
        Session.setLoggedInUser(null);
        firebaseAuth.signOut();
    }

    /* -------------------------------------------------------------------------- */
    /*                                ITEM                                */
    /* -------------------------------------------------------------------------- */
    public void getAvailableItems(User loggedInUser, MutableLiveData<ItemListQueryResult> itemListQueryResult){

    }

    public void getItemById(Long id, MutableLiveData<ItemQueryResult> itemQueryResult){

    }

    public void getItemsByName(String name, MutableLiveData<ItemListQueryResult> itemsQueryResult){

    }

    public void getItemsByCategory(String nameCategory, MutableLiveData<ItemListQueryResult> itemsQueryResult){

    }

    public void getItemsByOwner(ItemOwner itemOwner, MutableLiveData<ItemListQueryResult> itemsQueryResult){

    }

    /* -------------------------------------------------------------------------- */
    /*                                ITEM LENDING                                */
    /* -------------------------------------------------------------------------- */
    public void getItemLendingHistoryByOwner(ItemOwner owner, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){

    }

    /* -------------------------------------------------------------------------- */

    public void getItemLendingHistoryByRentalUser(User user, MutableLiveData<ItemLendingListQueryResult> itemLendingQueryResult){

    }

    public void getItemLendingById(Long itemLendingId, MutableLiveData<ItemLendingQueryResult> itemLendingQueryResult) {

    }

    public void rentItemByUser(Date dueDate, Long totalPrice, Item item, User user,
                               String delivery_address,
                               MutableLiveData<BasicResult> rentalResult){

    }

    public void returnItem(ItemLending itemLending, MutableLiveData<BasicResult> returnResult){

    }

    public void saveItem(Item item, MutableLiveData<BasicResult> saveItemResult){

    }

    public void updateItem(Item item, MutableLiveData<BasicResult> updateItemResult){

    }

    //Category
    private void saveCategory(Category category, MutableLiveData<BasicResult> saveCategoryResult){

    }

    public void getCategories(MutableLiveData<CategoryListQueryResult> categoryListQueryResult){

    }

    public void getCategoryByName(String nameCategory, MutableLiveData<CategoryQueryResult> categoryQueryResult){

    }

    public void getItemsByNameOrCategory(String search_text, MutableLiveData<ItemListQueryResult> itemListQueryResult) {

    }


    public void deleteItem(Long itemId, MutableLiveData<BasicResult> deleteItemResult) {

    }


}