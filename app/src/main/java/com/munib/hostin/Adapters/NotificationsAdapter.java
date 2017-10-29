package com.munib.hostin.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.munib.hostin.DataModel.NotificationsData;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.R;

import java.util.ArrayList;


public class NotificationsAdapter extends RecyclerView
        .Adapter<NotificationsAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<NotificationsData> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static NotificationsData current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView prices,places,hostel_name;
        ImageView imageView;
        LinearLayout main;
        MaterialRippleLayout materialRippleLayout;


        public DataObjectHolder(View itemView) {
            super(itemView);

            prices=(TextView) itemView.findViewById(R.id.prices);
            places=(TextView) itemView.findViewById(R.id.places);
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
    public void setOnItemClickListener(MyClickListener myClickListener) {
        NotificationsAdapter.myClickListener = myClickListener;
    }

    public NotificationsAdapter(Context ctx, ArrayList<NotificationsData> myDataset) {
        NotificationsAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_items_layout, parent, false);

        Log.d("mubi","here inside 1");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        Log.d("mubi","here inside 2");

        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((MainActivity) ctx).getSupportFragmentManager();
                HostelProfile fragment = new HostelProfile();
                fm.beginTransaction().addToBackStack("frag").replace(R.id.fragment, fragment).commit();
            }
        });

    }

    public void addItem(NotificationsData dataObj, int index) {
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