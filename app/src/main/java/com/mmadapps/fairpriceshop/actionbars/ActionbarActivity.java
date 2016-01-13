package com.mmadapps.fairpriceshop.actionbars;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.aadharseeding.AadharSeedingActivity;
import com.mmadapps.fairpriceshop.adapters.LanguageArrayAdapter;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;
import com.mmadapps.fairpriceshop.commoditydetails.CommodityDetailsActivity;
import com.mmadapps.fairpriceshop.complaintbooking.ComplaintBookingActivity;
import com.mmadapps.fairpriceshop.fieldinspection.FieldInspectionActivity;
import com.mmadapps.fairpriceshop.issueration.IssueRationActivity;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.main.HomeActivity;
import com.mmadapps.fairpriceshop.main.LoginScreenActivity;
import com.mmadapps.fairpriceshop.reports.ReportFragmentActivity;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.ServiceInteractor;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;
import java.util.Map;

/**
 * Created by Baskar on 12/14/2015.
 */
public abstract class ActionbarActivity extends FragmentActivity implements View.OnClickListener,ServiceInteractor {

    private final String TAG = "ActionBarActivity";
    private PopupWindow leftMenu = null;
    private PopupWindow settingsMenu = null,logoutPopup = null;
    private DialogFragment settingsPopup = null;

    private ImageView vI_al_homeMenuButton,vI_al_backButton;
    protected TextView vT_al_pageTitle,vT_ps_applyChangesButton,vT_ps_cancelButton,vT_pl_declineButton,vT_pl_acceptButton;
    private EditText vE_ps_printerName;

    //Left menu linearlayout
    public LinearLayout vL_plm_btnHome,vL_plm_btnIssueRation,vL_plm_btnCommodity_Receiving,vL_plm_btnAadharSeeding,
                         vL_plm_btnReports,vL_plm_btnComplaintBooking,vL_plm_btnFieldInspection,vL_plm_btnClosingBalance;

