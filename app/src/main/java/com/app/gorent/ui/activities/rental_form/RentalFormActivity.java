package com.app.gorent.ui.activities.rental_form;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.BasicResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class RentalFormActivity extends AppCompatActivity {

    TextView tv_total_price;
    RentalFormViewModel rentalFormViewModel;
    Button btn_accept, btn_cancel, btn_delivery_date;
    LinearLayout layout_rent_form;
    EditText et_current_date, et_delivery_date;
    Date currentDate, dueDate;
    Long totalPrice;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_form);
        rentalFormViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(RentalFormViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);

        bundle = getIntent().getExtras();
        rentalFormViewModel.retrieveItemById(bundle.getLong("itemId"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_rent_form);

        connectModelWithView();
        configureBtnAccept();
        configureBtnCancel();
        configureCurrentDate();
        configureDueDate();
        configureTextWatchers();
        configureRentalResultObserver();

        configureRentalFormStateObserver();
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void connectModelWithView(){
        btn_accept = findViewById(R.id.rental_form_btn_accept);
        btn_cancel = findViewById(R.id.rental_form_btn_cancel);
        btn_delivery_date = findViewById(R.id.rental_form_btn_delivery_date);
        et_delivery_date = findViewById(R.id.rental_form_et_delivery_date);
        et_current_date = findViewById(R.id.rental_form_et_current_date);
        tv_total_price = findViewById(R.id.rental_form_tv_total_price);
        layout_rent_form = findViewById(R.id.rental_form_layout_rent_form);
    }

    private void configureBtnAccept(){
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentalFormViewModel.rentItem(dueDate, totalPrice);
            }
        });
    }

    private void configureBtnCancel(){
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void cleanFields(){
        et_delivery_date.setText("");
        tv_total_price.setText("Total price: $0");
    }

    private void configureRentalResultObserver() {
        rentalFormViewModel.getRentalResult().observe(this, new Observer<BasicResult>() {
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
        rentalFormViewModel.getRentalFormState().observe(this, new Observer<RentalFormState>() {
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
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        totalPrice = rentalFormViewModel.getItem().getPrice()*Long.valueOf(days);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(RentalFormActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                rentalFormViewModel.rentalFormDataChanged(currentDate, dueDate, totalPrice);
            }
        };
        et_delivery_date.addTextChangedListener(afterTextChangedListener);
    }

    private void showRentalFailed(@StringRes final Integer errorString) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(RentalFormActivity.this, errorString, Toast.LENGTH_SHORT);
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
                Toast toast = Toast.makeText(RentalFormActivity.this, successString, Toast.LENGTH_SHORT);
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
