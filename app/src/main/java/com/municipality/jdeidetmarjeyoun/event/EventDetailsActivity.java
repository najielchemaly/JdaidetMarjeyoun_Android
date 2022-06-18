package com.municipality.jdeidetmarjeyoun.event;

import android.content.Intent;
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

import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.location.LocationImageSliderAdapter;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.news.NewsDetailsActivity;
import com.municipality.jdeidetmarjeyoun.news.NewsImageSliderAdapter;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Charbel on 9/23/2017.
 */

public class EventDetailsActivity extends AppCompatActivity {
//    private final Integer[] imagesEvents = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    private ViewPager mPagerEvent;
    private int currentPageEvent = 0;
    ImageButton btnBack, btnMenu;
    TextView txtDate, txtTitle, txtDetails;
    private ArrayList<String> imagesArray = new ArrayList<String>();
    LinearLayout viewMain;
    Animation drop_down, goUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_activity);

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
        }
    }

    private void initView() throws Exception {

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

        mPagerEvent = (ViewPager) findViewById(R.id.pager);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            News news = (News) b.getSerializable("events");
            txtTitle.setText(news.getTitle());
            txtDetails.setText(news.getDescription());
            txtDate.setText(news.getDate());

            String image;
            for(int i = 0; i < news.getImages().size(); i++){
                image = AppUtil.globalVariable.getMediaUrl() + news.getImages().get(i);
                imagesArray.add(image);
            }

            mPagerEvent.setAdapter(new NewsImageSliderAdapter(EventDetailsActivity.this, imagesArray));

            if(imagesArray.size() > 1){
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(mPagerEvent);
            }
        }
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
