package com.munib.hostin;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.DataModel.Facilities;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.Reviews;
import com.munib.hostin.DataModel.RoomTypes;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Main_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Main_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Main_fragment extends Fragment implements Filters.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog pDialog=null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] f_name,d_name,price;
    int[] img_res = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,
            R.drawable.pic6,R.drawable.pic7,R.drawable.pic3,R.drawable.pic2,R.drawable.pic1,R.drawable.pic4};

    ArrayList<HostelsData> hostels_arrayList = new ArrayList<HostelsData>();

    public Main_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Main_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Main_fragment newInstance(String param1, String param2) {
        Main_fragment fragment = new Main_fragment();
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

        getHostels();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);


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

        hostels_arrayList.clear();


        recyclerView= (RecyclerView) v.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);



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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    void getHostels()
    {
        if(haveNetworkConnection()) {
            String url = MainActivity.API+"get_hostels";

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

            StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {
                            try {

                                JSONObject response = new JSONObject(res.toString());
                                Log.d("mubi-res",response.toString());
                                boolean error = response.getBoolean("Error");

                                Log.d("mubi-res",error+"aa");
                                if(!error)
                                {
                                    JSONArray row_str=response.getJSONArray("Message");

                                    Log.d("mubi-res",row_str.length()+"");
                                    for(int i=0;i<row_str.length();i++)
                                    {
                                        Log.d("mubi-res","0");
                                        JSONObject obj=row_str.getJSONObject(i);
                                        Log.d("mubi-res",obj.toString());
                                        int hostel_id=obj.getInt("hostel_id");
                                        String name=obj.getString("hostel_name");
                                        Log.d("mubi-res",name);
                                        double lat=obj.getDouble("hostel_lat");
                                        Log.d("mubi-res",lat+"");
                                        double lang=obj.getDouble("hostel_lang");
                                        Log.d("mubi-res",lang+"");
                                        String about=obj.getString("hostel_about");
                                        String email=obj.getString("hostel_email");
                                        String phone=obj.getString("hostel_phone");
                                        String mobile=obj.getString("hostel_mobile");
                                        String type=obj.getString("hostel_type");

                                        Log.d("mubi-res","1");
                                        ArrayList<Facilities> arrayList_facilites=new ArrayList<>();

                                        JSONArray facilities=obj.getJSONArray("facilities");
                                        for(int j=0;j<facilities.length();j++)
                                        {
                                            JSONObject f_obj=facilities.getJSONObject(j);
                                            int f_id=f_obj.getInt("facility_id");
                                            String f_name=f_obj.getString("facility_name");
                                            String f_desc=f_obj.getString("facility_description");

                                            Log.d("mubi-ff1",f_name);
                                            arrayList_facilites.add(new Facilities(f_id,f_name,f_desc));

                                        }

                                        Log.d("mubi-res","2");

                                        ArrayList<Reviews> arrayList_reviews=new ArrayList<>();

                                        JSONArray reviews=obj.getJSONArray("reviews");
                                        Log.d("mubi-res","2.1");
                                        for(int j=0;j<reviews.length();j++)
                                        {
                                            Log.d("mubi-res","2.2");
                                            JSONObject r_obj=reviews.getJSONObject(j);
                                            int r_id=r_obj.getInt("review_id");
                                            Log.d("mubi-res","2.3");
                                            double r_food=(double)r_obj.getDouble("review_food");
                                            Log.d("mubi-res","2.3.1");
                                            double r_atmospher=r_obj.getDouble("review_atmosphere");
                                            Log.d("mubi-res","2.3.1");
                                            double r_cleanliness=r_obj.getDouble("review_cleanliness");
                                            double r_facilities=r_obj.getDouble("review_facilities");
                                            Log.d("mubi-res","2.3.2");
                                            double r_security=r_obj.getDouble("review_security");
                                            Log.d("mubi-res","2.4");
                                            double r_value=r_obj.getDouble("review_value");
                                            double r_overall=r_obj.getDouble("review_overall");
                                            String r_desc=r_obj.getString("review_description");
                                            String r_date=r_obj.getString("review_date");
                                            int user_id=r_obj.getInt("Users_user_id");
                                            String first_name=r_obj.getString("first_name");
                                            String last_name=r_obj.getString("last_name");

                                            Log.d("mubi-res","2.4");


                                            arrayList_reviews.add(new Reviews(r_id,r_food,r_atmospher,r_cleanliness,r_facilities,r_security,r_value,r_overall,r_desc,r_date,user_id,first_name,last_name));

                                        }

                                        Log.d("mubi-res","3");
                                        ArrayList<RoomTypes> arrayList_room_types=new ArrayList<>();

                                        JSONArray room_types=obj.getJSONArray("room_prices");
                                        for(int k=0;k<room_types.length();k++)
                                        {
                                            JSONObject r_obj=room_types.getJSONObject(k);
                                            int r_id=r_obj.getInt("Room_types_room_type_id");
                                            int price=r_obj.getInt("base_price");
                                            int price_with_mess=r_obj.getInt("price_with_mess");


                                            arrayList_room_types.add(new RoomTypes(r_id,r_id,price,price_with_mess));
                                        }
                                        Log.d("mubi-room_types",arrayList_room_types.size()+"");

                                        hostels_arrayList.add(new HostelsData(hostel_id,name,lat,lang,about,email,phone,mobile,type,arrayList_facilites,arrayList_reviews,arrayList_room_types));
                                    }

                                    adapter = new MainAdapter(getActivity(),hostels_arrayList);
                                    recyclerView.setAdapter(adapter);
                                    pDialog.hide();
                                }else{
                                    Toast.makeText(getActivity(), "Error Getting Data !",Toast.LENGTH_SHORT).show();
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
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strRequest, "get_hostels");


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
