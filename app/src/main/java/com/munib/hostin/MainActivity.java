package com.munib.hostin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        View a= findViewById(R.id.all);

        //Abi icons change nai horae abi me commit krta hn changes or tere pass update kruga to changes ajaegi
        //ok

        View b=new View();
    }
}
