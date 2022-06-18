package com.municipality.jdeidetmarjeyoun.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.news.NewsImageSliderAdapter;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Charbel on 10/21/2017.
 */

public class LocationDetailsActivity extends AppCompatActivity {

    private final Integer[] imagesEvents = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    TextView txtTitle, txtDetails, lblTitle;
    Button btnMap;
    ImageButton btnBack;
    private ViewPager mPagerEvent;
    private int currentPageEvent = 0;
    ImageButton btnMenu;
    LinearLayout viewMain;
    Animation drop_down, goUp;
    private ArrayList<String> imagesArray = new ArrayList<String>();
    String lat, longi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_details_activity);
        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {

        }
    }

    private void initView() throws Exception {
//        for (int i = 0; i < imagesEvents.length; i++)
//            imagesArray.add(imagesEvents[i]);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        btnMap = (Button) findViewById(R.id.btnMap);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

        mPagerEvent = (ViewPager) findViewById(R.id.pager);

        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPageEvent == imagesEvents.length) {
//                    currentPageEvent = 0;
//                }
//                mPagerEvent.setCurrentItem(currentPageEvent++, true);
//            }
//        };

        // timer for slideshow
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);

        setViewLanguage(SelectLanguageActivity.lang);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            News news = (News) bundle.getSerializable("news");
            longi = bundle.getString("long");
            lat = bundle.getString("lat");

            txtTitle.setText(news.getTitle());
            txtDetails.setText(news.getDescription());

            String image;
            for(int i = 0; i < news.getImages().size(); i++){
                image = AppUtil.globalVariable.getMediaUrl() + news.getImages().get(i);
                imagesArray.add(image);
            }

            mPagerEvent.setAdapter(new NewsImageSliderAdapter(LocationDetailsActivity.this, imagesArray));
            if(imagesArray.size() > 1){
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(mPagerEvent);
            }
        }
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

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationDetailsActivity.this, LocationMapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("long", longi);
                startActivity(intent);
            }
        });

    }

    private void setViewLanguage(String language) {
        if ("ar".equals(language)) {
            txtTitle.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.RIGHT);
            btnMap.setText("إفتح خريطة جوجل");
        } else {
            txtTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtDetails.setGravity(Gravity.CENTER | Gravity.LEFT);
            btnMap.setText("Open Google maps");
        }
    }
}
