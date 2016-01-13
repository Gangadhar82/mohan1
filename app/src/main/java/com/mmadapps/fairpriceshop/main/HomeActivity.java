package com.mmadapps.fairpriceshop.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.aadharseeding.AadharSeedingActivity;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.commoditydetails.CommodityDetailsActivity;
import com.mmadapps.fairpriceshop.complaintbooking.ComplaintBookingActivity;
import com.mmadapps.fairpriceshop.fieldinspection.FieldInspectionActivity;
import com.mmadapps.fairpriceshop.issueration.IssueRationActivity;
import com.mmadapps.fairpriceshop.reports.ReportFragmentActivity;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.stockbalancereport.StockBalanceActivity;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 12/10/2015.
 */
public class HomeActivity extends ActionbarActivity implements View.OnClickListener{

    LinearLayout vL_ahs_commodityReceivingLayout, vL_ahs_aadharSeedingLayout,vL_ahs_issueRationLayout,
            vL_ahs_complaingBookingLayout,vL_ahs_closignBalanaceLayout,vL_ahs_reportsLayout,
            vL_ahs_fieldInspectionLayout, vL_ahs_userPreferenceLayout;

    TextView vT_ahs_lblIssue, vT_ahs_lblRation, vT_ahs_lblCommodity, vT_ahs_lblReceiving, vT_ahs_lblComplaint, vT_ahs_lblBooking,
            vT_ahs_lblField, vT_ahs_lblInspection, vT_ahs_lblAadhar, vT_ahs_lblSeeding, vT_ahs_lblClosing, vT_ahs_lblBalance,
            vT_ahs_lblReports, vT_ahs_lblUser, vT_ahs_lblPreference;

