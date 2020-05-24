package com.app.gorent.ui.activities.item_form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemFormActivity extends AppCompatActivity {

    ItemFormViewModel itemFormViewModel;
    ImageView iv_item_picture;
    EditText et_item_name, et_item_description, et_item_price;
    Spinner sp_category, sp_fee_type;
    String category="", feeType="", picture_path="", current_picture_path="";
    Button btn_save;
    ProgressBar pg_loading;

    Uri picture_uri;
    private static final int REQUEST_TAKE_PHOTO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_new_item_form);
        itemFormViewModel = ViewModelProviders.of(this,new ViewModelFactory(getApplicationContext())).get(ItemFormViewModel.class);

        connectModelWithView();
        configureSpFeeType();
        configureTextWatchers();
        configureItemFormStateObserver();
        configureItemFormResultObserver();
        configureCategoriesObserver();
        configureBtnSave();
        configureTakePicture();
    }

    @Override
    public boolean onSupportNavigateUp(){
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void connectModelWithView() {
        iv_item_picture = findViewById(R.id.create_item_form_iv_picture);
        et_item_name = findViewById(R.id.create_item_form_et_name);
        et_item_description = findViewById(R.id.create_item_form_et_description);
        et_item_price = findViewById(R.id.create_item_form_et_price);
        sp_category = findViewById(R.id.create_item_form_sp_category);
        sp_fee_type = findViewById(R.id.create_item_form_sp_fee_type);
        btn_save = findViewById(R.id.create_item_form_btn_save);
        pg_loading = findViewById(R.id.create_item_form_pg_loading);
    }

    private void configureBtnSave() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg_loading.setVisibility(View.VISIBLE);
                itemFormViewModel.saveItem(et_item_name.getText().toString()+"",
                        et_item_description.getText().toString()+"",
                        et_item_price.getText().toString()+"",
                        feeType+"",
                        category+"",
                        picture_path+""
                        );
            }
        });
    }

    private void configureCategoriesObserver(){
        itemFormViewModel.getCategoryListQueryResult().observe(this, new Observer<CategoryListQueryResult>() {
            @Override
            public void onChanged(CategoryListQueryResult categoryListQueryResult) {
                if(categoryListQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(categoryListQueryResult.getError()!=null){
                    showErrorMessage(getString(categoryListQueryResult.getError()), 0);
                }
                if(categoryListQueryResult.getCategoryList()!=null){
                    configureSpCategory(categoryListQueryResult.getCategoryList());
                }
            }
        });
    }

    private void configureItemFormResultObserver(){
        itemFormViewModel.getItemFormResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(basicResult.getError()!=null){
                    showErrorMessage(getString(basicResult.getError()), 0);
                }
                if(basicResult.getSuccess()!=null){
                    showMessage(basicResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
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
                        showErrorMessage("Click on the camera and add a photo of the item to complete the registration.", 2000);
                    }else{
                        btn_save.setEnabled(itemFormState.isDataValid());
                    }
                }
            }
        });
    }

    private void configureSpCategory(List<Category> categories) {
        ArrayList<String> nameCategories = new ArrayList<>();
        nameCategories.add("Select a category");
        for(Category c: categories){
            nameCategories.add(c.getName());
        }
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
                            feeType+"", category+"");
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
                            feeType+"", category+"");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
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
                                                    feeType+"", category+"");
            }
        };
        et_item_name.addTextChangedListener(textWatcher);
        et_item_description.addTextChangedListener(textWatcher);
        et_item_price.addTextChangedListener(textWatcher);
    }

    private void showErrorMessage(final String errorMsg, long delayMillis){
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
        }, delayMillis);
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

    private void configureTakePicture(){
        askPermissions();
        iv_item_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // It found the activity that generated the picture
                if(takePictureIntent.resolveActivity(getApplicationContext().getPackageManager())!=null){
                    File file_picture = null;
                    try {
                        file_picture = createImageFile();
                    } catch (IOException ex){
                        Toast.makeText(getBaseContext(), "An error was occurred while generating the file",
                                Toast.LENGTH_SHORT).show();
                    }
                    //Check that the image file was successfully created
                    if(file_picture != null){
                        String authority = getString(R.string.authority_package);
                        picture_uri = FileProvider.getUriForFile(ItemFormActivity.this,authority+"",file_picture);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picture_uri);
                        startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
    }

    //Create an image file
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        current_picture_path = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            iv_item_picture.setImageURI(picture_uri);
            picture_path = current_picture_path;
            itemFormViewModel.dataChanged(et_item_name.getText().toString()+"",
                    et_item_description.getText().toString()+"",
                    et_item_price.getText().toString()+"",
                    feeType+"", category+"");
            Toast.makeText(this, "Picture was successfully saved in " + picture_path, Toast.LENGTH_SHORT).show();
        }
    }

    private void askPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}