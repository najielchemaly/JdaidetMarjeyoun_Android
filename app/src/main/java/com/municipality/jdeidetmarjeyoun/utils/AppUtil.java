package com.municipality.jdeidetmarjeyoun.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.municipality.jdeidetmarjeyoun.common.AbstractNameable;
import com.municipality.jdeidetmarjeyoun.common.DefaultNameable;
import com.municipality.jdeidetmarjeyoun.common.Nameable;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.ComplaintsType;
import com.municipality.jdeidetmarjeyoun.objects.GlobalVariable;
import com.municipality.jdeidetmarjeyoun.objects.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Charbel on 7/11/2017.
 */

public class AppUtil {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_USER = "user";
    public static final String PREFS_LANG = "lang";
    public static User user;
    public static GlobalVariable globalVariable;

    public static void showError(Activity activity, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("ar".equals(SelectLanguageActivity.lang) ? "تحذير" : "Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ar".equals(SelectLanguageActivity.lang) ? "تم" : "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void setLocale(Context activity, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public static Typeface setFontRegular(Context context){
        AssetManager am = context.getApplicationContext().getAssets();

        return Typeface.createFromAsset(am, "fonts/dinn_regular.ttf");
    }

    public static Typeface setFontBold(Context context){
        AssetManager am = context.getApplicationContext().getAssets();

        return Typeface.createFromAsset(am, "fonts/dinn_bold.ttf");
    }

    public static Typeface setFontLight(Context context){
        AssetManager am = context.getApplicationContext().getAssets();

        return Typeface.createFromAsset(am, "fonts/dinn_light.ttf");
    }

    public static List<AbstractNameable> transform(String placeHolder, List<? extends AbstractNameable> data){
        List<AbstractNameable> result = new ArrayList<>();
        result.add(new DefaultNameable(placeHolder));
        result.addAll(data);
        return result;
    }

    public static void saveUser(Context context, User user) {
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

    private static String[] years;
    public static String[] getYears() {
        if (years == null) {
            years = new String[50];
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR)+1;
            for (int index = 0; index < years.length; ) {
                years[index++] = String.valueOf(year-index);
            }
        }

        return years;
    }

}
