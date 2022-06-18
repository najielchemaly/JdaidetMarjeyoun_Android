package com.municipality.jdeidetmarjeyoun.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.Menu;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.RegisteredUser;
import com.municipality.jdeidetmarjeyoun.objects.User;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 11/3/2017.
 */

public class EditProfileActivity extends AppCompatActivity {

    TextView lblTitle;
    Button btnSave;
    ImageButton btnBack, btnMenu;
    EditText txtName, txtPhone, txtFullname, txtEmail, txtLocation;
    LinearLayout viewMain;
    Animation drop_down, goUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        try{
            initView();
            initListeners();
            setFonts();
        }catch (Exception e){
            Log.e("e",e.getMessage());
        }
    }

    private void initView() throws Exception {

        lblTitle = (TextView) findViewById(R.id.lblTitle);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtFullname = (EditText) findViewById(R.id.txtFullname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        viewMain = (LinearLayout) findViewById(R.id.viewMain);
        goUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        drop_down = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            txtName.setText(bundle.getString("name"));
            txtFullname.setText(bundle.getString("fullname"));
            txtLocation.setText(bundle.getString("location"));
            txtEmail.setText(bundle.getString("email"));
            txtPhone.setText(bundle.getString("phone"));
        }

        viewMain.addView(new Menu(this));
        viewMain.setVisibility(View.GONE);

       setViewLanguage();
    }

    private void setFonts() throws Exception {
        lblTitle.setTypeface(AppUtil.setFontRegular(this));
        btnSave.setTypeface(AppUtil.setFontRegular(this));
        txtName.setTypeface(AppUtil.setFontRegular(this));
        txtPhone.setTypeface(AppUtil.setFontRegular(this));
        txtFullname.setTypeface(AppUtil.setFontRegular(this));
        txtEmail.setTypeface(AppUtil.setFontRegular(this));
        txtLocation.setTypeface(AppUtil.setFontRegular(this));
    }

    private void initListeners() throws Exception {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiManager.getService().editProfile(AppUtil.user.getId(), txtFullname.getText().toString(), txtPhone.getText().toString(), txtEmail.getText().toString(), txtLocation.getText().toString()).enqueue(new Callback<RegisteredUser>() {
                    @Override
                    public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                        if (response.isSuccessful() && response.body().getStatus() == 1) {
                            AppUtil.saveUser(EditProfileActivity.this, response.body().getUser());
                            finish();
                        }

                        if (response.body().getMessage() != null) {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisteredUser> call, Throwable t) {
                        Log.d("", t.getMessage());
                    }
                });
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
                if (viewMain.getVisibility() == View.VISIBLE) {
                    btnMenu.setImageResource(R.drawable.menu);
                    viewMain.startAnimation(goUp);
                    viewMain.setVisibility(View.GONE);
                } else {
                    btnMenu.setImageResource(R.drawable.close);
                    viewMain.startAnimation(drop_down);
                    viewMain.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setViewLanguage(){
        if("ar".equals(SelectLanguageActivity.lang)){
            btnSave.setText("حفظ");
            lblTitle.setText("تعديل الملف الشخصي");
            txtName.setHint("اسم المستخدم");
            txtEmail.setHint("البريد الإلكتروني");
            txtLocation.setHint("الموقع");
            txtPhone.setHint("رقم الهاتف");
            txtName.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtPhone.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtFullname.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtEmail.setGravity(Gravity.CENTER | Gravity.RIGHT);
            txtLocation.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }else {
            btnSave.setText("Save");
            lblTitle.setText("Edit Profile");
            txtName.setHint("Username");
            txtLocation.setHint("Location");
            txtEmail.setHint("Email");
            txtPhone.setHint("Phone");
            txtName.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtPhone.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtPhone.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtFullname.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtEmail.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtLocation.setGravity(Gravity.CENTER | Gravity.LEFT);
        }
    }
}
