package com.municipality.jdeidetmarjeyoun.death;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.event.EventDetailsActivity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Death;
import com.municipality.jdeidetmarjeyoun.objects.Social;
import com.municipality.jdeidetmarjeyoun.objects.SocialNews;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class DeathsAdapter extends BaseAdapter {

    private List<Death> root;
    private Context context;
    private LayoutInflater mInflater;
    SocialNews socialNews;

    public DeathsAdapter(SocialNews socialNews, Context context) {
        this.root = root;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.socialNews = socialNews;
    }

    @Override
    public int getCount() {
        return socialNews.getSocials().size();
    }

    @Override
    public Object getItem(int position) {
        return socialNews.getSocials().get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) Math.random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Social death = (Social) getItem(position);
        convertView = mInflater.inflate(R.layout.deaths_row_items, null);

        TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
        TextView lblDescription = (TextView) convertView.findViewById(R.id.lblDescription);

        lblName.setTypeface(AppUtil.setFontBold(context));
        lblDescription.setTypeface(AppUtil.setFontRegular(context));

        if("ar".equals(SelectLanguageActivity.lang)){
            lblName.setGravity(Gravity.CENTER | Gravity.RIGHT);
            lblDescription.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            lblName.setGravity(Gravity.CENTER | Gravity.LEFT);
            lblDescription.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        lblName.setText(death.getTitle());
        lblDescription.setText(death.getDescription());

        return convertView;
    }
}
