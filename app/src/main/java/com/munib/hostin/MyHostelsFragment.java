package com.munib.hostin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import com.munib.hostin.Adapters.HostelsHistoryAdapter;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.PaymentsAdapter;
import com.munib.hostin.Adapters.PaymentsHistoryAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.DataModel.SavedHostelsData;
import com.munib.hostin.volley.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyHostelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyHostelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyHostelsFragment extends Fragment {

    RecyclerView history_rv;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> hostels_history=new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HostelsData current_hostel;

    Button proceed,add_card;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyHostelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyHostelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyHostelsFragment newInstance(String param1, String param2) {
        MyHostelsFragment fragment = new MyHostelsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_my_hostels, container, false);

        LinearLayout current_hostel_layout=(LinearLayout) v.findViewById(R.id.current_hostel_layout);
        TextView no_current_hostel=(TextView) v.findViewById(R.id.no_current_hostel);
        TextView name=(TextView) v.findViewById(R.id.hostel_name);
        Button mess=(Button) v.findViewById(R.id.mess_schedule);
        Button rate=(Button) v.findViewById(R.id.rate);
        ImageView imageView=(ImageView) v.findViewById(R.id.img);
        final Button leave=(Button) v.findViewById(R.id.leave_hostel);
        final NestedScrollView scrollView=(NestedScrollView) v.findViewById(R.id.scroll);

        if(SavedSharedPreferences.getCurrentHostelId(getActivity())!=0)
        {
            current_hostel_layout.setVisibility(View.VISIBLE);


                for(HostelsData host2 : Main_fragment.hostels_arrayList) {
                    if(SavedSharedPreferences.getCurrentHostelId(getActivity())==host2.getId())
                    {
                        current_hostel=host2;
                    }
                }


            name.setText(current_hostel.getName());
            if(current_hostel.getImages().size()>0)
                Picasso.get().load("http://13.127.35.98/Api/images/"+current_hostel.getImages().get(0)).placeholder(R.drawable.ic_location_city_grey_700_24dp).into(imageView);


            mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.fragment1, new FragmentMess()).setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out).addToBackStack("frag").commit();


                    }
            });
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View va) {

                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Loaing...");
                    progressDialog.show();


                    String url = MainActivity.API+"checkRatingPresence";

                    StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String res) {
                                    try {
                                        JSONObject response = new JSONObject(res.toString());
                                        Log.d("mubi",response.toString());
                                        boolean error = response.getBoolean("Error");
                                        Log.d("mubi",error+"aa");
                                        if(error)
                                        {
                                            final Dialog dialog = new Dialog(getActivity());
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.hostel_review_fragment);

                                            Window window = dialog.getWindow();
                                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                            final SimpleRatingBar food_rating=(SimpleRatingBar) dialog.findViewById(R.id.food_rating);
                                            final SimpleRatingBar atmosphere_rating=(SimpleRatingBar) dialog.findViewById(R.id.atmosphere_rating);
                                            final SimpleRatingBar cleanliness_rating=(SimpleRatingBar) dialog.findViewById(R.id.cleanliness_rating);
                                            final SimpleRatingBar facilities_rating=(SimpleRatingBar) dialog.findViewById(R.id.facilities_rating);
                                            final SimpleRatingBar security_rating=(SimpleRatingBar) dialog.findViewById(R.id.security_rating);
                                            final SimpleRatingBar value_rating=(SimpleRatingBar) dialog.findViewById(R.id.value_rating);

                                            final EditText desc=(EditText) dialog.findViewById(R.id.desc);

                                            Button submit = (Button) dialog.findViewById(R.id.btn_submit);
                                            ImageButton dialogcanel = (ImageButton) dialog.findViewById(R.id.btn_cancel);
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                   final double overall=(food_rating.getRating()+atmosphere_rating.getRating()+cleanliness_rating.getRating()+facilities_rating.getRating()+security_rating.getRating()+value_rating.getRating())/6;

                                                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                                                    progressDialog.setCancelable(false);
                                                    progressDialog.setMessage("Loaing...");
                                                    progressDialog.show();


                                                    String url = MainActivity.API+"addRatingFromUser";

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
                                                                            Toast.makeText(getActivity(),"Rating/Review Added Successfully!",Toast.LENGTH_LONG).show();
                                                                            progressDialog.hide();
                                                                            dialog.dismiss();
                                                                        }else{
                                                                            Toast.makeText(getActivity(),"There was problem adding review, Try Again!",Toast.LENGTH_LONG).show();

                                                                            progressDialog.hide();

                                                                        }

                                                                    } catch (Exception ex) {

                                                                    }
                                                                }
                                                            }, new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.d("mubi", "Error: " + error.getMessage());
                                                            // hide the progress dialog
                                                            progressDialog.hide();
                                                        }
                                                    }) {
                                                        @Override
                                                        protected Map<String, String> getParams() {
                                                            Map<String, String> params = new HashMap<String, String>();
                                                            params.put("hostel_id", SavedSharedPreferences.getCurrentHostelId(getActivity())+"");
                                                            params.put("user_id", SavedSharedPreferences.getUserId(getActivity())+"");
                                                            params.put("food_rating", food_rating.getRating()+"");
                                                            params.put("atmosphere_rating", atmosphere_rating.getRating()+"");
                                                            params.put("cleanliness_rating", cleanliness_rating.getRating()+"");
                                                            params.put("facilities_rating", facilities_rating.getRating()+"");
                                                            params.put("value_rating", value_rating.getRating()+"");
                                                            params.put("security_rating", security_rating.getRating()+"");
                                                            params.put("overall_rating", +overall+"");
                                                            params.put("desc",desc.getText().toString());


                                                            return params;
                                                        }
                                                    };

