package com.munib.hostin;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.munib.hostin.DataModel.HostelsData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by rana_ on 12/14/2017.
 */

public class BookingActivity extends AppCompatActivity {

    TextView name,price,place;
    CheckBox include_mess;
    RadioGroup radioGroup;
    Button proceed;
    HostelsData hostelsData;
    RadioButton rb_type_1,rb_type_2,rb_type_3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_fragment_1);

        name=(TextView) findViewById(R.id.hostel_name);
        place=(TextView) findViewById(R.id.location11);
        price=(TextView) findViewById(R.id.total_price);
        include_mess=(CheckBox) findViewById(R.id.include_mess);
        proceed =(Button) findViewById(R.id.proceed_btn);
        radioGroup=(RadioGroup) findViewById(R.id.rb_group);
        rb_type_1=(RadioButton) findViewById(R.id.rb_type_1);
        rb_type_2=(RadioButton) findViewById(R.id.rb_type_2);
        rb_type_3=(RadioButton) findViewById(R.id.rb_type_3);


        hostelsData=(HostelsData) getIntent().getBundleExtra("bndle").getSerializable("hostel_object");

        Log.d("mubi",hostelsData.getName());

        name.setText(hostelsData.getName());

        //Location City

        Geocoder geoCoder = new Geocoder(BookingActivity.this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(hostelsData.getLatitude(), hostelsData.getLongitude(), 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.


            place.setText(fnialAddress); //This will display the final address.

        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }


        for(int i=0;i<hostelsData.getRoomTypes().size();i++)
        {
            if(i==0)
            {
                rb_type_1.setText(hostelsData.getRoomTypes().get(i).getType_id()+ " Seater");
                rb_type_1.setVisibility(View.VISIBLE);
            }else if(i==1)
            {
                rb_type_2.setText(hostelsData.getRoomTypes().get(i).getType_id()+" Seaters");
                rb_type_2.setVisibility(View.VISIBLE);
            }else{
                rb_type_3.setText(hostelsData.getRoomTypes().get(i).getType_id()+ " Seaters");
                rb_type_3.setVisibility(View.VISIBLE);
            }

        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                if(include_mess.isChecked())
                {
                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }

                }else{

                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }

                }


            }
        });

        include_mess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);
                if(isChecked)
                {

                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getPrice_with_mess()+" /-");
                    }

                }else{


                    if(index==0)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else if(index==1)
                    {
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }else{
                        price.setText("PKR "+hostelsData.getRoomTypes().get(index).getBase_price()+" /-");
                    }

                }

            }
        });

        price.setText("PKR "+hostelsData.getRoomTypes().get(0).getBase_price()+" /-");


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a=new Intent(BookingActivity.this,PaymentProceedActivity.class);
                Bundle bndle=new Bundle();
                bndle.putSerializable("hostel_object",hostelsData);
                a.putExtra("bndle",bndle);
                String amount[]=price.getText().toString().split(" ");
                a.putExtra("amount",amount[1]);
                startActivity(a);
            }
        });

    }
}
