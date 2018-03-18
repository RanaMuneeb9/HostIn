package com.munib.hostin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.PaymentsAdapter;
import com.munib.hostin.Adapters.PaymentsHistoryAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.PaymentsData;
import com.munib.hostin.DataModel.SavedHostelsData;
import com.munib.hostin.volley.AppController;

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

    RecyclerView unpaid_rv,history_rv;
    RecyclerView.LayoutManager layoutManager,history_layoutManager;
    ArrayList<PaymentsData> unpaid_payments=new ArrayList<>();
    ArrayList<PaymentsData> payments_history=new ArrayList<>();
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
        View v = inflater.inflate(R.layout.fragment_my_hostels, container, false);

        LinearLayout current_hostel_layout=(LinearLayout) v.findViewById(R.id.current_hostel_layout);
        TextView no_current_hostel=(TextView) v.findViewById(R.id.no_current_hostel);
        TextView name=(TextView) v.findViewById(R.id.hostel_name);
        Button mess=(Button) v.findViewById(R.id.mess_schedule);
        Button rate=(Button) v.findViewById(R.id.rate);
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
            mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.fragment1, new FragmentMess()).setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out).addToBackStack("frag").commit();


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
}
