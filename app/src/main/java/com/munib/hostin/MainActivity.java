package com.munib.hostin;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity
        implements  Main_fragment.OnFragmentInteractionListener,change_pass_user.OnFragmentInteractionListener,
        settings.OnFragmentInteractionListener,About_us.OnFragmentInteractionListener,edit_user_profile.OnFragmentInteractionListener,
        user_profile.OnFragmentInteractionListener,AddCreditCard_fragment.OnFragmentInteractionListener,Payments_fragment.OnFragmentInteractionListener,
        Saved_hostels.OnFragmentInteractionListener,Notifications_fragment.OnFragmentInteractionListener,Filters.OnFragmentInteractionListener,
        HostelProfile.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener,MyHostelsFragment.OnFragmentInteractionListener,MapViewFragment.OnFragmentInteractionListener,FragmentMess.OnFragmentInteractionListener{

    public static boolean mSlideState=false;
    public static DrawerLayout drawer;

    public static String API="http://13.127.35.98:3000/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Main_fragment fragment = new Main_fragment();
        fm.beginTransaction().add(R.id.fragment,fragment).commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.setDrawerListener(new ActionBarDrawerToggle(this,
               drawer,
                new Toolbar(getApplicationContext()),
                0,
                0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mSlideState=false;//is Closed
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState=true;//is Opened
            }});

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.name)).setText(SavedSharedPreferences.getUserName(getApplicationContext()));
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.email)).setText(SavedSharedPreferences.getUserEmail(getApplicationContext()));

        if(SavedSharedPreferences.getUserImage(getApplicationContext()).equals("null"))
        {

        }else
        {
            Picasso.with(getApplicationContext()).load(MainActivity.API+"userImage/"+SavedSharedPreferences.getUserImage(getApplicationContext())).into((CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_image));

        }

//


        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //hui changeas??



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.explore) {

            FragmentManager fm = getSupportFragmentManager();
            Main_fragment fragment = new Main_fragment();
            fm.beginTransaction().replace(R.id.fragment,fragment).commit();

        } else if (id == R.id.my_hostel) {

            FragmentManager fm = getSupportFragmentManager();
            MyHostelsFragment fragment = new MyHostelsFragment();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();

        } else if (id == R.id.payments) {

            FragmentManager fm = getSupportFragmentManager();
            Payments_fragment fragment = new Payments_fragment();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();

        } else if (id == R.id.saved) {

            FragmentManager fm = getSupportFragmentManager();
            Saved_hostels fragment = new Saved_hostels();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();


        } else if (id == R.id.my_profile) {

            FragmentManager fm = getSupportFragmentManager();
            user_profile uprofile = new user_profile();
            fm.beginTransaction().replace(R.id.fragment,uprofile).addToBackStack(null).commit();

        } else if (id == R.id.notif) {
            FragmentManager fm = getSupportFragmentManager();
            Notifications_fragment fragment = new Notifications_fragment();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();


        }else if (id == R.id.nav_log_out) {

            SavedSharedPreferences.clear(getApplication());
            MainActivity.this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
