package com.municipality.jdeidetmarjeyoun.news;

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
import com.municipality.jdeidetmarjeyoun.event.EventImageSliderAdapter;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import com.municipality.jdeidetmarjeyoun.objects.News;

/**
 * Created by Charbel on 9/23/2017.
 */

public class NewsDetailsActivity extends AppCompatActivity {
//    private final Integer[] imagesNews = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    private ViewPager mPagerNews;
    private int currentPageNews = 0;
    ImageButton btnBack, btnMenu;
    TextView txtDate, txtTitle, txtDetails;
    private ArrayList<String> imagesArray = new ArrayList<String>();
    LinearLayout viewMain;
    Animation drop_down, goUp;


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
//        for (int i = 0; i < imagesNews.length; i++)
//            imagesArray.add(imagesNews[i]);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

        mPagerNews = (ViewPager) findViewById(R.id.pager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            News news = (News) bundle.get("news");
            txtTitle.setText(news.getTitle());
            txtDetails.setText(news.getDescription());
            txtDate.setText(news.getDate());

            String image;
            for(int i = 0; i < news.getImages().size(); i++){
                image = AppUtil.globalVariable.getMediaUrl() + news.getImages().get(i);
                imagesArray.add(image);
            }

            mPagerNews.setAdapter(new NewsImageSliderAdapter(NewsDetailsActivity.this, imagesArray));
            if(imagesArray.size() > 1){
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(mPagerNews);
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
