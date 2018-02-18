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
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.R;

import java.util.ArrayList;


public class PaymentsHistoryAdapter extends RecyclerView
        .Adapter<PaymentsHistoryAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "AdminQeueOrderViewAdapter";
    public static ArrayList<PaymentsData> mDataset;
    public  static Context ctx;
    private static MyClickListener myClickListener;
    static PaymentsData current_item;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {


        TextView amount,name,desc,date,status;

        public DataObjectHolder(View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.payment_name);
            desc=(TextView)  itemView.findViewById(R.id.payment_desc);
            date=(TextView) itemView.findViewById(R.id.payment_date);
            status=(TextView) itemView.findViewById(R.id.payment_status);
            amount=(TextView) itemView.findViewById(R.id.payment_amount);
        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        PaymentsHistoryAdapter.myClickListener = myClickListener;
    }

    public PaymentsHistoryAdapter(Context ctx, ArrayList<PaymentsData> myDataset) {
        PaymentsHistoryAdapter.ctx =ctx;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_items_layout, parent, false);

        Log.d("mubi","here inside 1");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        current_item= mDataset.get(position);
        Log.d("mubi","here inside 2");

        holder.name.setText(mDataset.get(position).getName());
        holder.amount.setText("PKR "+mDataset.get(position).getAmount()+" /-");
        holder.desc.setText(mDataset.get(position).getDesc());
        holder.date.setText(mDataset.get(position).getPaid_date());
    }

    public void addItem(PaymentsData dataObj, int index) {
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