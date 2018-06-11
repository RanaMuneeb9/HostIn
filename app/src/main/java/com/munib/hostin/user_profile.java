package com.munib.hostin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.jaygoo.widget.RangeSeekBar;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.munib.hostin.volley.AppController;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button add_payment,add_coupon,share,feedback,settings;

    RangeSeekBar radius_seekbar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    CircleImageView profile_image;

    public user_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static user_profile newInstance(String param1, String param2) {
        user_profile fragment = new user_profile();
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
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);


        profile_image=(CircleImageView) v.findViewById(R.id.profile_image);

        if(SavedSharedPreferences.getUserImage(getApplicationContext()).equals("null"))
        {

        }else{
            Picasso.get().load(MainActivity.API+"userImage/"+SavedSharedPreferences.getUserImage(getApplicationContext())).into(profile_image);
        }
        TextView name=(TextView) v.findViewById(R.id.person_name);
        TextView location=(TextView) v.findViewById(R.id.person_location);

        ImageButton profile_btn=(ImageButton) v.findViewById(R.id.profile_img_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });

        name.setText(SavedSharedPreferences.getUserName(getActivity()));

        radius_seekbar=(RangeSeekBar) v.findViewById(R.id.radius_seekbar);
        radius_seekbar.setValue(SavedSharedPreferences.getCustomRadius(getActivity()));
        radius_seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {

                SavedSharedPreferences.setCustomRadius(getActivity(),(int) min);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        LinearLayout change_password=(LinearLayout) v.findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View deleteDialogView = factory.inflate(R.layout.fragment_change_pass_user, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(getActivity()).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic

                        if(TextUtils.isEmpty( ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_pass)).getEditText().getText().toString())) {

                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_pass)).setError("Input required");
                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_pass)).setErrorEnabled(true);

                        }else if(TextUtils.isEmpty( ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).getEditText().getText().toString())) {

                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).setError("Input required");
                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).setErrorEnabled(true);

                        }else if(!((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).getEditText().getText().toString().equals(((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).getEditText().getText().toString()))
                        {
                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).setError("Password doesn't match!");
                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_repass)).setErrorEnabled(true);

                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_pass)).setError("Password doesn't match!");
                            ((TextInputLayout) deleteDialogView.findViewById(R.id.input_layout_pass)).setErrorEnabled(true);
                        }else{


                                String url = MainActivity.API + "userChangePassword";

                                final ProgressDialog pDialog = new ProgressDialog(getActivity());
                                pDialog.setMessage("Loading...");
                                pDialog.setCancelable(false);
                                pDialog.show();

                                StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                                        new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String res) {
                                                try {

                                                    JSONObject response = new JSONObject(res.toString());
                                                    Log.d("mubi", response.toString());
                                                    boolean error = response.getBoolean("Error");

                                                    Log.d("mubi", error + "aa");
                                                    if (!error) {
                                                        pDialog.hide();

                                                        Toast.makeText(getActivity(), "Password Changed Successfully!", Toast.LENGTH_SHORT).show();

                                                        deleteDialog.dismiss();
                                                    } else {
                                                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                                        pDialog.hide();
                                                    }

                                                } catch (Exception ex) {

                                                }
                                            }
                                        }, new com.android.volley.Response.ErrorListener() {

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
                                        params.put("password",((TextInputLayout)deleteDialogView.findViewById(R.id.input_layout_pass)).getEditText().getText().toString());

                                        return params;
                                    }
                                };

// Adding request to request queue
                                AppController.getInstance().addToRequestQueue(strRequest, "login");




                        }
                    }
                });
                deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();

            }
        });
        if(!SavedSharedPreferences.getUserLat(getActivity()).equals("0.0")) {
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(Float.parseFloat(SavedSharedPreferences.getUserLat(getActivity())), Float.parseFloat(SavedSharedPreferences.getUserLang(getActivity())), 1);
                int maxLines = address.get(0).getMaxAddressLineIndex();
                for (int i = 0; i < maxLines; i++) {
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



        if(savedInstanceState == null){
            final FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    fab.setVisibility(View.INVISIBLE);
                    Fragment filter=new edit_user_profile();
                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out);
                    transaction.replace(R.id.fragment,filter).addToBackStack(null).commit();


                }
            });
        }

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
     * This interface must be implemented by activities that contain getActivity()
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            Log.d("image-selected",images.get(0).path);

            File f = new File(images.get(0).path);

            Ion.with(getActivity())
                    .load(MainActivity.API+"uploadUserImage")
                    .setMultipartFile("image", f)
                    .setMultipartParameter("user_id",SavedSharedPreferences.getUserId(getApplicationContext())+"")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            try {
                                Log.d("mubi",result.getResult());
                                JSONObject obj=new JSONObject(result.getResult());
                                if(!obj.getBoolean("Error"))
                                {

                                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                                    SavedSharedPreferences.setUserImage(getApplicationContext(),obj.getString("fileName"));

                                    Log.d("mubi",MainActivity.API+"userImage/"+SavedSharedPreferences.getUserImage(getApplicationContext()));
                                    Picasso.get().load(MainActivity.API+"userImage/"+SavedSharedPreferences.getUserImage(getApplicationContext())).into(profile_image);
                                }

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    });


        }
    }

}
