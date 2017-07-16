package com.magdy.instructme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GridInfoListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public boolean mTwoPane;
    TextView navName, navEmail;
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mauthListener;
    BroadcastReceiver broadcastReceiver;
    CoordinatorLayout coordinatorLayout;
    int type = 0;
    Snackbar snackbar;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        FrameLayout flPanel2 = (FrameLayout) findViewById(R.id.fl_panel2);

        mTwoPane = (null != flPanel2);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getResources().getBoolean(R.bool.land_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);//layout itself
        nvDrawer = (NavigationView) findViewById(R.id.nvView); //navigation
        menu = nvDrawer.getMenu();
        View header = nvDrawer.inflateHeaderView(R.layout.nav_header);
        navEmail = (TextView) header.findViewById(R.id.emailtext);
        navName = (TextView) header.findViewById(R.id.uname);
        mDrawer.addDrawerListener(drawerToggle);


        setupDrawerContent(nvDrawer);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);

        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.no_courses), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        installListener();


        mauth = FirebaseAuth.getInstance();
        mauthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    menu.findItem(R.id.nav_fifth_logout).setTitle(R.string.login);
                    navName.setText(R.string.app_name);
                    navEmail.setText(R.string.login);
                } else {
                    menu.findItem(R.id.nav_fifth_logout).setTitle(R.string.logout);
                }
            }
        };
        mauth.addAuthStateListener(mauthListener);
    }



    private void installListener() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        if (broadcastReceiver == null) {


            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = extras.getParcelable("networkInfo");

                    NetworkInfo.State state;
                    if (info != null) {
                        state = info.getState();

                        Log.d("InternalBroadReceiver", info.toString() + " "
                                + state.toString());

                        if (state == NetworkInfo.State.CONNECTED) {

                            snackbar.dismiss();
                            unregisterReceiver(broadcastReceiver);

                        } else {
                            snackbar.setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            snackbar.show();
                        }

                    }
                }
            };


            registerReceiver(broadcastReceiver, intentFilter);
        }
    }


    public void setupDrawerContent(NavigationView upDrawerContent) {
        upDrawerContent.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {

        // Highlight the selected item has been done by NavigationView

        /*MainActivityFragment mainActivityFragment;*/

        menuItem.setChecked(true);
        // Set action bar title
        // Close the navigation drawer
        mDrawer.closeDrawers();
        switch (menuItem.getItemId()) {
            /*case R.id.nav_most:
                type = 0;
                break;
            case R.id.nav_top:
                type = 1;
                break;
            case R.id.nav_fav:
                type = 2;
                break;*/
            case R.id.nav_fifth_logout:
                if (mauth.getCurrentUser() == null) {
                    Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    mauth.signOut();

                }
                break;
            default:
                type = 0;
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar_home reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        OneFragment f = new OneFragment();
        f.setListInfoListenter(this);
        f.setCategory("computer");
        adapter.addFragment(f, "Computer");
        f = new OneFragment();
        f.setCategory("design");
        f.setListInfoListenter(this);
        adapter.addFragment(f, "Design");
        f = new OneFragment();
        f.setCategory("science");
        f.setListInfoListenter(this);
        adapter.addFragment(f, "Science");
        f = new OneFragment();
        f.setCategory("engineering");
        f.setListInfoListenter(this);
        adapter.addFragment(f, "Engineering");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void setSelected(Course course) {
        if (mTwoPane) {
            DetailsFragment detailActivityFragment = new DetailsFragment();
            Bundle extra = new Bundle();
            extra.putSerializable("course", course);
            extra.putBoolean("pane", mTwoPane);
            detailActivityFragment.setArguments(extra);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_panel2, detailActivityFragment).commit();

        } else {

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("course", course);
            intent.putExtra("pane", mTwoPane);
            startActivity(intent);
        }


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

         ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

         void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}