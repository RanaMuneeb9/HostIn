package com.munib.hostin.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.munib.hostin.DataModel.Facilities;
import com.munib.hostin.R;

import java.util.ArrayList;


public class FacilitiesAdapter extends RecyclerView
        .Adapter<FacilitiesAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<Facilities> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static Facilities current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView name;
        ImageView imageView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.name);
            imageView=(ImageView) itemView.findViewById(R.id.image);

        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        FacilitiesAdapter.myClickListener = myClickListener;
    }

    public FacilitiesAdapter(Context ctx, ArrayList<Facilities> myDataset) {
        FacilitiesAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_facility_type, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        holder.name.setText(mDataset.get(position).getName());
        if(mDataset.get(position).getName().equals("Wifi"))
        {
            holder.imageView.setImageResource(R.drawable.ic_wifi_grey_700_24dp);
        }else if(mDataset.get(position).getName().equals("Mess"))
        {
            holder.imageView.setImageResource(R.drawable.mess_icon);
        }else if(mDataset.get(position).getName().equals("Generator"))
        {
            holder.imageView.setImageResource(R.drawable.thunder_icon);
        }else if(mDataset.get(position).getName().equals("Gym"))
        {
            holder.imageView.setImageResource(R.drawable.gym_icon);
        }else if(mDataset.get(position).getName().equals("Laundry"))
        {
            holder.imageView.setImageResource(R.drawable.laundry_icon);
        }else if(mDataset.get(position).getName().equals("Geyser"))
        {
            holder.imageView.setImageResource(R.drawable.geyser_icon);
        }

    }

    public void addItem(Facilities dataObj, int index) {
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