package com.marjeyoun.jdeidetmarjeyoun.news;

import android.content.Context;
import android.content.Intent;
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
import com.marjeyoun.jdeidetmarjeyoun.objects.Event;
import com.marjeyoun.jdeidetmarjeyoun.objects.News;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class NewsAdapter extends BaseAdapter {

    private List<News> root;
    private Context context;
    private LayoutInflater mInflater;

    public NewsAdapter(List<News> root, Context context) {
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

        News news = (News) getItem(position);
        if("ar".equals(SelectLanguageActivity.lang)){
            convertView = mInflater.inflate(R.layout.event_row_items_ar, null);
        }else {
            convertView = mInflater.inflate(R.layout.event_row_items_en, null);
        }

        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        ImageView imgEvent = (ImageView) convertView.findViewById(R.id.imgEvent);
        RelativeLayout view = (RelativeLayout) convertView.findViewById(R.id.viewClick);

        txtTitle.setText(news.getTitle());
        txtDescription.setText(news.getDescription());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
