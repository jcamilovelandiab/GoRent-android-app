package com.app.gorent.ui.activities.main.rent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.activities.rent_item_details.RentItemDetailsActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemListQueryResult;

import java.util.ArrayList;

public class RentFragment extends Fragment {

    private RentViewModel rentViewModel;
    private ListView lv_items;
    private ItemListAdapter itemListAdapter;
    private ProgressBar pg_loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assert(getActivity()!=null);
        rentViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity().getApplicationContext())).get(RentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rent, container, false);
        lv_items = root.findViewById(R.id.rent_lv_items);
        pg_loading = root.findViewById(R.id.rent_pg_loading);
        prepareAvailableItemsObservable();
        return root;
    }

    private void prepareAvailableItemsObservable(){
        rentViewModel.getAvailableItems().observe(getViewLifecycleOwner(), new Observer<ItemListQueryResult>() {
            @Override
            public void onChanged(ItemListQueryResult itemListQueryResult) {
                if(itemListQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemListQueryResult.getError()!=null){
                    Toast.makeText(getContext(), itemListQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if(itemListQueryResult.getItems()!=null){
                    itemListAdapter = new ItemListAdapter(getActivity(), (ArrayList<Item>) itemListQueryResult.getItems());
                    lv_items.setAdapter(itemListAdapter);
                    itemListAdapter.notifyDataSetChanged();
                    configureClickableItems();
                }
            }
        });
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

    @Override
    public void onResume() {
        pg_loading.setVisibility(View.VISIBLE);
        rentViewModel.retrieveAvailableItems();
        super.onResume();
    }
}
