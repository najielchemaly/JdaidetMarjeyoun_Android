package com.marjeyoun.jdeidetmarjeyoun.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.objects.Event;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 9/16/2017.
 */

public class Events_Activity extends AppCompatActivity {

    ListView listEvents;
    TextView lblTitle;
    EventsAdapter eventsAdapter;
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        try {
            initView();
            styleView();
            initListeners();
        }catch (Exception e){
            Log.e("error: ", e.getMessage());
        }
    }

    private List<Event> createDummyEvents() {
        List<Event> events = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            Event event = new Event();
            event.setTitle("asdasd" + i);
            event.setDescription("des" + i);
            events.add(event);
        }

        return events;
    }

    private void initView() throws Exception {

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        listEvents = (ListView) findViewById(R.id.listEvents);
        lblTitle = (TextView) findViewById(R.id.lblTitle);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
         lblTitle.setText(b.getString("title"));
        }

        eventsAdapter = new EventsAdapter(createDummyEvents(), getApplicationContext());
        listEvents.setAdapter(eventsAdapter);
    }

    private void styleView() throws Exception {
        lblTitle.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
    }

    private void initListeners() throws Exception {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
