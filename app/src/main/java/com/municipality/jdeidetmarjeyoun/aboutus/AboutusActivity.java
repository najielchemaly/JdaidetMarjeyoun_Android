package com.municipality.jdeidetmarjeyoun.aboutus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.event.EventImageSliderAdapter;
import com.municipality.jdeidetmarjeyoun.fees.Fees_Activity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.AboutUs;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/23/2017.
 */

public class AboutusActivity extends AppCompatActivity {
//    private final Integer[] imagesEvents = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    private ViewPager mPagerEvent;
    private int currentPageEvent = 0;
    ImageButton btnBack, btnMenu;
    TextView txtTitle, txtDetails;
    LinearLayout viewMain;
    Animation drop_down, goUp;
    CircleIndicator indicator;
    private ArrayList<String> imagesArray = new ArrayList<String>();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_activity);

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
        }
    }

    private void initView() throws Exception {
//        for (int i = 0; i < imagesEvents.length; i++)
//            imagesArray.add(imagesEvents[i]);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        mPagerEvent = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        txtTitle.setText("");
        txtDetails.setText("");

        progress = new ProgressDialog(AboutusActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        ApiManager.getService().getAboutUs(SelectLanguageActivity.lang).enqueue(new Callback<AboutUs>() {
            @Override
            public void onResponse(Call<AboutUs> call, Response<AboutUs> response) {
                AboutUs aboutUs = response.body();
                if(aboutUs.getStatus() == 1){
                    txtTitle.setText(aboutUs.getTitle());
                    txtDetails.setText(aboutUs.getDescription());

                    if(aboutUs.getImages().size() > 0){
                        imagesArray.add(AppUtil.globalVariable.getMediaUrl() + aboutUs.getImages().get(0));
                    }else {
                        imagesArray.add(AppUtil.globalVariable.getMediaUrl() + AppUtil.globalVariable.getMediaDefaultImage());
                    }
                    progress.dismiss();

                    mPagerEvent.setAdapter(new EventImageSliderAdapter(AboutusActivity.this, imagesArray));
//                    indicator.setViewPager(mPagerEvent);
                }else {
                    AppUtil.showError(AboutusActivity.this, getResources().getString(R.string.error_message));
                }
            }

            @Override
            public void onFailure(Call<AboutUs> call, Throwable t) {

            }
        });

//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPageEvent == imagesEvents.length) {
//                    currentPageEvent = 0;
//                }
//                mPagerEvent.setCurrentItem(currentPageEvent++, true);
//            }
//        };
//
//        // timer for slideshow
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);
    }

    private void styleView() throws Exception {
        txtTitle.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        txtDetails.setTypeface(AppUtil.setFontRegular(getApplicationContext()));

        setViewLanguage(SelectLanguageActivity.lang);
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
                if(viewMain.getVisibility() == View.VISIBLE){
                    btnMenu.setImageResource(R.drawable.menu);
                    viewMain.startAnimation(goUp);
                    viewMain.setVisibility(View.GONE);
                }else {
                    btnMenu.setImageResource(R.drawable.close);
                    viewMain.startAnimation(drop_down);
                    viewMain.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setViewLanguage(String language) {
        if("ar".equals(language)){
            txtTitle.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            txtTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.LEFT);
        }
    }
}
