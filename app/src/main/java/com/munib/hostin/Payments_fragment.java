package com.munib.hostin;

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
import android.widget.TextView;

import com.munib.hostin.Adapters.MainAdapter;
import com.munib.hostin.Adapters.PaymentsAdapter;
import com.munib.hostin.Adapters.PaymentsHistoryAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.PaymentsData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Payments_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Payments_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Payments_fragment extends Fragment {

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


        TextView unpaid_msg=(TextView) v.findViewById(R.id.unpaid_rv_txtmsg);
        TextView history_msg=(TextView) v.findViewById(R.id.history_rv_txtmsg);


        unpaid_rv = (RecyclerView)v.findViewById(R.id.unpaid_rv);
        history_rv=(RecyclerView) v.findViewById(R.id.payment_history_rv);




        unpaid_payments.add(new PaymentsData());
        unpaid_payments.add(new PaymentsData());

        PaymentsAdapter adapter = new PaymentsAdapter(getActivity(),unpaid_payments);

        unpaid_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        unpaid_rv.setAdapter(adapter);
        unpaid_rv.setNestedScrollingEnabled(false);
        unpaid_rv.setLayoutManager(layoutManager);



        payments_history.add(new PaymentsData());
        payments_history.add(new PaymentsData());
        payments_history.add(new PaymentsData());
        payments_history.add(new PaymentsData());
        payments_history.add(new PaymentsData());

        PaymentsHistoryAdapter history_adapter = new PaymentsHistoryAdapter(getActivity(),payments_history);
        history_layoutManager = new LinearLayoutManager(this.getActivity());
        history_rv.setLayoutManager(history_layoutManager);
        history_rv.setAdapter(history_adapter);
        history_rv.setNestedScrollingEnabled(false);

        if(payments_history.size()>0)
            history_msg.setVisibility(View.GONE);
        else
            history_rv.setVisibility(View.GONE);
        if(unpaid_payments.size()>0)
            unpaid_msg.setVisibility(View.GONE);
        else
            unpaid_rv.setVisibility(View.GONE);

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
}
