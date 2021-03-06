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
import com.munib.hostin.DataModel.Reviews;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.R;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView
        .Adapter<ReviewsAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<Reviews> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static Reviews current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView name,date,desc;
        ImageView imageView;


        public DataObjectHolder(View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.name);
            date=(TextView)  itemView.findViewById(R.id.date);
            imageView=(ImageView) itemView.findViewById(R.id.img);
            desc=(TextView) itemView.findViewById(R.id.desc);

        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        ReviewsAdapter.myClickListener = myClickListener;
    }

    public ReviewsAdapter(Context ctx, ArrayList<Reviews> myDataset) {
        ReviewsAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_items_layout, parent, false);

        Log.d("mubi","here inside 1");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        Log.d("mubi","here inside 2");

        holder.name.setText(current_item.getName());
        holder.date.setText(current_item.getReview_date());
        holder.desc.setText(current_item.getReview_description());

    }

    public void addItem(Reviews dataObj, int index) {
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