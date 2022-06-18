package com.municipality.jdeidetmarjeyoun;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.aboutus.AboutusActivity;
import com.municipality.jdeidetmarjeyoun.death.Deaths_Activity;
import com.municipality.jdeidetmarjeyoun.event.Events_Activity;
import com.municipality.jdeidetmarjeyoun.fees.Fees_Activity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.location.LocationActivity;
import com.municipality.jdeidetmarjeyoun.news.News_Activity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

/**
 * Created by Charbel on 11/1/2017.
 */

public class Menu extends RelativeLayout {
    private TextView lblNews, lblActivities, lblFees, lblLocation, lblAboutus, lblDeaths, lblHome;

    public Menu(Context context) {
        super(context);
        init(context);
    }

    public Menu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Menu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        inflate(getContext(), R.layout.menu_view_layout, this);
        lblHome = (TextView) findViewById(R.id.lblHome);
        lblNews = (TextView) findViewById(R.id.lblNews);
        lblActivities = (TextView) findViewById(R.id.lblActivities);
        lblFees = (TextView) findViewById(R.id.lblFees);
        lblLocation = (TextView) findViewById(R.id.lblLocation);
        lblAboutus = (TextView) findViewById(R.id.lblAboutus);
        lblDeaths = (TextView) findViewById(R.id.lblDeaths);

        lblHome.setTypeface(AppUtil.setFontRegular(context));
        lblNews.setTypeface(AppUtil.setFontRegular(context));
        lblActivities.setTypeface(AppUtil.setFontRegular(context));
        lblFees.setTypeface(AppUtil.setFontRegular(context));
        lblLocation.setTypeface(AppUtil.setFontRegular(context));
        lblAboutus.setTypeface(AppUtil.setFontRegular(context));
        lblDeaths.setTypeface(AppUtil.setFontRegular(context));

        if ("ar".equals(SelectLanguageActivity.lang)) {
            lblHome.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblNews.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblActivities.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblFees.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblLocation.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblAboutus.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblDeaths.setGravity(Gravity.CENTER | Gravity.RIGHT);
        } else {
            lblHome.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblNews.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblActivities.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblFees.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblLocation.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblAboutus.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblDeaths.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        lblHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        lblNews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, News_Activity.class);
                context.startActivity(intent);
            }
        });

        lblActivities.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Events_Activity.class);
                context.startActivity(intent);
            }
        });

        lblFees.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fees_Activity.class);
                context.startActivity(intent);
            }
        });

        lblLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocationActivity.class);
                context.startActivity(intent);
            }
        });

        lblAboutus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutusActivity.class);
                context.startActivity(intent);
            }
        });

        lblDeaths.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Deaths_Activity.class);
                context.startActivity(intent);
            }
        });
    }
}