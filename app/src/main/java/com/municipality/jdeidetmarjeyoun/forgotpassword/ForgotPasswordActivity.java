package com.municipality.jdeidetmarjeyoun.forgotpassword;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.RegisteredUser;
import com.municipality.jdeidetmarjeyoun.objects.SimpleResponse;
import com.municipality.jdeidetmarjeyoun.profile.EditProfileActivity;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText txtEmail;
    Button btnReset;
    LinearLayout viewCancel;

    ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("ar".equals(SelectLanguageActivity.lang)) {
            setContentView(R.layout.forgot_activity_ar);
        } else {
            setContentView(R.layout.forgot_activity_en);
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
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnReset = (Button) findViewById(R.id.btnReset);
        viewCancel = (LinearLayout) findViewById(R.id.viewCancel);
    }

    private void styleView() throws Exception {
        txtEmail.setTypeface(AppUtil.setFontRegular(this));
        btnReset.setTypeface(AppUtil.setFontRegular(this));
    }

    private void initListeners() throws Exception {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    ApiManager.getService().forgotPassword(txtEmail.getText().toString()).enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.body().getMessage() != null) {
                                Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            if (response.isSuccessful() && response.body().getStatus() == 1) {
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            Log.d("", t.getMessage());
                        }
                    });
                }
            }
        });
        viewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validate() {

        if (!StringUtils.isValid(txtEmail.getText())) {
            AppUtil.showError(ForgotPasswordActivity.this, getResources().getString(R.string.error_email));
            return false;
        }

        return true;
    }
}
