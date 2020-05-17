package com.app.gorent.ui.activities.item_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.activities.new_item.ItemFormActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.ItemQueryResult;

public class ItemInformationActivity extends AppCompatActivity {

    ItemInformationViewModel itemInformationViewModel;

    EditText et_item_name, et_item_description, et_item_price;
    ImageView iv_item_picture;
    Spinner sp_category;
    Button btn_update, btn_cancel;
    ProgressBar pg_loading;

    LinearLayout layout_update_buttons;
    boolean updateMode = false;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        itemInformationViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ItemInformationViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);
        connectModelWithView();
        configureBtnCancel();
        prepareItemQueryObserver();
        bundle = this.getIntent().getExtras();
        assert bundle != null;
        itemInformationViewModel.findItemById(bundle.getLong("itemId"));
    }

    private void connectModelWithView() {
        btn_update =findViewById(R.id.item_info_btn_update);
        btn_cancel = findViewById(R.id.item_info_btn_cancel);
        layout_update_buttons = findViewById(R.id.item_info_layout_update_buttons);
        pg_loading = findViewById(R.id.item_info_pg_loading);
        et_item_name = findViewById(R.id.item_info_et_item_name);
        et_item_description = findViewById(R.id.item_info_et_item_description);
        et_item_price = findViewById(R.id.item_info_et_item_price);
        iv_item_picture = findViewById(R.id.item_info_iv_item_picture);
        sp_category = findViewById(R.id.item_info_sp_category);
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
        btn_update.setEnabled(true);
        btn_cancel.setEnabled(true);
        updateMode = true;
    }

    private void deactivateUpdate(){
        layout_update_buttons.setVisibility(View.GONE);
        btn_update.setEnabled(false);
        btn_cancel.setEnabled(false);
        updateMode = false;
    }

    private void deleteItem(){
        Toast.makeText(ItemInformationActivity.this, "Item deleted", Toast.LENGTH_SHORT);
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
                    //...... image, category....
                }
            }
        });
    }

}
