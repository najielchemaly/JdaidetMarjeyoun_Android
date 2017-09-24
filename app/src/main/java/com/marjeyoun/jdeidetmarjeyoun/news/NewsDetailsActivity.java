package com.marjeyoun.jdeidetmarjeyoun.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.event.EventImageSliderAdapter;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Charbel on 9/23/2017.
 */

public class NewsDetailsActivity extends AppCompatActivity {
    private static final Integer[] images = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    private static ViewPager mPager;
    private static int currentPage = 0;
    ImageButton btnBack, btnMenu;
    TextView txtDate, txtTitle, txtDetails;
    private ArrayList<Integer> imagesArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
        }
    }

    private void initView() throws Exception {
        for (int i = 0; i < images.length; i++)
            imagesArray.add(images[i]);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new EventImageSliderAdapter(NewsDetailsActivity.this, imagesArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        // timer for slideshow
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    private void styleView() throws Exception {
        txtDate.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
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
                // TODO: show custom menu
            }
        });

    }

    private void setViewLanguage(String language) {
        if("ar".equals(language)){
            txtDate.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtTitle.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            txtDate.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.LEFT);
        }
    }
}
