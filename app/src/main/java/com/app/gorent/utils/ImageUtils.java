package com.app.gorent.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.app.gorent.R;

import java.io.File;

public class ImageUtils {

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


}
