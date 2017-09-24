package com.marjeyoun.jdeidetmarjeyoun.death;

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

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.event.EventDetailsActivity;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Death;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class DeathsAdapter extends BaseAdapter {

    private List<Death> root;
    private Context context;
    private LayoutInflater mInflater;

    public DeathsAdapter(List<Death> root, Context context) {
        this.root = root;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return root.size();
    }

    @Override
    public Object getItem(int position) {
        return root.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) Math.random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Death death = (Death) getItem(position);
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

        lblName.setText(death.getName());
        lblDescription.setText(death.getDescription());

        return convertView;
    }
}
