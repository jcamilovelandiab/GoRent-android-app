package com.app.gorent.ui.activities.main.rent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.activities.rent_item_details.RentItemDetailsActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;

public class RentFragment extends Fragment {

    RentViewModel rentViewModel;
    ListView lv_items;
    ItemListAdapter itemListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rentViewModel =
                ViewModelProviders.of(this, new ViewModelFactory()).get(RentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rent, container, false);
        lv_items = root.findViewById(R.id.rent_lv_items);
        retrieveAvailableItems();
        configureClickableItems();
        return root;
    }

    private void retrieveAvailableItems(){
        itemListAdapter = new ItemListAdapter(getActivity(), (ArrayList<Item>) rentViewModel.getItemList());
        lv_items.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();
    }

    private void configureClickableItems(){
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) lv_items.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), RentItemDetailsActivity.class);
                intent.putExtra("itemId",item.getId());
                startActivity(intent);
            }
        });
    }

}
