package com.anjass.raihan.monica20.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.anjass.raihan.monica20.R;

public class FragmentMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_talk:
                    mTextMessage.setText("Talk");
                    return true;
                case R.id.navigation_files:
                    mTextMessage.setText("Files");
                    return true;
                case R.id.navigation_committee:
                    mTextMessage.setText("Committee");
                    return true;
                case R.id.navigation_todo:
                    mTextMessage.setText("To-do List");
                    return true;
                case R.id.navigation_item:
                    mTextMessage.setText("Item List");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
