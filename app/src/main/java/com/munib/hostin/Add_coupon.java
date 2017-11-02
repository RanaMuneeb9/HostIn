package com.munib.hostin;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Add_coupon extends DialogFragment implements View.OnClickListener{

    Button apply,cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_coupon, container, false);
        apply = (Button)v.findViewById(R.id.apply);
        cancel = (Button)v.findViewById(R.id.cancel);
        apply.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.apply)
        {
            this.dismiss();
        }
        else if(v.getId()==R.id.cancel)
        {
            this.dismiss();
        }
    }
}
