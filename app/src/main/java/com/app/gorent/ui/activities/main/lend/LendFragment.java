package com.app.gorent.ui.activities.main.lend;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.activities.item_information.ItemInformationActivity;
import com.app.gorent.ui.activities.item_form.ItemFormActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.ItemListQueryResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LendFragment extends Fragment {

    private LendViewModel lendViewModel;
    FloatingActionButton fab_add_button;
    ListView lv_items;
    ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_lend, container, false);
        lendViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(LendViewModel.class);

        connectModelWithView(root);
        configureListItemResultObserver();
        configureAddButton();
        return root;
    }

    private void connectModelWithView(View view){
        fab_add_button = view.findViewById(R.id.lend_item_fab_add);
        lv_items = view.findViewById(R.id.lend_item_my_item_list);
        pg_loading = view.findViewById(R.id.lend_pg_loading);
    }

    private void configureClickableItems() {
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) lv_items.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), ItemInformationActivity.class);
                intent.putExtra("itemId",item.getId());
                startActivity(intent);
            }
        });
    }

    private void configureAddButton(){
        fab_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemFormActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureListItemResultObserver() {
        lendViewModel.getItemListQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemListQueryResult>() {
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

    @Override
    public void onResume() {
        lendViewModel.findMyItems();
        super.onResume();
    }
}
