package com.app.gorent.ui.new_item;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;

public class ItemFormActivity extends AppCompatActivity {

    ItemFormViewModel itemFormViewModel;
    ImageView iv_item_picture;
    EditText et_item_name, et_item_description, et_item_price;
    Spinner sp_category, sp_fee_type;
    String category="", feeType="", picture_path="", current_picture_path="";
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_new_item_form);
        itemFormViewModel = ViewModelProviders.of(this,new ViewModelFactory()).get(ItemFormViewModel.class);

        connectModelWithView();
        configureSpCategory();
        configureSpFeeType();
        configureTextWatchers();
        configureItemFormStateObserver();
        configureBtnSave();
    }

    private void configureBtnSave() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void configureItemFormStateObserver() {
        itemFormViewModel.getItemFormState().observe(this, new Observer<ItemFormState>() {
            @Override
            public void onChanged(ItemFormState itemFormState) {
                if(itemFormState==null) return;
                if(itemFormState.getNameError()!=null){
                    et_item_name.setError(getString(itemFormState.getNameError()));
                }
                if(itemFormState.getDescriptionError()!=null){
                    et_item_description.setError(getString(itemFormState.getDescriptionError()));
                }
                if(itemFormState.getPriceError()!=null){
                    et_item_price.setError(getString(itemFormState.getPriceError()));
                }
                if(itemFormState.isDataValid()){
                    if(picture_path.isEmpty()){
                        showErrorMessage("Click on the camera and add a photo of the item to complete the registration.");
                    }else{
                        btn_save.setEnabled(itemFormState.isDataValid());
                    }
                }

            }
        });
    }

    private void connectModelWithView() {
        iv_item_picture = findViewById(R.id.create_item_form_iv_picture);
        et_item_name = findViewById(R.id.create_item_form_et_name);
        et_item_description = findViewById(R.id.create_item_form_et_description);
        et_item_price = findViewById(R.id.create_item_form_et_price);
        sp_category = findViewById(R.id.create_item_form_sp_category);
        sp_fee_type = findViewById(R.id.create_item_form_sp_fee_type);
        btn_save = findViewById(R.id.create_item_form_btn_save);
    }

    private void configureSpCategory() {
        ArrayList<String> nameCategories = new ArrayList<>();
        nameCategories.add("Select a category");
        nameCategories.addAll(itemFormViewModel.getCategories());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameCategories);
        sp_category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    category = parent.getItemAtPosition(position).toString();
                    showMessage("Selected category: "+category);
                    itemFormViewModel.dataChanged(et_item_name.getText().toString()+"",
                            et_item_description.getText().toString()+"",
                            et_item_price.getText().toString()+"",
                            feeType, category);
                }

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
                if(position!=0){
                    feeType = parent.getItemAtPosition(position).toString();
                    showMessage("Selected fee type: "+feeType);
                    itemFormViewModel.dataChanged(et_item_name.getText().toString()+"",
                            et_item_description.getText().toString()+"",
                            et_item_price.getText().toString()+"",
                            feeType, category);
                }
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

    private void configureTextWatchers(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                itemFormViewModel.dataChanged(et_item_name.getText().toString()+"",
                                            et_item_description.getText().toString()+"",
                                                        et_item_price.getText().toString()+"",
                                                    feeType, category);
            }
        };
        et_item_name.addTextChangedListener(textWatcher);
        et_item_description.addTextChangedListener(textWatcher);
        et_item_price.addTextChangedListener(textWatcher);
    }

    private void showErrorMessage(final String errorMsg){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ItemFormActivity.this, errorMsg, Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(ItemFormActivity.this, msg, Toast.LENGTH_SHORT);
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
