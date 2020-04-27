package com.app.gorent.ui.item_details;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.rental_form.RentalFormActivity;
import com.app.gorent.ui.rental_form.RentalFormState;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.BasicResult;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ItemDetailsActivity extends AppCompatActivity {

    ItemDetailsViewModel itemDetailsViewModel;
    TextView tv_item_information;
    ImageView iv_item_picture;
    Button btn_rent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        itemDetailsViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ItemDetailsViewModel.class);

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
                Intent intent = new Intent(ItemDetailsActivity.this, RentalFormActivity.class);
                intent.putExtra("itemId",itemDetailsViewModel.getItem().getId());
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
        Item item = itemDetailsViewModel.retrieveItemById(itemId);
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
