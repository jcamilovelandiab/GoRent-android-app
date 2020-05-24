package com.app.gorent.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gorent.R;
import com.app.gorent.data.model.ItemLending;
import com.app.gorent.utils.MyUtils;

import java.util.List;

public class ItemLendingRecyclerViewAdapter extends RecyclerView.Adapter<ItemLendingRecyclerViewAdapter.ItemLendingViewHolder>{

    private Context context;
    private List<ItemLending> itemLendingList;
    private RecyclerViewClickListener listener;

    public ItemLendingRecyclerViewAdapter(Context context, List<ItemLending> itemLendingList, RecyclerViewClickListener listener) {
        this.context = context;
        this.itemLendingList = itemLendingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemLendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_item_lending_row, parent, false);
        return new ItemLendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemLendingViewHolder holder, int position) {
        ItemLending itemLending= itemLendingList.get(position);
        holder.bind(itemLending, listener);
    }

    @Override
    public int getItemCount() {
        return itemLendingList.size();
    }

    public class ItemLendingViewHolder extends RecyclerView.ViewHolder{

        TextView tv_item_name, tv_item_description, tv_item_lending_rent_date,
                tv_item_lending_due_date, tv_item_lending_return_date, tv_item_lending_total_price,
                tv_item_lending_tv_owner, tv_item_lending_delivery_address;
        ImageView iv_image;
        ImageButton ib_more;

        public ItemLendingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.item_lending_row_tv_item_name);
            tv_item_description = itemView.findViewById(R.id.item_lending_row_tv_item_description);
            tv_item_lending_rent_date = itemView.findViewById(R.id.item_lending_row_tv_rent_date);
            tv_item_lending_due_date = itemView.findViewById(R.id.item_lending_row_tv_due_date);
            tv_item_lending_return_date = itemView.findViewById(R.id.item_lending_row_tv_return_date);
            tv_item_lending_total_price = itemView.findViewById(R.id.item_lending_row_tv_total_price);
            iv_image = itemView.findViewById(R.id.item_lending_row_iv_item_picture);
            tv_item_lending_tv_owner = itemView.findViewById(R.id.item_lending_row_tv_owner);
            tv_item_lending_delivery_address = itemView.findViewById(R.id.item_lending_row_tv_delivery_address);
            ib_more = itemView.findViewById(R.id.item_lending_row_iv_item_ib_more);
        }

        public void bind(final ItemLending itemLending, final RecyclerViewClickListener listener){
            tv_item_name.setText(itemLending.getItem().getName());
            tv_item_description.setText(itemLending.getItem().getDescription());
            tv_item_lending_rent_date.setText("Rent date: "+itemLending.getLendingDate().toString());
            tv_item_lending_due_date.setText("Due date: "+itemLending.getDueDate().toString());
            String returnDateStr = "Item has not returned yet.";
            if(itemLending.getReturnDate()!=null){
                returnDateStr = itemLending.getReturnDate().toString();
            }
            tv_item_lending_return_date.setText("Return date: "+returnDateStr);
            tv_item_lending_total_price.setText("Total fee: "+itemLending.getTotalPrice().toString());
            tv_item_lending_delivery_address.setText("Delivery address: "+itemLending.getDelivery_address());
            boolean hasImage = false;
            if(itemLending.getItem().getImage_path()!=null){
                Uri photoUri = MyUtils.loadImage(context, itemLending.getItem().getImage_path());
                if(photoUri!=null){
                    iv_image.setImageURI(photoUri);
                    hasImage = true;
                }
            }
            if(!hasImage){
                if(itemLending.getItem().getCategory().getName().toLowerCase().equals("houses")){
                    iv_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.houses));
                }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("cars")){
                    iv_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.cars));
                }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("pianos")){
                    iv_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.pianos));
                }else if(itemLending.getItem().getCategory().getName().toLowerCase().equals("laptops")){
                    iv_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.laptops));
                }
            }
            tv_item_lending_tv_owner.setText(itemLending.getItem().getItemOwner().getFull_name());
            ib_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMoreItemLendingClicked(itemLending);
                }
            });
        }
    }



}
