package com.app.gorent.data.storage;

//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class DataSourceFirebase {

    private FirebaseFirestore fireStoreDB;
    //private FirebaseAuth firebaseAuth;

    private static DataSourceFirebase instance = null;

    private DataSourceFirebase(){
        fireStoreDB = FirebaseFirestore.getInstance();
        //firebaseAuth = FirebaseAuth.getInstance();
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

}