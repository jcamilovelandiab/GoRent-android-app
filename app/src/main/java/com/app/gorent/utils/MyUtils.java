package com.app.gorent.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.FileProvider;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyUtils {

    public static Uri loadImage(Context context, String path_image){
        try{
            File filePhoto=new File(path_image);
            String authority = context.getString(R.string.authority_package);
            return FileProvider.getUriForFile(context,authority,filePhoto);
        }catch (Exception ex){
            //Toast.makeText(this.context, "An error occurred while attempting to load the image", Toast.LENGTH_SHORT).show();
            Log.d("Loading image","Error occurred while attempting to load the image "+path_image+"\nMessage: "+ex.getMessage()+"\nCause: "+ex.getCause());
            return null;
        }
    }

    public static File createImageFile(Context context, String imageFileName) throws IOException {
        // Create an image file name
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        assert storageDir != null;
        String strStorageDir = storageDir.getAbsolutePath();
        File image = new File(strStorageDir+"/"+imageFileName);
        return image;
    }

    public static boolean checkFileExists(Context context, String imageFileName) throws IOException {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String pathImage = storageDir.getAbsolutePath()+"/"+imageFileName;
        File imageFile = new File(pathImage);
        boolean exists = imageFile.exists();
        boolean isDirectory = imageFile.isDirectory();
        return  exists && !isDirectory;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
