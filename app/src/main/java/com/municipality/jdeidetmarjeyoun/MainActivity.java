package com.municipality.jdeidetmarjeyoun;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.complain.Complain_fragment;
import com.municipality.jdeidetmarjeyoun.contacts.Contacts_fragment;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.notifications.Notifications_fragment;
import com.municipality.jdeidetmarjeyoun.objects.GlobalVariable;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.profile.ProfileFragment;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    boolean doubleBackToExitPressedOnce;
    ProgressDialog progress;

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
                                selectedFragment = ProfileFragment.newInstance();
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
                                selectedFragment = HomePage_fragment.newInstance();
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
                                selectedFragment = ProfileFragment.newInstance();
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
        bottomNavigationViewAr.setSelectedItemId(R.id.action_home);

        progress = new ProgressDialog(MainActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        ApiManager.getService().getGlobalVariables(SelectLanguageActivity.lang).enqueue(new Callback<GlobalVariable>() {
            @Override
            public void onResponse(Call<GlobalVariable> call, Response<GlobalVariable> response) {
                GlobalVariable globalVariable = response.body();
                if (globalVariable.getStatus() == 1) {
                    AppUtil.globalVariable = globalVariable;
                } else {
                    AppUtil.showError(MainActivity.this, getResources().getString(R.string.error_message));
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<GlobalVariable> call, Throwable t) {
                try {
                    progress.dismiss();
                    Log.e("e", t.getMessage());
                } catch (Exception ex) {
                    Log.e("e", ex.getMessage());
                }
            }
        });
        AppUtil.user = getUser(this);
    }

    public User getUser(Context context) {
        SharedPreferences settings;
        String json;
        settings = context.getSharedPreferences(AppUtil.PREFS_NAME, Context.MODE_PRIVATE); //1

        Gson gson = new Gson();
        json = settings.getString(AppUtil.PREFS_USER, ""); //2
        User user = gson.fromJson(json, User.class);
        return user;
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
