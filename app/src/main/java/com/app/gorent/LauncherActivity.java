package com.app.gorent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.gorent.data.storage.Storage;
import com.app.gorent.ui.auth.login.LoginActivity;
import com.app.gorent.ui.main.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Storage storage = new Storage( this );
        if ( storage.containsToken() ){
            startActivity( new Intent( this, MainActivity.class ) );
        }else{
            startActivity( new Intent( this, LoginActivity.class ) );
        }
        finish();
    }
}
