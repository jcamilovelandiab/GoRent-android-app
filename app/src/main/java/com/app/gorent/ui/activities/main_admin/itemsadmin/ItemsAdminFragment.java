package com.app.gorent.ui.activities.main_admin.itemsadmin;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.activities.item_information.ItemInformationActivity;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemListQueryResult;

import java.util.ArrayList;

public class ItemsAdminFragment extends Fragment {

    private ItemsAdminViewModel itemsAdminViewModel;
    private ListView lv_items;
    ProgressBar pg_loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items_admin, container, false);
        itemsAdminViewModel = ViewModelProviders.of(getActivity(), new ViewModelFactory(getActivity().getApplicationContext())).get(ItemsAdminViewModel.class);

        lv_items = root.findViewById(R.id.items_admin_item_list);
        pg_loading = root.findViewById(R.id.items_admin_pg_loading);

        prepareItemListObserver();

        return root;
    }

    private void prepareItemListObserver() {
        itemsAdminViewModel.getItemListQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemListQueryResult>() {
            @Override
            public void onChanged(ItemListQueryResult itemListQueryResult) {
                if(itemListQueryResult == null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemListQueryResult.getError()!=null){
                    Toast.makeText(getContext(), itemListQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if(itemListQueryResult.getItems()!=null){
                    ItemListAdapter itemListAdapter = new ItemListAdapter(getActivity(),
                            (ArrayList<Item>) itemListQueryResult.getItems());
                    lv_items.setAdapter(itemListAdapter);
                    itemListAdapter.notifyDataSetChanged();
                    configureClickableItems();
                }
            }
        });
    }

    private void configureClickableItems() {
        lv_items.setOnItemClickListener((parent, view, position, id) -> {
            Item item = (Item) lv_items.getAdapter().getItem(position);
            Intent intent = new Intent(getActivity(), ItemInformationActivity.class);
            intent.putExtra("itemId",item.getId());
            startActivity(intent);
        });
    }



}
