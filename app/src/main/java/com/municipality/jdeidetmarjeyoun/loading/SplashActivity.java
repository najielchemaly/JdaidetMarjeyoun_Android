package com.municipality.jdeidetmarjeyoun.loading;

/**
 * Created by Charbel on 9/13/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtil.user = getUser(SplashActivity.this);

                if(AppUtil.user == null){
                    Intent langIntent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                    SplashActivity.this.startActivity(langIntent);
                    SplashActivity.this.finish();
                }else {
                    if("ar".equals(getLang(SplashActivity.this))){
                        AppUtil.setLocale(getApplicationContext(), "ar");
                        SelectLanguageActivity.lang = "ar";
                    }else {
                        AppUtil.setLocale(getApplicationContext(), "es");
                        SelectLanguageActivity.lang = "en";
                    }
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
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

    public String getLang(Context context) {
        SharedPreferences settings;
        String lang;
        settings = context.getSharedPreferences(AppUtil.PREFS_NAME, Context.MODE_PRIVATE); //1

        lang = settings.getString(AppUtil.PREFS_LANG, ""); //2

        return lang;
    }
}