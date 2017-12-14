package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rana_ on 12/14/2017.
 */

public class PaymentProceedActivity extends AppCompatActivity {

    ProgressDialog pDialog=null;
    HostelsData hostelsData;
    int total_amount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_credit_card);

        hostelsData=(HostelsData) getIntent().getBundleExtra("bndle").getSerializable("hostel_object");
        total_amount=Integer.parseInt(getIntent().getExtras().getString("amount"));



        final Button pay=(Button) findViewById(R.id.pay_btn);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Payment Successfully Paid !",Toast.LENGTH_LONG).show();


                String url = MainActivity.API+"insertBooking";
                if(haveNetworkConnection()) {
                    pDialog=new ProgressDialog(PaymentProceedActivity.this);
                    pDialog.setMessage("Please Wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String res) {
                                    try {

                                        JSONObject response = new JSONObject(res.toString());
                                        Log.d("mubi", response.toString());
                                        boolean error = response.getBoolean("Error");

                                        Log.d("mubi", error + "aa");
                                        if (!error) {
                                            pDialog.hide();

                                            Toast.makeText(getApplicationContext(),response.getString("Message"),Toast.LENGTH_LONG).show();
                                            SavedSharedPreferences.setCurrentHostelId(getApplicationContext(),hostelsData.getId());
                                            Intent a=new Intent(PaymentProceedActivity.this,MainActivity.class);
                                            startActivity(a);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error While Booking !", Toast.LENGTH_SHORT).show();
                                            pDialog.hide();
                                        }

                                    } catch (Exception ex) {

                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("mubi", "Error: " + error.getMessage());
                            // hide the progress dialog
                            pDialog.hide();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("payment_status", "done");
                            params.put("payment_amount", total_amount+"");
                            params.put("hostel_id",hostelsData.getId()+"");
                            params.put("user_id",SavedSharedPreferences.getUserId(getApplicationContext())+"");

                            return params;
                        }
                    };


// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "login");
                }


            }
        });
    }
    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
