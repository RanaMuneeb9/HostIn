package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.munib.hostin.Adapters.FacilitiesAdapter;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.PaymentsAdapter;
import com.munib.hostin.Adapters.ReviewsAdapter;
import com.munib.hostin.Adapters.RoomTypesAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.DataModel.Reviews;
import com.munib.hostin.DataModel.SavedHostelsData;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HostelProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HostelProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostelProfile extends Fragment implements Filters.OnFragmentInteractionListener,OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GoogleMap mMap;
    RecyclerView rv_room_types;
    TextView location,name,about;
    boolean saved;
    Button book_btn,save_btn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv_reviews;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Reviews> reviews_list=new ArrayList<>();

    HostelsData hostel_data;

    private OnFragmentInteractionListener mListener;

    public HostelProfile() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HostelProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static HostelProfile newInstance(String param1, String param2) {
        HostelProfile fragment = new HostelProfile();
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
        View v = inflater.inflate(R.layout.hostel_profile_fragment, container, false);

        hostel_data = (HostelsData) getArguments().getSerializable("hostel_object");


        save_btn=(Button) v.findViewById(R.id.save_btn);
        for (SavedHostelsData data:Main_fragment.saved_hostels)
        {
            if(data.getHostel_id()==hostel_data.getId())
            {
                saved=true;
                save_btn.setBackground(getResources().getDrawable(R.drawable.save_icon_done));
            }
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


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);


                if(!saved)
                {
                    if(haveNetworkConnection()) {
                        pDialog.show();
                        String url = MainActivity.API+"SaveHostel";

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

                                                saved=true;
                                                save_btn.setBackground(getResources().getDrawable(R.drawable.save_icon_done));
                                                pDialog.hide();

                                            }else{
                                                Toast.makeText(getActivity(), "Wrong Password/Email !",Toast.LENGTH_SHORT).show();
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
                                params.put("hostel_id", hostel_data.getId()+"");
                                params.put("user_id", SavedSharedPreferences.getUserId(getActivity())+"");

                                return params;
                            }
                        };

// Adding request to request queue
                        AppController.getInstance().addToRequestQueue(strRequest, "save_hostel");


                    }

                }else{

                    pDialog.show();
                    String url = MainActivity.API+"UnsaveHostel";

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
                                            saved=false;
                                            save_btn.setBackground(getResources().getDrawable(R.drawable.save_icon));
                                            pDialog.hide();

                                        }else{
                                            Toast.makeText(getActivity(), "Wrong Password/Email !",Toast.LENGTH_SHORT).show();
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
                            params.put("hostel_id", hostel_data.getId()+"");
                            params.put("user_id", SavedSharedPreferences.getUserId(getActivity())+"");

                            return params;
                        }
                    };

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strRequest, "login");


                }

            }
        });

        location=(TextView) v.findViewById(R.id.location);
        name=(TextView) v.findViewById(R.id.hostel_name);
        about=(TextView) v.findViewById(R.id.about);

        name.setText(hostel_data.getName());
        about.setText(hostel_data.getAbout());



        BannerSlider bannerSlider = (BannerSlider) v.findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.pic1));
        banners.add(new DrawableBanner(R.drawable.pic2));
        banners.add(new DrawableBanner(R.drawable.pic3));
        banners.add(new DrawableBanner(R.drawable.pic4));
        banners.add(new DrawableBanner(R.drawable.pic5));
        bannerSlider.setBanners(banners);


        //Location City

        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(hostel_data.getLatitude(), hostel_data.getLongitude(), 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.


            location.setText(fnialAddress); //This will display the final address.

        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }


        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        rv_room_types=(RecyclerView) v.findViewById(R.id.rv_room_types);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_room_types.setLayoutManager(manager);
        Log.d("mubi-l",hostel_data.getRoomTypes().size()+"");
        RoomTypesAdapter roomTypesAdapter=new RoomTypesAdapter(getActivity(),hostel_data.getRoomTypes());
        rv_room_types.setAdapter(roomTypesAdapter);



        //Reviews Calculations

        double food=0,atmosphere=0,cleanliness=0,facilities=0,security=0,value=0;
        for(int i=0;i<hostel_data.getReviews().size();i++)
        {
            food+=hostel_data.getReviews().get(i).getReview_food();
            atmosphere+=hostel_data.getReviews().get(i).getReview_atmosphere();
            cleanliness+=hostel_data.getReviews().get(i).getReview_cleanliness();
            facilities+=hostel_data.getReviews().get(i).getReview_facilities();
            security+=hostel_data.getReviews().get(i).getReview_security();
            value+=hostel_data.getReviews().get(i).getReview_value();
        }

        SimpleRatingBar food_rating=(SimpleRatingBar) v.findViewById(R.id.food_rating);
        SimpleRatingBar atmosphere_rating=(SimpleRatingBar) v.findViewById(R.id.atmosphere_rating);
        SimpleRatingBar cleanliness_rating=(SimpleRatingBar) v.findViewById(R.id.cleanliness_rating);
        SimpleRatingBar facilities_rating=(SimpleRatingBar) v.findViewById(R.id.facilities_rating);
        SimpleRatingBar security_rating=(SimpleRatingBar) v.findViewById(R.id.security_rating);
        SimpleRatingBar value_rating=(SimpleRatingBar) v.findViewById(R.id.value_rating);
        TextView overall_rating=(TextView) v.findViewById(R.id.overall_rating);
        TextView total_reviews=(TextView) v.findViewById(R.id.total_reviews);

        overall_rating.setText(hostel_data.getAverageReview()+"");
        food_rating.setRating((int)food);
        atmosphere_rating.setRating((int)atmosphere);
        cleanliness_rating.setRating((int)cleanliness);
        facilities_rating.setRating((int)facilities);
        security_rating.setRating((int)security);
        value_rating.setRating((int)value);
        total_reviews.setText(hostel_data.getReviews().size()+" Reviews");
        TextView more_reviews=(TextView) v.findViewById(R.id.view_more_reviews);

