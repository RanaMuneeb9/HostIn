package com.munib.hostin;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jaygoo.widget.RangeSeekBar;
import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.DataModel.HostelsData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Filters.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Filters#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Filters extends Fragment {

    Button proceed;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static RangeSeekBar price_seekbar,rating_seekbar;

    public static boolean rating_filter=false,price_filter=false,type_filter=false,seater_filter=false;

    public static int price_min=6000,price_max=15000,rating_min=0,_rating_max=5;

    public Filters() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Filters.
     */
    // TODO: Rename and change types and number of parameters
    public static Filters newInstance(String param1, String param2) {
        Filters fragment = new Filters();
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
        View v = inflater.inflate(R.layout.fragment_filters, container, false);


        price_seekbar=(RangeSeekBar) v.findViewById(R.id.seekbar1);
        rating_seekbar=(RangeSeekBar) v.findViewById(R.id.rating_seekbar);

        if(SavedSharedPreferences.getRating(getContext())==0)
        {
            rating_seekbar.setValue(0);
        }else{
            rating_seekbar.setValue(SavedSharedPreferences.getRating(getContext()));
        }

        int min,max;
        if(SavedSharedPreferences.getMinPrice(getContext()).equals("0.0"))
        {
            min=6000;
        }else{
            min=Integer.parseInt(SavedSharedPreferences.getMinPrice(getContext()));
        }

        if(SavedSharedPreferences.getMaxPrice(getContext()).equals("0.0"))
        {
            max=15000;
        }else
        {
            max=Integer.parseInt(SavedSharedPreferences.getMaxPrice(getContext()));
        }

        price_seekbar.setValue(min,max);
        price_seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                price_min=(int)min;
                price_max=(int)max;
                if(price_min==6000 && price_max==15000)
                {

                    price_filter=false;
                }
                SavedSharedPreferences.setMinPrice(getActivity(),price_min+"");
                SavedSharedPreferences.setMaxPrice(getActivity(),price_max+"");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });



        rating_seekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                rating_min=(int)min;

                if(rating_min==0)
                {
                    rating_filter=false;
                }
                SavedSharedPreferences.setRating(getActivity(),rating_min);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Main_fragment.floatingActionButton.setImageResource(R.drawable.filter_icon);
                    Main_fragment.filter_menu=false;
                    return false;
                }
                return false;
            }
        } );

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
        getFragmentManager().popBackStack();
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
