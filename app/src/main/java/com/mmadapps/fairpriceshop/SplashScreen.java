package com.mmadapps.fairpriceshop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.mmadapps.fairpriceshop.main.LoginScreenActivity;
import com.mmadapps.fairpriceshop.utils.Helper;

import java.io.IOException;

/**
 * Created by Baskar on 12/10/2015.
 */
public class SplashScreen extends Activity {
    //splashscreen timeout
    private static int SPLASH_TIME_OUT = 3000;

    private Helper mHelper;
    //handler for splashscreen
    Handler mHandler;
    Runnable mRunnable;

    //sharedfreferance values
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundResource(R.drawable.splash_page);
        setContentView(linearLayout);
        createDataBase();

        sharedPreferences = getSharedPreferences("FAIRPRICESHOP",MODE_PRIVATE);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent splashScreenIntent = new Intent(SplashScreen.this, LoginScreenActivity.class);
                splashScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(splashScreenIntent);
                overridePendingTransition(0, 0);
                finish();
            }
        };
    }

    private void createDataBase() {
        try {
            mHelper = new Helper(SplashScreen.this);
            mHelper.createDataBase();
            mHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
        finish();
    }
}
