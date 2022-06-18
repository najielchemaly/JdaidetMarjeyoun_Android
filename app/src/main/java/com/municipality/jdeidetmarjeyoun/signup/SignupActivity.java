package com.municipality.jdeidetmarjeyoun.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Profile;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/13/2017.
 */

public class SignupActivity extends AppCompatActivity {

    TextView lblSignup, lblSubTitle, lblCancel;
    EditText txtUsername, txtPassword, txtPasswordConfirm, txtPhone, txtFullname;
    Button btnSignup, btnCancel, btnConfirm;
    RelativeLayout viewOverlay;
    RelativeLayout viewAlert;
    ProgressDialog progress;
    Animation goUp, dropDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("ar".equals(SelectLanguageActivity.lang)) {
            setContentView(R.layout.signup_activity_ar);
        } else {
            setContentView(R.layout.signup_activity_en);
        }

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            AppUtil.showError(SignupActivity.this, e.getMessage());
        }
    }

    private void initView() throws Exception {
        lblSignup = (TextView) findViewById(R.id.lblSignup);
        lblSubTitle = (TextView) findViewById(R.id.lblSubTitle);
        lblCancel = (TextView) findViewById(R.id.lblCancel);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtFullname = (EditText) findViewById(R.id.txtFullname);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);
        viewOverlay = (RelativeLayout) findViewById(R.id.viewOverlay);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        viewAlert = (RelativeLayout) findViewById(R.id.viewAlert);

        goUp = AnimationUtils.loadAnimation(this, R.anim.go_up);
        dropDown = AnimationUtils.loadAnimation(this, R.anim.drop_down);
    }

    private void styleView() throws Exception {
        lblSignup.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        lblSubTitle.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtUsername.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPassword.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPasswordConfirm.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPhone.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtFullname.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnSignup.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        lblCancel.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnConfirm.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnCancel.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
    }

    private void initListeners() throws Exception {

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showAlert();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lblCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAlert();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setFullName(txtFullname.getText().toString());
                user.setUserName(txtUsername.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                user.setPhoneNumber(txtPhone.getText().toString());

                progress = new ProgressDialog(SignupActivity.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage(getResources().getString(R.string.loading_message));
                progress.setIndeterminate(true);
                progress.setCanceledOnTouchOutside(false);
                progress.show();

                ApiManager.getService().registerUser(txtUsername.getText().toString(), txtFullname.getText().toString(), txtPhone.getText().toString(), txtPasswordConfirm.getText().toString()).enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Profile profile = response.body();
                        if (response.body() != null && profile.getStatus() == 1 && response.isSuccessful()) {
                            saveUser(SignupActivity.this, profile.getUser());
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        progress.dismiss();
                        hideAlert();
                        Log.e("e", t.getMessage());
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAlert();
            }
        });
    }

    // save user in shared pref and locally
    public void saveUser(Context context, User user) {
        AppUtil.user = user;

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(AppUtil.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(AppUtil.PREFS_USER, json);
        editor.apply();
    }

    private void showAlert() {
        viewAlert.startAnimation(goUp);
        viewAlert.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
        viewOverlay.animate().alpha(1f).setDuration(500);
    }

    private void hideAlert() {
        viewAlert.startAnimation(dropDown);
        dropDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewAlert.setVisibility(View.GONE);
                viewOverlay.setVisibility(View.GONE);
                viewOverlay.animate().alpha(0f).setDuration(500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private boolean validate() {
        if (StringUtils.isValid(txtUsername.getText())
                && StringUtils.isValid(txtPassword.getText())
                && StringUtils.isValid(txtPasswordConfirm.getText())
                && StringUtils.isValid(txtPhone.getText())
                && StringUtils.isValid(txtFullname.getText())) {
            return true;
        } else {
            if (!StringUtils.isValid(txtFullname.getText())) {
                AppUtil.showError(SignupActivity.this, getResources().getString(R.string.error_fullname));
            } else if (!StringUtils.isValid(txtUsername.getText())) {
                AppUtil.showError(SignupActivity.this, getResources().getString(R.string.error_username));
            } else if (!StringUtils.isValid(txtPassword.getText())) {
                AppUtil.showError(SignupActivity.this, getResources().getString(R.string.error_password));
            } else if (!StringUtils.isValid(txtPasswordConfirm.getText()) || !(txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString()))) {
                AppUtil.showError(SignupActivity.this, getResources().getString(R.string.error_passwords_confirmation));
            } else if (!StringUtils.isValid(txtPhone.getText())) {
                AppUtil.showError(SignupActivity.this, getResources().getString(R.string.error_password));
            }
            return false;
        }
    }

    private boolean validate2() {
        if (!StringUtils.isValid(txtFullname.getText())) {
            AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang) ? "الاسم الكامل مفقود" : "Fullname field cannot be empty");
            return false;
        }
        if (!StringUtils.isValid(txtUsername.getText())) {
            AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang) ? "لا يمكن أن يكون حقل اسم المستخدم فارغا" : "Username field cannot be empty");
            return false;
        }
        if (!StringUtils.isValid(txtPassword.getText())) {
            AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang) ? "لا يمكن أن يكون حقل كلمة المرور فارغا" : "Password field cannot be empty");
            return false;
        }
        if (!StringUtils.isValid(txtPasswordConfirm.getText()) || !(txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString()))) {
            AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang) ? "كلمات المرور غير متطابقة" : "Passwords do not match");
            return false;
        }
        if (!StringUtils.isValid(txtPhone.getText())) {
            AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang) ? "رقم الهاتف مفقود" : "Phone number field cannot be empty");
            return false;
        }
        return true;
    }
}