    private Helper mHelper;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_screen);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_home, false);
        initViews();
        updateCaptions();
        initVariables();
        setFonts();
    }

    private void initVariables() {
        mHelper = new Helper(this);
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vT_ahs_lblIssue = (TextView) findViewById(R.id.vT_ahs_lblIssue);
                vT_ahs_lblRation = (TextView) findViewById(R.id.vT_ahs_lblRation);
                vT_ahs_lblCommodity = (TextView) findViewById(R.id.vT_ahs_lblCommodity);
                vT_ahs_lblReceiving = (TextView) findViewById(R.id.vT_ahs_lblReceiving);
                vT_ahs_lblComplaint = (TextView) findViewById(R.id.vT_ahs_lblComplaint);
                vT_ahs_lblBooking = (TextView) findViewById(R.id.vT_ahs_lblBooking);
                vT_ahs_lblField = (TextView) findViewById(R.id.vT_ahs_lblField);
                vT_ahs_lblInspection = (TextView) findViewById(R.id.vT_ahs_lblInspection);
                vT_ahs_lblAadhar = (TextView) findViewById(R.id.vT_ahs_lblAadhar);
                vT_ahs_lblSeeding = (TextView) findViewById(R.id.vT_ahs_lblSeeding);
                vT_ahs_lblClosing = (TextView) findViewById(R.id.vT_ahs_lblClosing);
                vT_ahs_lblBalance = (TextView) findViewById(R.id.vT_ahs_lblBalance);
                vT_ahs_lblReports = (TextView) findViewById(R.id.vT_ahs_lblReports);
                vT_ahs_lblUser = (TextView) findViewById(R.id.vT_ahs_lblUser);
                vT_ahs_lblPreference = (TextView) findViewById(R.id.vT_ahs_lblPreference);

                vT_ahs_lblIssue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblRation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblCommodity.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblReceiving.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblComplaint.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblBooking.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblField.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblInspection.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblAadhar.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblSeeding.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblClosing.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblBalance.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblReports.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ahs_lblUser.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_ahs_lblPreference.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void initViews() {
        vL_ahs_issueRationLayout = (LinearLayout) findViewById(R.id.vL_ahs_issueRationLayout);
        vL_ahs_commodityReceivingLayout = (LinearLayout) findViewById(R.id.vL_ahs_commodityReceivingLayout);
        vL_ahs_aadharSeedingLayout = (LinearLayout) findViewById(R.id.vL_ahs_aadharSeedingLayout);
        vL_ahs_complaingBookingLayout = (LinearLayout) findViewById(R.id.vL_ahs_complaingBookingLayout);
        vL_ahs_reportsLayout = (LinearLayout) findViewById(R.id.vL_ahs_reportsLayout);
        vL_ahs_fieldInspectionLayout = (LinearLayout) findViewById(R.id.vL_ahs_fieldInspectionLayout);
        vL_ahs_userPreferenceLayout = (LinearLayout) findViewById(R.id.vL_ahs_userPreferenceLayout);
        vL_ahs_closignBalanaceLayout = (LinearLayout) findViewById(R.id.vL_ahs_closignBalanaceLayout);

        vL_ahs_issueRationLayout.setOnClickListener(this);
        vL_ahs_commodityReceivingLayout.setOnClickListener(this);
        vL_ahs_aadharSeedingLayout.setOnClickListener(this);
        vL_ahs_complaingBookingLayout.setOnClickListener(this);
        vL_ahs_reportsLayout.setOnClickListener(this);
        vL_ahs_fieldInspectionLayout.setOnClickListener(this);
        vL_ahs_userPreferenceLayout.setOnClickListener(this);
        vL_ahs_closignBalanaceLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vL_ahs_issueRationLayout:
                vL_ahs_issueRationLayout.setEnabled(false);
                callServiceClass(UtilsClass.GET_ALLOCATION);
                vL_ahs_issueRationLayout.setEnabled(true);
                break;
            case R.id.vL_ahs_commodityReceivingLayout:
                vL_ahs_commodityReceivingLayout.setEnabled(false);
                Intent commodityDetailsIntent = new Intent(HomeActivity.this, CommodityDetailsActivity.class);
                startActivity(commodityDetailsIntent);
                overridePendingTransition(0, 0);
                vL_ahs_commodityReceivingLayout.setEnabled(true);
                break;
            case R.id.vL_ahs_aadharSeedingLayout:
                Intent aadharSeedingIntent = new Intent(HomeActivity.this, AadharSeedingActivity.class);
                startActivity(aadharSeedingIntent);
                overridePendingTransition(0, 0);
                break;
            case R.id.vL_ahs_complaingBookingLayout:
                vL_ahs_complaingBookingLayout.setEnabled(false);
                Intent complaintBookingIntent = new Intent(HomeActivity.this, ComplaintBookingActivity.class);
                startActivity(complaintBookingIntent);
                overridePendingTransition(0, 0);
                vL_ahs_complaingBookingLayout.setEnabled(true);
                break;
            case R.id.vL_ahs_reportsLayout:
                vL_ahs_reportsLayout.setEnabled(false);
                Intent reportIntent = new Intent(HomeActivity.this, ReportFragmentActivity.class);
                startActivity(reportIntent);
                overridePendingTransition(0, 0);
                vL_ahs_reportsLayout.setEnabled(true);
                break;
            case R.id.vL_ahs_fieldInspectionLayout:
                vL_ahs_fieldInspectionLayout.setEnabled(false);
                Intent inspectionIntent = new Intent(HomeActivity.this, FieldInspectionActivity.class);
                startActivity(inspectionIntent);
                overridePendingTransition(0, 0);
                vL_ahs_fieldInspectionLayout.setEnabled(true);
                break;
            case R.id.vL_ahs_userPreferenceLayout: // EPSON PosPrinter
                /*Intent calendar = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(calendar);
                overridePendingTransition(0,0);*/
                break;
            case R.id.vL_ahs_closignBalanaceLayout:
                vL_ahs_closignBalanaceLayout.setEnabled(false);
                Intent stockBalanceIntent = new Intent(HomeActivity.this, StockBalanceActivity.class);
                startActivity(stockBalanceIntent);
                overridePendingTransition(0, 0);
                vL_ahs_closignBalanaceLayout.setEnabled(true);
                break;
        }
    }

    private void callServiceClass(String serviceMethodName) {
        new AsyncServiceCall(this, this, serviceMethodName);
    }

    @Override
    public void onBackPressed() {
        showLogoutPopUp();
    }


    @Override
    public boolean callService(String service) {
        WebService webService = new WebService();
        JParser parser = new JParser();
        String inParam;
        if (service.equals(UtilsClass.GET_ALLOCATION)) {
            inParam = "fpsCode=103301100002&saleDate=2015-07-01";
            String result = webService.callService(WebService.ServiceAPI.GET_ALLOCATION, inParam);
            Log.e("GetAllocation", "" + result);
            if (result == null || result.length() == 0) {
                Log.e(TAG, "allocation Details service is null");
                return false;
            } else {
                List<AllocationDetails> allocationDetails = parser.parsingAllocationDetails(result);
                if (allocationDetails == null || allocationDetails.size() == 0) {
                    Log.e(TAG, "allocation Details parse is null");
                    return false;
                } else {
                    mHelper.openDataBase();
                    mHelper.insertAllocation(allocationDetails);
                    mHelper.close();
                    return true;
                }
            }
        }
        return super.callService(service);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if (isCompleted) {
            if (mService.equals(UtilsClass.GET_ALLOCATION)) {
                Intent issueRationIntent = new Intent(HomeActivity.this, IssueRationActivity.class);
                startActivity(issueRationIntent);
                overridePendingTransition(0, 0);
            }
        } else {
            Toast.makeText(HomeActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(HomeActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ISSUE)) {
            vT_ahs_lblIssue.setText(languageCaptionsMap.get(CaptionsKey.KEY_ISSUE));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION)) {
            vT_ahs_lblRation.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)) {
            vT_ahs_lblCommodity.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RECEIVING)) {
            vT_ahs_lblReceiving.setText(languageCaptionsMap.get(CaptionsKey.KEY_RECEIVING));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT)) {
            vT_ahs_lblComplaint.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_BOOKING)) {
            vT_ahs_lblBooking.setText(languageCaptionsMap.get(CaptionsKey.KEY_BOOKING));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FIELD)) {
            vT_ahs_lblField.setText(languageCaptionsMap.get(CaptionsKey.KEY_FIELD));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_INSPECTION)) {
            vT_ahs_lblInspection.setText(languageCaptionsMap.get(CaptionsKey.KEY_INSPECTION));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_AADHAR)) {
            vT_ahs_lblAadhar.setText(languageCaptionsMap.get(CaptionsKey.KEY_AADHAR));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_SEEDING)) {
            vT_ahs_lblSeeding.setText(languageCaptionsMap.get(CaptionsKey.KEY_SEEDING));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CLOSING)) {
            vT_ahs_lblClosing.setText(languageCaptionsMap.get(CaptionsKey.KEY_CLOSING));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_BALANCE)) {
            vT_ahs_lblBalance.setText(languageCaptionsMap.get(CaptionsKey.KEY_BALANCE));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_REPORTS)) {
            vT_ahs_lblReports.setText(languageCaptionsMap.get(CaptionsKey.KEY_REPORTS));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_USER)) {
            vT_ahs_lblUser.setText(languageCaptionsMap.get(CaptionsKey.KEY_USER));
        }if(languageCaptionsMap.containsKey(CaptionsKey.KEY_PREFERENCES)) {
            vT_ahs_lblPreference.setText(languageCaptionsMap.get(CaptionsKey.KEY_PREFERENCES));
        }
    }


}
