package com.munib.hostin;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.pagination.Pagination;
import com.munib.hostin.tableview.TableViewAdapter;
import com.munib.hostin.tableview.TableViewListener;
import com.munib.hostin.tableview.model.Cell;
import com.munib.hostin.tableview.model.ColumnHeader;
import com.munib.hostin.tableview.model.RowHeader;
import com.munib.hostin.volley.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMess.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMess extends Fragment implements Filters.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private AbstractTableAdapter mTableViewAdapter;
    ProgressDialog progressDialog;

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    // Constant values for icons
    public static final int SAD = 0;
    public static final int HAPPY = 1;
    public static final int BOY = 0;
    public static final int GIRL = 1;

    private OnFragmentInteractionListener mListener;

    public FragmentMess() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMess.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMess newInstance(String param1, String param2) {
        FragmentMess fragment = new FragmentMess();
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
       final  View v = inflater.inflate(R.layout.fragment_mess, container, false);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loaing...");




            progressDialog.show();
            String url = MainActivity.API+"getMess";

            StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {
                            try {

                                JSONObject response = new JSONObject(res.toString());
                                Log.d("mubi",response.toString());
                                boolean error = response.getBoolean("Error");
                                JSONArray array=response.getJSONArray("Schedule");

                                Log.d("mubi",error+"aa");
                                if(!error)
                                {

                                    TableLayout stk = (TableLayout) v.findViewById(R.id.table_main);
                                    TableRow tbrow0 = new TableRow(getActivity());
                                    TextView tv0 = new TextView(getActivity());
                                    tv0.setText(" Day ");
                                    tv0.setTextSize(18);
                                    tv0.setTypeface(null, Typeface.BOLD);
                                    tv0.setBackground(getActivity().getDrawable(R.drawable.cell_shape));
                                    tv0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv0.setPadding(0,40,0,40);
                                    tbrow0.addView(tv0);
                                    TextView tv1 = new TextView(getActivity());
                                    tv1.setText(" Breakfast ");
                                    tv1.setTextSize(18);
                                    tv1.setTypeface(null, Typeface.BOLD);
                                    tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv1.setBackground(getActivity().getDrawable(R.drawable.cell_shape));
                                    tv1.setPadding(0,40,0,40);
                                    tbrow0.addView(tv1);
                                    TextView tv2 = new TextView(getActivity());
                                    tv2.setText(" Lunch ");
                                    tv2.setTextSize(18);
                                    tv2.setTypeface(null, Typeface.BOLD);
                                    tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv2.setBackground(getActivity().getDrawable(R.drawable.cell_shape));
                                    tv2.setPadding(0,40,0,40);
                                    tbrow0.addView(tv2);
                                    TextView tv3 = new TextView(getActivity());
                                    tv3.setText(" Dinner ");
                                    tv3.setTextSize(18);
                                    tv3.setTypeface(null, Typeface.BOLD);
                                    tv3.setBackground(getActivity().getDrawable(R.drawable.cell_shape));
                                    tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv3.setPadding(0,40,0,40);
                                    tbrow0.addView(tv3);
                                    stk.addView(tbrow0);

                                    for (int i = 0; i < array.length(); i++) {
                                        TableRow tbrow = new TableRow(getActivity());
                                        TextView t1v = new TextView(getActivity());
                                        t1v.setText("  "+array.getJSONObject(i).getString("day")+"  ");
                                        t1v.setTextSize(18);
                                        t1v.setBackground(getActivity().getDrawable(R.drawable.cell_shape));
                                        t1v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        t1v.setTypeface(null, Typeface.BOLD);
                                        t1v.setPadding(0,40,0,40);
                                        tbrow.addView(t1v);
                                        TextView t2v = new TextView(getActivity());
                                        t2v.setText("  "+array.getJSONObject(i).getString("breakfast")+"  ");
                                        t2v.setTextSize(18);
                                        t2v.setBackground(getActivity().getDrawable(R.drawable.cell_shape1));
                                        t2v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        t2v.setPadding(0,40,0,40);
                                        t2v.setTextColor(Color.WHITE);
                                        tbrow.addView(t2v);
                                        TextView t3v = new TextView(getActivity());
                                        t3v.setText("  "+array.getJSONObject(i).getString("lunch")+"  ");
                                        t3v.setTextSize(18);
                                        t3v.setBackground(getActivity().getDrawable(R.drawable.cell_shape1));
                                        t3v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        t3v.setPadding(0,40,0,40);
                                        t3v.setTextColor(Color.WHITE);
                                        tbrow.addView(t3v);
                                        TextView t4v = new TextView(getActivity());
                                        t4v.setText("  "+array.getJSONObject(i).getString("dinner")+"  ");
                                        t4v.setTextSize(18);
                                        t4v.setBackground(getActivity().getDrawable(R.drawable.cell_shape1));
                                        t4v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        t4v.setPadding(0,40,0,40);
                                        t4v.setTextColor(Color.WHITE);
                                        tbrow.addView(t4v);
                                        stk.addView(tbrow);
                                    }

                                    progressDialog.hide();
                                }else{
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

                    return params;
                }
            };

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strRequest, "mess");


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
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

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());

        // Set adapter
        mTableViewAdapter = new TableViewAdapter(getContext());
        tableView.setAdapter(mTableViewAdapter);

        // Disable shadow
        //tableView.getSelectionHandler().setShadowEnabled(false);

        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        tableView.setLayoutParams(tlp);

        // Set TableView listener
        tableView.setTableViewListener(new TableViewListener(tableView));
        return tableView;
    }


}


