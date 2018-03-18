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
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.R;

import java.util.ArrayList;

/**
 * Created by MuhammadMusa on 10/26/2017.
 */

public class SavedHostelAdapter extends RecyclerView
        .Adapter<SavedHostelAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<HostelsData> mDataset;
    public static Context ctx;
    private static SavedHostelAdapter.MyClickListener myClickListener;
    static HostelsData current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        TextView prices,hostel_name;
        SimpleRatingBar ratingBar;
        ImageView imageView;
        LinearLayout main;
        MaterialRippleLayout materialRippleLayout;



        public DataObjectHolder(View itemView) {
            super(itemView);

            prices=(TextView) itemView.findViewById(R.id.prices);
            ratingBar=(SimpleRatingBar) itemView.findViewById(R.id.ratings);
            hostel_name=(TextView)  itemView.findViewById(R.id.hostel_name);
            imageView=(ImageView) itemView.findViewById(R.id.img);
            materialRippleLayout=(MaterialRippleLayout) itemView.findViewById(R.id.ripple);

//            itemView.findViewById(R.id.ripple).setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//
//
//                    // handle me
//                }
//            });



        }

    }

    public void setOnItemClickListener(SavedHostelAdapter.MyClickListener myClickListener) {
        SavedHostelAdapter.myClickListener = myClickListener;
    }

    public SavedHostelAdapter(Context ctx, ArrayList<HostelsData> myDataset) {
        SavedHostelAdapter.ctx = ctx;
        mDataset = myDataset;
    }

    @Override
    public SavedHostelAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hostel_items_layout, parent, false);

        Log.d("mubi", "here inside 1");
        SavedHostelAdapter.DataObjectHolder dataObjectHolder = new SavedHostelAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(SavedHostelAdapter.DataObjectHolder holder, final int position) {

        current_item = mDataset.get(position);
        Log.d("mubi", "here inside 2");
        holder.prices.setText(mDataset.get(position).getRoomTypes().get(0).getPrice()+"");
        holder.hostel_name.setText(mDataset.get(position).getName());
        holder.ratingBar.setRating((int)mDataset.get(position).getAverageReview());
        holder.ratingBar.setEnable(false);
//        holder.imageView.setImageResource(mDataset.get(position).getImg_res());
        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current_item= mDataset.get(position);
                Log.d("mubi-rooms",mDataset.get(position).getRoomTypes().size()+"");
                FragmentManager fm = ((MainActivity) ctx).getSupportFragmentManager();
                HostelProfile fragment = new HostelProfile();
                Bundle a=new Bundle();
                a.putSerializable("hostel_object",current_item);
                fragment.setArguments(a);
                fm.beginTransaction().addToBackStack("frag").replace(R.id.fragment, fragment).commit();
            }
        });
    }

    public void addItem(HostelsData dataObj, int index) {
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