package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {


    ProgressDialog pDialog=null;

    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_forgot_password);


            email=(EditText) findViewById(R.id.email);

            Button forgot_btn =(Button)findViewById(R.id.forgot_btn);
            forgot_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty( ((TextInputLayout) findViewById(R.id.input_layout_email)).getEditText().getText().toString())) {

                        ((TextInputLayout) findViewById(R.id.input_layout_email)).setError("Input required");
                        ((TextInputLayout) findViewById(R.id.input_layout_email)).setErrorEnabled(true);

                    }
                    else {
                        if (haveNetworkConnection()) {
                            String url = MainActivity.API + "userForgotPassword";

                            pDialog = new ProgressDialog(ForgotPassword.this);
                            pDialog.setMessage("Loading...");
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

                                                    Toast.makeText(getApplicationContext(),"Sent! Please find your password in your email!",Toast.LENGTH_LONG).show();

                                                    finish();

                                                } else {
                                                    Toast.makeText(ForgotPassword.this, "Email not found!", Toast.LENGTH_SHORT).show();
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
                                    params.put("email", email.getText().toString());
                                    return params;
                                }
                            };

// Adding request to request queue
                            AppController.getInstance().addToRequestQueue(strRequest, "login");


                        }
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
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
