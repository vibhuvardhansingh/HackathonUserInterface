package info.andriodhive.hackathonuser;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BottomNavigationActivity extends AppCompatActivity {



    testFragment fragment = new testFragment();
    Leaderboard_Fragment fragment1 = new Leaderboard_Fragment();
    Profile_Fragment profile_fragment = new Profile_Fragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        if (item.getItemId() == R.id.navigation_threshold) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, profile_fragment).addToBackStack(null).commit();
            return true;
        } else if (item.getItemId() == R.id.navigation_bin_near_me) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment1).addToBackStack(null).commit();
            return true;
        }
        else if (item.getItemId() == R.id.navigation_bin_near_me) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment1).addToBackStack(null).commit();
            return true;
        }else if (item.getItemId() == R.id.navigation_bin_near_me) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment1).addToBackStack(null).commit();
            return true;
        }
        else
        return false;

               /*     case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.title_notifications);
                        return true;*/
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

     /*   Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

    }
}