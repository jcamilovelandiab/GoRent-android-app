package com.app.gorent.ui.item_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

public class ItemInformationActivity extends AppCompatActivity {

    ItemInformationViewModel itemInformationViewModel;

    Button btn_update, btn_cancel;
    LinearLayout layout_update_buttons;
    boolean updateMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);
        itemInformationViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ItemInformationViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);
        connectModelWithView();
        configureBtnCancel();
    }

    private void connectModelWithView() {
        btn_update =findViewById(R.id.item_information_btn_update);
        btn_cancel = findViewById(R.id.item_information_btn_cancel);
        layout_update_buttons = findViewById(R.id.item_information_layout_update_buttons);
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

}
