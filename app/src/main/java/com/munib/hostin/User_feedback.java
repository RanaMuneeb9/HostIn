package com.munib.hostin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

public class User_feedback extends DialogFragment implements View.OnClickListener {

    Button submit;
    RatingBar mratingbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_feedback, container, false);
        mratingbar = (RatingBar)v.findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable)mratingbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#9932CC"), PorterDuff.Mode.SRC_ATOP);
        submit = (Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submit)
        {
            this.dismiss();
        }
    }
}