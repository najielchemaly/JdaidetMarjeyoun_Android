package com.municipality.jdeidetmarjeyoun.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Link_;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class LinksAdapter extends BaseAdapter {

    private List<Link_> root;
    private Context context;
    private LayoutInflater mInflater;

    public LinksAdapter(List<Link_> root, Context context) {
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

        final Link_ link = (Link_) getItem(position);

        convertView = mInflater.inflate(R.layout.links_row_item, null);


        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtLink = (TextView) convertView.findViewById(R.id.txtLink);
        LinearLayout viewLinks = (LinearLayout) convertView.findViewById(R.id.viewLinks);

        if("ar".equals(SelectLanguageActivity.lang)){
            txtTitle.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtLink.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            txtTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtLink.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        txtTitle.setText(link.getTitle());
        txtLink.setText(link.getUrl());

        viewLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                if(link.getUrl().contains("http://") || link.getUrl().contains("https://")){
                    httpIntent.setData(Uri.parse(link.getUrl()));
                }else {
                    httpIntent.setData(Uri.parse("http://" + link.getUrl()));
                }

                httpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(httpIntent);
            }
        });

        return convertView;
    }
}
