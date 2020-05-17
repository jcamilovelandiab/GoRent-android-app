package com.app.gorent.ui.activities.main.rented_items_history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.ui.adapters.ItemLendingListAdapter;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.ItemLendingQueryResult;

import java.util.ArrayList;

public class RentedItemsFragment extends Fragment {

    private RentedItemsViewModel rentedItemsViewModel;
    ListView lv_item_lending_list;
    ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rentedItemsViewModel =
                ViewModelProviders.of(this, new ViewModelFactory()).get(RentedItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rented_items, container, false);
        lv_item_lending_list = root.findViewById(R.id.rented_items_lv_item_lending_list);
        pg_loading = root.findViewById(R.id.rented_items__pg_loading);
        prepareItemLendingHistoryObserver();
        return root;
    }

    public void prepareItemLendingHistoryObserver(){
        rentedItemsViewModel.getItemLendingQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemLendingQueryResult>() {
            @Override
            public void onChanged(ItemLendingQueryResult itemLendingQueryResult) {
                if(itemLendingQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemLendingQueryResult.getError()!=null){
                    Toast.makeText(getActivity(), itemLendingQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if(itemLendingQueryResult.getItemLendingList()!=null){
                    ItemLendingListAdapter itemLendingListAdapter = new ItemLendingListAdapter(
                            getActivity(),(ArrayList<ItemLending>) itemLendingQueryResult.getItemLendingList());
                    lv_item_lending_list.setAdapter(itemLendingListAdapter);
                    itemLendingListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
