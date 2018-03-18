package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.volley.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link edit_user_profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link edit_user_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edit_user_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button save_profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText first_name,last_name,phone;
    TextView gender,email;

    private OnFragmentInteractionListener mListener;

    public edit_user_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment edit_user_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static edit_user_profile newInstance(String param1, String param2) {
        edit_user_profile fragment = new edit_user_profile();
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
        View v = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);


        CircleImageView profile_image=(CircleImageView) v.findViewById(R.id.profile_image);

        if(SavedSharedPreferences.getUserImage(getApplicationContext()).equals("null"))
        {

        }else{
            Picasso.with(getApplicationContext()).load(MainActivity.API+"userImage/"+SavedSharedPreferences.getUserImage(getApplicationContext())).into(profile_image);
        }

        first_name=(EditText) v.findViewById(R.id.first_name);
        last_name=(EditText) v.findViewById(R.id.last_name);
        email=(TextView) v.findViewById(R.id.email);
        phone=(EditText) v.findViewById(R.id.phone);
        gender=(TextView) v.findViewById(R.id.gender);


        String arr[]=SavedSharedPreferences.getUserName(getActivity()).split(" ");

        first_name.setText(arr[0]);
        last_name.setText(arr[1]);

        email.setText(SavedSharedPreferences.getUserEmail(getActivity()));
        phone.setText(SavedSharedPreferences.getMobileNo(getActivity()));
        gender.setText(SavedSharedPreferences.getUserGender(getActivity()));


        save_profile=(Button)v.findViewById(R.id.save_profile);
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = MainActivity.API+"editUserProfile";

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

                                    Log.d("mubi",error+"aa");
                                    if(!error)
                                    {
                                        Toast.makeText(getActivity(), "Profile Updated Successfully !",Toast.LENGTH_SHORT).show();

                                        pDialog.hide();

                                        SavedSharedPreferences.setUserName(getActivity(),first_name.getText().toString()+" "+last_name.getText().toString());
                                        SavedSharedPreferences.setMobile(getActivity(),phone.getText().toString());

                                        getFragmentManager().popBackStack();

                                       ;
                                    }else{
                                        Toast.makeText(getActivity(), "Error Occurred While Saving !",Toast.LENGTH_SHORT).show();
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
                        params.put("firstName", first_name.getText().toString());
                        params.put("lastName", last_name.getText().toString());
                        params.put("phone", phone.getText().toString());

                        return params;
                    }
                };

// Adding request to request queue
                AppController.getInstance().addToRequestQueue(strRequest, "editUserProfile");

            }
        });

        return v;
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
}
