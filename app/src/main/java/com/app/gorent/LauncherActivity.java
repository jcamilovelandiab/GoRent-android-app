package com.app.gorent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.gorent.data.storage.Session;
import com.app.gorent.ui.activities.auth.login.LoginActivity;
import com.app.gorent.ui.activities.main.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Session session = new Session( this );
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (session.containsToken()){
                startActivity( new Intent( LauncherActivity.this, MainActivity.class ) );
            }else{
                startActivity( new Intent( LauncherActivity.this, LoginActivity.class ) );
            }
            finish();
        }, 5000);
    }
}
