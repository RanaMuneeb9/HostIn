package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button login_btn;
    TextView signup;
    ProgressDialog pDialog=null;

    EditText email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);

        Button login_btn =(Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()) {
                    String url = "http://192.168.8.104:3000/api/userLogin";

                    pDialog = new ProgressDialog(Login.this);
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

                                       Log.d("mubi",error+"aa");
                                        if(!error)
                                        {
                                            pDialog.hide();
                                            Intent intent =new Intent(Login.this,MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(Login.this, "Wrong Password/Email !",Toast.LENGTH_SHORT).show();
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
                            params.put("password", password.getText().toString());

                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "login");


                }else
                {
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
            }
        });

        final TextView signup = (TextView) findViewById(R.id.signup_text);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, SignUp.class);
                startActivity(intent);
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
