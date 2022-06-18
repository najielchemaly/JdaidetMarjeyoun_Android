package com.municipality.jdeidetmarjeyoun.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.forgotpassword.ForgotPasswordActivity;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.LoginUser;
import com.municipality.jdeidetmarjeyoun.objects.RegisteredUser;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.signup.SignupActivity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 11/21/2017.
 */

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnSignup, buttonForgot;
    LinearLayout viewSkip;
    TextView lblSkip, lblLogin;
    LoginUser loginUser;

    ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("ar".equals(SelectLanguageActivity.lang)) {
            setContentView(R.layout.login_activity_ar);
        } else {
            setContentView(R.layout.login_activity_en);
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
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        viewSkip = (LinearLayout) findViewById(R.id.viewSkip);
        lblSkip = (TextView) findViewById(R.id.lblSkip);
//        lblLogin = (TextView) findViewById(R.id.lblLogin);
        buttonForgot = (Button) findViewById(R.id.buttonForgot);
    }

    private void styleView() throws Exception {
//        lblLogin.setTypeface(AppUtil.setFontBold(this));
        txtUsername.setTypeface(AppUtil.setFontRegular(this));
        txtPassword.setTypeface(AppUtil.setFontRegular(this));
        lblSkip.setTypeface(AppUtil.setFontRegular(this));
        btnLogin.setTypeface(AppUtil.setFontRegular(this));
        btnSignup.setTypeface(AppUtil.setFontRegular(this));
        buttonForgot.setTypeface(AppUtil.setFontBold(this));
    }

    private void initListeners() throws Exception {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    try {
                        progress = new ProgressDialog(LoginActivity.this);
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setMessage(getResources().getString(R.string.loading_message));
                        progress.setIndeterminate(true);
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        ApiManager.getService().login(txtUsername.getText().toString(), txtPassword.getText().toString()).enqueue(new Callback<RegisteredUser>() {
                            @Override
                            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                                if (response.isSuccessful() && response.body().getStatus() == 1) {
                                    AppUtil.saveUser(LoginActivity.this, response.body().getUser());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                    progress.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                                Log.e("e",t.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        lblSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate() {

        if (!StringUtils.isValid(txtUsername.getText())) {
            AppUtil.showError(LoginActivity.this, getResources().getString(R.string.error_username));
            return false;
        }
        if (!StringUtils.isValid(txtPassword.getText())) {
            AppUtil.showError(LoginActivity.this, getResources().getString(R.string.error_password));
            return false;
        }

        loginUser = new LoginUser();
        loginUser.setPassword(txtPassword.getText().toString());
        loginUser.setUserName(txtUsername.getText().toString());
        return true;
    }
}
