package com.munib.hostin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.volley.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String provider;
    Button signup;
    ProgressDialog pDialog;
    EditText first_name,last_name,email,mobile,password,confirm_passowrd;
    RadioGroup radioGroup;
    String gender="Male";
    double lat,lang;
    GPSTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tracker = new GPSTracker(this);
        checkLocationPermission();
        first_name=(EditText) findViewById(R.id.input_first_name);
        last_name=(EditText) findViewById(R.id.input_last_name);
        email=(EditText) findViewById(R.id.input_email);
        mobile=(EditText) findViewById(R.id.input_mobile);
        password=(EditText) findViewById(R.id.input_password);
        confirm_passowrd=(EditText) findViewById(R.id.confirm_password);

        radioGroup=(RadioGroup) findViewById(R.id.RGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.male) {
                    gender="Male";
                }else{
                    gender="Female";
                }
            }
        });

        signup=(Button) findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (haveNetworkConnection()) {
                    String url = MainActivity.API+"add_user";

                    pDialog = new ProgressDialog(SignUp.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String res) {
                                    try {

                                        JSONObject response = new JSONObject(res.toString());
                                        Log.d("mubi",response.toString());
                                        boolean error = response.getBoolean("Error");
                                        String message = response.getString("Message");

                                        Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
                                        if (!error) {
                                            pDialog.hide();

                                            SavedSharedPreferences.setUserId(getApplicationContext(),response.getJSONObject("Row").getInt("insertId"));
                                            SavedSharedPreferences.setUserName(getApplicationContext(),first_name.getText().toString()+" "+last_name.getText().toString());
                                            SavedSharedPreferences.setUserEmail(getApplicationContext(),email.getText().toString());
                                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
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
                            params.put("first_name",first_name.getText().toString());
                            params.put("last_name", last_name.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("mobile_no", mobile.getText().toString());
                            params.put("password",confirm_passowrd.getText().toString());
                            params.put("user_gender",gender);
                            params.put("user_lat",lat+"");
                            params.put("user_lang",lang+"");

                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "signup");

                } else {

                    LinearLayout main_layout=(LinearLayout) findViewById(R.id.main_layout);
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "No Internet Connection !", Snackbar.LENGTH_LONG)
                            .setAction("Settings", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(Settings.ACTION_SETTINGS);
                                    startActivity(intent);
                                }
                            });

                    snackbar.show();

                }
            }});
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
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location")
                        .setMessage("Please Allow Location Request")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SignUp.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        if (!tracker.canGetLocation()) {
                            tracker.showSettingsAlert();
                        } else {
                            lat = tracker.getLatitude();
                            lang = tracker.getLongitude();
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                lat = tracker.getLatitude();
                lang = tracker.getLongitude();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                lat = tracker.getLatitude();
                lang = tracker.getLongitude();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lang = location.getLongitude();

        Log.i("Location info: Lat", lat+"");
        Log.i("Location info: Lng", lang+"");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
