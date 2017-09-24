package com.marjeyoun.jdeidetmarjeyoun.loading;

/**
 * Created by Charbel on 9/13/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.marjeyoun.jdeidetmarjeyoun.MainActivity;
import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}