// Adding request to request queue
                                                    AppController.getInstance().addToRequestQueue(strRequest, "add_rating");

                                                    dialog.dismiss();
                                                }
                                            });

                                            dialogcanel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    dialog.dismiss();

                                                }
                                            });
                                            dialog.show();

                                            progressDialog.hide();
                                        }else{
                                            Toast.makeText(getActivity(),"You have Already Added the Review!",Toast.LENGTH_LONG).show();
                                            progressDialog.hide();
                                        }

                                    } catch (Exception ex) {

                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("mubi", "Error: " + error.getMessage());
                            // hide the progress dialog
                            progressDialog.hide();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("hostel_id", SavedSharedPreferences.getCurrentHostelId(getActivity())+"");
                            params.put("user_id", SavedSharedPreferences.getUserId(getActivity())+"");
                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "check_rating");


                }
            });

        }else{
            no_current_hostel.setVisibility(View.VISIBLE);
        }

        Button drawe_bnt=(Button) v.findViewById(R.id.drawer_btn);
        drawe_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.mSlideState){
                    MainActivity.drawer.closeDrawer(Gravity.START);
                }else{
                    MainActivity.drawer.openDrawer(Gravity.START);
                }


            }
        });

        if(SavedSharedPreferences.getLeaveRequest(getActivity())==1)
        {
            leave.setEnabled(false);
            leave.setTextColor(android.R.color.secondary_text_dark);
        }
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (haveNetworkConnection()) {
                    String url = MainActivity.API + "requestLeave";

                    final ProgressDialog pDialog = new ProgressDialog(getActivity());
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

                                            Toast.makeText(getActivity(),"Request Submitted Successfully!",Toast.LENGTH_LONG).show();
                                            SavedSharedPreferences.setLeaveRequest(getActivity(),1);

                                            leave.setEnabled(false);
                                            leave.setTextColor(android.R.color.secondary_text_dark);

                                        } else {
                                            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
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
                            params.put("user_id",SavedSharedPreferences.getUserId(getActivity())+"");
                            params.put("hostel_id", current_hostel.getId()+"");

                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "login");


                }
            }
        });


        history_rv=(RecyclerView) v.findViewById(R.id.history_hostel_rv);
        history_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        history_rv.setNestedScrollingEnabled(false);
        history_rv.setLayoutManager(layoutManager);


        getHostelsData();


        return  v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void getHostelsData()
    {

        if(haveNetworkConnection()) {
            String url = MainActivity.API+"getHostelsHistory";


           final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                                String msg=response.getString("Message");
                                Log.d("mubi",error+"aa");
                                if(!error)
                                {

                                    JSONArray array=response.getJSONArray("History");

                                    for(int i=0;i<array.length();i++)
                                    {
                                        JSONObject object=array.getJSONObject(i);
                                        hostels_history.add(object.getString("hostel_name"));

                                    }

                                    HostelsHistoryAdapter adapter=new HostelsHistoryAdapter(getActivity(),hostels_history);
                                    history_rv.setAdapter(adapter);
                                    pDialog.hide();

                                }else{
                                    Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
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
                    params.put("user_id", SavedSharedPreferences.getUserId(getActivity())+"");

                    return params;
                }
            };

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strRequest, "hostel_history");


        }
    }
    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
