package com.municipality.jdeidetmarjeyoun.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import com.municipality.jdeidetmarjeyoun.objects.News;

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

        final News news = (News) getItem(position);
        if("ar".equals(SelectLanguageActivity.lang)){
            convertView = mInflater.inflate(R.layout.event_row_items_ar, null);
        }else {
            convertView = mInflater.inflate(R.layout.event_row_items_en, null);
        }

        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        ImageView imgEvent = (ImageView) convertView.findViewById(R.id.imgEvent);
        RelativeLayout view = (RelativeLayout) convertView.findViewById(R.id.viewClick);

        String image;
        if(news.getImages().size() > 0 && news.getImages()!= null){
            image = AppUtil.globalVariable.getMediaUrl() + news.getImages().get(0);
        }else {
            image = AppUtil.globalVariable.getMediaUrl() + AppUtil.globalVariable.getMediaDefaultImage();
        }

        Picasso.with(context)
                .load(image)
                .into(imgEvent);

        txtTitle.setText(news.getTitle());
        txtDescription.setText(news.getDescription());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, NewsDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("news", news);
                    context.startActivity(intent);
                } catch (Exception ex) {
                    Log.e("", ex.getLocalizedMessage());
                }
            }
        });

        return convertView;
    }
}
