package com.marjeyoun.jdeidetmarjeyoun.notifications;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Notification;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    private List<Notification> root;
    private Context context;
    private LayoutInflater mInflater;

    public NotificationAdapter(List<Notification> root, Context context) {
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

        Notification notification = (Notification) getItem(position);

        convertView = mInflater.inflate(R.layout.notifications_row_items, null);

        TextView txtNotification = (TextView) convertView.findViewById(R.id.txtNotification);

        if("ar".equals(SelectLanguageActivity.lang)){
            txtNotification.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            txtNotification.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        txtNotification.setTypeface(AppUtil.setFontRegular(context));

        txtNotification.setText(notification.getDescription());

        return convertView;
    }
}
