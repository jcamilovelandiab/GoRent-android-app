package com.app.gorent.ui.activities.item_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.activities.item_form.ItemFormState;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.ImageUtils;
import com.app.gorent.utils.result.ItemQueryResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemInformationActivity extends AppCompatActivity {

    ItemInformationViewModel itemInformationViewModel;

    EditText et_item_name, et_item_description, et_item_price;
    ImageView iv_item_picture;
    Spinner sp_category, sp_fee_type;
    Button btn_update, btn_cancel;
    ProgressBar pg_loading;
    String category, feeType, picture_path="", current_picture_path="";
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> feeTypeAdapter;

    LinearLayout layout_update_buttons, layout_form_item;
    boolean updateMode = false;

    Bundle bundle;
    Uri picture_uri;
    private static final int REQUEST_TAKE_PHOTO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        itemInformationViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplicationContext())).get(ItemInformationViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);
        connectModelWithView();

        bundle = this.getIntent().getExtras();
        assert bundle != null;
        configureSpFeeType();
        prepareCategoriesObserver();
        prepareItemFormStateObserver();
        configureBtnCancel();
        prepareItemQueryObserver();
        deactivateUpdate();
    }

    private void connectModelWithView() {
        btn_update =findViewById(R.id.item_info_btn_update);
        btn_cancel = findViewById(R.id.item_info_btn_cancel);
        layout_form_item = findViewById(R.id.item_info_layout_form_item);
        layout_update_buttons = findViewById(R.id.item_info_layout_update_buttons);
        pg_loading = findViewById(R.id.item_info_pg_loading);
        et_item_name = findViewById(R.id.item_info_et_item_name);
        et_item_description = findViewById(R.id.item_info_et_item_description);
        et_item_price = findViewById(R.id.item_info_et_item_price);
        iv_item_picture = findViewById(R.id.item_info_iv_item_picture);
        sp_category = findViewById(R.id.item_info_sp_category);
        sp_fee_type = findViewById(R.id.item_info_sp_fee_type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_information_update:
                activateUpdate();
                break;
            case R.id.menu_item_information_delete:
                deleteItem();
                break;
            default:
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
        return true;
    }

    private void activateUpdate(){
        layout_update_buttons.setVisibility(View.VISIBLE);
        updateMode = true;
        disableEnableControls(true, layout_form_item);
    }

    private void deactivateUpdate(){
        layout_update_buttons.setVisibility(View.GONE);
        updateMode = false;
        disableEnableControls(false, layout_form_item);
    }

    private void disableEnableControls(boolean enable, ViewGroup vg){
        for(int i=0; i<vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if(child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    private void deleteItem(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        itemInformationViewModel.deleteItem();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the item?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void configureBtnCancel(){
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateUpdate();
            }
        });
    }

    private void prepareItemQueryObserver(){
        itemInformationViewModel.getItemQueryResult().observe(this, new Observer<ItemQueryResult>() {
            @Override
            public void onChanged(ItemQueryResult itemQueryResult) {
                if(itemQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemQueryResult.getError()!=null){
                    Toast.makeText(ItemInformationActivity.this, itemQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if(itemQueryResult.getItem()!=null){
                    Item item = itemQueryResult.getItem();
                    et_item_name.setText(item.getName());
                    et_item_description.setText(item.getDescription());
                    et_item_price.setText(item.getPrice().toString());
                    //...... image, category, fee type....
                    for(int i=0; i<categoryAdapter.getCount(); i++){
                        if(categoryAdapter.getItem(i).equals(item.getCategory().getName())){
                            sp_category.setSelection(i);
                            category = item.getCategory().getName();
                            break;
                        }
                    }
                    for(int i=0; i<feeTypeAdapter.getCount(); i++){
                        if(feeTypeAdapter.getItem(i).equals(item.getFeeType())){
                            sp_fee_type.setSelection(i);
                            feeType = item.getFeeType();
                            break;
                        }
                    }
                    configureTextWatchers();
                    prepareUpdateResultObserver();
                    prepareDeleteResultObserver();
                    loadImage(item);
                    picture_path = (item.getImage_path()!=null)?item.getImage_path(): picture_path;
                    configureTakePicture();
                    configureBtnUpdate();
                }
            }
        });
    }

    private void prepareCategoriesObserver(){
        itemInformationViewModel.getCategoryListQueryResult().observe(this, new Observer<CategoryListQueryResult>() {
            @Override
            public void onChanged(CategoryListQueryResult categoryListQueryResult) {
                if(categoryListQueryResult==null) return;
                if(categoryListQueryResult.getError()!=null){
                    showErrorMessage(getString(categoryListQueryResult.getError()));
                }
                if(categoryListQueryResult.getCategoryList()!=null){
                    configureSpCategory(categoryListQueryResult.getCategoryList());
                    itemInformationViewModel.findItemById(bundle.getString("itemId"));
                }
            }
        });
    }

    private void prepareItemFormStateObserver(){
        itemInformationViewModel.getItemFormState().observe(this, new Observer<ItemFormState>() {
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
                if(itemFormState.isDataValid() && updateMode){
                    if(picture_path == null || picture_path.isEmpty()){
                        showErrorMessage("Click on the camera and add a photo of the item to complete the registration.");
                    }else{
                        btn_update.setEnabled(itemFormState.isDataValid());
                    }
                }
            }
        });
    }

    private void prepareUpdateResultObserver(){
        itemInformationViewModel.getUpdateItemResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult updateItemResult) {
                if(updateItemResult == null) return;
                deactivateUpdate();
                if(updateItemResult.getError()!=null){
                    showErrorMessage(getString(updateItemResult.getError()));
                }
                if(updateItemResult.getSuccess()!=null){
                    showMessage(updateItemResult.getSuccess());
                }
            }
        });
    }

    private void prepareDeleteResultObserver(){
        itemInformationViewModel.getDeleteItemResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult deleteItemResult) {
                if(deleteItemResult == null) return;
                if(deleteItemResult.getError()!=null){
                    showErrorMessage(getString(deleteItemResult.getError()));
                }
                if(deleteItemResult.getSuccess()!=null){
                    showMessage(deleteItemResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void configureSpCategory(List<Category> categories){
        ArrayList<String> nameCategories = new ArrayList<>();
        nameCategories.add("Select a category");
        for(Category c: categories){
            nameCategories.add(c.getName());
        }
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameCategories);
        sp_category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    category = parent.getItemAtPosition(position).toString();
                    if(updateMode) showMessage("Selected category: "+category);
                    itemInformationViewModel.dataChanged(et_item_name.getText().toString()+"",
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
        feeTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.fee_types));
        sp_fee_type.setAdapter(feeTypeAdapter);
        feeTypeAdapter.notifyDataSetChanged();
        sp_fee_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    feeType = parent.getItemAtPosition(position).toString();
                    if(updateMode) showMessage("Selected fee type: "+feeType);
                    itemInformationViewModel.dataChanged(et_item_name.getText().toString()+"",
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
                itemInformationViewModel.dataChanged(et_item_name.getText().toString()+"",
                        et_item_description.getText().toString()+"",
                        et_item_price.getText().toString()+"",
                        feeType+"", category+"");
            }
        };
        et_item_name.addTextChangedListener(textWatcher);
        et_item_description.addTextChangedListener(textWatcher);
        et_item_price.addTextChangedListener(textWatcher);
    }

    private void configureBtnUpdate(){
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemInformationViewModel.updateItem(et_item_name.getText().toString()+"",
                        et_item_description.getText().toString()+"",
                        et_item_price.getText().toString()+"",
                        feeType+"", category+"", picture_path+"");
            }
        });
    }

    private void showErrorMessage(final String errorMsg){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ItemInformationActivity.this, errorMsg, Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(ItemInformationActivity.this, msg, Toast.LENGTH_SHORT);
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

    public void loadImage(Item item){
        boolean hasImage = false;
        if(item.getImage_path()!=null){
            Uri photoUri = ImageUtils.loadImage(this, item.getImage_path());
            if(photoUri!=null){
                iv_item_picture.setImageURI(photoUri);
                hasImage = true;
            }
        }
        if(!hasImage){
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
                        picture_uri = FileProvider.getUriForFile(ItemInformationActivity.this,authority+"",file_picture);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            iv_item_picture.setImageURI(picture_uri);
            picture_path = current_picture_path;
            itemInformationViewModel.dataChanged(et_item_name.getText().toString()+"",
                    et_item_description.getText().toString()+"",
                    et_item_price.getText().toString()+"",
                    feeType+"", category+"");
            Toast.makeText(this, "Picture was successfully saved in " + picture_path, Toast.LENGTH_SHORT).show();
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
