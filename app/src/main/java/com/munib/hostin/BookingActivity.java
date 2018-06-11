package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.volley.AppController;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rana_ on 12/14/2017.
 */

public class BookingActivity extends AppCompatActivity {

    TextView name,price,place;
    CheckBox include_mess;
    RadioGroup radioGroup;
    Button proceed;
    HostelsData hostelsData;
    RadioButton rb_type_1,rb_type_2,rb_type_3;
    String mess_string="no";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_fragment_1);

        name=(TextView) findViewById(R.id.hostel_name);
        place=(TextView) findViewById(R.id.location11);
        price=(TextView) findViewById(R.id.total_price);
        include_mess=(CheckBox) findViewById(R.id.include_mess);
        proceed =(Button) findViewById(R.id.proceed_btn);
        radioGroup=(RadioGroup) findViewById(R.id.rb_group);
        rb_type_1=(RadioButton) findViewById(R.id.rb_type_1);
        rb_type_2=(RadioButton) findViewById(R.id.rb_type_2);
        rb_type_3=(RadioButton) findViewById(R.id.rb_type_3);


        hostelsData=(HostelsData) getIntent().getBundleExtra("bndle").getSerializable("hostel_object");

        Log.d("mubi",hostelsData.getName());

        name.setText(hostelsData.getName());

        //Location City

        Geocoder geoCoder = new Geocoder(BookingActivity.this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(hostelsData.getLatitude(), hostelsData.getLongitude(), 1);


            String fnialAddress = address.get(0).getAddressLine(0); //This is the complete address.


            place.setText(fnialAddress); //This will display the final address.

        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }


        for(int i=0;i<hostelsData.getRoomTypes().size();i++)
        {
            if(i==0)
            {
                rb_type_1.setText(hostelsData.getRoomTypes().get(i).getType_id()+ " Seater");
                rb_type_1.setVisibility(View.VISIBLE);
            }else if(i==1)
            {
                rb_type_2.setText(hostelsData.getRoomTypes().get(i).getType_id()+" Seaters");
                rb_type_2.setVisibility(View.VISIBLE);
            }else{
                rb_type_3.setText(hostelsData.getRoomTypes().get(i).getType_id()+ " Seaters");
                rb_type_3.setVisibility(View.VISIBLE);
            }

        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                if(include_mess.isChecked())
                {
                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }

                }else{

                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }

                }


            }
        });


        include_mess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);
                if(isChecked)
                {

                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }

                    mess_string="yes";
                }else{


                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }

                    mess_string="no";
                }

            }
        });

        price.setText("PKR "+hostelsData.getRoomTypes().get(0).getBase_price()+" /-");


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String amount[]=price.getText().toString().split(" ");

                String url = MainActivity.API+"insertBooking";
                if(haveNetworkConnection()) {

                    final ProgressDialog pDialog=new ProgressDialog(BookingActivity.this);
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


                                        if (!error) {

                                            Log.d("mubi", error + "aa");

                                            Toast.makeText(getApplicationContext(), "Booking has been made ! You will soon get confirmation Message !", Toast.LENGTH_LONG).show();
                                            SavedSharedPreferences.setBookingRequest(getApplicationContext(),1);

                                            Log.d("mubi-book",SavedSharedPreferences.getBookingRequest(getApplicationContext())+"");
                                       pDialog.hide();
                                       finish();
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

                            params.put("hostel_id", hostelsData.getId() + "");
                            params.put("user_id", SavedSharedPreferences.getUserId(getApplicationContext()) + "");
                            params.put("payment_status", "unpaid");
                            params.put("booking_status", "done");
                            Calendar c = Calendar.getInstance();
                            params.put("payment_name", "Payment for "+c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );
                            params.put("payment_details", "First Payment of users while booking form the app");
                            params.put("payment_amount", amount[1] + "");
                            params.put("hostel_mess",mess_string);

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
