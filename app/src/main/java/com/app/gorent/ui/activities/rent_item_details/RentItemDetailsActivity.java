package com.app.gorent.ui.activities.rent_item_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.activities.rental_form.RentalFormActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemQueryResult;

public class RentItemDetailsActivity extends AppCompatActivity {

    RentItemDetailsViewModel rentItemDetailsViewModel;
    TextView tv_item_information;
    ImageView iv_item_picture;
    Button btn_rent;
    ProgressBar pg_loading;
    Long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_item_details);
        rentItemDetailsViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplicationContext())).get(RentItemDetailsViewModel.class);

        connectViewWithModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);

        itemId = this.getIntent().getExtras().getLong("itemId");
        rentItemDetailsViewModel.retrieveItemById(itemId);
        configureItemObserver();
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void connectViewWithModel(){
        iv_item_picture = findViewById(R.id.rent_item_details_iv_item_picture);
        tv_item_information = findViewById(R.id.rent_item_details_tv_item_information);
        btn_rent = findViewById(R.id.rent_item_details_btn_rent);
        pg_loading = findViewById(R.id.rent_item_details_pg_loading);
    }

    private void configureItemObserver() {
        rentItemDetailsViewModel.getItemQueryResult().observe(this, new Observer<ItemQueryResult>() {
            @Override
            public void onChanged(ItemQueryResult itemQueryResult) {
                if(itemQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemQueryResult.getError()!=null){
                    showErrorMessage(getString(itemQueryResult.getError()));
                }
                if(itemQueryResult.getItem()!=null){
                    Item item = itemQueryResult.getItem();
                    tv_item_information.setText(item.toString());
                    if(item.getCategory().getName().toLowerCase().equals("houses")){
                        iv_item_picture.setImageDrawable(getResources().getDrawable(R.drawable.houses));
                    }else if(item.getCategory().getName().toLowerCase().equals("cars")){
                        iv_item_picture.setImageDrawable(getResources().getDrawable(R.drawable.cars));
                    }else if(item.getCategory().getName().toLowerCase().equals("pianos")){
                        iv_item_picture.setImageDrawable(getResources().getDrawable(R.drawable.pianos));
                    }else if(item.getCategory().getName().toLowerCase().equals("laptops")){
                        iv_item_picture.setImageDrawable(getResources().getDrawable(R.drawable.laptops));
                    }
                    configureBtnRent();
                }

            }
        });
    }

    private void configureBtnRent(){
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentItemDetailsActivity.this, RentalFormActivity.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void showErrorMessage(final String errorMsg){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(RentItemDetailsActivity.this, errorMsg, Toast.LENGTH_LONG);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setGravity(Gravity.CENTER);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        }, 2500);
    }

    private void showMessage(final String msg){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(RentItemDetailsActivity.this, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0,0);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.parseColor("#03DAC5"), PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                toast.show();
            }
        });
    }


}
