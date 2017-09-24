package com.marjeyoun.jdeidetmarjeyoun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.marjeyoun.jdeidetmarjeyoun.death.Deaths_Activity;
import com.marjeyoun.jdeidetmarjeyoun.event.EventDetailsActivity;
import com.marjeyoun.jdeidetmarjeyoun.event.EventImageSliderAdapter;
import com.marjeyoun.jdeidetmarjeyoun.event.Events_Activity;
import com.marjeyoun.jdeidetmarjeyoun.news.News_Activity;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Charbel on 9/16/2017.
 */

public class HomePage_fragment extends Fragment {

    ImageButton btnBills, btnEvents, btnSocials, btnAbout, btnNews, btnLocation;
    CardView viewBills, viewEvents, viewAbout, viewSocials, viewNews, viewLocation;
    Animation leftToRight, rightToLeft;
    private View parentView;
    TextView lblLocation, lblDeaths, lblAbout, lblEvents, lblNews, lblFees;
    private static final Integer[] images = {R.drawable.newstest, R.drawable.home_background, R.drawable.newstest, R.drawable.home_background, R.drawable.newstest};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Integer> imagesArray = new ArrayList<Integer>();

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

        for (int i = 0; i < images.length; i++)
            imagesArray.add(images[i]);

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

        mPager = (ViewPager) parentView.findViewById(R.id.pager);
        mPager.setAdapter(new EventImageSliderAdapter(getActivity(), imagesArray));
        CircleIndicator indicator = (CircleIndicator) parentView.findViewById(R.id.indicator);
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
//                Intent i = new Intent(getActivity(), LoginActivity.class);
//                startActivity(i);
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
//                Intent i = new Intent(getActivity(), NotificationsActivity.class);
//                startActivity(i);
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
//                Intent intent = new Intent(getActivity(), CirculairSearchActivity.class);
//                getActivity().startActivity(intent);
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
