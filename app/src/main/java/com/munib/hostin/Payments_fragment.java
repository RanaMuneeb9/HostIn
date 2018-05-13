package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.NotificationsAdapter;
import com.munib.hostin.Adapters.PaymentsAdapter;
import com.munib.hostin.Adapters.PaymentsHistoryAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.NotificationsData;
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Payments_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Payments_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Payments_fragment extends Fragment {

    TextView history_msg,unpaid_msg;
    ProgressDialog pDialog=null;
    RecyclerView unpaid_rv,history_rv;
    RecyclerView.LayoutManager layoutManager,history_layoutManager;
    ArrayList<PaymentsData> unpaid_payments=new ArrayList<>();
    ArrayList<PaymentsData> payments_history=new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button proceed,add_card;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Payments_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Payments_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Payments_fragment newInstance(String param1, String param2) {
        Payments_fragment fragment = new Payments_fragment();
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
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

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


        unpaid_msg=(TextView) v.findViewById(R.id.unpaid_rv_txtmsg);
        history_msg=(TextView) v.findViewById(R.id.history_rv_txtmsg);


        unpaid_rv = (RecyclerView)v.findViewById(R.id.unpaid_rv);
        history_rv=(RecyclerView) v.findViewById(R.id.payment_history_rv);



        unpaid_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        unpaid_rv.setNestedScrollingEnabled(false);
        unpaid_rv.setLayoutManager(layoutManager);




        history_layoutManager = new LinearLayoutManager(this.getActivity());
        history_rv.setLayoutManager(history_layoutManager);
        history_rv.setNestedScrollingEnabled(false);


        if(SavedSharedPreferences.getCurrentHostelId(getActivity())!=0) {

            getPaymentsData();
        }
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

    void getPaymentsData()
    {

        if(haveNetworkConnection()) {
            String url = MainActivity.API+"userPayments";


            pDialog = new ProgressDialog(getActivity());
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
                                    pDialog.hide();

                                    Log.d("mubi","inside 00");
                                    JSONArray array=response.getJSONArray("Payments");

                                    for(int i=0;i<array.length();i++) {
                                        JSONObject obj = array.getJSONObject(i);

                                        Log.d("mubi",error+"inside 1");
                                        int id = obj.getInt("payment_id");
                                        String title = obj.getString("payment_name");
                                        Log.d("mubi",error+"inside 1.1");
                                        String desc = obj.getString("payment_details");
                                        int amount = obj.getInt("payment_amount");
                                        Log.d("mubi",error+"inside 1.2");
                                        String status = obj.getString("payment_status");
                                        String created_date = obj.getString("payment_created_date");
                                        Log.d("mubi",error+"inside 1.3");
                                        String paid_date = obj.getString("payment_paid_date");
                                        int hostel_id = obj.getInt("Hostels_hostel_id");
                                        String hostel_name = obj.getString("hostel_name");
                                        String hostel_email = obj.getString("hostel_email");
                                        int user_id = obj.getInt("Users_user_id");
                                        Log.d("mubi",hostel_id+"");
                                        if(status.equals("done"))
                                        payments_history.add(new PaymentsData(id,user_id,hostel_id,hostel_name,amount,title,desc,status,created_date,paid_date,hostel_email));
                                        else
                                        unpaid_payments.add(new PaymentsData(id,user_id,hostel_id,hostel_name,amount,title,desc,status,created_date,paid_date,hostel_email));


                                    }
                                    Log.d("mubi",error+"inside 3");

                                    if(payments_history.size()>0)
                                        history_msg.setVisibility(View.GONE);
                                    else
                                        history_rv.setVisibility(View.GONE);
                                    if(unpaid_payments.size()>0)
                                        unpaid_msg.setVisibility(View.GONE);
                                    else
                                        unpaid_rv.setVisibility(View.GONE);
                                    PaymentsHistoryAdapter history_adapter = new PaymentsHistoryAdapter(getActivity(),payments_history);
                                    history_rv.setAdapter(history_adapter);

                                    PaymentsAdapter adapter = new PaymentsAdapter(getActivity(),unpaid_payments);
                                    unpaid_rv.setAdapter(adapter);

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
            AppController.getInstance().addToRequestQueue(strRequest, "login");


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
