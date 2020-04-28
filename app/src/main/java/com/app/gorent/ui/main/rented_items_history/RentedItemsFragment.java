package com.app.gorent.ui.main.rented_items_history;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.ui.adapters.ItemLendingListAdapter;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;

public class RentedItemsFragment extends Fragment {

    private RentedItemsViewModel lentItemsViewModel;
    ListView lv_item_lending_list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lentItemsViewModel =
                ViewModelProviders.of(this, new ViewModelFactory()).get(RentedItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rented_items, container, false);
        lv_item_lending_list = root.findViewById(R.id.rented_items_lv_item_lending_list);
        retrieveItemLendingHistory();
        return root;
    }

    public void retrieveItemLendingHistory(){
        ItemLendingListAdapter itemLendingListAdapter = new ItemLendingListAdapter(
                getActivity(),(ArrayList<ItemLending>) lentItemsViewModel.getItemLendingList());
        lv_item_lending_list.setAdapter(itemLendingListAdapter);
        itemLendingListAdapter.notifyDataSetChanged();
    }
}
