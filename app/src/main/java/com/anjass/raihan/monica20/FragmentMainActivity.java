package com.anjass.raihan.monica20;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anjass.raihan.monica20.Fragment.CommitteeFragment;
import com.anjass.raihan.monica20.Fragment.FilesFragment;
import com.anjass.raihan.monica20.Fragment.ItemListFragment;
import com.anjass.raihan.monica20.Fragment.TalkFragment;
import com.anjass.raihan.monica20.Fragment.TodoListFragment;

public class FragmentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar actionBar;
    private Fragment selectedFragment;
    private BottomNavigationView navigation;
    private DrawerLayout drawer;
    private ImageButton icon_close;
    private NavigationView navigationView;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);



        // Settings for the drawer
        actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, actionBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Close button
        header = navigationView.getHeaderView(0);
        icon_close = (ImageButton) header.findViewById(R.id.icon_close);
        icon_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });



        // Settings for bottom navbar
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Setting bottom navbar color
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_selected}, // selected
                new int[] {-android.R.attr.state_focused}, // focused
        };
        int[] colors = new int[] {
                Color.parseColor("#623EEA"),
                Color.parseColor("#3B3745"),
        };
        ColorStateList myList = new ColorStateList(states, colors);

        // Setting default for bottom navbar
        actionBar.setTitle("Committee");
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_committee);
            navigation.setItemIconTintList(myList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CommitteeFragment()).commit();
        }
    }



    /* Override methods for Drawer Logic */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.settings) {
            // Handle the camera action
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_talk:
                    selectedFragment = new TalkFragment();
                    actionBar.setTitle("Talk");
                    break;
                case R.id.navigation_files:
                    selectedFragment = new FilesFragment();
                    actionBar.setTitle("Files");
                    break;
                case R.id.navigation_committee:
                    selectedFragment = new CommitteeFragment();
                    actionBar.setTitle("Committee");
                    break;
                case R.id.navigation_todo:
                    selectedFragment = new TodoListFragment();
                    actionBar.setTitle("To-do List");
                    break;
                case R.id.navigation_item:
                    selectedFragment = new ItemListFragment();
                    actionBar.setTitle("Item List");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}
