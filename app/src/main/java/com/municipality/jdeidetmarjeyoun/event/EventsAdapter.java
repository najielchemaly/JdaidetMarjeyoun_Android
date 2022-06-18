package com.municipality.jdeidetmarjeyoun.event;

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
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.municipality.jdeidetmarjeyoun.objects.News;

/**
 * Created by Charbel on 8/24/2017.
 */

public class EventsAdapter extends BaseAdapter {

    private List<News> root;
    private Context context;
    private LayoutInflater mInflater;

    public EventsAdapter(List<News> root, Context context) {
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

        final News event = (News) getItem(position);
        if("ar".equals(SelectLanguageActivity.lang)){
            convertView = mInflater.inflate(R.layout.event_row_items_ar, null);
        }else {
            convertView = mInflater.inflate(R.layout.event_row_items_en, null);
        }

        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        ImageView imgEvent = (ImageView) convertView.findViewById(R.id.imgEvent);
        RelativeLayout view = (RelativeLayout) convertView.findViewById(R.id.viewClick);

        txtTitle.setText(event.getTitle());
        txtDescription.setText(event.getDescription());

        String image;

        if(event.getImages().size() > 0 && event.getImages()!= null){
            image = AppUtil.globalVariable.getMediaUrl() + event.getImages().get(0);
        }else {
            image = AppUtil.globalVariable.getMediaUrl() + AppUtil.globalVariable.getMediaDefaultImage();
        }
        Picasso.with(context)
                .load(image)
                .into(imgEvent);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("events", event);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
