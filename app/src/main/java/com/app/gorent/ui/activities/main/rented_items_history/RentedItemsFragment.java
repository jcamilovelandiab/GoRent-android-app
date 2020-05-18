package com.app.gorent.ui.activities.main.rented_items_history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.ui.adapters.ItemLendingListAdapter;
import com.app.gorent.ui.adapters.ItemLendingRecyclerViewAdapter;
import com.app.gorent.ui.adapters.RecyclerViewClickListener;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.ItemLendingQueryResult;

import java.util.ArrayList;

public class RentedItemsFragment extends Fragment {

    private RentedItemsViewModel rentedItemsViewModel;
    RecyclerView rv_item_lending_list;
    ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rentedItemsViewModel =
                ViewModelProviders.of(this, new ViewModelFactory()).get(RentedItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_renting_history, container, false);
        connectModelWithView(root);
        prepareItemLendingHistoryObserver();
        return root;
    }

    private void connectModelWithView(View root){
        rv_item_lending_list = root.findViewById(R.id.rented_items_rv_item_lending_list);
        pg_loading = root.findViewById(R.id.rented_items_pg_loading);
        rv_item_lending_list.setHasFixedSize(true);
        rv_item_lending_list.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                    RecyclerView.Adapter adapter_item_lending_list = new ItemLendingRecyclerViewAdapter(getActivity(),
                            itemLendingQueryResult.getItemLendingList(), new RecyclerViewClickListener() {
                        @Override
                        public void onMoreItemLendingClicked(ItemLending itemLending) {
                            configureDialog(itemLending);
                        }
                    });
                    rv_item_lending_list.setAdapter(adapter_item_lending_list);
                    adapter_item_lending_list.notifyDataSetChanged();
                }
            }
        });
    }

    private void configureDialog(final ItemLending itemLending){
        final String[] options = {"Return item", "Contact the item's owner", "Report problem"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        returnItem(itemLending);
                        break;
                    case 1:
                        contactOwner(itemLending);
                        break;
                    case 2:
                        reportProblem(itemLending);
                        break;
                }
            }
        });
        Dialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void returnItem(ItemLending itemLending){

    }

    private void contactOwner(ItemLending itemLending){

    }

    private void reportProblem(ItemLending itemLending){

    }

}
