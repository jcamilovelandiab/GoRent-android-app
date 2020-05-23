package com.app.gorent.ui.activities.main.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;
import com.app.gorent.ui.adapters.ItemListAdapter;
import com.app.gorent.ui.viewmodel.ViewModelFactory;
import com.app.gorent.utils.result.ItemListQueryResult;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private ListView lv_items;
    private EditText et_search_text;
    private Button btn_filter;
    private ProgressBar pg_loading;
    String keyword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assert(getActivity()!=null);
        searchViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity().getApplicationContext())).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        connectModelWithView(root);
        configureBtnFilter();
        prepareItemListObserver();
        configureSearchEditText();
        return root;
    }

    private void connectModelWithView(View view){
        lv_items = view.findViewById(R.id.search_lv_items);
        et_search_text = view.findViewById(R.id.search_et_search_text);
        btn_filter = view.findViewById(R.id.search_btn_filter);
        pg_loading = view.findViewById(R.id.search_pg_loading);
    }

    private void configureSearchEditText(){
        et_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(et_search_text.getText().toString().trim().isEmpty()){
                        Toast.makeText(getContext(), "Please enter a keyword to search for the item", Toast.LENGTH_SHORT).show();
                    }else{
                        pg_loading.setVisibility(View.VISIBLE);
                        searchViewModel.searchItems(et_search_text.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    private void configureBtnFilter(){
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureFilterDialog();
            }
        });
    }

    private void configureFilterDialog(){

    }

    private void prepareItemListObserver() {
        searchViewModel.getItemListQueryResult().observe(getViewLifecycleOwner(), new Observer<ItemListQueryResult>() {
            @Override
            public void onChanged(ItemListQueryResult itemListQueryResult) {
                if(itemListQueryResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(itemListQueryResult.getError()!=null){
                    Toast.makeText(getActivity(), itemListQueryResult.getError(), Toast.LENGTH_SHORT).show();
                }
                if(itemListQueryResult.getItems()!=null){
                    ItemListAdapter itemListAdapter = new ItemListAdapter(getActivity(),
                            (ArrayList<Item>) itemListQueryResult.getItems());
                    lv_items.setAdapter(itemListAdapter);
                    itemListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
