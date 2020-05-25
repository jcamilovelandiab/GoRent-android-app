package com.app.gorent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.app.gorent.data.storage.Session;
import com.app.gorent.ui.activities.auth.login.LoginActivity;
import com.app.gorent.ui.activities.main.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        askPermissions();
    }

    private void initApp(){

        final Handler handler = new Handler();
        Session session = new Session( this );
        if(session.containsToken()){
            handler.postDelayed(() -> {
                Intent intent = new Intent(LauncherActivity.this, AuthorizationActivity.class);
                intent.putExtra("email", session.getLoggedInUser().getEmail());
                startActivity(intent);
                finish();
            }, 2000);
        }else{
            handler.postDelayed(() -> {
                startActivity( new Intent( LauncherActivity.this, LoginActivity.class ) );
                finish();
            }, 3000);
        }
    }

    private void askPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }else{
            initApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initApp();
                Toast.makeText(this, "Write external storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The application needs write permissions.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
