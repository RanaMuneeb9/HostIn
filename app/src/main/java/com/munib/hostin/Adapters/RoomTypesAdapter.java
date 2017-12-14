package com.munib.hostin.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import com.balysv.materialripple.MaterialRippleLayout;
import com.munib.hostin.DataModel.RoomTypes;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.R;

import java.util.ArrayList;


public class RoomTypesAdapter extends RecyclerView
        .Adapter<RoomTypesAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<RoomTypes> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static RoomTypes current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView price;
        ImageView imageView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            price=(TextView) itemView.findViewById(R.id.price);
            imageView=(ImageView) itemView.findViewById(R.id.image);

        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        RoomTypesAdapter.myClickListener = myClickListener;
    }

    public RoomTypesAdapter(Context ctx, ArrayList<RoomTypes> myDataset) {
        RoomTypesAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_types, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        Log.d("mubi-r",mDataset.get(position).getType_id()+"");
        holder.price.setText("PKR "+mDataset.get(position).getBase_price()+"/-");
        if(mDataset.get(position).getType_id()==1)
        {
            holder.imageView.setImageResource(R.drawable.person);
        }else if(mDataset.get(position).getType_id()==2)
        {
            holder.imageView.setImageResource(R.drawable.person2);
        }else{
            holder.imageView.setImageResource(R.drawable.person3);
        }

    }

    public void addItem(RoomTypes dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);

    }
}