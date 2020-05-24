package com.app.gorent.data.storage;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.User;
import com.app.gorent.ui.activities.auth.LoggedInUserView;
import com.app.gorent.utils.MyUtils;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.result.CategoryQueryResult;
import com.app.gorent.utils.result.ItemLendingListQueryResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;
import com.app.gorent.utils.result.ItemListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFirebase {

    private FirebaseFirestore fireStoreDB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser loggedUser;
    private StorageReference mStorageRef;

    private static DataSourceFirebase instance = null;
    private Context context;

    private DataSourceFirebase(Context context){
        this.context = context;
        fireStoreDB = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        //Offline configuration
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        this.fireStoreDB.setFirestoreSettings(settings);
    }

    public static DataSourceFirebase getInstance(Context context){
        if(instance == null){
            instance = new DataSourceFirebase(context);
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
        fireStoreDB.collection("Items").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Item> items = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                        Item element = snapshot.toObject(Item.class);
                        element.setId(snapshot.getId());
                        items.add(element);
                    }
                }
                itemListQueryResult.setValue(new ItemListQueryResult(items));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                itemListQueryResult.setValue(new ItemListQueryResult(R.string.error_retrieving_items));
            }
        });
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
        Map<String, Object> itemMap = itemToMap(item);
        MutableLiveData<BasicResult> uploadImageResult = new MutableLiveData<>();
        uploadImage(item.getImage_path(), uploadImageResult);
        fireStoreDB.collection("/Items").add(itemMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                saveItemResult.setValue(new BasicResult("Item successfully saved!"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                saveItemResult.setValue(new BasicResult(R.string.error_saving_item));
            }
        });
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

    private void uploadImage(String image_path, MutableLiveData<BasicResult> imageUploadResult){
        Uri photoUri = MyUtils.loadImage(context, image_path);
        if(photoUri!=null) {
            StorageReference fileReference =  mStorageRef.child(image_path);
            fileReference.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageUploadResult.setValue(new BasicResult("Image uploaded successfully!"));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    imageUploadResult.setValue(new BasicResult(R.string.error_uploading_image));
                }
            });
        }else{
            imageUploadResult.setValue(new BasicResult(R.string.error_image_not_found));
        }
    }

    private Map<String,Object> itemToMap(Item item){
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("description", item.getDescription());
        itemMap.put("price", item.getPrice());
        itemMap.put("feeType", item.getFeeType());
        itemMap.put("isRent", item.isRent());
        itemMap.put("image_path", item.getImage_path());
        Map<String, Object> categoryMap = categoryToMap(item.getCategory());
        itemMap.put("category", categoryMap);
        Map<String, Object> itemOwnerMap = itemOwnerToMap(item.getItemOwner());
        itemMap.put("itemOwner", itemOwnerMap);
        return itemMap;
    }

    private Map<String, Object> categoryToMap(Category category){
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id",category.getId());
        categoryMap.put("name", category.getName());
        categoryMap.put("description", category.getDescription());
        return categoryMap;
    }

    private Map<String, Object> itemOwnerToMap(ItemOwner itemOwner){
        Map<String, Object> itemOwnerMap = new HashMap<>();
        itemOwnerMap.put("email", itemOwner.getEmail());
        itemOwnerMap.put("full_name", itemOwner.getFull_name());
        return itemOwnerMap;
    }

    public StorageReference getStorageReference() {
        return mStorageRef;
    }

}