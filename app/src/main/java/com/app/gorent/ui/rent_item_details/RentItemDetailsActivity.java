package com.app.gorent.ui.rent_item_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.rental_form.RentalFormActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

public class RentItemDetailsActivity extends AppCompatActivity {

    RentItemDetailsViewModel rentItemDetailsViewModel;
    TextView tv_item_information;
    ImageView iv_item_picture;
    Button btn_rent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        rentItemDetailsViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(RentItemDetailsViewModel.class);

        connectViewWithModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);

        bundle = getIntent().getExtras();
        retrieveItemInformation(bundle.getLong("itemId"));
        configureBtnRent();
    }

    private void connectViewWithModel(){
        iv_item_picture = findViewById(R.id.item_info_iv_item_picture);
        tv_item_information = findViewById(R.id.item_info_tv_item_information);
        btn_rent = findViewById(R.id.item_info_btn_rent);
    }

    private void configureBtnRent(){
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentItemDetailsActivity.this, RentalFormActivity.class);
                intent.putExtra("itemId", rentItemDetailsViewModel.getItem().getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void retrieveItemInformation(Long itemId) {
        Item item = rentItemDetailsViewModel.retrieveItemById(itemId);
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
    }


}
