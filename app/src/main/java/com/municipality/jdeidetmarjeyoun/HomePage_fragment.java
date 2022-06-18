package com.municipality.jdeidetmarjeyoun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.aboutus.AboutusActivity;
import com.municipality.jdeidetmarjeyoun.death.Deaths_Activity;
import com.municipality.jdeidetmarjeyoun.event.Events_Activity;
import com.municipality.jdeidetmarjeyoun.fees.Fees_Activity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.location.LocationActivity;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.news.News_Activity;
import com.municipality.jdeidetmarjeyoun.objects.HighlightedNews;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/16/2017.
 */

public class HomePage_fragment extends Fragment {

    ImageButton btnBills, btnEvents, btnSocials, btnAbout, btnNews, btnLocation;
    CardView viewBills, viewEvents, viewAbout, viewSocials, viewNews, viewLocation;
    Animation leftToRight, rightToLeft;
    private View parentView;
    TextView lblLocation, lblDeaths, lblAbout, lblEvents, lblNews, lblFees;
    private final String[] imagesHome = {};
    private ViewPager mPagerHome;
    private int currentPageHome = 0;
    private ArrayList<String> imagesArray = new ArrayList<String>();
    private ArrayList<String> descArray = new ArrayList<String>();
    private List<News> newses = new ArrayList<News>();
    CircleIndicator indicator;

    public static HomePage_fragment newInstance() {
        HomePage_fragment fragment = new HomePage_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.homepage_fragment, container, false);

        try {
            initView();
            styleView();
            startAnimation();
            initListeners();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception {
//        for (int i = 0; i < imagesHome.length; i++)
//            imagesArray.add(imagesHome[i]);

        leftToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left);

        btnBills = (ImageButton) parentView.findViewById(R.id.btnBills);
        btnEvents = (ImageButton) parentView.findViewById(R.id.btnEvents);
        btnNews = (ImageButton) parentView.findViewById(R.id.btnNews);
        btnLocation = (ImageButton) parentView.findViewById(R.id.btnLocation);
        btnSocials = (ImageButton) parentView.findViewById(R.id.btnSocials);
        btnAbout = (ImageButton) parentView.findViewById(R.id.btnAbout);
        viewBills = (CardView) parentView.findViewById(R.id.viewBills);
        viewLocation = (CardView) parentView.findViewById(R.id.viewLocation);
        viewNews = (CardView) parentView.findViewById(R.id.viewNews);
        viewEvents = (CardView) parentView.findViewById(R.id.viewEvents);
        viewAbout = (CardView) parentView.findViewById(R.id.viewAbout);
        viewSocials = (CardView) parentView.findViewById(R.id.viewSocials);
        lblFees = (TextView) parentView.findViewById(R.id.lblFees);
        lblLocation = (TextView) parentView.findViewById(R.id.lblLocation);
        lblAbout = (TextView) parentView.findViewById(R.id.lblAbout);
        lblEvents = (TextView) parentView.findViewById(R.id.lblEvent);
        lblNews = (TextView) parentView.findViewById(R.id.lblNews);
        lblDeaths = (TextView) parentView.findViewById(R.id.lblDeaths);

        mPagerHome = (ViewPager) parentView.findViewById(R.id.pager);

        indicator = (CircleIndicator) parentView.findViewById(R.id.indicator);

        ApiManager.getService().getHighlightedNews(SelectLanguageActivity.lang).enqueue(new Callback<HighlightedNews>() {
            @Override
            public void onResponse(Call<HighlightedNews> call, Response<HighlightedNews> response) {

                try {
                    HighlightedNews highlightedNews = response.body();
                    String image;
                    if(highlightedNews.getNews() != null && highlightedNews.getNews().size() > 0){
                        newses = highlightedNews.getNews();
                        for (int i = 0 ; i < highlightedNews.getNews().size() ; i++) {
                            if (highlightedNews.getNews().get(i).getImages() != null
                                    && highlightedNews.getNews().get(i).getImages().size() > 0) {
                                image = AppUtil.globalVariable.getMediaUrl() + highlightedNews.getNews().get(i).getImages().get(0);
                                imagesArray.add(image);
                            } else {
                                imagesArray.add(AppUtil.globalVariable.getMediaUrl() + AppUtil.globalVariable.getMediaDefaultImage());
                            }

                            if (highlightedNews.getNews().get(i).getShortDescription() != null) {
                                descArray.add(highlightedNews.getNews().get(i).getShortDescription());
                            }
                        }
                        mPagerHome.setAdapter(new HomeImageSliderAdapter(getActivity(), imagesArray, descArray, newses));
                        indicator.setViewPager(mPagerHome);
                    }
                } catch (Exception e) {
                    Log.e("e", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<HighlightedNews> call, Throwable t) {

            }
        });
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPageHome == imagesArray.size()) {
//                    currentPageHome = 0;
//                }
//                mPagerHome.setCurrentItem(currentPageHome++, true);
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

    }
    private void styleView() throws Exception {
        lblDeaths.setTypeface(AppUtil.setFontRegular(getContext()));
        lblNews.setTypeface(AppUtil.setFontRegular(getContext()));
        lblEvents.setTypeface(AppUtil.setFontRegular(getContext()));
        lblAbout.setTypeface(AppUtil.setFontRegular(getContext()));
        lblLocation.setTypeface(AppUtil.setFontRegular(getContext()));
        lblFees.setTypeface(AppUtil.setFontRegular(getContext()));
    }

    private void initListeners() throws Exception {
        btnBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Fees_Activity.class);
                startActivity(i);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), News_Activity.class);
                startActivity(i);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationActivity.class);
                startActivity(i);
            }
        });

        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Events_Activity.class);
                intent.putExtra("title", lblEvents.getText().toString().trim());
                getActivity().startActivity(intent);
            }
        });

        btnSocials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Deaths_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutusActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void startAnimation() throws Exception {
        viewBills.startAnimation(leftToRight);
        viewEvents.startAnimation(leftToRight);
        viewNews.startAnimation(leftToRight);

        viewAbout.startAnimation(rightToLeft);
        viewSocials.startAnimation(rightToLeft);
        viewLocation.startAnimation(rightToLeft);
    }
}
