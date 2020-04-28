package com.app.gorent.ui.main.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    ListView lv_items;
    EditText et_search_text;
    Button btn_filter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this, new ViewModelFactory()).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        connectModelWithView(root);
        configureBtnFilter();
        return root;
    }

    private void connectModelWithView(View view){
        lv_items = view.findViewById(R.id.search_lv_items);
        et_search_text = view.findViewById(R.id.search_et_search_text);
        btn_filter = view.findViewById(R.id.search_btn_filter);
    }

    private void configureBtnFilter(){
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListAdapter itemListAdapter = new ItemListAdapter(getActivity(),
                        (ArrayList<Item>) searchViewModel.getItems(et_search_text.getText().toString()));
                lv_items.setAdapter(itemListAdapter);
                itemListAdapter.notifyDataSetChanged();
            }
        });
    }

}
