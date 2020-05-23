package com.app.gorent.data.storage;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import javax.sql.DataSource;

public class DataSourceFirestore {

    FirebaseFirestore db;
    private static DataSourceFirestore instance = null;

    private DataSourceFirestore(){
        db = FirebaseFirestore.getInstance();
        //Offline configuration
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        this.db.setFirestoreSettings(settings);
    }

    public static DataSourceFirestore getInstance(){
        if(instance == null){
            instance = new DataSourceFirestore();
        }
        return instance;
    }

}
