package com.mmadapps.fairpriceshop.aadharseeding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.adapters.FamilyMembersAdapter;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.posprinter.FPSAadharSeedingPrinter;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 12/11/2015.
 */
public class AadharSeedingActivity extends ActionbarActivity {

    private ListView vL_aas_commodityList;
    private LinearLayout vL_aas_contentLayout,vL_aas_cardHolderDetailsLayout;
    private TextView vT_aas_btnGetDetails,vT_aas_lblCardNumberValue,vT_aas_lblCardTypeValue;
    private EditText vE_aas_RCNumberValue;
    private TextView vT_aas_lblRCNumber,vT_aas_lblCardNumber,vT_aas_lblCardType,vT_aas_lblName,
            vT_aas_lblAge,vT_aas_lblGender,vT_aas_lblRelation,vT_aas_lblAadharSeeding;

    //Variables
    private static final String TAG = "AadharSeedingActivity";

    private List<RationLiftingMemberDetails> mRationLiftingMemberDetailsList;
    private RationCardHolderDetails mCardHolderDetails;
    private FamilyMembersAdapter familyMembersAdapter;
    private AgentDetails mAgentDetails;
    private String mRationCardNumber;
    //private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_aadhar_seeding);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_aadhar_seeding,true);
        initializeViews();
        updateCaptions();
        initializeVariables();
        setFonts();
        setActionEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        mAgentDetails = getAgentDetails();
        mHelper.close();
    }

    private void initializeVariables() {
        //mHelper = new Helper(this);
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vE_aas_RCNumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_btnGetDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblCardNumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblCardTypeValue.setTypeface(FPSApplication.fMyriadPro_Semibold);



                vT_aas_lblRCNumber = (TextView) findViewById(R.id.vT_aas_lblRCNumber);
                vT_aas_lblCardNumber = (TextView) findViewById(R.id.vT_aas_lblCardNumber);
                vT_aas_lblCardType = (TextView) findViewById(R.id.vT_aas_lblCardType);
                vT_aas_lblName = (TextView) findViewById(R.id.vT_aas_lblName);
                vT_aas_lblAge = (TextView) findViewById(R.id.vT_aas_lblAge);
                vT_aas_lblGender = (TextView) findViewById(R.id.vT_aas_lblGender);
                vT_aas_lblRelation = (TextView) findViewById(R.id.vT_aas_lblRelation);
                vT_aas_lblAadharSeeding = (TextView) findViewById(R.id.vT_aas_lblAadharSeeding);

                vT_aas_lblRCNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_aas_lblCardNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_aas_lblCardType.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_aas_lblName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblAge.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblGender.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblRelation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_aas_lblAadharSeeding.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void setActionEvents() {
        vT_aas_btnGetDetails.setOnClickListener(this);
    }

    private void setMemberListAdapter() {
        if(mRationLiftingMemberDetailsList == null || mRationLiftingMemberDetailsList.size() == 0){
            Log.e(TAG,"member details list is empty");
            Toast.makeText(AadharSeedingActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            familyMembersAdapter = new FamilyMembersAdapter(AadharSeedingActivity.this, UtilsClass.AADHARSEEDING, this, mRationLiftingMemberDetailsList,null,null);
            vL_aas_contentLayout.setVisibility(View.VISIBLE);
            vL_aas_commodityList.setAdapter(familyMembersAdapter);
            setListViewHeightBasedOnChildren(vL_aas_commodityList);
        }
    }

    private void setCardHolderDetails(){
        if(mCardHolderDetails == null){
            vL_aas_cardHolderDetailsLayout.setVisibility(View.GONE);
        }else {
            vL_aas_cardHolderDetailsLayout.setVisibility(View.VISIBLE);
            vT_aas_lblCardNumberValue.setText(mCardHolderDetails.getmCardNumber());
            vT_aas_lblCardTypeValue.setText("Null");
        }
    }

    private void initializeViews() {
        vE_aas_RCNumberValue = (EditText) findViewById(R.id.vE_aas_RCNumberValue);
        vT_aas_btnGetDetails = (TextView) findViewById(R.id.vT_aas_btnGetDetails);
        vL_aas_commodityList = (ListView) findViewById(R.id.vL_aas_commodityList);
        vL_aas_contentLayout = (LinearLayout) findViewById(R.id.vL_aas_contentLayout);
        vL_aas_cardHolderDetailsLayout = (LinearLayout) findViewById(R.id.vL_aas_cardHolderDetailsLayout);
        vT_aas_lblCardNumberValue = (TextView) findViewById(R.id.vT_aas_lblCardNumberValue);
        vT_aas_lblCardTypeValue = (TextView) findViewById(R.id.vT_aas_lblCardTypeValue);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_aas_btnGetDetails:
                mRationCardNumber = ""+vE_aas_RCNumberValue.getText().toString();
                if(mRationCardNumber.length() == 0){
                    Toast.makeText(AadharSeedingActivity.this, "Please enter ration card number", Toast.LENGTH_SHORT).show();
                }else {
                    resetViews();
                    vT_aas_btnGetDetails.setEnabled(false);
                    new AsyncServiceCall(this, this, UtilsClass.GET_RATION_CARD_HOLDER_DETAILS);
                    vT_aas_btnGetDetails.setEnabled(true);
                }
                break;
        }
    }

    private void resetViews() {
        vL_aas_cardHolderDetailsLayout.setVisibility(View.GONE);
        vL_aas_contentLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean callService(String service) {
        WebService webService =new WebService();
        JParser parser = new JParser();
        String inParam;
        String result;
        mRationLiftingMemberDetailsList = null;
        if(service.equals(UtilsClass.POST_CREATE_AADHAARSEED)){
            inParam = webService.CreateJsonForCreateAadharSeeding(mAgentDetails.getmUserId(),mCardHolderDetails,familyMembersAdapter.getSelectedMemberDetails(),UtilsClass.VALIDATING_AADHAR);
            result = webService.callService(WebService.ServiceAPI.POST_CREATE_AADHAARSEED, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"GetRationLiftingMemberDetails service is null");
            } else {
                Log.e("PostCreateAadharSeed", result);
                String responseResult = parser.parsingUidAuthendication(result);
                if(responseResult.equals("true")){
                    return false;
                }else {
                    return this.callService(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS);
                }
            }
        }else if(service.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)) {
                inParam = "rationCardNo="+mRationCardNumber+"&stateId="+mAgentDetails.getmState()+"&fpsCode="+mAgentDetails.getmFpsCode();
            //"rationCardNo=021000002463&stateId=02&fpsCode=103301100002";
                result = webService.callService(WebService.ServiceAPI.GET_RATION_CARD_HOLDER_DETAILS, inParam);
                if (result == null || result.length() == 0) {
                    Log.e(TAG, "GetRationcardHolderDetails response is null");
                } else {
                    mCardHolderDetails = parser.parsingRationcardholderDetails(result);
                    if (mCardHolderDetails == null) {
                        Log.e(TAG, "GetRationcardHolderDetails object is null");
                    } else {
                        return this.callService(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS);
                    }
                }
        }else if(service.equals(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS)){
            inParam = "rationCardNo="+mRationCardNumber+"&stateId="+mAgentDetails.getmState();
            result = webService.callService(WebService.ServiceAPI.GET_RATION_LIFTING_MEMBERS_DETAILS, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG, "GetRationLiftingMemberDetails service is null");
            } else {
                Log.e("GetRationLiftingMbrDtl", result);
                mRationLiftingMemberDetailsList = parser.parseRationLiftingMembersDetails(result);
                return true;
            }
        }
        return super.callService(service);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted) {
            if(mService.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)) {
                setCardHolderDetails();
                setMemberListAdapter();
            }else if(mService.equals(UtilsClass.POST_CREATE_AADHAARSEED)){
                Toast.makeText(AadharSeedingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                UtilsClass.mSelectedMember = familyMembersAdapter.getSelectedMemberDetails();
                UtilsClass.mCardHolderDetails = mCardHolderDetails;
                Intent receipt = new Intent(AadharSeedingActivity.this, FPSAadharSeedingPrinter.class);
                receipt.putExtra(UtilsClass.PRINTER_KEY,UtilsClass.AADHAR_SEEDING);
                startActivity(receipt);
            }
        }else{
            Toast.makeText(AadharSeedingActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            UtilsClass.mSelectedMember = null;
            UtilsClass.mCardHolderDetails = null;
        }
    }

    private AgentDetails getAgentDetails() {
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        AgentDetails mAgent = mHelper.getAgentDetails();
        mHelper.close();
        return mAgent;
    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(AadharSeedingActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_aas_lblRCNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_GET_DETAILS)){
            vT_aas_btnGetDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_GET_DETAILS));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CARD_NUMBER)){
            vT_aas_lblCardNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_GET_DETAILS));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CARD_TYPE)){
            vT_aas_lblCardType.setText(languageCaptionsMap.get(CaptionsKey.KEY_CARD_TYPE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_aas_lblName.setText(languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_AGE)){
            vT_aas_lblAge.setText(languageCaptionsMap.get(CaptionsKey.KEY_AGE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_GENDER)){
            vT_aas_lblGender.setText(languageCaptionsMap.get(CaptionsKey.KEY_GENDER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RELATION)){
            vT_aas_lblRelation.setText(languageCaptionsMap.get(CaptionsKey.KEY_RELATION));
            //
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_AADHAR_STATUS)){
            vT_aas_lblAadharSeeding.setText(languageCaptionsMap.get(CaptionsKey.KEY_AADHAR_STATUS));
        }


    }

}
