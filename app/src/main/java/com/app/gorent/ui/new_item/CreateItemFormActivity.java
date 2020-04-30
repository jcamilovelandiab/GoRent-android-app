package com.app.gorent.ui.new_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateItemFormActivity extends AppCompatActivity {

    CreateItemFormViewModel createItemFormViewModel;
    ImageView iv_item_picture;
    TextView tv_item_name, tv_item_description, tv_item_price;
    Spinner sp_category, sp_fee_type;
    String category, feeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_new_item_form);
        createItemFormViewModel = ViewModelProviders.of(this,new ViewModelFactory()).get(CreateItemFormViewModel.class);
        connectModelWithView();
        configureSpCategory();
        configureSpFeeType();
    }

    private void connectModelWithView() {
        iv_item_picture = findViewById(R.id.create_item_form_iv_picture);
        tv_item_name = findViewById(R.id.create_item_form_name);
        tv_item_description = findViewById(R.id.create_item_form_description);
        tv_item_price = findViewById(R.id.create_item_form_price);
        sp_category = findViewById(R.id.create_item_form_sp_category);
        sp_fee_type = findViewById(R.id.create_item_form_sp_fee_type);
    }

    private void configureSpCategory() {
        ArrayList<String> nameCategories = new ArrayList<>();
        nameCategories.add("Select a category");
        nameCategories.addAll(createItemFormViewModel.getCategories());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameCategories);
        sp_category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) return;
                category = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateItemFormActivity.this, "Selected category: "+category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void configureSpFeeType(){
        sp_fee_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) return;
                feeType = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateItemFormActivity.this, "Selected fee type: "+feeType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

}
