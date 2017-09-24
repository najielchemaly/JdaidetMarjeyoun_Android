package com.marjeyoun.jdeidetmarjeyoun.notifications;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 9/24/2017.
 */

public class Notifications_fragment extends Fragment {

    ListView listNotifications;
    NotificationAdapter notificationAdapter;
    private View parentView;

    public static Notifications_fragment newInstance() {
        Notifications_fragment fragment = new Notifications_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.notifications_fragment, container, false);

        try {
            initView();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception {
        listNotifications = (ListView) parentView.findViewById(R.id.listNotifications);


        notificationAdapter = new NotificationAdapter("ar".equals(SelectLanguageActivity.lang) ? createDummyNotifiactionsAr() : createDummyNotificationsEn(), getActivity());
        listNotifications.setAdapter(notificationAdapter);
    }

    private List<Notification> createDummyNotifiactionsAr() {
        List<Notification> itemList = new ArrayList<>();

        Notification notification = new Notification();
        notification.setDescription("بمناسبة عيد الجيش ندعوكم للمشاركة في يوم الشباب الرياضي على بولفار جديده مرجعيون");
        itemList.add(notification);

        notification = new Notification();
        notification.setDescription("موعدكم مع نشاط البلدية \"سوق الأكل\" في سحت جديده مرجعيون الأحد ٣٠ تموز ");
        itemList.add(notification);


        return itemList;
    }

    private List<Notification> createDummyNotificationsEn() {
        List<Notification> itemList = new ArrayList<>();

        Notification notification = new Notification();
        notification.setDescription("On the occasion of the Army Day, we invite you to participate in the Youth Sports Day on the new Marjayoun Boulevard");
        itemList.add(notification);

        notification = new Notification();
        notification.setDescription("Your date with the activity of the municipality \"Market of eating\" in Sahat new Marjayoun Sunday, July 30");
        itemList.add(notification);

        return itemList;
    }
}
