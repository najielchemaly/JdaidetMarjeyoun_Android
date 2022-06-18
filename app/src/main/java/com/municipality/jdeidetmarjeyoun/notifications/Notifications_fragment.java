package com.municipality.jdeidetmarjeyoun.notifications;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.event.Events_Activity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Notifications;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/24/2017.
 */

public class Notifications_fragment extends Fragment {

    ListView listNotifications;
    NotificationAdapter notificationAdapter;
    private View parentView;
    ProgressDialog progress;
    SwipeRefreshLayout mSwipeRefreshLayout;

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
            initListeners();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception {
        listNotifications = (ListView) parentView.findViewById(R.id.listNotifications);
        mSwipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipeToRefresh);

        getNotifications();
    }

    private void initListeners() throws Exception {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotifications();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getNotifications(){
        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        ApiManager.getService().getNotifications(SelectLanguageActivity.lang).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                if(response.isSuccessful() && response.body().getStatus() == 1) {
                    if(response.body().getNotifcations() != null && response.body().getNotifcations().size() > 0){
                        notificationAdapter = new NotificationAdapter(response.body(), getActivity());
                        listNotifications.setAdapter(notificationAdapter);
                    }
                }
                else{
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Log.e("e",t.getMessage());
                progress.dismiss();
            }
        });
    }
}
