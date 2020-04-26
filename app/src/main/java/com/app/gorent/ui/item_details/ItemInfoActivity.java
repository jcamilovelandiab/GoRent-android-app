package com.app.gorent.ui.item_details;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.BasicResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ItemInfoActivity extends AppCompatActivity {

    TextView tv_item_information, tv_total_price;
    ImageView iv_item_picture;
    ItemInfoViewModel itemInfoViewModel;
    Button btn_rent, btn_accept, btn_cancel, btn_delivery_date;
    LinearLayout layout_rent_form;
    EditText et_current_date, et_delivery_date;
    Date currentDate, dueDate;
    Long totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        itemInfoViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ItemInfoViewModel.class);
        connectViewWithModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);
        Long itemId = getIntent().getExtras().getLong("itemId");
        retrieveItemInformation(itemId);
        configureBtnRent();
        configureBtnCancel();
        configureBtnAccept();
        configureCurrentDate();
        configureDueDate();
        configureTextWatchers();
        configureRentalResultObserver();
        configureRentalFormStateObserver();
    }

    private void connectViewWithModel(){
        iv_item_picture = findViewById(R.id.item_info_iv_item_picture);
        tv_item_information = findViewById(R.id.item_info_tv_item_information);
        btn_rent = findViewById(R.id.item_info_btn_rent);
        btn_accept = findViewById(R.id.item_info_btn_accept);
        btn_cancel = findViewById(R.id.item_info_btn_cancel);
        btn_delivery_date = findViewById(R.id.item_info_btn_delivery_date);
        et_delivery_date = findViewById(R.id.item_info_et_delivery_date);
        et_current_date = findViewById(R.id.item_info_et_current_date);
        tv_total_price = findViewById(R.id.item_info_tv_total_price);
        layout_rent_form = findViewById(R.id.item_info_layout_rent_form);
    }

    private void configureBtnAccept(){
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemInfoViewModel.rentItem(dueDate, totalPrice);
            }
        });
    }

    private void cleanFields(){
        et_delivery_date.setText("");
        tv_total_price.setText("Total price: $0");
    }

    private void configureRentalResultObserver() {
        itemInfoViewModel.getRentalResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    showRentalFailed(basicResult.getError());
                }
                if(basicResult.getSuccess()!=null){
                    showRentalSuccess(basicResult.getSuccess());
                    cleanFields();
                }
            }
        });
    }

    private void configureRentalFormStateObserver(){
        itemInfoViewModel.getRentalFormState().observe(this, new Observer<RentalFormState>() {
            @Override
            public void onChanged(RentalFormState rentalFormState) {
                if(rentalFormState==null) return;
                btn_accept.setEnabled(rentalFormState.isDataValid());
                if(rentalFormState.getDueDateError()!=null){
                    et_delivery_date.setError(getString(rentalFormState.getDueDateError()));
                }
                if(rentalFormState.isDataValid()){
                    btn_accept.setEnabled(true);
                    configureTotalPrice();
                }
            }
        });
    }

    private void configureTotalPrice(){
        long diff = currentDate.getTime() - dueDate.getTime();
        long days =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        totalPrice = itemInfoViewModel.getItem().getPrice()*Long.valueOf(days);
        tv_total_price.setText("Total price: $"+totalPrice);
    }

    private void configureCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH), month=c.get(Calendar.MONTH), year = c.get(Calendar.YEAR);
        currentDate = new GregorianCalendar(year, month-1, day).getTime();
        et_current_date.setText(day+"/"+(month+1)+"/"+year);
    }

    private void configureDueDate() {
        btn_delivery_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH), month=c.get(Calendar.MONTH), year = c.get(Calendar.YEAR);
                currentDate = new GregorianCalendar(year, month, day).getTime();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ItemInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear , int dayOfMonth) {
                        int deliveryDay = dayOfMonth, deliveryMonth=monthOfYear+1, deliveryYear = year;
                        dueDate = new GregorianCalendar(deliveryYear, deliveryMonth - 1, deliveryDay).getTime();
                        et_delivery_date.setText(deliveryDay+"/"+deliveryMonth+"/"+deliveryYear);
                    }
                },year,month,day);
                et_current_date.setText(day+"/"+(month+1)+"/"+year);
            }
        });
    }

    private void configureTextWatchers(){
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                itemInfoViewModel.rentalFormDataChanged(currentDate, dueDate, totalPrice);
            }
        };
        et_delivery_date.addTextChangedListener(afterTextChangedListener);
    }

    private void configureBtnRent(){
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_rent_form.setVisibility(View.VISIBLE);
                btn_rent.setVisibility(View.GONE);
            }
        });
    }

    private void configureBtnCancel(){
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_rent_form.setVisibility(View.GONE);
                btn_rent.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void retrieveItemInformation(Long itemId) {
        Item item = itemInfoViewModel.retrieveItemById(itemId);
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

    private void showRentalFailed(@StringRes final Integer errorString) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ItemInfoActivity.this, errorString, Toast.LENGTH_SHORT);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        });
    }

    private void showRentalSuccess(final String successString) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ItemInfoActivity.this, successString, Toast.LENGTH_SHORT);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        });
    }
}
