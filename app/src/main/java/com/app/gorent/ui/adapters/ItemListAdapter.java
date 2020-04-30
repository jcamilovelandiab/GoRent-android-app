package com.app.gorent.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gorent.R;
import com.app.gorent.data.model.Item;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;

    public ItemListAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.items = itemList;
    }

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
            view = inflater.inflate(R.layout.layout_item_row,null);
            Item item = (Item) getItem(position);

            TextView tv_item_name = view.findViewById(R.id.item_row_layout_tv_name);
            tv_item_name.setText(item.getName());

            TextView tv_item_description = view.findViewById(R.id.item_row_layout_tv_description);
            tv_item_description.setText(item.getDescription());

            TextView tv_item_price = view.findViewById(R.id.item_row_layout_tv_price);
            tv_item_price.setText(String.format("$ %d",item.getPrice()));

            TextView tv_item_time_unit = view.findViewById(R.id.item_row_layout_tv_time_unit);
            tv_item_time_unit.setText(String.format("Fee type: %s",item.getFeeType()));

            ImageView iv_image = view.findViewById(R.id.item_row_layout_iv_image);
            if(item.getCategory().getName().toLowerCase().equals("houses")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.houses));
            }else if(item.getCategory().getName().toLowerCase().equals("cars")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.cars));
            }else if(item.getCategory().getName().toLowerCase().equals("pianos")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.pianos));
            }else if(item.getCategory().getName().toLowerCase().equals("laptops")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.laptops));
            }

        }
        return view;
    }
}
