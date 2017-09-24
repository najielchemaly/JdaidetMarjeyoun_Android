package com.marjeyoun.jdeidetmarjeyoun.language;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;
import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.signup.SignupActivity;

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
                AppUtil.setLocale(getApplicationContext(), "ar");
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                lang = "ar";
            }
        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.setLocale(getApplicationContext(), "es");
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                lang = "en";
            }
        });
    }
}
