package com.munib.hostin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.Main_fragment;
import com.munib.hostin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainAdapter extends RecyclerView
        .Adapter<MainAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<HostelsData> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static HostelsData current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView title,due_date,status;


        public DataObjectHolder(View itemView) {
            super(itemView);

            title=(TextView) itemView.findViewById(R.id.dname);
            due_date=(TextView) itemView.findViewById(R.id.price);
            status=(TextView)  itemView.findViewById(R.id.fname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = ((MainActivity) ctx).getSupportFragmentManager();
                    HostelProfile fragment = new HostelProfile();
                    fm.beginTransaction().addToBackStack("frag").replace(R.id.fragment,fragment).commit();
                }
            });


        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        MainAdapter.myClickListener = myClickListener;
    }

    public MainAdapter(Context ctx, ArrayList<HostelsData> myDataset) {
        MainAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_layout, parent, false);

        Log.d("mubi","here inside 1");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        Log.d("mubi","here inside 2");
        holder.title.setText(mDataset.get(position).getD_name());
        holder.due_date.setText(mDataset.get(position).getF_name());

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