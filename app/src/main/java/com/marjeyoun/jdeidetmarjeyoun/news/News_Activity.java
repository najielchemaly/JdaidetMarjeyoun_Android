package com.marjeyoun.jdeidetmarjeyoun.news;

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
import com.marjeyoun.jdeidetmarjeyoun.objects.News;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 9/16/2017.
 */

public class News_Activity extends AppCompatActivity {

    ListView listNews;
    TextView lblTitle;
    NewsAdapter newsAdapter;
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        try {
            initView();
            styleView();
            initListeners();
        }catch (Exception e){
            Log.e("error: ", e.getMessage());
        }
    }

    private List<News> createDummyEvents() {
        List<News> newses = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            News news = new News();
            news.setTitle("News Title" + i);
            news.setDescription("News Description" + i);
            newses.add(news);
        }

        return newses;
    }

    private void initView() throws Exception {

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        listNews = (ListView) findViewById(R.id.listNews);
        lblTitle = (TextView) findViewById(R.id.lblTitle);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
         lblTitle.setText(b.getString("title"));
        }

        newsAdapter = new NewsAdapter(createDummyEvents(), getApplicationContext());
        listNews.setAdapter(newsAdapter);
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
