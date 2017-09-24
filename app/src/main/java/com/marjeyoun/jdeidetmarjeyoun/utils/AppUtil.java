package com.marjeyoun.jdeidetmarjeyoun.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Charbel on 7/11/2017.
 */

public class AppUtil {

    public static void showError(Activity activity, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
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
}
