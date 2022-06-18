package com.municipality.jdeidetmarjeyoun.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.MainActivity;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.login.LoginActivity;
import com.municipality.jdeidetmarjeyoun.objects.LoginUser;
import com.municipality.jdeidetmarjeyoun.objects.SimpleResponse;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.signup.SignupActivity;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 10/29/2017.
 */

public class ProfileFragment extends Fragment {

    TextView lblFullName, lblEditProfile, txtName, txtEmail, txtPhone,
            txtLocation, lblSettings, txtNotification, txtLanguage, txtPassword, lblPass, lblLanguage, lblMessage, txtLogout;
    EditText txtNewPass, txtReNewPass;
    Button btnSave, btnEnglish, btnArabic;
    CardView viewInfo;
    Switch switchNotification;
    RelativeLayout viewChangeLang, viewChangePass, viewLogout;
    Animation goUp, dropDown;
    LinearLayout viewLanguage, viewPassword;
    RelativeLayout viewOpacity, viewNoProfile;
    int LANGUAGE = 1;
    int PASSWORD = 0;
    private View parentView;
    ProgressDialog progress;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if ("ar".equals(SelectLanguageActivity.lang)) {
            parentView = inflater.inflate(R.layout.profile_fragment_ar, container, false);
        } else {
            parentView = inflater.inflate(R.layout.profile_fragment_en, container, false);
        }

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception {
        switchNotification = (Switch) parentView.findViewById(R.id.switchNotification);
        viewChangeLang = (RelativeLayout) parentView.findViewById(R.id.viewChangeLang);
        viewChangePass = (RelativeLayout) parentView.findViewById(R.id.viewChangePass);
        viewNoProfile = (RelativeLayout) parentView.findViewById(R.id.viewNoProfile);
        txtNotification = (TextView) parentView.findViewById(R.id.txtNotification);
        lblEditProfile = (TextView) parentView.findViewById(R.id.lblEditProfile);
        txtLogout = (TextView) parentView.findViewById(R.id.txtLogout);
        viewLogout = (RelativeLayout) parentView.findViewById(R.id.viewLogout);
        lblFullName = (TextView) parentView.findViewById(R.id.lblFullName);
        txtName = (TextView) parentView.findViewById(R.id.txtName);
        txtEmail = (TextView) parentView.findViewById(R.id.txtEmail);
        lblMessage = (TextView) parentView.findViewById(R.id.lblMessage);
        lblLanguage = (TextView) parentView.findViewById(R.id.lblLanguage);
        txtPhone = (TextView) parentView.findViewById(R.id.txtPhone);
        txtLocation = (TextView) parentView.findViewById(R.id.txtLocation);
        lblSettings = (TextView) parentView.findViewById(R.id.lblSettings);
        txtPassword = (TextView) parentView.findViewById(R.id.txtPassword);
        lblPass = (TextView) parentView.findViewById(R.id.lblPass);
        txtLanguage = (TextView) parentView.findViewById(R.id.txtLanguage);
        txtNewPass = (EditText) parentView.findViewById(R.id.txtNewPass);
        txtReNewPass = (EditText) parentView.findViewById(R.id.txtReNewPass);
        btnSave = (Button) parentView.findViewById(R.id.btnSave);
        btnEnglish = (Button) parentView.findViewById(R.id.btnEnglish);
        btnArabic = (Button) parentView.findViewById(R.id.btnArabic);
        viewInfo = (CardView) parentView.findViewById(R.id.viewInfo);
        viewLanguage = (LinearLayout) parentView.findViewById(R.id.viewLanguage);
        viewPassword = (LinearLayout) parentView.findViewById(R.id.viewPassword);
        viewOpacity = (RelativeLayout) parentView.findViewById(R.id.viewOpacity);

        goUp = AnimationUtils.loadAnimation(getActivity(), R.anim.go_up);
        dropDown = AnimationUtils.loadAnimation(getActivity(), R.anim.drop_down);
        viewInfo.setVisibility(View.GONE);
        viewOpacity.setVisibility(View.GONE);

        if (AppUtil.user == null) {
            viewNoProfile.setVisibility(View.VISIBLE);
            viewChangePass.setVisibility(View.GONE);
        } else {
            viewNoProfile.setVisibility(View.GONE);
            viewChangePass.setVisibility(View.VISIBLE);

            fillUserInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        fillUserInfo();
    }

    private void fillUserInfo() {
        txtName.setText(AppUtil.user.getUserName());
        lblFullName.setText(AppUtil.user.getFullName());
        txtPhone.setText(AppUtil.user.getPhoneNumber());
        txtEmail.setText(AppUtil.user.getEmail());
        txtLocation.setText(AppUtil.user.getAddress());
    }

    private void styleView() throws Exception {
        lblFullName.setTypeface(AppUtil.setFontRegular(getContext()));
        lblEditProfile.setTypeface(AppUtil.setFontRegular(getContext()));
        txtName.setTypeface(AppUtil.setFontRegular(getContext()));
        txtEmail.setTypeface(AppUtil.setFontRegular(getContext()));
        txtPhone.setTypeface(AppUtil.setFontRegular(getContext()));
        txtLocation.setTypeface(AppUtil.setFontRegular(getContext()));
        lblSettings.setTypeface(AppUtil.setFontRegular(getContext()));
        txtNotification.setTypeface(AppUtil.setFontRegular(getContext()));
        txtPassword.setTypeface(AppUtil.setFontRegular(getContext()));
        lblPass.setTypeface(AppUtil.setFontRegular(getContext()));
        txtLanguage.setTypeface(AppUtil.setFontRegular(getContext()));
        lblMessage.setTypeface(AppUtil.setFontRegular(getContext()));
        txtNewPass.setTypeface(AppUtil.setFontRegular(getContext()));
        txtReNewPass.setTypeface(AppUtil.setFontRegular(getContext()));
        btnSave.setTypeface(AppUtil.setFontRegular(getContext()));
        btnEnglish.setTypeface(AppUtil.setFontRegular(getContext()));
        btnArabic.setTypeface(AppUtil.setFontRegular(getContext()));
        lblLanguage.setTypeface(AppUtil.setFontRegular(getContext()));
    }

    private void initListeners() throws Exception {
        viewChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(PASSWORD);
            }
        });

        viewChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(LANGUAGE);

            }
        });

        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.setLocale(getActivity(), "ar");
                SelectLanguageActivity.lang = "ar";
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                hideInfo();

                FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/jdeidetmarjeyounnewsen");
                FirebaseMessaging.getInstance().subscribeToTopic("/topics/jdeidetmarjeyounnewsar");
            }
        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.setLocale(getActivity(), "en");
                SelectLanguageActivity.lang = "en";
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                hideInfo();

                FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/jdeidetmarjeyounnewsar");
                FirebaseMessaging.getInstance().subscribeToTopic("/topics/jdeidetmarjeyounnewsen");
            }
        });

        viewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.user = null;

                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = getActivity().getSharedPreferences(AppUtil.PREFS_NAME, Context.MODE_PRIVATE);
                editor = settings.edit();

                Gson gson = new Gson();
                String json = gson.toJson(AppUtil.user);
                editor.putString(AppUtil.PREFS_USER, json);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isValid(txtNewPass.getText()) && StringUtils.isValid(txtReNewPass)) {
                    if (txtNewPass.getText().toString().equals(txtReNewPass.getText().toString())) {

                        progress = new ProgressDialog(getActivity());
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setMessage(getResources().getString(R.string.loading_message));
                        progress.setIndeterminate(true);
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();

                        ApiManager.getService().changePassword(AppUtil.user.getId(), txtNewPass.getText().toString().trim()).enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                SimpleResponse s = response.body();
                                if (s != null && response.isSuccessful() && s.getStatus() == 1) {
                                    Toast.makeText(getActivity(), s.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideInfo();
                                }
                                progress.dismiss();
                            }

                            @Override
                            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                hideInfo();
                                progress.dismiss();
                            }

                        });
                    } else {
                        AppUtil.showError(getActivity(), getResources().getString(R.string.error_passwords_confirmation));
                    }
                } else {
                    AppUtil.showError(getActivity(), getResources().getString(R.string.fill_fields_error));
                }
            }
        });

        viewOpacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInfo();
            }
        });

        lblEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                i.putExtra("fullname", lblFullName.getText().toString());
                i.putExtra("name", txtName.getText().toString());
                i.putExtra("location", txtLocation.getText().toString());
                i.putExtra("email", txtEmail.getText().toString());
                i.putExtra("phone", txtPhone.getText().toString());
                startActivity(i);
            }
        });

        lblMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        viewNoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        switchNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchNotification.isChecked()) {
                    FirebaseMessaging.getInstance().subscribeToTopic("/topics/jdeidetmarjeyounnews" + SelectLanguageActivity.lang);
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/jdeidetmarjeyounnews" + SelectLanguageActivity.lang);
                }
            }
        });
    }

    private void showInfo(int type) {
        viewInfo.startAnimation(goUp);
        viewInfo.setVisibility(View.VISIBLE);
        viewOpacity.setVisibility(View.VISIBLE);
        viewOpacity.animate().alpha(1f).setDuration(500);
        if (type == 0) {
            viewPassword.setVisibility(View.VISIBLE);
            viewLanguage.setVisibility(View.GONE);
        } else {
            viewPassword.setVisibility(View.GONE);
            viewLanguage.setVisibility(View.VISIBLE);
        }
    }

    private void hideInfo() {
        viewInfo.startAnimation(dropDown);
        txtReNewPass.setText("");
        txtNewPass.setText("");
        dropDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewInfo.setVisibility(View.GONE);
                viewOpacity.setVisibility(View.GONE);
                viewOpacity.animate().alpha(0f).setDuration(500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
