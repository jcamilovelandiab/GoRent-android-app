package com.app.gorent.ui.activities.main_admin.item_lending_history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.ui.adapters.ItemLendingRecyclerViewAdapter;
import com.app.gorent.ui.adapters.RecyclerViewClickListener;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemLendingListQueryResult;

public class ItemLendingHistory extends Fragment {

    private ItemLendingHistoryViewModel itemLendingHistoryViewModel;

    private RecyclerView rv_item_lending_list;
    private ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_item_lending_history, container, false);
        itemLendingHistoryViewModel = ViewModelProviders.of(getActivity(),
                new ViewModelFactory(getActivity().getApplicationContext())).get(ItemLendingHistoryViewModel.class);
        connectModelWithView(root);
        prepareItemLendingHistoryObserver();

        return root;
    }

    private void connectModelWithView(View root){
        rv_item_lending_list = root.findViewById(R.id.admin_item_lending_list);
        rv_item_lending_list.setHasFixedSize(true);
        rv_item_lending_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        pg_loading = root.findViewById(R.id.admin_item_lending_list_pg_loading);
    }

    private void prepareItemLendingHistoryObserver() {
        itemLendingHistoryViewModel.getItemLendingListQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemLendingListQueryResult>() {
            @Override
            public void onChanged(ItemLendingListQueryResult itemLendingListQueryResult) {
                pg_loading.setVisibility(View.GONE);
                if(itemLendingListQueryResult.getItemLendingList()!=null) {
                    if (itemLendingListQueryResult.getItemLendingList().size() > 0) {
                        RecyclerView.Adapter adapter_item_lending_list = new ItemLendingRecyclerViewAdapter(getActivity(),
                                itemLendingListQueryResult.getItemLendingList(), new RecyclerViewClickListener() {
                            @Override
                            public void onMoreItemLendingClicked(ItemLending itemLending) {
                                configureDialog(itemLending);
                            }
                        });
                        rv_item_lending_list.setAdapter(adapter_item_lending_list);
                        adapter_item_lending_list.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void configureDialog(ItemLending itemLending){
    }


}
