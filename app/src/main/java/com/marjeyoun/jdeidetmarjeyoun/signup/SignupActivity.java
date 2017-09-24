package com.marjeyoun.jdeidetmarjeyoun.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.MainActivity;
import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;
import com.marjeyoun.jdeidetmarjeyoun.utils.StringUtils;

/**
 * Created by Charbel on 9/13/2017.
 */

public class SignupActivity extends AppCompatActivity {

    TextView lblSignup, lblSubTitle, lblSkip;
    EditText txtUsername, txtPassword, txtPasswordConfirm, txtPhone;
    Button btnSignup, btnCancel, btnConfirm;
    RelativeLayout viewAlert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if("ar".equals(SelectLanguageActivity.lang)){
            setContentView(R.layout.signup_activity_ar);
        }else {
            setContentView(R.layout.signup_activity_en);
        }

        try{
            initView();
            styleView();
            initListeners();
        }catch (Exception e){
            AppUtil.showError(SignupActivity.this, e.getMessage());
        }
    }

    private void initView() throws Exception {
        lblSignup = (TextView) findViewById(R.id.lblSignup);
        lblSubTitle = (TextView) findViewById(R.id.lblSubTitle);
        lblSkip = (TextView) findViewById(R.id.lblSkip);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        viewAlert = (RelativeLayout) findViewById(R.id.viewAlert);
    }

    private void styleView() throws Exception{
        lblSignup.setTypeface(AppUtil.setFontBold(getApplicationContext()));
        lblSubTitle.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtUsername.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPassword.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPasswordConfirm.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        txtPhone.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnSignup.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        lblSkip.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnConfirm.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
        btnCancel.setTypeface(AppUtil.setFontRegular(getApplicationContext()));
    }

    private void initListeners() throws Exception {

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        lblSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean validate() {
        if (StringUtils.isValid(txtUsername.getText())
                && StringUtils.isValid(txtPassword.getText())
                && StringUtils.isValid(txtPasswordConfirm.getText())
                && StringUtils.isValid(txtPhone.getText())) {
            return true;
        } else {
            if (!StringUtils.isValid(txtUsername.getText())) {
                AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang)? "اسم المستخدم مفقود" : "No username");
            } else if (!StringUtils.isValid(txtPassword.getText())) {
                AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang)? "كلمة مرور مفقودة" : "No password");
            } else if (!StringUtils.isValid(txtPasswordConfirm.getText()) || !(txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString()))) {
                AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang)? "كلمات المرور غير متطابقة" : "Passwords do not match");
            } else if (!StringUtils.isValid(txtPhone.getText())) {
                AppUtil.showError(SignupActivity.this, "ar".equals(SelectLanguageActivity.lang)? "رقم الهاتف مفقود" : "No phone number");
            }
            return false;
        }
    }
}
