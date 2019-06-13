package com.anjass.raihan.monica20.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Adapter.List_Adapter;
import com.anjass.raihan.monica20.Authentication.LandingActivity;
import com.anjass.raihan.monica20.Class.List_Class;
import com.anjass.raihan.monica20.CreateCommittee;
import com.anjass.raihan.monica20.FragmentMainActivity;
import com.anjass.raihan.monica20.R;
import com.anjass.raihan.monica20.Splash;
import com.anjass.raihan.monica20.TestActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ImageButton icon_close;
    private NavigationView navigationView;
    private View header;
    private ImageView prev_mon_btn,
            next_mon_btn;
    private TextView prev_mon,
            curr_mon,
            next_mon;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseToDoList;
    CompactCalendarView compactCalendar;

    String userData_string;
    SimpleDateFormat dateFormatMonthDisplay = new SimpleDateFormat("MMMM", Locale.getDefault()),
            dateFormatThreeLetter = new SimpleDateFormat("MMM", Locale.getDefault());
    boolean shouldShow = false;
    List<List_Class> itemList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Firebase code
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // In context code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (currentUser != null)
            userData_string = "Userdata \n" +
                "Email: " +currentUser.getEmail();
        else
            userData_string = "Account signed out";

        // Calendar Test ====================
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        Event ev1 = new Event(Color.parseColor("#3D358B"), 1558170030000L, "Some extra data that I want to store.");
        compactCalendar.addEvent(ev1);

        Event ev2 = new Event(Color.parseColor("#3D358B"), 1558170030000L, "Maen");
        compactCalendar.addEvent(ev2);

        // Query for events
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendar.getEvents(1558170030000L); // can also take a Date object

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Toast.makeText(getApplicationContext(),
                        "Day was clicked: " +dateClicked
                                +" with events " +events, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Toast.makeText(getApplicationContext(),
                        "Month was scrolled to: "
                                +dateFormatMonthDisplay.format(firstDayOfNewMonth), Toast.LENGTH_SHORT).show();
            }
        });
        compactCalendar.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
            }
        });

        // Setting month view
        curr_mon = (TextView) findViewById(R.id.curr_mon);
        prev_mon = (TextView) findViewById(R.id.prev_mon);
        next_mon = (TextView) findViewById(R.id.next_mon);

        prev_mon.setText(dateFormatThreeLetter.format(compactCalendar.getFirstDayOfCurrentMonth()));
        prev_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollLeft();
                setPrevCurrenNextMonth(compactCalendar.getFirstDayOfCurrentMonth());
            }
        });
        prev_mon_btn = (ImageView) findViewById(R.id.prev_mon_btn);
        prev_mon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollLeft();
                setPrevCurrenNextMonth(compactCalendar.getFirstDayOfCurrentMonth());
            }
        });

        next_mon.setText(dateFormatThreeLetter.format(compactCalendar.getFirstDayOfCurrentMonth()));
        next_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollRight();
                setPrevCurrenNextMonth(compactCalendar.getFirstDayOfCurrentMonth());
            }
        });
        next_mon_btn = (ImageView) findViewById(R.id.next_mon_btn);
        next_mon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollRight();
                setPrevCurrenNextMonth(compactCalendar.getFirstDayOfCurrentMonth());
            }
        });
        // Calendar Test end ====================


        // Getting data from Firebase =====================
        databaseToDoList = FirebaseDatabase.getInstance().getReference("itemList");
        //Ambil data dari Firebase Database
        databaseToDoList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Kalo update, clear dulu biar ga numpuk
                itemList.clear();

                // Kirim data perchild ke kelas responden
                for(DataSnapshot respondenSnapshot : dataSnapshot.getChildren()){
                    List_Class list_class = respondenSnapshot.getValue(List_Class.class);

                    itemList.add(list_class);
                }

                /*Event ev1 = new Event(Color.parseColor("#3D358B"),
                        , "Some extra data that I want to store.");
                compactCalendar.addEvent(ev1);*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Getting data from Firebase  end =====================

        // Context Codes
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, userData_string, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

        // Link button to Techopreneur
        LinearLayout item2 = (LinearLayout) header.findViewById(R.id.item2);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), FragmentMainActivity.class);
                    startActivity(i);
                    drawer.closeDrawer(GravityCompat.START);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Link button to Committee Creation
        LinearLayout createCommittee = (LinearLayout) header.findViewById(R.id.itemNew);
        createCommittee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), CreateCommittee.class);
                    startActivity(i);
                    drawer.closeDrawer(GravityCompat.START);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        setPrevCurrenNextMonth(compactCalendar.getFirstDayOfCurrentMonth());
    }

    public void setPrevCurrenNextMonth(Date dateParsed){
        Calendar setMonth = Calendar.getInstance();

        // Current Month
        setMonth.setTime(dateParsed);
        curr_mon.setText(dateFormatMonthDisplay.format(setMonth.getTime()));

        // Prev Month
        setMonth.setTime(dateParsed);
        setMonth.add(Calendar.MONTH, -1);
        prev_mon.setText(dateFormatThreeLetter.format(setMonth.getTime()));

        // Next Month
        setMonth.setTime(dateParsed);
        setMonth.add(Calendar.MONTH, 1);
        next_mon.setText(dateFormatThreeLetter.format(setMonth.getTime()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            // Handle the logout action
            mAuth.signOut();

            finish();
            startActivity(new Intent(getApplicationContext(), LandingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_out) {
            // Handle the logout action

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
