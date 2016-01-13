package com.mmadapps.fairpriceshop.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.adapters.LanguageArrayAdapter;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;
import com.mmadapps.fairpriceshop.bean.LoginDetails;
import com.mmadapps.fairpriceshop.bean.MenuDetails;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.sms.SmsClass;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

public class LoginScreenActivity extends ActionbarActivity implements View.OnClickListener {

    private boolean isOtpRequired = false;
    private int generatedOTP = 0;

    private TextView vT_al_loginButton, vT_al_fingerPrintLoginButton,vT_al_submitButton,vT_al_lblFPSDetails;
    private Spinner vS_al_languageSpinner;
    private EditText vE_al_UserName,vE_al_Password,vE_al_otpValue;
    private LinearLayout vL_al_otpLayout;

    private final String TAG = "LoginScreenActivity";
    private Helper mHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String selectedLanguage;
    private boolean isFirstTimeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("FAIRPRICESHOP",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initializeViews();
        setFonts();
        initVariables();
        callServiceClass(UtilsClass.GET_LANGUAGES);
    }


    private void initVariables() {
        mHelper = new Helper(this);
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vE_al_Password.setTypeface(FPSApplication.fMyriadPro_Regular);
                vE_al_UserName.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_al_loginButton.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_al_fingerPrintLoginButton.setTypeface(FPSApplication.fMyriadPro_Regular);
                vE_al_otpValue.setTypeface(FPSApplication.fMyriadPro_Semibold);

                TextView vT_al_lblOr;

                vT_al_lblFPSDetails = (TextView) findViewById(R.id.vT_al_lblFPSDetails);
                vT_al_lblOr = (TextView) findViewById(R.id.vT_al_lblOr);

                vT_al_lblFPSDetails.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_al_lblOr.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        }.run();
    }

    private void setLanguageSpinner() {
        mHelper.openDataBase();
        final List<LanguageDetails> languageDetails = mHelper.getLanguageList();
        mHelper.close();
        if(languageDetails == null || languageDetails.size() == 0){
            Log.e(TAG,"No Languages From Database");
        }else {
            LanguageArrayAdapter languageArrayAdapter = new LanguageArrayAdapter(this,0,languageDetails);
            vS_al_languageSpinner.setAdapter(languageArrayAdapter);
        }

        vS_al_languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languageDetails.get(position).getmCode();
                String oldLang = sharedPreferences.getString(UtilsClass.SP_LANGUAGE_CODE,"en-IN");
                //Toast.makeText(LoginScreenActivity.this, selectedLanguage, Toast.LENGTH_SHORT).show();
                if (oldLang == selectedLanguage || languageDetails.get(position).getmIsDefault().equalsIgnoreCase("Y") || isFirstTimeSpinner){
                    isFirstTimeSpinner = false;
                }else{
                    editor.putString(UtilsClass.SP_LANGUAGE_CODE,selectedLanguage);
                    editor.commit();
                    callServiceClass(UtilsClass.GET_LANGUAGES);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeViews() {
        vT_al_loginButton = (TextView) findViewById(R.id.vT_al_loginButton);
        vT_al_fingerPrintLoginButton = (TextView) findViewById(R.id.vT_al_fingerPrintLoginButton);
        vS_al_languageSpinner = (Spinner) findViewById(R.id.vS_al_languageSpinner);
        vE_al_Password = (EditText) findViewById(R.id.vE_al_Password);
        vE_al_UserName = (EditText) findViewById(R.id.vE_al_UserName);
        vE_al_otpValue = (EditText) findViewById(R.id.vE_al_otpValue);
        vT_al_submitButton = (TextView) findViewById(R.id.vT_al_submitButton);
        vL_al_otpLayout = (LinearLayout) findViewById(R.id.vL_al_otpLayout);

        vT_al_loginButton.setOnClickListener(this);
        vT_al_fingerPrintLoginButton.setOnClickListener(this);
        vT_al_submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vT_al_loginButton:
                vT_al_loginButton.setEnabled(false);
                String userName = ""+vE_al_UserName.getText().toString().trim();
                String password = ""+vE_al_Password.getText().toString().trim();
                if(userName.length() == 0){
                    Toast.makeText(LoginScreenActivity.this,"Enter user name",Toast.LENGTH_SHORT).show();
                }if(password.length() == 0){
                    Toast.makeText(LoginScreenActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                }else {
                    callServiceClass(UtilsClass.GET_AGENT_AUTHENTICATE);
                }
                vT_al_loginButton.setEnabled(true);
                break;
            case R.id.vT_al_fingerPrintLoginButton:
                vT_al_fingerPrintLoginButton.setEnabled(false);
                callServiceClass(UtilsClass.POST_UIDAI_AUTHENTICATE);
                vT_al_fingerPrintLoginButton.setEnabled(true);
                break;
            case R.id.vT_al_submitButton:
                if(validateOtp()){
                    callServiceClass(UtilsClass.GET_MENUS_BY_ROLE_ID);
                    //redirectToHomePage();
                }
                break;
        }
    }

    private boolean validateOtp() {
        String enteredOTP = ""+vE_al_otpValue.getText().toString();
        if(enteredOTP.length() == 0){
            Toast.makeText(LoginScreenActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
        }else{
            Double eOTP = Double.parseDouble(enteredOTP);
            if(generatedOTP == eOTP){
                generatedOTP = 0;
                return true;
            }else{
                Toast.makeText(LoginScreenActivity.this, "OTP is wrong", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }


    @Override
    public boolean callService(String mService) {
        mHelper.openDataBase();
        WebService webService = new WebService();
        JParser parser = new JParser();
        String inParam;
        if(mService.equals(UtilsClass.GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE)){
            webService = new WebService();
            JParser jParser = new JParser();
            inParam = "stateId=27";  //"stateId=17&pageUrl=~/Team/SaleAppGenricControl&languageCode=hi";
            String result = webService.callService(WebService.ServiceAPI.GET_LANGUAGES,inParam);    //webService.callService(WebService.ServiceAPI.GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE,inParam);
            if(result == null || result.length() == 0){

            }else{
                /*languageCaptionsMap = jParser.parsingCaptionsLanguage(result);
                if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){

                }else{
                    return true;
                }*/
                return true;
            }
        }else if(mService.equals(UtilsClass.POST_UIDAI_AUTHENTICATE)){
            inParam = webService.CreateJsonForUidAuthentication(UtilsClass.UID_SAMPLE_DATA,"12345678912","02", false);
            String result = webService.callService(WebService.ServiceAPI.GET_AGENT_AUTHENTICATE, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"Login Service is null");
            } else {
                LoginDetails loginDetails = parser.parsingLoginAuthentications(result);
                if(loginDetails == null){
                    Log.e(TAG,"login parse is null");
                }else{
                    if(loginDetails.getmStatus().equals("1")){
                        return this.callService(UtilsClass.GET_AGENT_DETAILS);
                    }
                }
                Log.e("GetAuthentication", result);
                return true;
            }
        }else if(mService.equals(UtilsClass.GET_AGENT_AUTHENTICATE)) {
            String userName = ""+vE_al_UserName.getText().toString().trim();
            String password = ""+vE_al_Password.getText().toString().trim();
            inParam =  userName+"/"+password; //"loginId=tempFPS&password=bf07dc1be38b20cd6e46949a1071f9";
            String result = webService.callService(WebService.ServiceAPI.GET_AGENT_AUTHENTICATE, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"Login Service is null");
            } else {
                LoginDetails loginDetails = parser.parsingLoginAuthentications(result);
                if(loginDetails == null || !loginDetails.getmStatus().equals("1")){
                    Log.e(TAG,"login parse is null");
                }else{
                    if (loginDetails.getmStatus().equals("1")) {
                        if (loginDetails.getmIsOtpRequired().equalsIgnoreCase("true")) {
                            //TODO generateOTP and Send Sms;
                            sendOtpToMobile();
                            isOtpRequired = true;
                            return true;
                        } else {
                            isOtpRequired = false;
                            return this.callService(UtilsClass.GET_AGENT_DETAILS);
                        }
                    }
                }
                Log.e("GetAuthentication", result);
            }
        } else if(mService.equals(UtilsClass.GET_AGENT_DETAILS)){
            inParam = "loginId=tempFPS";
            String result = webService.callService(WebService.ServiceAPI.GET_AGENT_DETAILS, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"Agent Details Service is null");
            } else {
                AgentDetails agentDetails = parser.parsingAgentDetails(result);
                if(agentDetails == null){
                    Log.e(TAG,"Agent Details parse is null");
                }else{
                    UtilsClass.FPS_CODE = agentDetails.getmFpsCode();
                    mHelper.openDataBase();
                    mHelper.insertAgentDetails(agentDetails);
                    mHelper.close();
                    return true;//this.callService(UtilsClass.GET_MENUS_BY_ROLE_ID);
                }
                Log.e("GetAgentDetails", result);
            }
        }else if(mService.equals(UtilsClass.GET_LANGUAGES)){
            inParam = "stateId=00";
            String result = webService.callService(WebService.ServiceAPI.GET_LANGUAGES, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"Language Service is null");
            } else {
                List<LanguageDetails> languageDetails = parser.parsingLanguageDetails(result);
                if(languageDetails == null || languageDetails.size() == 0){
                    Log.e(TAG,"Language parse is null");
                }else{
                    mHelper.insertLanguages(languageDetails);
                    mHelper.close();
                }
                Log.e("GetLanguages", result);
                return true;
            }
        }else if(mService.equals(UtilsClass.GET_MENUS_BY_ROLE_ID)){
            mHelper.openDataBase();
            AgentDetails mAgentDetails = mHelper.getAgentDetails();
            mHelper.close();

            inParam = "roleId="+mAgentDetails.getmRoleId()+"&stateid="+mAgentDetails.getmState()+"&languageCode="+selectedLanguage.split("-")[0];     //"roleId=18&stateid=02&languageCode=en";
            String result = webService.callService(WebService.ServiceAPI.GET_MENUS_BY_ROLE_ID, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"MenuByRoleId Service is null");
            } else {
                List<MenuDetails> menuDetails = parser.parsingMenuDetails(result);
                if(menuDetails == null || menuDetails.size() == 0){
                    Log.e(TAG,"MenuByRoleId parse is null");
                }else{
                    mHelper.openDataBase();
                    mHelper.insertingMenudetails(menuDetails);
                    mHelper.close();
                }
                Log.e("MenuByRoleId", result);
                return true;
            }
        }
        return super.callService(mService);
    }

    private void sendOtpToMobile() {
        generatedOTP = UtilsClass.generateOTP();
        Log.e("GENERATED OTP", ""+generatedOTP);
        if(generatedOTP > 0) {
            SmsClass.sendSMS(this, "9591701816", "Hello baskar otp is" + generatedOTP);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginScreenActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void callServiceClass(String serviceMethodName){
        new AsyncServiceCall(this,this,serviceMethodName);
    }

    @Override
    public void updateUI(boolean isCompleted,String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted) {
            if(mService.equals(UtilsClass.GET_AGENT_AUTHENTICATE) || mService.equals(UtilsClass.POST_UIDAI_AUTHENTICATE)) {
                if(isOtpRequired){
                    vL_al_otpLayout.setVisibility(View.VISIBLE);
                }else{
                    vL_al_otpLayout.setVisibility(View.GONE);
                    redirectToHomePage();
                }
            }else if (mService.equals(UtilsClass.GET_LANGUAGES)){
                setLanguageSpinner();
            }else if(mService.equals(UtilsClass.GET_MENUS_BY_ROLE_ID)){
                redirectToHomePage();
            }
        }else {
            //TODO Failure
            Toast.makeText(LoginScreenActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectToHomePage() {
        Intent homePage = new Intent(LoginScreenActivity.this, HomeActivity.class);
        startActivity(homePage);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        generatedOTP = 0;
        vE_al_otpValue.setText("");
        vL_al_otpLayout.setVisibility(View.GONE);
    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(LoginScreenActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if (languageCaptionsMap == null || languageCaptionsMap.size() == 0) {
            return;
        }
        if (languageCaptionsMap.containsKey(CaptionsKey.KEY_LOGIN)) {
            vT_al_loginButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_LOGIN));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FINGER_PRINT_LOGIN)) {
            vT_al_fingerPrintLoginButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_FINGER_PRINT_LOGIN));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_SUBMIT)) {
            vT_al_submitButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FAIR_PRICE_SHOP_BEGIN)){
            vT_al_lblFPSDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_FAIR_PRICE_SHOP_BEGIN));
        }
    }


    // BIOMATRIX
}
