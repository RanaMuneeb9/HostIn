package com.munib.hostin;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements  Main_fragment.OnFragmentInteractionListener,CreditCard.OnFragmentInteractionListener,payment.OnFragmentInteractionListener,Saved_hostels.OnFragmentInteractionListener,Filters.OnFragmentInteractionListener,HostelProfile.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener{

    public static boolean mSlideState=false;
    public static DrawerLayout drawer;
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

            finish();
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);

        } else if (id == R.id.payments) {

            FragmentManager fm = getSupportFragmentManager();
            payment fragment = new payment();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();

        } else if (id == R.id.saved) {

            FragmentManager fm = getSupportFragmentManager();
            Saved_hostels fragment = new Saved_hostels();
            fm.beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();


        } else if (id == R.id.my_profile) {

        } else if (id == R.id.notif) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
