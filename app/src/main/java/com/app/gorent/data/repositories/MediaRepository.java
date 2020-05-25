package com.app.gorent.data.repositories;

import android.content.Context;

import com.google.firebase.storage.StorageReference;

public class MediaRepository extends Repository{

    private static volatile MediaRepository instance;

    private MediaRepository(Context context) {
        super(context);
    }

    public static MediaRepository getInstance(Context context){
        if(instance==null){
            instance = new MediaRepository(context);
        }
        return instance;
    }

    public StorageReference getStorageReference(){
        return getDataSourceFirebase().getStorageReference();
    }

}
