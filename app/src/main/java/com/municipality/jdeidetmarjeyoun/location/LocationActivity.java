package com.municipality.jdeidetmarjeyoun.location;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.base.Predicate;
import android.support.test.espresso.core.deps.guava.base.Predicates;
import android.support.test.espresso.core.deps.guava.collect.Collections2;
import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.fees.Fees_Activity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.news.ObjectNews;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.objects.PlaceCategory;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 10/21/2017.
 */

public class LocationActivity extends AppCompatActivity {

    ImageButton btnBack, btnMenu;
    TextView lblTitle, lblMessage;
    MaterialSpinner spinnerType;
    EditText txtName;
    RelativeLayout viewNoSearch, viewSearch;
    Button btnSearch;
    ListView listLocations;
    LocationsAdapter locationsAdapter;
    boolean isArabic;
    LinearLayout viewMain;
    Animation drop_down, goUp;
    ProgressDialog progress;

    private List<News> filteredNews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("ar".equals(SelectLanguageActivity.lang)) {
            setContentView(R.layout.location_activity_ar);
            isArabic = true;
        } else {
            setContentView(R.layout.location_activity_en);
            isArabic = false;
        }

        try {
            initView();
            setFonts();
            initListeners();
        } catch (Exception e) {

        }
    }

    private void initView() throws Exception {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        spinnerType = (MaterialSpinner) findViewById(R.id.spinnerType);
        txtName = (EditText) findViewById(R.id.txtName);
        viewNoSearch = (RelativeLayout) findViewById(R.id.viewNoSearch);
        viewSearch = (RelativeLayout) findViewById(R.id.viewSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        listLocations = (ListView) findViewById(R.id.listLocations);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

        viewNoSearch.setVisibility(View.GONE);
        viewSearch.setVisibility(View.VISIBLE);

        if(AppUtil.globalVariable.getPlaceCategories().size() > 0) {
            spinnerType.setItems(AppUtil.transform(getResources().getString(R.string.select_category),
                    AppUtil.globalVariable.getPlaceCategories()));
        } else {
            spinnerType.setItems(getResources().getString(R.string.select_category));
        }

        progress = new ProgressDialog(LocationActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        ApiManager.getService().getNews("placestovisit", SelectLanguageActivity.lang).enqueue(new Callback<ObjectNews>() {
            @Override
            public void onResponse(Call<ObjectNews> call, Response<ObjectNews> response) {
                ObjectNews objectNews = response.body();
                if(objectNews.getNews() != null && objectNews.getNews().size() > 0){
                    filteredNews = objectNews.getNews();
                    locationsAdapter = new LocationsAdapter(filteredNews, LocationActivity.this);
                    listLocations.setAdapter(locationsAdapter);
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ObjectNews> call, Throwable t) {
                Log.e("e", t.getMessage());
                progress.dismiss();
            }
        });
    }

    private void initListeners() throws Exception {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

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
    }

    private void setFonts() throws Exception {
        lblMessage.setTypeface(AppUtil.setFontRegular(this));
        lblTitle.setTypeface(AppUtil.setFontRegular(this));
        txtName.setTypeface(AppUtil.setFontRegular(this));
        spinnerType.setTypeface(AppUtil.setFontRegular(this));
        btnSearch.setTypeface(AppUtil.setFontRegular(this));
    }

    private void filterData() {
        List<News> filteredNews = this.filteredNews;

        if(spinnerType.getText() != null && !spinnerType.getText().toString().isEmpty() &&
                !spinnerType.getText().toString().equals(getResources().getString(R.string.select_category))) {
            filteredNews = new ArrayList<>(Collections2.filter(filteredNews, new Predicate<News>() {
                @Override
                public boolean apply(@javax.annotation.Nullable News news) {
                    return news.getCategory().equals(spinnerType.getText().toString());
                }
            }));
        }

        if(txtName.getText() != null && !txtName.getText().toString().isEmpty()) {
            filteredNews = new ArrayList<>(Collections2.filter(filteredNews, new Predicate<News>() {
                @Override
                public boolean apply(@javax.annotation.Nullable News news) {
                    return news.getTitle().contains(txtName.getText().toString());
                }
            }));
        }

        if(filteredNews.size() == 0) {
            viewNoSearch.setVisibility(View.VISIBLE);
            viewSearch.setVisibility(View.GONE);
        } else {
            viewNoSearch.setVisibility(View.GONE);
            viewSearch.setVisibility(View.VISIBLE);

            locationsAdapter.root = filteredNews;
            locationsAdapter.notifyDataSetChanged();
        }

    }
}
