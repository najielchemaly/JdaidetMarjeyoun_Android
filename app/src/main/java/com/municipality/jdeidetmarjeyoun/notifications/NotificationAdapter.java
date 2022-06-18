package com.municipality.jdeidetmarjeyoun.notifications;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Notification;
import com.municipality.jdeidetmarjeyoun.objects.Notifications;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    private List<Notification> root;
    private Context context;
    private LayoutInflater mInflater;

    public NotificationAdapter(Notifications notifications, Context context) {
        this.root = notifications.getNotifcations();
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

        Notification notification = (Notification) getItem(position);

        convertView = mInflater.inflate(R.layout.notifications_row_items, null);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);

        if("ar".equals(SelectLanguageActivity.lang)){
            txtTitle.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtDescription.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            txtTitle.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtDescription.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        txtTitle.setTypeface(AppUtil.setFontBold(context));
        txtTitle.setText(notification.getTitle());

        txtDescription.setTypeface(AppUtil.setFontRegular(context));
        txtDescription.setText(notification.getMessage());

        return convertView;
    }
}
