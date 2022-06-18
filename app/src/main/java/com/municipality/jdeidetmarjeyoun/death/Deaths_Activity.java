package com.municipality.jdeidetmarjeyoun.death;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.aboutus.AboutusActivity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Death;
import com.municipality.jdeidetmarjeyoun.objects.SocialNews;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/16/2017.
 */

public class Deaths_Activity extends AppCompatActivity {

    DeathsAdapter deathsAdapter;
    Animation drop_down, goUp;
    LinearLayout viewMain;
    ListView listDeaths;
    ImageButton btnMenu;
    ImageButton btnBack;
    TextView lblTitle;
    ProgressDialog progress;
    SwipeRefreshLayout mSwipeRefreshLayout;

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
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        listDeaths = (ListView) findViewById(R.id.listDeaths);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        callService();
        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);
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

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewMain.getVisibility() == View.VISIBLE) {
                    btnMenu.setImageResource(R.drawable.menu);
                    viewMain.startAnimation(goUp);
                    viewMain.setVisibility(View.GONE);
                } else {
                    btnMenu.setImageResource(R.drawable.close);
                    viewMain.startAnimation(drop_down);
                    viewMain.setVisibility(View.VISIBLE);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void callService() {
        progress = new ProgressDialog(Deaths_Activity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        String lang = SelectLanguageActivity.lang;
        ApiManager.getService().getSocialNews(lang).enqueue(new Callback<SocialNews>() {
            @Override
            public void onResponse(Call<SocialNews> call, Response<SocialNews> response) {
                if(response.isSuccessful() && response.body().getStatus() == 1) {
                    if(response.body().getSocials() != null && response.body().getSocials().size() > 0){
                        deathsAdapter = new DeathsAdapter(response.body(), getApplicationContext());
                        listDeaths.setAdapter(deathsAdapter);
                    }else {
                        // TODO EMPTY MESSAGE
                    }
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SocialNews> call, Throwable t) {
                Log.e("e",t.getMessage());
            }
        });

    }
}
