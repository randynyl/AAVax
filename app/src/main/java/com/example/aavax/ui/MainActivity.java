package com.example.aavax.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aavax.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    private TextView mToolbarTitle;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbarTitle = findViewById(R.id.toolbar_title);

        /*
        firebaseManager =  new FirebaseManager();
        firebaseManager.updateVaccine();*/


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            Fragment fragment = new HomePageFragment();
            doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, "");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String title = "";
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomePageFragment();
                            title = getString(R.string.my_vaccines);
                            break;
                        case R.id.navigation_travel:
                            selectedFragment = new TravelPageFragment();
                            title = getString(R.string.continents);
                            break;
                        case R.id.navigation_reminders:
                            selectedFragment = new RemindersPageFragment();
                            title = getString(R.string.title_reminders);
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfilePageFragment();
                            title = getString(R.string.title_profile);
                            break;
                    }
                    doFragmentTransaction(selectedFragment, title, true, "");

                    return true;
                }
            };

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!message.equals("")){
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.intent_message), message);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, fragment, tag);
        if(addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {
        mToolbarTitle.setText(fragmentTag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println(getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().beginTransaction().commit();
        }
    }
}