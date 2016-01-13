package com.mmadapps.fairpriceshop.main;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Baskar on 12/22/2015.
 */

public class FPSApplication extends Application {
    public static Typeface fMyriadPro_Regular,fMyriadPro_Semibold,fSegoeui;

    @Override
    public void onCreate() {
        super.onCreate();
        fMyriadPro_Regular = Typeface.createFromAsset(getAssets(), "MyriadPro-Regular.otf");
        fMyriadPro_Semibold = Typeface.createFromAsset(getAssets(), "MyriadPro-Semibold.otf");
        fSegoeui = Typeface.createFromAsset(getAssets(), "segoeui_0.ttf");
    }
}
