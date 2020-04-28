package com.app.gorent.ui.main.lend;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.item_information.ItemInformationActivity;
import com.app.gorent.ui.new_item.CreateItemFormActivity;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LendFragment extends Fragment {

    private LendViewModel lendViewModel;
    FloatingActionButton fab_add_button;
    ListView lv_items;

    public static LendFragment newInstance() {
        return new LendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_lend, container, false);
        lendViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(LendViewModel.class);

        connectModelWithView(root);
        retrieveLoggedUserItems();
        configureAddButton();
        configureClickableItems();
        return root;
    }

    private void connectModelWithView(View view){
        fab_add_button = view.findViewById(R.id.lend_item_fab_add);
        lv_items = view.findViewById(R.id.lend_item_my_item_list);
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
                Intent intent = new Intent(getActivity(), CreateItemFormActivity.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveLoggedUserItems() {
        ItemListAdapter itemListAdapter = new ItemListAdapter(getActivity(),
                (ArrayList<Item>) lendViewModel.getItemsOfLoggedUser());
        lv_items.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();
    }

}
