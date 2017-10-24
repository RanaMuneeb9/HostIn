package com.munib.hostin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MuhammadMusa on 10/24/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Dataprovider> arrayList=new ArrayList<Dataprovider>();

    public RecyclerAdapter(ArrayList<Dataprovider> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Dataprovider dataprovider=  arrayList.get(position);
        holder.imageView.setImageResource(dataprovider.getImg_res());
        holder.f_name.setText(dataprovider.getF_name());
        holder.d_name.setText(dataprovider.getD_name());
        holder.price.setText(dataprovider.getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView f_name,d_name,price;
        public RecyclerViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            f_name = (TextView)view.findViewById(R.id.fname);
            d_name = (TextView)view.findViewById(R.id.dname);
            price = (TextView)view.findViewById(R.id.price);
        }
    }
}
