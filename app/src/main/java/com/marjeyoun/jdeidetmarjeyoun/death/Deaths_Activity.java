package com.marjeyoun.jdeidetmarjeyoun.death;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Death;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 9/16/2017.
 */

public class Deaths_Activity extends AppCompatActivity {

    ListView listDeaths;
    TextView lblTitle;
    DeathsAdapter deathsAdapter;
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deaths_activity);

        try {
            initView();
            styleView();
            initListeners();
        }catch (Exception e){
            Log.e("error: ", e.getMessage());
        }
    }

    private List<Death> createDummyDeathsAr() {
        List<Death> itemList = new ArrayList<>();

        Death death = new Death();
        death.setName("الدكتور تراز حنا");
        death.setDescription("نتقابل التعازي يوم الأحد 17 حزيران من الساعة الثالثة بعد الظهور إلى السابعة مساءً  في سنتر فردان");
        itemList.add(death);

        death = new Death();
        death.setName("الدكتور تراز حنا");
        death.setDescription("نتقابل التعازي يوم الأحد 17 حزيران من الساعة الثالثة بعد الظهور إلى السابعة مساءً  في سنتر فردان");
        itemList.add(death);

        death = new Death();
        death.setName("الدكتور تراز حنا");
        death.setDescription("نتقابل التعازي يوم الأحد 17 حزيران من الساعة الثالثة بعد الظهور إلى السابعة مساءً  في سنتر فردان");
        itemList.add(death);

        death = new Death();
        death.setName("الدكتور تراز حنا");
        death.setDescription("نتقابل التعازي يوم الأحد 17 حزيران من الساعة الثالثة بعد الظهور إلى السابعة مساءً  في سنتر فردان");
        itemList.add(death);

        return itemList;
    }

    private List<Death> createDummyDeathsEN() {
        List<Death> itemList = new ArrayList<>();

        Death death = new Death();
        death.setName("Dr. Therez Hanna");
        death.setDescription("We meet on Sunday 17 June from 3:00 pm to 7 pm at the Ferdinand Center");
        itemList.add(death);

        death = new Death();
        death.setName("Dr. Therez Hanna");
        death.setDescription("We meet on Sunday 17 June from 3:00 pm to 7 pm at the Ferdinand Center");
        itemList.add(death);

        death = new Death();
        death.setName("Dr. Therez Hanna");
        death.setDescription("We meet on Sunday 17 June from 3:00 pm to 7 pm at the Ferdinand Center");
        itemList.add(death);

        death = new Death();
        death.setName("Dr. Therez Hanna");
        death.setDescription("We meet on Sunday 17 June from 3:00 pm to 7 pm at the Ferdinand Center");
        itemList.add(death);

        return itemList;
    }

    private void initView() throws Exception {

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        listDeaths = (ListView) findViewById(R.id.listDeaths);
        lblTitle = (TextView) findViewById(R.id.lblTitle);

        deathsAdapter= new DeathsAdapter("ar".equals(SelectLanguageActivity.lang) ? createDummyDeathsAr() : createDummyDeathsEN(), getApplicationContext());
        listDeaths.setAdapter(deathsAdapter);
    }

    private void styleView() throws Exception {
        lblTitle.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
    }

    private void initListeners() throws Exception {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
