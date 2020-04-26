package com.app.gorent.ui.main.rent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.gorent.R;

public class RentFragment extends Fragment {

    private RentViewModel rentViewModel;
    ListView lv_items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rentViewModel =
                ViewModelProviders.of(this).get(RentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rent, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        rentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
