package com.app.gorent.ui.activities.rental_form;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.ItemQueryResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static com.app.gorent.utils.Constantes.MAPVIEW_BUNDLE_KEY;

public class RentalFormActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    TextView tv_total_price;
    RentalFormViewModel rentalFormViewModel;
    Button btn_accept, btn_cancel, btn_delivery_date;
    LinearLayout layout_rent_form;
    EditText et_current_date, et_delivery_date, et_address;
    TextInputLayout til_delivery_date;
    Date currentDate, dueDate;
    Long totalPrice;
    Bundle bundle;
    ProgressBar pg_loading;

    MapView mv_map;
    GoogleMap myMap;

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
        initGoogleMap(savedInstanceState);
        configureItemQueryObserver();
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void connectModelWithView(){
        btn_accept = findViewById(R.id.rental_form_btn_rent);
        btn_cancel = findViewById(R.id.rental_form_btn_cancel);
        btn_delivery_date = findViewById(R.id.rental_form_btn_delivery_date);
        et_delivery_date = findViewById(R.id.rental_form_et_delivery_date);
        til_delivery_date = findViewById(R.id.rental_form_til_delivery_date);
        et_current_date = findViewById(R.id.rental_form_et_current_date);
        tv_total_price = findViewById(R.id.rental_form_tv_total_price);
        layout_rent_form = findViewById(R.id.rental_form_layout_rent_form);
        et_address = findViewById(R.id.rental_form_et_address);
        pg_loading = findViewById(R.id.rental_form_pg_loading);
        mv_map = findViewById(R.id.rental_form_mv_map);
    }

    private void configureBtnAccept(){
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg_loading.setVisibility(View.VISIBLE);
                rentalFormViewModel.rentItem(dueDate, totalPrice, et_address.getText().toString()+"");
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

    private void configureItemQueryObserver(){
        rentalFormViewModel.getItemQueryResult().observe(this, new Observer<ItemQueryResult>() {
            @Override
            public void onChanged(ItemQueryResult itemQueryResult) {
                if(itemQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemQueryResult.getError()!=null){
                    showErrorMsg(itemQueryResult.getError());
                }
                if(itemQueryResult.getItem()!=null){
                    configureBtnAccept();
                    configureBtnCancel();
                    configureCurrentDate();
                    configureDueDate();
                    configureTextWatchers();
                    configureRentalResultObserver();
                    configureRentalFormStateObserver();
                    configureAddressEditText();
                }
            }
        });
    }

    private void configureRentalResultObserver() {
        rentalFormViewModel.getRentalResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(basicResult.getError()!=null){
                    showErrorMsg(basicResult.getError());
                }
                if(basicResult.getSuccess()!=null){
                    showSuccessMsg(basicResult.getSuccess());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 3*1000);
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
                til_delivery_date.setErrorEnabled(rentalFormState.getDueDateError()!=null);
                if(rentalFormState.getDueDateError()!=null){
                    til_delivery_date.setError(getString(rentalFormState.getDueDateError()));
                }
                if(rentalFormState.getAddressError()!=null){
                    et_address.setError(getString(rentalFormState.getAddressError()));
                }
                btn_accept.setEnabled(rentalFormState.isDataValid());
                if(rentalFormState.isDataValid()){
                    configureTotalPrice();
                }
            }
        });
    }

    private void configureTotalPrice(){
        long diff = dueDate.getTime() - currentDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        totalPrice = rentalFormViewModel.getItemQueryResult().getValue().getItem().getPrice()*Long.valueOf(days);
        tv_total_price.setText("Total price: $"+totalPrice);
    }

    private void configureCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH), month=c.get(Calendar.MONTH), year = c.get(Calendar.YEAR);
        currentDate = new GregorianCalendar(year, month, day).getTime();
        et_current_date.setText(day+"/"+(month+1)+"/"+year);
    }

    private void configureDueDate() {
        btn_delivery_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int day, month, year;
                if(dueDate==null){
                    final Calendar c = Calendar.getInstance();
                    day=c.get(Calendar.DAY_OF_MONTH); month=c.get(Calendar.MONTH); year = c.get(Calendar.YEAR);
                }else{
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dueDate);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(RentalFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear , int dayOfMonth) {
                        int deliveryDay = dayOfMonth, deliveryMonth=monthOfYear+1, deliveryYear = year;
                        dueDate = new GregorianCalendar(deliveryYear, deliveryMonth-1, deliveryDay).getTime();
                        et_delivery_date.setText(deliveryDay+"/"+deliveryMonth+"/"+deliveryYear);
                    }
                },year,month,day);
                datePickerDialog.show();
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
                rentalFormViewModel.rentalFormDataChanged(currentDate, dueDate, totalPrice, et_address.getText().toString()+"");
            }
        };
        et_delivery_date.addTextChangedListener(afterTextChangedListener);
        et_address.addTextChangedListener(afterTextChangedListener);
    }

    private void showErrorMsg(@StringRes final Integer errorString) {
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

    private void showSuccessMsg(final String successString) {
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

    /* --------------------------------------------------*/
    /*                  SETTING UP MAP                   */
    /* --------------------------------------------------*/
    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mv_map.onCreate(mapViewBundle);
        mv_map.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mv_map.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mv_map.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mv_map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mv_map.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;
        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Ubicación"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    REQUEST_LOCATION_PERMISSION);
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        mv_map.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mv_map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mv_map.onLowMemory();
    }

    private void configureAddressEditText(){
        et_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(et_address.getText().toString().trim().isEmpty()){
                        Toast.makeText(RentalFormActivity.this,
                                "Please enter an address to locate on the map", Toast.LENGTH_SHORT).show();
                    }else{
                        findAddress(et_address.getText().toString());
                    }
                }
                return false;
            }
        });
    }


    private void findAddress(String address){
        List<Address> direcciones = null;
        Geocoder geocoder = new Geocoder(this);
        MarkerOptions markerOptions = new MarkerOptions();
        try {
            direcciones = geocoder.getFromLocationName(address,5);
            if(direcciones!=null){
                //for(int i=0; i<direcciones.size(); i++){
                myMap.clear();
                Address d = direcciones.get(0);
                LatLng latlng = new LatLng(d.getLatitude(),d.getLongitude());
                markerOptions.position(latlng);
                markerOptions.title("Delivery location");
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                myMap.addMarker(markerOptions);
                myMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                myMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                //}
            }else{
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
