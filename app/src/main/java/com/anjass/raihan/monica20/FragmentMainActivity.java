package com.anjass.raihan.monica20;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.anjass.raihan.monica20.Fragment.CommitteeFragment;
import com.anjass.raihan.monica20.Fragment.FilesFragment;
import com.anjass.raihan.monica20.Fragment.FilesFragment;
import com.anjass.raihan.monica20.Fragment.ItemListFragment;
import com.anjass.raihan.monica20.Fragment.TalkFragment;
import com.anjass.raihan.monica20.Fragment.TodoListFragment;
import com.anjass.raihan.monica20.R;

public class FragmentMainActivity extends AppCompatActivity {

    private Fragment selectedFragment;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_committee);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommitteeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_talk:
                    selectedFragment = new TalkFragment();
                    break;
                case R.id.navigation_files:
                    selectedFragment = new FilesFragment();
                    break;
                case R.id.navigation_committee:
                    selectedFragment = new CommitteeFragment();
                    break;
                case R.id.navigation_todo:
                    selectedFragment = new TodoListFragment();
                    break;
                case R.id.navigation_item:
                    selectedFragment = new ItemListFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

}
