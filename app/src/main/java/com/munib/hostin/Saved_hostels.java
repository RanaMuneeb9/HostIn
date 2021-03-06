package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.SavedHostelAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.SavedHostelsData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Saved_hostels.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Saved_hostels#newInstance} factory method to
 * create an instance of this fragment.Mubi don
 */
public class Saved_hostels extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView1;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] h_name,p_name,pay;
    int[] img_res1 = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,
            R.drawable.pic6,R.drawable.pic7,R.drawable.pic7,R.drawable.pic7,R.drawable.pic7,R.drawable.pic7};

    ArrayList<HostelsData> arrayList = new ArrayList<HostelsData>();

    public Saved_hostels() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Saved_hostels.
     */
    // TODO: Rename and change types and number of parameters
    public static Saved_hostels newInstance(String param1, String param2) {
        Saved_hostels fragment = new Saved_hostels();
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
        View v = inflater.inflate(R.layout.fragment_saved_hostels, container, false);


        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


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

        arrayList.clear();
        recyclerView1 = (RecyclerView)v.findViewById(R.id.recycler_view1);


        for(SavedHostelsData host1 : Main_fragment.saved_hostels) {
            for(HostelsData host2 : Main_fragment.hostels_arrayList) {
                if(host1.getHostel_id()==host2.getId())
                {
                    arrayList.add(host2);
                }
            }
        }

        adapter = new SavedHostelAdapter(getActivity(),arrayList);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);

        progressDialog.hide();

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
