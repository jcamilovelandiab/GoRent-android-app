package com.app.gorent.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_row_layout,null);
            Item item = (Item) getItem(position);

            TextView tv_item_name = view.findViewById(R.id.item_row_layout_tv_name);
            tv_item_name.setText(item.getName());

            TextView tv_item_description = view.findViewById(R.id.item_row_layout_tv_description);
            tv_item_description.setText(item.getDescription());

            TextView tv_item_price = view.findViewById(R.id.item_row_layout_tv_price);
            tv_item_price.setText(item.getPrice().toString());

            TextView tv_item_time_unit = view.findViewById(R.id.item_row_layout_tv_time_unit);
            tv_item_time_unit.setText(item.getPrice().toString());

            ImageView iv_image = view.findViewById(R.id.item_row_layout_iv_image);
            if(item.getCategory().getName().equals("houses")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.houses));
            }else if(item.getCategory().getName().equals("cars")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.cars));
            }else if(item.getCategory().getName().equals("pianos")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.pianos));
            }else if(item.getCategory().getName().equals("laptops")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.laptops));
            }

        }
        return view;
    }
}
