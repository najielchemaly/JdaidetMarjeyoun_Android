package com.municipality.jdeidetmarjeyoun.fees;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.aboutus.AboutusActivity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Fees;
import com.municipality.jdeidetmarjeyoun.objects.FeesInfo;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/24/2017.
 */

public class Fees_Activity extends AppCompatActivity {

    TextView lblTitle, lblAmount, lblBlockName, lblYear, txtAmount, txtBlockName, txtYear;
    EditText txtSection, txtBlockNumber;
    MaterialSpinner spinnerYear;
    RelativeLayout viewResult, overlay;
    ImageButton btnBack, btnMenu;
    Button btnCloseResult;
    Button btnSearch;
    LinearLayout viewMain;
    ProgressDialog progress;
    Animation drop_down, goUp, drop_down_dialog, goUp_dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("ar".equals(SelectLanguageActivity.lang)) {
            setContentView(R.layout.fees_activity_ar);
        } else {
            setContentView(R.layout.fees_activity_en);
        }

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private void initView() throws Exception {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnCloseResult = (Button) findViewById(R.id.btnCloseResult);
        txtSection = (EditText) findViewById(R.id.txtSection);
        txtBlockNumber = (EditText) findViewById(R.id.txtBlockNumber);
        spinnerYear = (MaterialSpinner) findViewById(R.id.spinnerYear);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblAmount = (TextView) findViewById(R.id.lblAmount);
        lblBlockName = (TextView) findViewById(R.id.lblBlockName);
        lblYear = (TextView) findViewById(R.id.lblYear);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtBlockName = (TextView) findViewById(R.id.txtBlockName);
        txtYear = (TextView) findViewById(R.id.txtYear);
        viewResult = (RelativeLayout) findViewById(R.id.viewResult);
        overlay = (RelativeLayout) findViewById(R.id.overlay);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);
        goUp_dialog = AnimationUtils.loadAnimation(this, R.anim.go_up);
        drop_down_dialog = AnimationUtils.loadAnimation(this, R.anim.drop_down);

        spinnerYear.setItems(AppUtil.getYears());

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);
        viewResult.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
        overlay.setAlpha(0f);
    }

    private void styleView() throws Exception {
        txtSection.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtBlockNumber.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        spinnerYear.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnSearch.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        lblTitle.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        txtYear.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtBlockName.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtAmount.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        lblAmount.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        lblBlockName.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        lblYear.setTypeface(AppUtil.setFontBold(getApplicationContext()));
    }

    private void initListeners() throws Exception {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StringUtils.isValid(txtBlockNumber.getText()) && StringUtils.isValid(txtSection.getText())){
                    final Fees fees = new Fees();
                    fees.setYear(spinnerYear.getText().toString().trim());
                    fees.setBlocknumber(txtBlockNumber.getText().toString());
                    fees.setSection(txtSection.getText().toString());

                    progress = new ProgressDialog(Fees_Activity.this);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setMessage(getResources().getString(R.string.loading_message));
                    progress.setIndeterminate(true);
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();

                    ApiManager.getService().getFees(SelectLanguageActivity.lang, fees.getBlocknumber(), fees.getSection(), fees.getYear()).enqueue(new Callback<FeesInfo>() {
                        @Override
                        public void onResponse(Call<FeesInfo> call, Response<FeesInfo> response) {
                            FeesInfo feesInfo = response.body();

                            if(feesInfo.getStatus() == 1){
                                if (feesInfo.getFees() != null) {
                                    txtAmount.setText(feesInfo.getFees().getAmount());
                                    txtBlockName.setText(feesInfo.getFees().getBlocknumber());
                                    txtYear.setText(feesInfo.getFees().getYear());

                                    viewResult.startAnimation(goUp_dialog);
                                    viewResult.setVisibility(View.VISIBLE);

                                    overlay.setVisibility(View.VISIBLE);
                                    overlay.animate().alpha(1f).setDuration(500);
                                } else {
                                    AppUtil.showError(Fees_Activity.this, getResources().getString(R.string.no_result));
                                }

                                progress.dismiss();
                            }else {
                                AppUtil.showError(Fees_Activity.this, getResources().getString(R.string.error_message));
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<FeesInfo> call, Throwable t) {
                            progress.dismiss();
                        }
                    });
                }else {
                    AppUtil.showError(Fees_Activity.this, getResources().getString(R.string.fill_fields_error));
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewMain.getVisibility() == View.VISIBLE) {
                    btnMenu.setImageResource(R.drawable.menu);
                    viewMain.startAnimation(goUp);
                    viewMain.setVisibility(View.GONE);
                } else {
                    viewMain.setVisibility(View.VISIBLE);
                    viewMain.startAnimation(drop_down);
                    btnMenu.setImageResource(R.drawable.close);
                }
            }
        });

        btnCloseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResult.startAnimation(drop_down_dialog);

                overlay.setVisibility(View.GONE);
                overlay.animate().alpha(0f).setDuration(500);
                drop_down_dialog.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        viewResult.setVisibility(View.GONE);
                    }
                });
            }
        });

        viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Workaround
            }
        });

        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}


