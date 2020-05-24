package com.app.gorent.ui.activities.return_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.ItemLendingQueryResult;

public class ReturnItemActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button btn_return;
    ReturnItemViewModel returnItemViewModel;
    ProgressBar pg_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnItemViewModel = ViewModelProviders.of(this,
                new ViewModelFactory(getApplicationContext())).get(ReturnItemViewModel.class);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_return_item);
        setContentView(R.layout.activity_return_item);

        connectModelWithView();
        prepareItemLendingObservable();
        prepareReturnItemResult();

        //ItemLending itemLending = (ItemLending) this.getIntent().getSerializableExtra("itemLending");
        String itemLendingId = this.getIntent().getExtras().getString("itemLendingId");
        //returnItemViewModel.setItemLending(itemLending);
        returnItemViewModel.getItemLending(itemLendingId);
    }

    private void connectModelWithView(){
        ratingBar = findViewById(R.id.return_item_rb_rating_bar);
        btn_return = findViewById(R.id.return_item_btn_return);
        pg_loading = findViewById(R.id.return_item_pg_loading);
    }

    @Override
    public boolean onSupportNavigateUp(){
        closeActivity(0);
        return true;
    }

    private void prepareItemLendingObservable(){
        returnItemViewModel.getItemLendingQuery().observe(this, new Observer<ItemLendingQueryResult>() {
            @Override
            public void onChanged(ItemLendingQueryResult itemLendingQueryResult) {
                if(itemLendingQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemLendingQueryResult.getError()!=null){
                    showErrorMessage(getString(itemLendingQueryResult.getError())+". Try later!");
                }
                if(itemLendingQueryResult.getItemLending()!=null){
                    configureBtnReturn();
                    btn_return.setEnabled(true);
                }
            }
        });
    }

    private void prepareReturnItemResult(){
        returnItemViewModel.getReturnItemResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult returnItemResult) {
                if(returnItemResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(returnItemResult.getError()!=null){
                    showErrorMessage(getString(returnItemResult.getError()));
                }
                if(returnItemResult.getSuccess()!=null){
                    showMessage(returnItemResult.getSuccess());
                    closeActivity(1500);
                }
            }
        });
    }

    private void closeActivity(long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setResult(Activity.RESULT_OK);
                finish();
            }
        }, delayMillis);
    }

    private void showErrorMessage(final String errorMsg){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ReturnItemActivity.this, errorMsg, Toast.LENGTH_LONG);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setGravity(Gravity.CENTER);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        }, 0);
    }

    private void showMessage(final String msg){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ReturnItemActivity.this, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0,0);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                toast.show();
            }
        });
    }

    private void configureBtnReturn() {
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg_loading.setVisibility(View.VISIBLE);
                returnItemViewModel.returnItem();
            }
        });
    }

}
