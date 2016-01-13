package com.mmadapps.fairpriceshop.userpreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.adapters.LanguageArrayAdapter;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;

import java.util.List;

/**
 * Created by baskar on 1/12/2016.
 */
public class UserPreferencesActivity extends ActionbarActivity {

    private Spinner vS_aup_languageSpinner;
    private EditText vE_aup_printerName;
    private TextView vT_aup_cancelButton,vT_aup_applyChangesButton;
    private Button vB_aup_browsePrinter;

    //variables
    private final String TAG = "LoginScreenActivity";
    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_preferences);
        super.onCreate(savedInstanceState);
        initalizeViews();
        setFonts();
        setLanguageSpinner();
        setActionEvents();
    }

    private void setLanguageSpinner() {
        mHelper=new Helper(this);
        mHelper.openDataBase();
        final List<LanguageDetails> languageDetails = mHelper.getLanguageList();
        mHelper.close();
        if(languageDetails == null || languageDetails.size() == 0){
            Log.e(TAG,"No Languages From Database");
        }else {
            LanguageArrayAdapter languageArrayAdapter = new LanguageArrayAdapter(this,0,languageDetails);
            vS_aup_languageSpinner.setAdapter(languageArrayAdapter);
        }
    }

    private void initalizeViews() {
        vS_aup_languageSpinner = (Spinner) findViewById(R.id.vS_aup_languageSpinner);
        vE_aup_printerName = (EditText) findViewById(R.id.vE_aup_printerName);
        vT_aup_cancelButton = (TextView) findViewById(R.id.vT_aup_cancelButton);
        vT_aup_applyChangesButton = (TextView) findViewById(R.id.vT_aup_applyChangesButton);
        vB_aup_browsePrinter = (Button) findViewById(R.id.vB_aup_browsePrinter);
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                TextView vT_aup_lblSettings,vT_aup_lblSelectLanguage,vT_aup_lblPrinterName;
                vT_aup_lblSettings = (TextView) findViewById(R.id.vT_aup_lblSettings);
                vT_aup_lblSelectLanguage = (TextView) findViewById(R.id.vT_aup_lblSelectLanguage);
                vT_aup_lblPrinterName = (TextView) findViewById(R.id.vT_aup_lblPrinterName);

                vT_aup_lblSettings.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aup_lblSelectLanguage.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_aup_lblPrinterName.setTypeface(FPSApplication.fMyriadPro_Regular);
                vE_aup_printerName.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_aup_cancelButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aup_applyChangesButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vB_aup_browsePrinter.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }


    private void setActionEvents() {
        vT_aup_cancelButton.setOnClickListener(this);
        vT_aup_applyChangesButton.setOnClickListener(this);
        vB_aup_browsePrinter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_aup_applyChangesButton:

                break;
            case R.id.vT_aup_cancelButton:
                finish();
                break;
        }
    }

    @Override
    protected void updateCaptions() {
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        /*vT_aup_lblSettings.setTypeface(FPSApplication.fMyriadPro_Semibold);
        vT_aup_lblSelectLanguage.setTypeface(FPSApplication.fMyriadPro_Regular);
        vT_aup_lblPrinterName.setTypeface(FPSApplication.fMyriadPro_Regular);*/

      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ADD_PRINTER_NAME)){
            vE_aup_printerName.setText(languageCaptionsMap.get(CaptionsKey.KEY_ADD_PRINTER_NAME));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CANCEL)){
            vT_aup_cancelButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_CANCEL));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_SAVE_CHANGES)){
            vT_aup_applyChangesButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_SAVE_CHANGES));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ADD_PRINTER_NAME)){
            vB_aup_browsePrinter.setText(languageCaptionsMap.get(CaptionsKey.KEY_ADD_PRINTER_NAME));
        }

    }
}
