package com.municipality.jdeidetmarjeyoun.location;

import android.content.Context;
import android.content.Intent;
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
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class LocationsAdapter extends BaseAdapter {

    public List<News> root;
    private Context context;
    private LayoutInflater mInflater;

    public LocationsAdapter(List<News> root, Context context) {
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
            convertView = mInflater.inflate(R.layout.location_row_item_ar, null);
        }else {
            convertView = mInflater.inflate(R.layout.location_row_item_en, null);
        }

        TextView txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
        ImageView imgLocation = (ImageView) convertView.findViewById(R.id.imgLocation);
        RelativeLayout view = (RelativeLayout) convertView.findViewById(R.id.viewClick);

        txtLocation.setText(news.getTitle());

        String image = AppUtil.globalVariable.getMediaUrl() + (news.getImages().size() > 0 ? news.getImages().get(0) : AppUtil.globalVariable.getMediaDefaultImage());
        Picasso.with(context)
                .load(image)
                .into(imgLocation);

//        imgLocation.setImageResource(news.getImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocationDetailsActivity.class);
                intent.putExtra("news", news);
                intent.putExtra("lat", news.getLocation().split(",")[0]);
                intent.putExtra("long", news.getLocation().split(",")[1]);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
