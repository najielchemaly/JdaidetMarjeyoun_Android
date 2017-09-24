package com.marjeyoun.jdeidetmarjeyoun;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.marjeyoun.jdeidetmarjeyoun.complain.Complain_fragment;
import com.marjeyoun.jdeidetmarjeyoun.contacts.Contacts_fragment;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.notifications.Notifications_fragment;

public class MainActivity extends AppCompatActivity{

    boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main_activity);

        BottomNavigationView bottomNavigationViewAr = (BottomNavigationView) findViewById(R.id.bottom_navigation_ar);
        BottomNavigationView bottomNavigationViewEn = (BottomNavigationView) findViewById(R.id.bottom_navigation_en);

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationViewAr);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationViewEn);

        if("en".equals(SelectLanguageActivity.lang)){
            bottomNavigationViewEn.setVisibility(View.VISIBLE);
            bottomNavigationViewAr.setVisibility(View.GONE);
        }else {
            bottomNavigationViewEn.setVisibility(View.GONE);
            bottomNavigationViewAr.setVisibility(View.VISIBLE);
        }

        bottomNavigationViewEn.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment =
                                        HomePage_fragment.newInstance();
                                break;
                            case R.id.action_contact:
                                selectedFragment = Contacts_fragment.newInstance();
                                break;

                            case R.id.action_complain:
                                selectedFragment = Complain_fragment.newInstance();
                                break;

                            case R.id.action_notification:
                                selectedFragment = Notifications_fragment.newInstance();
                                break;

                            case R.id.action_profile:
//                                selectedFragment = AproposFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        bottomNavigationViewAr.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment =
                                        HomePage_fragment.newInstance();
                                break;
                            case R.id.action_contact:
                                selectedFragment = Contacts_fragment.newInstance();
                                break;

                            case R.id.action_complain:
                                selectedFragment = Complain_fragment.newInstance();
                                break;

                            case R.id.action_notification:
                                selectedFragment = Notifications_fragment.newInstance();
                                break;

                            case R.id.action_profile:
//                                selectedFragment = AproposFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, HomePage_fragment.newInstance());
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

            // handle back button
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

            return true;
        }

        return false;
    }
}
