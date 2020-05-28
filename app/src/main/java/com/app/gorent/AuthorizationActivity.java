package com.app.gorent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.data.model.Role;
import com.app.gorent.ui.activities.main.MainActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.UserQueryResult;

public class AuthorizationActivity extends AppCompatActivity {

    AuthorizationViewModel authorizationViewModel;
    ProgressBar pg_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorizationViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplicationContext()))
                .get(AuthorizationViewModel.class);
        setContentView(R.layout.activity_authorization);
        String email = getIntent().getExtras().getString("email");
        authorizationViewModel.getUser(email);
        pg_loading = findViewById(R.id.authorization_pg_loading);
        pg_loading.setVisibility(View.VISIBLE);
        prepareUserObserver();
    }

    private void prepareUserObserver(){
        authorizationViewModel.getUserQueryResult().observe(this, userQueryResult -> {
            if(userQueryResult==null) return;
            pg_loading.setVisibility(View.GONE);
            if(userQueryResult.getError()!=null){
                updateUiUser();
            }
            if(userQueryResult.getUser()!=null){
                if(userQueryResult.getUser().getRole().equals(Role.admin)){
                    updateUiAdmin();
                }else{
                    updateUiUser();
                }
            }
        });
    }

    private void updateUiAdmin(){
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), "Logging in admin...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
        //Complete and destroy login activity once successful
        finishA();
    }

    private void updateUiUser(){
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
        //Complete and destroy login activity once successful
        finishA();
    }

    private void finishA(){
        new Handler().postDelayed(() -> {
            setResult(Activity.RESULT_OK);
            finish();
        }, 2000);
    }

}
