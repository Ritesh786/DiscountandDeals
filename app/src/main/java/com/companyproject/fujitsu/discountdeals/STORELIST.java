package com.companyproject.fujitsu.discountdeals;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class STORELIST extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    TextView mTitle;
    Toolbar toolbar;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BlankFragment fragment = new BlankFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frame_trans, fragment).addToBackStack("STORE LIST").commit();
        manager.addOnBackStackChangedListener(this);

        if (session.checkLogin())
            finish();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            }
            else {
                getFragmentManager().popBackStack();
            }

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.storelist, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//            mTitle.invalidate();
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_store) {

            BlankFragment fragment = new BlankFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("STORE LIST").commit();
            manager.addOnBackStackChangedListener(this);

        } else if (id == R.id.nav_search) {

            Search fragment = new Search();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("SEARCH").commit();
            manager.addOnBackStackChangedListener(this);

        } else if (id == R.id.nav_reedemcoupon) {


            ReedemCoupon fragment = new ReedemCoupon();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("REEDEM COUPON").commit();
            manager.addOnBackStackChangedListener(this);


        } else if (id == R.id.nav_password) {

            ChangePassword fragment = new ChangePassword();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("CHANGE PASSWORD").commit();
            manager.addOnBackStackChangedListener(this);

        }
//        else if (id == R.id.nav_add) {
//
//            AddDeals fragment = new AddDeals();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("ADD DEALS").commit();
//            manager.addOnBackStackChangedListener(this);
//        }

//        else if (id == R.id.nav_history) {
//
//            HistoryLIst fragment = new HistoryLIst();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("HISTORY LIST").commit();
//            manager.addOnBackStackChangedListener(this);
//        }

        else if (id == R.id.nav_logout) {

            session.logoutUser();
            STORELIST.this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {

        try {

            int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

            FragmentManager.BackStackEntry lastBackStackEntry =
                    getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

            mTitle.setText(lastBackStackEntry.getName());

        } catch (Exception e) {
           e.printStackTrace();
            STORELIST.this.finish();
            Log.e("prob",e.toString());
        }



    }
}
