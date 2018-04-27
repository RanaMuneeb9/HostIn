package com.munib.hostin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.wallet.Payments;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rana_ on 12/14/2017.
 */

public class PaymentProceedActivity extends AppCompatActivity {

    ProgressDialog pDialog=null;
    int hostels_id;
    int total_amount;
    final Context myApp = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_credit_card);

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        hostels_id=Integer.parseInt(getIntent().getExtras().getString("hostel_id"));
        total_amount=Integer.parseInt(getIntent().getExtras().getString("amount"));

        Log.d("mubi-pay",hostels_id+" : "+total_amount+" : "+SavedSharedPreferences.getUserId(getApplication()));


        final WebView browser = (WebView)findViewById(R.id.webview);
/* JavaScript must be enabled if you want it to work, obviously */
        browser.getSettings().setJavaScriptEnabled(true);

/* Register a new JavaScript interface called HTMLOUT */
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

/* WebViewClient must be set BEFORE calling loadUrl! */
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                pDialog.hide();
        /* This call inject JavaScript into the page which just finished loading. */
                browser.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
                Log.i("Listener", "Start");

            }
        });

/* load a web page */
        browser.loadUrl(MainActivity.API+"paymentPage");



//        final Button pay=(Button) findViewById(R.id.pay_btn);
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(),"Payment Successfully Paid !",Toast.LENGTH_LONG).show();
//
//

//                }
//
//
//            }
//        });
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

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            Log.d("mubi-html",html);

            if(html.equals("<head><head></head><body>Successfully authorized the provided credit card</body></head>"))
            {

                String url = MainActivity.API+"insertBooking";
                if(haveNetworkConnection()) {
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

                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(PaymentProceedActivity.this);

                                            Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_LONG).show();
                                            SavedSharedPreferences.setCurrentHostelId(getApplicationContext(), hostels_id);
                                            Log.d("mubi", error + "bb");
                                        } else {
                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(PaymentProceedActivity.this);
                                            Toast.makeText(getApplicationContext(), "Error While Booking !", Toast.LENGTH_SHORT).show();
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
                            Calendar c = Calendar.getInstance();
                            params.put("payment_name", "Payment for "+c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) );
                            params.put("payment_details", "First Payment of users while booking form the app");
                            params.put("payment_amount", total_amount + "");
                            params.put("hostel_id", hostels_id + "");
                            params.put("user_id", SavedSharedPreferences.getUserId(getApplicationContext()) + "");

                            return params;
                        }
                    };


// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "login");

                }
            }else if(html.equals("<head><head></head><body>Payment Authorization Failed:  Please verify your Credit Card details are entered correctly and try again, or try another payment method.</body></head>")){

                final Dialog dialog = new Dialog(PaymentProceedActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_error);

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_yes);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
            // process the html as needed by the app
        }

        @JavascriptInterface
        public void performClick()
        {
            Log.d("LOGIN::", "Clicked");
            pDialog.show();
            Toast.makeText(getApplicationContext(), "Login clicked", Toast.LENGTH_LONG).show();
        }
    }
}
