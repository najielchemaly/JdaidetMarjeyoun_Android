package com.municipality.jdeidetmarjeyoun.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/16/2017.
 */

public class News_Activity extends AppCompatActivity {

    ListView listNews;
    TextView lblTitle;
    NewsAdapter newsAdapter;
    ImageButton btnBack, btnMenu;
    LinearLayout viewMain;
    Animation drop_down, goUp;
    ProgressDialog progress;
    SwipeRefreshLayout mSwipeRefreshLayout;

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

    private void initView() throws Exception {

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        listNews = (ListView) findViewById(R.id.listNews);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);
        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            lblTitle.setText(bundle.getString("title"));
        }

        callService();
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

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewMain.getVisibility() == View.VISIBLE){
                    btnMenu.setImageResource(R.drawable.menu);
                    viewMain.startAnimation(goUp);
                    viewMain.setVisibility(View.GONE);
                }else {
                    btnMenu.setImageResource(R.drawable.close);
                    viewMain.startAnimation(drop_down);
                    viewMain.setVisibility(View.VISIBLE);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void callService() {
        progress = new ProgressDialog(News_Activity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        ApiManager.getService().getNews("latestnews", SelectLanguageActivity.lang).enqueue(new Callback<ObjectNews>() {
            @Override
            public void onResponse(Call<ObjectNews> call, Response<ObjectNews> response) {
                ObjectNews news = response.body();
                if(news.getNews() != null && news.getNews().size() > 0){
                    newsAdapter = new NewsAdapter(news.getNews(), getApplicationContext());
                    listNews.setAdapter(newsAdapter);
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ObjectNews> call, Throwable t) {
                Log.e("error", t.getMessage());
                progress.dismiss();
            }
        });
    }
}
