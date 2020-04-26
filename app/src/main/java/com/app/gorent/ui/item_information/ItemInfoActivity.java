package com.app.gorent.ui.item_information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

public class ItemInfoActivity extends AppCompatActivity {

    TextView tv_item_information;
    ImageView iv_item_picture;
    ItemInfoViewModel itemInfoViewModel;
    Button btn_rent, btn_accept, btn_cancel;
    LinearLayout layout_rent_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        itemInfoViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ItemInfoViewModel.class);
        connectViewWithModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_item_info);
        Long itemId =  getIntent().getExtras().getLong("itemId");
        retrieveItemInformation(itemId);
        configureBtnRent();
        configureBtnCancel();
    }

    private void connectViewWithModel(){
        iv_item_picture = findViewById(R.id.item_info_iv_item_picture);
        tv_item_information = findViewById(R.id.item_info_tv_item_information);
        btn_rent = findViewById(R.id.item_info_btn_rent);
        btn_accept = findViewById(R.id.item_info_btn_accept);
        btn_cancel = findViewById(R.id.item_info_btn_cancel);
        layout_rent_form = findViewById(R.id.item_info_layout_rent_form);
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
        Item item = itemInfoViewModel.getItemById(itemId);
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
