package com.app.gorent.ui.activities.main.lent_items_history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.ui.adapters.ItemLendingRecyclerViewAdapter;
import com.app.gorent.ui.adapters.RecyclerViewClickListener;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemLendingListQueryResult;

public class LentItemsFragment extends Fragment {

    private LentItemsViewModel lentItemsViewModel;
    private RecyclerView rv_item_lending_list;
    private ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assert(getActivity()!=null);
        lentItemsViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity().getApplicationContext())).get(LentItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lending_history, container, false);
        connectModelWithView(root);
        prepareItemLendingObserver();
        return root;
    }

    private void connectModelWithView(View root){
        rv_item_lending_list = root.findViewById(R.id.lent_items_rv_item_lending_list);
        rv_item_lending_list.setHasFixedSize(true);
        rv_item_lending_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        pg_loading = root.findViewById(R.id.lent_items_pg_loading);
    }

    private void prepareItemLendingObserver() {
        lentItemsViewModel.getItemLendingQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemLendingListQueryResult>() {
            @Override
            public void onChanged(ItemLendingListQueryResult itemLendingListQueryResult) {
                if(itemLendingListQueryResult ==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemLendingListQueryResult.getItemLendingList()!=null){
                    if(itemLendingListQueryResult.getItemLendingList().size()>0){
                        RecyclerView.Adapter adapter_item_lending_list = new ItemLendingRecyclerViewAdapter(getActivity(),
                                itemLendingListQueryResult.getItemLendingList(), new RecyclerViewClickListener() {
                            @Override
                            public void onMoreItemLendingClicked(ItemLending itemLending) {
                                configureDialog(itemLending);
                            }
                        });
                        rv_item_lending_list.setAdapter(adapter_item_lending_list);
                        adapter_item_lending_list.notifyDataSetChanged();
                    }else{
                        showMessage("You haven't earned any money! \n" +
                                "Lend an article!!\nWhat are you waiting for?");
                    }
                }
                if(itemLendingListQueryResult.getError()!=null){
                    Toast.makeText(getContext(), itemLendingListQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configureDialog(ItemLending itemLending){
    }

    private void showMessage(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}
