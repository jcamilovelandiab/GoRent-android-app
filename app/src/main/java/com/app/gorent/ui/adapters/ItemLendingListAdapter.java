package com.app.gorent.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.data.repositories.MediaRepository;
import com.app.gorent.utils.MyUtils;

import java.util.ArrayList;

public class ItemLendingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ItemLending> itemLendingList;
    private MediaRepository mediaRepository;

    public ItemLendingListAdapter(Context context, ArrayList<ItemLending> itemLendingList) {
        this.context = context;
        this.itemLendingList = itemLendingList;
        this.mediaRepository = MediaRepository.getInstance(context);
    }

    @Override
    public int getCount() {
        return itemLendingList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemLendingList.get(position);
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
            view = inflater.inflate(R.layout.layout_item_lending_row,null);
            ItemLending itemLending = (ItemLending) getItem(position);

            TextView tv_item_name = view.findViewById(R.id.item_lending_row_tv_item_name);
            tv_item_name.setText(itemLending.getItem().getName());

            TextView tv_item_description = view.findViewById(R.id.item_lending_row_tv_item_description);
            tv_item_description.setText(itemLending.getItem().getDescription());

            TextView tv_item_lending_rent_date = view.findViewById(R.id.item_lending_row_tv_rent_date);
            tv_item_lending_rent_date.setText("Rent date: "+itemLending.getLendingDate().toString());

            TextView tv_item_lending_due_date = view.findViewById(R.id.item_lending_row_tv_due_date);
            tv_item_lending_due_date.setText("Due date: "+itemLending.getDueDate().toString());

            TextView tv_item_lending_return_date = view.findViewById(R.id.item_lending_row_tv_return_date);

            String returnDateStr = "Item has not yet returned.";
            if(itemLending.getReturnDate()!=null){
                returnDateStr = itemLending.getReturnDate().toString();
            }
            tv_item_lending_return_date.setText("Return date: "+returnDateStr);

            TextView tv_item_lending_total_price = view.findViewById(R.id.item_lending_row_tv_total_price);
            tv_item_lending_total_price.setText("Total fee: "+itemLending.getTotalPrice().toString());


            ImageView iv_image = view.findViewById(R.id.item_lending_row_iv_item_picture);
            boolean hasImage = false;
            if(itemLending.getItem().getImage_path()!=null){
                Uri photoUri = MyUtils.loadImage(context, itemLending.getItem().getImage_path());
                if(photoUri!=null){
                    iv_image.setImageURI(photoUri);
                    hasImage = true;
                }
            }


            if(itemLending.getItem().getCategory().getName().toLowerCase().equals("houses")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.houses));
            }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("cars")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.cars));
            }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("pianos")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.pianos));
            }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("laptops")){
                iv_image.setImageDrawable(view.getResources().getDrawable(R.drawable.laptops));
            }

        }
        return view;
    }

}
