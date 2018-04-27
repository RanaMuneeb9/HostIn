package com.munib.hostin.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.HostelProfile;
import com.munib.hostin.MainActivity;
import com.munib.hostin.Main_fragment;
import com.munib.hostin.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


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


        TextView amount,name,desc,date,status,hostel;
        MaterialRippleLayout materialRippleLayout;



        public DataObjectHolder(View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.payment_name);
            desc=(TextView)  itemView.findViewById(R.id.payment_desc);
            status=(TextView) itemView.findViewById(R.id.payment_status);
            amount=(TextView) itemView.findViewById(R.id.payment_amount);
            hostel=(TextView) itemView.findViewById(R.id.hostel_name);
            materialRippleLayout=(MaterialRippleLayout) itemView.findViewById(R.id.ripple);

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
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        current_item= mDataset.get(position);
        Log.d("mubi","here inside 2");

        holder.name.setText(mDataset.get(position).getName());
        holder.amount.setText("PKR "+mDataset.get(position).getAmount()+" /-");
        holder.desc.setText(mDataset.get(position).getDesc());
        if(mDataset.get(position).getStatus().equals("done"))
        holder.status.setText("Paid");

        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(ctx);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.payment_detail_dialog);

                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ((TextView) dialog.findViewById(R.id.payment_name)).setText(mDataset.get(position).getName());
                ((TextView) dialog.findViewById(R.id.payment_amount)).setText(mDataset.get(position).getAmount()+"");
                ((TextView) dialog.findViewById(R.id.payment_description)).setText(mDataset.get(position).getDesc());
                ((TextView) dialog.findViewById(R.id.payment_hostel)).setText(mDataset.get(position).getHostel_name());
                ((TextView) dialog.findViewById(R.id.payment_status)).setText(mDataset.get(position).getStatus());
                ((TextView) dialog.findViewById(R.id.created_date)).setText(mDataset.get(position).getCreated_date());
                ((TextView) dialog.findViewById(R.id.paid_date)).setText(mDataset.get(position).getPaid_date());

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_yes);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
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
    public static Date fromISO8601UTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}