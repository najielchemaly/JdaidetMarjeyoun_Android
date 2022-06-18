package com.municipality.jdeidetmarjeyoun.language;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.login.LoginActivity;
import com.municipality.jdeidetmarjeyoun.objects.GlobalVariable;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.signup.SignupActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/13/2017.
 */

public class SelectLanguageActivity extends AppCompatActivity {

    Button btnArabic, btnEnglish;
    TextView lblMuni, lblTitleEn, lblTitleAr;
    public static String lang ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_activity);

        try{
            initView();
            styleView();
            initListeners();
        }catch (Exception e){
            AppUtil.showError(this, "Error : " + e.getMessage());
        }
    }

    private void initView() throws Exception {
        btnArabic = (Button) findViewById(R.id.btnArabic);
        btnEnglish = (Button) findViewById(R.id.btnEnglish);
        lblMuni = (TextView) findViewById(R.id.lblMuni);
        lblTitleEn = (TextView) findViewById(R.id.lblTitleEn);
        lblTitleAr = (TextView) findViewById(R.id.lblTitleAr);
    }

    private void styleView() throws Exception {
        lblMuni.setTypeface(AppUtil.setFontLight(getApplicationContext()));
        lblTitleEn.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        lblTitleAr.setTypeface(AppUtil.setFontBold(getApplicationContext()));
    }

    private void initListeners() throws Exception {
        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("ar");
            }
        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("en");
            }
        });
    }

    private void setLanguage(String language){
        if("ar".equals(language)){
            AppUtil.setLocale(getApplicationContext(), "ar");
            lang = "ar";

            FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/jdeidetmarjeyounnewsen");
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/jdeidetmarjeyounnewsar");
        }else {
            AppUtil.setLocale(getApplicationContext(), "es");
            lang = "en";

            FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/jdeidetmarjeyounnewsar");
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/jdeidetmarjeyounnewsen");
        }

        saveLang(SelectLanguageActivity.this, lang);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveLang(Context context, String lang) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(AppUtil.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(AppUtil.PREFS_LANG, lang);
        editor.apply();
    }
}