    public static Map<String,String> languageCaptionsMap = null;
    public static String LastSelectedLanguage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initalizeMenu();
        }catch (Exception e){
            Log.e(TAG,"No ActionBar Exception");
            e.printStackTrace();
        }
    }

    protected void initalizeMenu(){
        ImageView vI_al_settingButton;
        vI_al_homeMenuButton = (ImageView) findViewById(R.id.vI_al_homeMenuButton);
        vI_al_settingButton = (ImageView) findViewById(R.id.vI_al_settingButton);
        vT_al_pageTitle = (TextView) findViewById(R.id.vT_al_pageTitle);

        vT_al_pageTitle.setTypeface(FPSApplication.fMyriadPro_Semibold);

        vI_al_homeMenuButton.setOnClickListener(this);
        vI_al_settingButton.setOnClickListener(this);
    }

    protected void setPageTitle(int mTitle, boolean isEnableBackButton){
        vT_al_pageTitle.setText(getString(mTitle));
        if(isEnableBackButton){
            vI_al_homeMenuButton.setTag("BACK");
            vI_al_homeMenuButton.setImageResource(R.drawable.back);
        }else{
            vI_al_homeMenuButton.setTag("HOME");
            vI_al_homeMenuButton.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vI_al_homeMenuButton:
                String tag = vI_al_homeMenuButton.getTag().toString();
                if(tag.equalsIgnoreCase("BACK")){
                    finish();
                }else {
                    showLeftMenu();
                }
                break;
            case R.id.vI_al_settingButton:
                showSettingsMenu();
                break;
            case R.id.vI_pm_homeButton:
                if(leftMenu != null && leftMenu.isShowing())
                    leftMenu.dismiss();
                break;
            case R.id.vL_psm_userPreferenceLayout:
                if(settingsMenu != null && settingsMenu.isShowing())
                    settingsMenu.dismiss();
                showSettingsPopUp();
                break;
            case R.id.vL_psm_logoutLayout:
                if(settingsMenu != null && settingsMenu.isShowing())
                    settingsMenu.dismiss();
                showLogoutPopUp();
                break;
            case R.id.vT_pl_acceptButton:
                if(logoutPopup != null && logoutPopup.isShowing())
                    logoutPopup.dismiss();
                redirectToLogin();
                break;
            case R.id.vT_pl_declineButton:
                if(logoutPopup != null && logoutPopup.isShowing())
                    logoutPopup.dismiss();
                break;
            case R.id.vT_ps_applyChangesButton:
                if(settingsPopup != null && settingsPopup.isVisible()){
                    settingsPopup.dismiss();
                }
                //TODO Language Service Call;
                new AsyncServiceCall(this, this, UtilsClass.GET_LANGUAGES);
                break;
            case R.id.vT_ps_cancelButton:
                if(settingsPopup != null && settingsPopup.isVisible()){
                    settingsPopup.dismiss();
                }
                break;
            case R.id.vL_plm_btnHome:
                Intent homeIntent=new Intent(ActionbarActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnIssueRation:
                Intent issueRationIntent=new Intent(ActionbarActivity.this,IssueRationActivity.class);
                startActivity(issueRationIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnCommodity_Receiving:
                Intent commodityReceivingIntent=new Intent(ActionbarActivity.this,CommodityDetailsActivity.class);
                startActivity(commodityReceivingIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnAadharSeeding:
                Intent aadharSeedingIntent=new Intent(ActionbarActivity.this,AadharSeedingActivity.class);
                startActivity(aadharSeedingIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnReports:
                Intent reportsIntent=new Intent(ActionbarActivity.this,ReportFragmentActivity.class);
                startActivity(reportsIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnComplaintBooking:
                Intent complaintBookingIntent=new Intent(ActionbarActivity.this,ComplaintBookingActivity.class);
                startActivity(complaintBookingIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnFieldInspection:
                Intent fieldInspectionIntent=new Intent(ActionbarActivity.this,FieldInspectionActivity.class);
                startActivity(fieldInspectionIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }
                break;
            case R.id.vL_plm_btnClosingBalance:
                /*Intent closingBalanceIntent=new Intent(ActionbarActivity.this,ActionbarActivity.class);
                startActivity(closingBalanceIntent);
                overridePendingTransition(0,0);
                if(leftMenu!=null) {
                    leftMenu.dismiss();
                }*/
                Toast.makeText(ActionbarActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void redirectToLogin() {
        Intent login = new Intent(ActionbarActivity.this, LoginScreenActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        overridePendingTransition(0,0);
        finish();
    }

    private void showSettingsPopUp() {
        settingsPopup = new DialogFragment(){

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.popup_settings, container, false);
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                initializeSettingsViews(view);
                return view;
            }
        };
        settingsPopup.show(getSupportFragmentManager(),null);
    }

    private void initializeSettingsViews(final View layout) {
        Spinner vS_ps_languageSpinner;
        vT_ps_applyChangesButton = (TextView) layout.findViewById(R.id.vT_ps_applyChangesButton);
        vT_ps_cancelButton = (TextView) layout.findViewById(R.id.vT_ps_cancelButton);
        vS_ps_languageSpinner = (Spinner) layout.findViewById(R.id.vS_ps_languageSpinner);
        vE_ps_printerName = (EditText) layout.findViewById(R.id.vE_ps_printerName);

        new Runnable() {
            @Override
            public void run() {
                vT_ps_cancelButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ps_applyChangesButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vE_ps_printerName.setTypeface(FPSApplication.fMyriadPro_Regular);

                TextView vT_ps_lblSettings = (TextView) layout.findViewById(R.id.vT_ps_lblSettings);
                TextView vT_ps_lblSelectLanguage = (TextView) layout.findViewById(R.id.vT_ps_lblSelectLanguage);
                TextView vT_ps_lblPrinterName = (TextView)layout.findViewById(R.id.vT_ps_lblPrinterName);

                vT_ps_lblSettings.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ps_lblSelectLanguage.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ps_lblPrinterName.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        };
        vT_ps_cancelButton.setOnClickListener(this);
        vT_ps_applyChangesButton.setOnClickListener(this);

        setLanguageSpinner(vS_ps_languageSpinner);
        /*ArrayAdapter adapter = new ArrayAdapter<>(ActionbarActivity.this, android.R.layout.simple_spinner_item, );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vS_ps_languageSpinner.setAdapter(adapter);*/
    }

    private void setLanguageSpinner(Spinner vS_ps_languageSpinner) {
        Helper mHelper;
        mHelper = new Helper(this);
        mHelper.openDataBase();
        List<LanguageDetails> languageDetails = mHelper.getLanguageList();
        mHelper.close();
        if(languageDetails == null || languageDetails.size() == 0){
            Log.e(TAG,"No Languages From Database");
        }else {
            LanguageArrayAdapter languageArrayAdapter = new LanguageArrayAdapter(this,0,languageDetails);
            vS_ps_languageSpinner.setAdapter(languageArrayAdapter);
        }
    }

    protected void showLogoutPopUp() {
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.vL_pl_logout);
        View layout=getLayoutInflater().inflate(R.layout.popup_logout ,viewGroup,false);

        logoutPopup = new PopupWindow(ActionbarActivity.this);
        logoutPopup.setContentView(layout);
        logoutPopup.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        logoutPopup.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        logoutPopup.setFocusable(true);
        logoutPopup.setOutsideTouchable(true);

        //logoutPopup.setBackgroundDrawable(new BitmapDrawable());
        logoutPopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        logoutPopup.update();

        initializeLogoutViews(layout);
    }

    private void initializeLogoutViews(final View layout) {
        vT_pl_acceptButton = (TextView) layout.findViewById(R.id.vT_pl_acceptButton);
        vT_pl_declineButton = (TextView) layout.findViewById(R.id.vT_pl_declineButton);

        new Runnable() {
            @Override
            public void run() {
                vT_pl_acceptButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pl_declineButton.setTypeface(FPSApplication.fMyriadPro_Semibold);

                TextView vT_pl_lblLogout = (TextView) layout.findViewById(R.id.vT_pl_lblLogout);
                TextView vT_pl_lblLogoutPrompt = (TextView) layout.findViewById(R.id.vT_pl_lblLogoutPrompt);

                vT_pl_lblLogout.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pl_lblLogoutPrompt.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        };

        vT_pl_declineButton.setOnClickListener(this);
        vT_pl_acceptButton.setOnClickListener(this);
    }

    private void showSettingsMenu() {
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.vL_psm_settingLayout);
        View layout = getLayoutInflater().inflate(R.layout.popup_settings_menu,viewGroup,false);

        settingsMenu = new PopupWindow(ActionbarActivity.this);
        settingsMenu.setContentView(layout);
        settingsMenu.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        settingsMenu.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        settingsMenu.setFocusable(true);

        // Clear the default translucent background
        settingsMenu.setBackgroundDrawable(new BitmapDrawable());
        settingsMenu.setAnimationStyle(R.style.settingsMenuAnimations);
        settingsMenu.showAtLocation(layout, Gravity.END | Gravity.TOP, 50,50);

        initalizeSettingsMenu(layout);
    }

    private void initalizeSettingsMenu(View layout) {
        LinearLayout vL_psm_userPreferenceLayout,vL_psm_logoutLayout;
        vL_psm_userPreferenceLayout = (LinearLayout) layout.findViewById(R.id.vL_psm_userPreferenceLayout);
        vL_psm_logoutLayout = (LinearLayout) layout.findViewById(R.id.vL_psm_logoutLayout);
        final TextView vT_psm_lblUserPreferences = (TextView)layout.findViewById(R.id.vT_psm_lblUserPreferences);
        final TextView vT_plm_lblLogout = (TextView) layout.findViewById(R.id.vT_plm_lblLogout);
        new Runnable(){

            @Override
            public void run() {
                vT_psm_lblUserPreferences.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_plm_lblLogout.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        }.run();

        vL_psm_userPreferenceLayout.setOnClickListener(this);
        vL_psm_logoutLayout.setOnClickListener(this);
    }

    private void showLeftMenu() {
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.vL_pm_menuGroup);
        View layout = getLayoutInflater().inflate(R.layout.popup_left_menu,viewGroup,false);

        leftMenu = new PopupWindow(ActionbarActivity.this);
        leftMenu.setContentView(layout);
        leftMenu.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        leftMenu.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        leftMenu.setFocusable(true);

        // Clear the default translucent background
        leftMenu.setAnimationStyle(R.style.leftMenuAnimations);
        leftMenu.showAtLocation(layout, Gravity.START | Gravity.TOP, 0,0);

        initializeMenuButtons(layout);
    }

    private void initializeMenuButtons(final View layout) {
        ImageView vI_pm_homeButton;
        vI_pm_homeButton = (ImageView) layout.findViewById(R.id.vI_pm_homeButton);
        vL_plm_btnHome= (LinearLayout)layout.findViewById(R.id.vL_plm_btnHome);
        vL_plm_btnIssueRation= (LinearLayout)layout.findViewById(R.id.vL_plm_btnIssueRation);
        vL_plm_btnCommodity_Receiving= (LinearLayout)layout.findViewById(R.id.vL_plm_btnCommodity_Receiving);
        vL_plm_btnAadharSeeding= (LinearLayout) layout.findViewById(R.id.vL_plm_btnAadharSeeding);
        vL_plm_btnReports= (LinearLayout) layout.findViewById(R.id.vL_plm_btnReports);
        vL_plm_btnComplaintBooking= (LinearLayout)layout.findViewById(R.id.vL_plm_btnComplaintBooking);
        vL_plm_btnFieldInspection= (LinearLayout)layout.findViewById(R.id.vL_plm_btnFieldInspection);
        vL_plm_btnClosingBalance= (LinearLayout)layout.findViewById(R.id.vL_plm_btnClosingBalance);

        new Runnable() {
            @Override
            public void run() {
                TextView vT_plm_lblHome = (TextView)layout.findViewById(R.id.vT_plm_lblHome);
                TextView vT_plm_lblIssueRation = (TextView)layout.findViewById(R.id.vT_plm_lblIssueRation);
                TextView vT_plm_lblCommodityReceiving = (TextView)layout.findViewById(R.id.vT_plm_lblCommodityReceiving);
                TextView vT_plm_lblAadharSeeding = (TextView)layout.findViewById(R.id.vT_plm_lblAadharSeeding);
                TextView vT_plm_lblReports = (TextView)layout.findViewById(R.id.vT_plm_lblReports);
                TextView vT_plm_lblComplaintBooking = (TextView)layout.findViewById(R.id.vT_plm_lblComplaintBooking);
                TextView vT_plm_lblFieldInspection = (TextView)layout.findViewById(R.id.vT_plm_lblFieldInspection);
                TextView vT_plm_lblClosingBalance = (TextView)layout.findViewById(R.id.vT_plm_lblClosingBalance);

                vT_plm_lblHome.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblIssueRation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblCommodityReceiving.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblAadharSeeding.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblReports.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblComplaintBooking.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblFieldInspection.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_plm_lblClosingBalance.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();

        vI_pm_homeButton.setOnClickListener(this);
        vL_plm_btnHome.setOnClickListener(this);
        vL_plm_btnIssueRation.setOnClickListener(this);
        vL_plm_btnCommodity_Receiving.setOnClickListener(this);
        vL_plm_btnAadharSeeding.setOnClickListener(this);
        vL_plm_btnReports.setOnClickListener(this);
        vL_plm_btnComplaintBooking.setOnClickListener(this);
        vL_plm_btnFieldInspection.setOnClickListener(this);
        vL_plm_btnClosingBalance.setOnClickListener(this);
    }

    protected void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        //int height = size.y;
        int totalHeight = 0;
        int leftPadding = listView.getPaddingLeft();
        int rightPadding = listView.getPaddingRight();
        int listViewWidth = screenWidth - leftPadding - rightPadding;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
            listItem.measure(widthSpec, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public boolean callService(String service) {
        if(service.equals(UtilsClass.GET_LANGUAGES)){
            WebService webService = new WebService();
            JParser jParser = new JParser();
            String inParam = "stateId=27";  //"stateId=17&pageUrl=~/Team/SaleAppGenricControl&languageCode=hi";
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
        }
        return false;
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        if(isCompleted){
            if(mService.equals(UtilsClass.GET_LANGUAGES)){
                updateCaptions();
            }
        }else{

        }
    }

    protected void showAadharPopup(){
        AlertDialog dialog = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("");
        builder.setCancelable(false);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Redirect to
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null)
                    dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
    }

    protected abstract void updateCaptions();
}