//        if(hostel_data.getReviews().size()<=2)
//            more_reviews.setVisibility(View.GONE);




        rv_reviews = (RecyclerView)v.findViewById(R.id.rv_reviews);

        for(int i=0;i<hostel_data.getReviews().size();i++)
        reviews_list.add(hostel_data.getReviews().get(i));

        more_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MoreReviewsActivity.class);
                intent.putExtra("reviews",reviews_list);
               startActivity(intent);

            }
        });

        ReviewsAdapter adapter = new ReviewsAdapter(getActivity(),reviews_list);

        rv_reviews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        rv_reviews.setAdapter(adapter);
        rv_reviews.setNestedScrollingEnabled(false);
        rv_reviews.setLayoutManager(layoutManager);



        for(int i=0;i<hostel_data.getFacilities().size();i++)
            Log.d("mubi-f",hostel_data.getFacilities().get(i).getName());

        RecyclerView rv_facilities = (RecyclerView)v.findViewById(R.id.rv_facilities);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        rv_facilities.setLayoutManager(mLayoutManager);
        FacilitiesAdapter adapter1=new FacilitiesAdapter(getActivity(),hostel_data.getFacilities());
        rv_facilities.setAdapter(adapter1);


        book_btn=(Button)v.findViewById(R.id.book_btn);
        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a=new Intent(getActivity(),BookingActivity.class);
                Bundle bndle=new Bundle();
                bndle.putSerializable("hostel_object",hostel_data);
                a.putExtra("bndle",bndle);
                startActivity(a);
            }
        });



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
        getFragmentManager().popBackStack();

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(hostel_data.getLatitude(),hostel_data.getLongitude());

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(sydney);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);


        mMap.addMarker(new MarkerOptions().position(sydney).title(hostel_data.getName()));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
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


