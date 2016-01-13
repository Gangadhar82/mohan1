package com.mmadapps.fairpriceshop.issueration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.adapters.AllocationMonthsAdapter;
import com.mmadapps.fairpriceshop.adapters.AllocationNumberAdapter;
import com.mmadapps.fairpriceshop.adapters.FamilyMembersAdapter;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.posprinter.FPSSalesReceiptPrinter;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Baskar on 12/10/2015.
 */
public class IssueRationActivity extends ActionbarActivity implements View.OnClickListener {

    //Views
    private Spinner vS_air_allocationMonthSpinner,vS_air_AllocationNumberSpinner;
    private TextView vT_air_btnGetDetails,vT_air_btnMakeSale,vT_air_CardNumberValue,vT_air_CardTypeValue,vT_air_WaiverCountValue,vT_air_TotalValue;
    private ListView vL_air_commodityList,vL_air_relationsList;
    private LinearLayout vL_air_contentsLayout,vL_air_cardDetailsLayout,vL_air_familyMembersTileLayout,vL_air_commodityTileLayout,vL_air_totalTileLayout;
    private EditText vE_air_RCNumberValue;
   //language views
    private   TextView vT_air_lblRCNumber,vT_air_lblAllocationNumber,vT_air_lblAllocationMonth,vT_air_lblCardNumber,vT_air_lblCardType,vT_air_lblWaiverCount,
            vT_air_lblName,vT_air_lblAge,vT_air_lblGender,vT_air_lblRelation,vT_air_lblAadharStatus,vT_air_lblCommodity,vT_air_lblFPSSQuantity,
            vT_air_lblEligibleQuantity,vT_air_lblGivenQuantity,vT_air_lblBalanceQuantity,vT_air_lblQFSale,vT_air_lblPQuantity,vT_air_lblPrice,vT_air_lblTotal;

    //Variables
    private static final String TAG = "IssueRationActivity";
    private List<AllocationDetails> mAllocationDetailsList;
    private List<SchemeDetailsOnRationCardNumber> mSchemeDetailsOnRationCardNumbers;
    private List<RationLiftingMemberDetails> mRationLiftingMemberDetailsList;
    private Map<Integer, String> mSelectedCommodities;
    private double mTotalPrice = 0;
    private FamilyMembersAdapter familyMembersAdapter;
    private CommodityIssueAdapter commodityIssueAdapter;
    private RationCardHolderDetails mCardHolderDetails;
    private AgentDetails mAgentDetails;
    private String mRationCardNumber;
    Helper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_issue_ration);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_issue_ration,true);
        initializeViews();
        updateCaptions();
        initializeVariable();
        setFonts();
        setActionEvents();
    }

    private void initializeVariable() {
        mHelper = new Helper(this);
        mHelper.openDataBase();
        mAllocationDetailsList = mHelper.getAllocationDetails();
        mHelper.close();
        if(mAllocationDetailsList != null){
            setAllocationNumberSpinner();
            setAllocationMonthSpinner();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentDetails = getAgentDetails();
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vE_air_RCNumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_btnGetDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_btnMakeSale.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_CardNumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_CardTypeValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_WaiverCountValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_TotalValue.setTypeface(FPSApplication.fMyriadPro_Semibold);



                vT_air_lblRCNumber = (TextView) findViewById(R.id.vT_air_lblRCNumber);
                vT_air_lblAllocationNumber = (TextView) findViewById(R.id.vT_air_lblAllocationNumber);
                vT_air_lblAllocationMonth = (TextView) findViewById(R.id.vT_air_lblAllocationMonth);
                vT_air_lblCardNumber = (TextView) findViewById(R.id.vT_air_lblCardNumber);
                vT_air_lblCardType = (TextView) findViewById(R.id.vT_air_lblCardType);
                vT_air_lblWaiverCount = (TextView) findViewById(R.id.vT_air_lblWaiverCount);
                vT_air_lblName = (TextView) findViewById(R.id.vT_air_lblName);
                vT_air_lblAge = (TextView) findViewById(R.id.vT_air_lblAge);
                vT_air_lblGender = (TextView) findViewById(R.id.vT_air_lblGender);
                vT_air_lblRelation = (TextView) findViewById(R.id.vT_air_lblRelation);
                vT_air_lblAadharStatus = (TextView) findViewById(R.id.vT_air_lblAadharStatus);
                vT_air_lblCommodity = (TextView) findViewById(R.id.vT_air_lblCommodity);
                vT_air_lblFPSSQuantity = (TextView) findViewById(R.id.vT_air_lblFPSSQuantity);
                vT_air_lblEligibleQuantity = (TextView) findViewById(R.id.vT_air_lblEligibleQuantity);
                vT_air_lblGivenQuantity = (TextView) findViewById(R.id.vT_air_lblGivenQuantity);
                vT_air_lblBalanceQuantity = (TextView) findViewById(R.id.vT_air_lblBalanceQuantity);
                vT_air_lblQFSale = (TextView) findViewById(R.id.vT_air_lblQFSale);
                vT_air_lblPQuantity = (TextView) findViewById(R.id.vT_air_lblPQuantity);
                vT_air_lblPrice = (TextView) findViewById(R.id.vT_air_lblPrice);
                vT_air_lblTotal = (TextView) findViewById(R.id.vT_air_lblTotal);

                vT_air_lblRCNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblAllocationNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblAllocationMonth.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblCardNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblCardType.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblWaiverCount.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_air_lblName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblAge.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblGender.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblRelation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblAadharStatus.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblCommodity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblFPSSQuantity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblEligibleQuantity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblGivenQuantity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblBalanceQuantity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblQFSale.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblPQuantity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblPrice.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_air_lblTotal.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void setActionEvents() {
        vT_air_btnGetDetails.setOnClickListener(this);
        vT_air_btnMakeSale.setOnClickListener(this);
    }

    private void setAllocationNumberSpinner(){
        AllocationMonthsAdapter monthsAdapter = new AllocationMonthsAdapter(this, 0, mAllocationDetailsList);
        vS_air_allocationMonthSpinner.setAdapter(monthsAdapter);
    }

    private void setAllocationMonthSpinner() {
        AllocationNumberAdapter numberAdapter = new AllocationNumberAdapter(this, 0, mAllocationDetailsList);
        vS_air_AllocationNumberSpinner.setAdapter(numberAdapter);
    }

    private void setCommodityList(){
        if(mSchemeDetailsOnRationCardNumbers == null || mSchemeDetailsOnRationCardNumbers.size()==0){
            vL_air_totalTileLayout.setVisibility(View.GONE);
            vL_air_commodityTileLayout.setVisibility(View.GONE);
            vL_air_commodityList.setVisibility(View.GONE);
            Log.e(TAG, "SchemeDetailsOnRationCardNumbers List is empty");
            Toast.makeText(IssueRationActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            vL_air_totalTileLayout.setVisibility(View.VISIBLE);
            vL_air_commodityTileLayout.setVisibility(View.VISIBLE);
            vL_air_commodityList.setVisibility(View.VISIBLE);
            mSelectedCommodities = new HashMap<>();
            commodityIssueAdapter = new CommodityIssueAdapter();
            vL_air_commodityList.setAdapter(commodityIssueAdapter);
            setListViewHeightBasedOnChildren(vL_air_commodityList);
        }
    }

    private void setCardHolderDetails(){
        if(mCardHolderDetails == null){
            vL_air_contentsLayout.setVisibility(View.GONE);
            Log.e(TAG,"mCardholderdetails object is null");
        }else {
            vL_air_contentsLayout.setVisibility(View.VISIBLE);
            vT_air_CardNumberValue.setText(mCardHolderDetails.getmCardNumber());
            vT_air_CardTypeValue.setText(mCardHolderDetails.getmSchemeName());
            String wavierCount = mCardHolderDetails.getmWaiverCountsUtilised()+"/"+mCardHolderDetails.getmApprovedWaiverCounter();
            vT_air_WaiverCountValue.setText(wavierCount);
        }
    }

    private void setMemberList() {
        if(mRationLiftingMemberDetailsList == null || mRationLiftingMemberDetailsList.size() == 0){
            vL_air_familyMembersTileLayout.setVisibility(View.GONE);
            vL_air_relationsList.setVisibility(View.GONE);
            Log.e(TAG,"RationLiftingMemberList is empty");
            Toast.makeText(IssueRationActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            vL_air_familyMembersTileLayout.setVisibility(View.VISIBLE);
            vL_air_cardDetailsLayout.setVisibility(View.VISIBLE);
            vL_air_relationsList.setVisibility(View.VISIBLE);

            familyMembersAdapter = new FamilyMembersAdapter(IssueRationActivity.this, UtilsClass.ISSUERATION, this, mRationLiftingMemberDetailsList,mCardHolderDetails.getmWaiverCountsUtilised(),mCardHolderDetails.getmApprovedWaiverCounter());
            vL_air_relationsList.setAdapter(familyMembersAdapter);
            setListViewHeightBasedOnChildren(vL_air_relationsList);
        }
    }

    private void initializeViews() {
        vE_air_RCNumberValue = (EditText) findViewById(R.id.vE_air_RCNumberValue);
        vS_air_AllocationNumberSpinner = (Spinner) findViewById(R.id.vS_air_AllocationNumberSpinner);
        vT_air_CardNumberValue = (TextView) findViewById(R.id.vT_air_CardNumberValue);
        vT_air_CardTypeValue = (TextView) findViewById(R.id.vT_air_CardTypeValue);
        vT_air_WaiverCountValue = (TextView) findViewById(R.id.vT_air_WaiverCountValue);
        vT_air_TotalValue = (TextView) findViewById(R.id.vT_air_TotalValue);
        vT_air_btnGetDetails = (TextView) findViewById(R.id.vT_air_btnGetDetails);
        vT_air_btnMakeSale = (TextView) findViewById(R.id.vT_air_btnMakeSale);
        vS_air_allocationMonthSpinner= (Spinner) findViewById(R.id.vS_air_allocationMonthSpinner);
        vL_air_commodityList = (ListView)findViewById(R.id.vL_air_commodityList);
        vL_air_relationsList=(ListView)findViewById(R.id.vL_air_relationsList);
        vL_air_contentsLayout = (LinearLayout) findViewById(R.id.vL_air_contentsLayout);
        vL_air_cardDetailsLayout = (LinearLayout) findViewById(R.id.vL_air_cardDetailsLayout);
        vL_air_familyMembersTileLayout = (LinearLayout) findViewById(R.id.vL_air_familyMembersTileLayout);
        vL_air_commodityTileLayout = (LinearLayout) findViewById(R.id.vL_air_commodityTileLayout);
        vL_air_totalTileLayout = (LinearLayout) findViewById(R.id.vL_air_totalTileLayout);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_air_btnGetDetails:
                mRationCardNumber = ""+vE_air_RCNumberValue.getText().toString();
                if(mRationCardNumber.length() == 0){
                    Toast.makeText(IssueRationActivity.this, "Please enter ration card number", Toast.LENGTH_SHORT).show();
                }else {
                    resetViews();
                    vT_air_btnGetDetails.setEnabled(false);
                    callServiceClass(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS);
                    vT_air_btnGetDetails.setEnabled(true);
                }
                break;
            case R.id.vT_air_btnMakeSale:
                callServiceClass(UtilsClass.POST_SALE_CREATE);
                break;
        }
    }

    private void callServiceClass(String serviceMethodName){
        new AsyncServiceCall(this,this,serviceMethodName);
    }

    private void resetViews() {
        vT_air_btnMakeSale.setVisibility(View.GONE);
        vL_air_totalTileLayout.setVisibility(View.GONE);
        vL_air_commodityTileLayout.setVisibility(View.GONE);
        vL_air_commodityList.setVisibility(View.GONE);
        vL_air_familyMembersTileLayout.setVisibility(View.GONE);
        vL_air_cardDetailsLayout.setVisibility(View.GONE);
        vL_air_relationsList.setVisibility(View.GONE);
    }

    @Override
    public boolean callService(String mService) {
        WebService webService = new WebService();
        JParser parser = new JParser();
        String inParam;
        String result;
        if(mService.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)){
            inParam = "rationCardNo="+mRationCardNumber+"&stateId="+mAgentDetails.getmState()+"&fpsCode="+mAgentDetails.getmFpsCode();
            //"rationCardNo=021000002463&stateId=02&fpsCode=103301100002";
            result = webService.callService(WebService.ServiceAPI.GET_RATION_CARD_HOLDER_DETAILS,inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG,"GetRationcardHolderDetails response is null");
            }else {
                mCardHolderDetails = parser.parsingRationcardholderDetails(result);
                if (mCardHolderDetails == null) {
                    Log.e(TAG, "GetRationcardHolderDetails object is null");
                } else {
                    return this.callService(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS);
                }
            }
        }else if(mService.equals(UtilsClass.POST_CREATE_AADHAARSEED)){
            RationLiftingMemberDetails memberDetails = familyMembersAdapter.getSelectedMemberDetails();
            inParam = webService.CreateJsonForCreateAadharSeeding(mAgentDetails.getmUserId(),mCardHolderDetails,memberDetails,UtilsClass.VALIDATING_AADHAR);
            result = webService.callService(WebService.ServiceAPI.POST_CREATE_AADHAARSEED, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"post aadhar create service is null");
            } else {
                Log.e("PostCreateAadharSeed", result);
                return this.callService(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS);
            }
        }else if (mService.equals(UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS)) {
            mSchemeDetailsOnRationCardNumbers = null;
            inParam = "rationCardNo="+mRationCardNumber+"&stateId="+mAgentDetails.getmState();
            result = webService.callService(WebService.ServiceAPI.GET_RATION_LIFTING_MEMBERS_DETAILS, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG, "GetRationLiftingMemberDetails service is null");
            } else {
                Log.e("GetRationLiftingMbrDtl", result);
                mRationLiftingMemberDetailsList = parser.parseRationLiftingMembersDetails(result);
                return true;
            }

        }else if(mService.equals(UtilsClass.GET_SCHEME_DETAILS_FOR_RATION_CARD)){
            String allocationOrderNo = getAllocationDetails().getmAllocationOrderNo();
            inParam = "rationCardNo="+mRationCardNumber+"&fpsCode="+mAgentDetails.getmFpsCode()+"&allocationOrderNo="+allocationOrderNo+"&stateId="+mAgentDetails.getmState();
            //"rationCardNo=021000002463&fpsCode=103301100002&allocationOrderNo=RALLOC072015-072015/02&stateId=02";
            result = webService.callService(WebService.ServiceAPI.GET_SCHEME_DETAILS_FOR_RATION_CARD, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"GetSchemaDetailsForRationCard service is null");
            } else {
                Log.e("GetSchemaDetailsForRC", result);
                mSchemeDetailsOnRationCardNumbers = parser.parsingScemeDetailsOnRationCardNumber(result);
                return true;
            }
        }else if(mService.equals(UtilsClass.POST_SALE_CREATE)){
            RationLiftingMemberDetails memberDetails = familyMembersAdapter.getSelectedMemberDetails();
            AllocationDetails allocationDetails = getAllocationDetails();
            List<SchemeDetailsOnRationCardNumber> selectedCommodities = getSelectedCommodities();
            inParam = webService.CreateJsonForSalesCreate(mTotalPrice, mAgentDetails, allocationDetails, memberDetails,selectedCommodities);
            result = webService.callService(WebService.ServiceAPI.POST_SALE_CREATE, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"PostSaleCreate service is null");
            } else {
                Log.e("PostSalesCreate Result", result);

                return true;
            }
        }
        return super.callService(mService);
    }

    private AgentDetails getAgentDetails() {
        mHelper.openDataBase();
        AgentDetails mAgent = mHelper.getAgentDetails();
        return mAgent;
    }

    public AllocationDetails getAllocationDetails() {
        return mAllocationDetailsList.get(vS_air_AllocationNumberSpinner.getSelectedItemPosition());
    }

    public List<SchemeDetailsOnRationCardNumber> getSelectedCommodities() {
        List<SchemeDetailsOnRationCardNumber> selectedCommodities = new ArrayList<>();
        Set<Integer> keys = mSelectedCommodities.keySet();
        for(int key: keys){
            selectedCommodities.add(mSchemeDetailsOnRationCardNumbers.get(key));
        }
        return selectedCommodities;
    }

    @Override
    public void updateUI(boolean aBoolean, String mService) {
        super.updateUI(aBoolean,mService);
        if(aBoolean){
            if(mService.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)) {
                setCardHolderDetails();
                setMemberList();
            }else if(mService.equals(UtilsClass.GET_SCHEME_DETAILS_FOR_RATION_CARD)){
                setCommodityList();
            }else if(mService.equals(UtilsClass.POST_SALE_CREATE)){
                UtilsClass.mSelectedCommodities = getSelectedCommodities();
                UtilsClass.mAllocationDetails = getAllocationDetails();
                UtilsClass.mSelectedMember = familyMembersAdapter.getSelectedMemberDetails();
                Intent printer = new Intent(IssueRationActivity.this, FPSSalesReceiptPrinter.class);
                startActivityForResult(printer, 0);
                Toast.makeText(IssueRationActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }else if(mService.equals(UtilsClass.POST_CREATE_AADHAARSEED)){
                vL_air_totalTileLayout.setVisibility(View.GONE);
                vL_air_commodityTileLayout.setVisibility(View.GONE);
                vL_air_commodityList.setVisibility(View.GONE);
                setMemberList();
                //setCommodityList();
            }
        }else {
            Toast.makeText(IssueRationActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            UtilsClass.mSelectedCommodities = null;
            UtilsClass.mAllocationDetails = null;
            UtilsClass.mSelectedMember = null;

            mSchemeDetailsOnRationCardNumbers = null;
            mRationLiftingMemberDetailsList = null;

            vL_air_totalTileLayout.setVisibility(View.GONE);
            vL_air_commodityTileLayout.setVisibility(View.GONE);
            vL_air_commodityList.setVisibility(View.GONE);
            vL_air_familyMembersTileLayout.setVisibility(View.GONE);
            vL_air_cardDetailsLayout.setVisibility(View.GONE);
            vL_air_relationsList.setVisibility(View.GONE);
            vT_air_btnMakeSale.setVisibility(View.GONE);

            familyMembersAdapter.notifyDataSetChanged();
            vL_air_relationsList.setAdapter(new FamilyMembersAdapter(IssueRationActivity.this,UtilsClass.ISSUERATION,this,null,null,null));
        }
    }

    //TODO Commodity Adapter
    private class CommodityIssueAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(mSchemeDetailsOnRationCardNumbers == null)
                return 0;
            return mSchemeDetailsOnRationCardNumbers.size();
        }

        @Override
        public SchemeDetailsOnRationCardNumber getItem(int position) {
            return mSchemeDetailsOnRationCardNumbers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.adapter_commodity_issue,parent,false);
            }
            final EditText vE_air_QuantityForSale= (EditText) convertView.findViewById(R.id.vE_air_quantityForSale);
            final ImageView vI_afm_selectedRow = (ImageView) convertView.findViewById(R.id.vI_acm_selectedRow);
            TextView vT_aci_lblCommodity = (TextView) convertView.findViewById(R.id.vT_aci_lblCommodity);
            TextView vT_aci_lblFPSStock = (TextView) convertView.findViewById(R.id.vT_aci_lblFPSStock);
            TextView vT_aci_lblEligibleQTY = (TextView) convertView.findViewById(R.id.vT_aci_lblEligibleQTY);
            final TextView vT_aci_lblGivenQty = (TextView) convertView.findViewById(R.id.vT_aci_lblGivenQty);
            final TextView vT_aci_lblBalanceQty = (TextView) convertView.findViewById(R.id.vT_aci_lblBalanceQty);
            TextView vT_aci_lblPriceQty = (TextView) convertView.findViewById(R.id.vT_aci_lblPriceQty);
            TextView vT_aci_lblSchemeName = (TextView) convertView.findViewById(R.id.vT_aci_lblSchemeName);
            final TextView vT_aci_lblPrice = (TextView) convertView.findViewById(R.id.vT_aci_lblPrice);
            LinearLayout vL_afm_familyMembers = (LinearLayout) convertView.findViewById(R.id.vL_acm_familyMembers);

            if (position % 2 == 0) {
                vL_afm_familyMembers.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                vL_afm_familyMembers.setBackgroundColor(getResources().getColor(R.color.red_light));
            }
            if(mSelectedCommodities.containsKey(position)){
                vE_air_QuantityForSale.setEnabled(true);
                vI_afm_selectedRow.setImageResource(R.drawable.ir_selected);
            }else{
                vE_air_QuantityForSale.setHint("0");
                vE_air_QuantityForSale.setEnabled(false);
                vI_afm_selectedRow.setImageResource(R.drawable.ir_unselected);
            }

            final SchemeDetailsOnRationCardNumber schemeDetails = getItem(position);

            String rmnQty, alctQty, lftQty, balQty, comRate;
            double remaingQty = 0,allotedQty = 0, liftedQty = 0, balancedQty = 0, commRate = 0;

            rmnQty =  schemeDetails.getmRemainingQty().trim();
            alctQty =  schemeDetails.getmAllotedQty().trim();
            lftQty =  schemeDetails.getmLiftedQty().trim();
            balQty =  schemeDetails.getmBalancedQty().trim();
            comRate = schemeDetails.getmRates();

            if(rmnQty != null && rmnQty.length() > 0){
                remaingQty = Double.parseDouble(rmnQty);
                rmnQty = UtilsClass.df.format(remaingQty);
            }else{
                rmnQty="0";
            }
            if(alctQty != null && alctQty.length() > 0){
                allotedQty = Double.parseDouble(alctQty);
                alctQty = UtilsClass.df.format(allotedQty);
            }else {
                alctQty="0";
            }if(lftQty != null && lftQty.length() > 0){
                    liftedQty = Double.parseDouble(lftQty);
                    lftQty = UtilsClass.df.format(liftedQty);
            }else{
                lftQty="0";
            }
            if(balQty != null && balQty.length() > 0){
                balancedQty = Double.parseDouble(balQty);
                balQty = UtilsClass.df.format(balancedQty);
            }else{
                balQty="0";
            }if(comRate != null && comRate.length() > 0){
                commRate = Double.parseDouble(comRate);
                comRate = UtilsClass.df.format(commRate);
            }else{
                comRate="0";
            }


            vT_aci_lblSchemeName.setText(schemeDetails.getmSchemeName());
            vT_aci_lblCommodity.setText(schemeDetails.getmCommodityName());
            vT_aci_lblFPSStock.setText(rmnQty);
            vT_aci_lblEligibleQTY.setText(alctQty);
            vT_aci_lblGivenQty.setText(lftQty);
            vT_aci_lblBalanceQty.setText(alctQty);
            vT_aci_lblPriceQty.setText(comRate);
            vT_aci_lblPrice.setText("0");

            vE_air_QuantityForSale.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String ltdQ = schemeDetails.getmLiftedQty().trim();
                    double ltdQty = (ltdQ!= null && ltdQ.length()>0)? Double.parseDouble(ltdQ) : 0;
                    double givenQty = 0;

                    String balQty = schemeDetails.getmAllotedQty().trim();  //schemeDetails.getmBalancedQty().trim();
                    double bQty = (balQty!= null && balQty.length()>0)? Double.parseDouble(balQty) : 0;
                    double blQty = 0;

                    String rates = schemeDetails.getmRates();

                    if(s == null || s.length() == 0){
                        s = "0";
                        if(mSelectedCommodities.containsKey(position))
                            mSelectedCommodities.remove(position);
                        Log.e(TAG, "Commodity price is empty");
                    }
                    String qty = s.toString().trim();
                    double enteredQty = Double.parseDouble(qty);

                    if (balQty == null || balQty.length() == 0) {
                        Log.e(TAG, "Balance Qty is empty");
                    } else {
                        double balanceQty = Double.parseDouble(balQty.trim());
                        if (balanceQty >= enteredQty) {
                            givenQty = ltdQty + enteredQty;
                            blQty = bQty - enteredQty;
                            mSelectedCommodities.put(position, qty);
                            schemeDetails.setmSoldQty(qty);
                        } else {
                            vE_air_QuantityForSale.setText(UtilsClass.df.format(balanceQty));
                            mSelectedCommodities.put(position, balQty);
                            schemeDetails.setmSoldQty(balQty);
                            Toast.makeText(IssueRationActivity.this, "Quantity should less than Balance Quantity", Toast.LENGTH_SHORT).show();
                        }
                    }

                    double commodityPrice = calculateTotalAmount(position,rates);
                    vT_aci_lblPrice.setText(UtilsClass.df.format(commodityPrice));

                    String mTotalPrice = ""+commodityPrice;
                    vT_aci_lblGivenQty.setText(UtilsClass.df.format(givenQty));

                    vT_aci_lblBalanceQty.setText(UtilsClass.df.format(blQty));

                    schemeDetails.setmTotalAmount(mTotalPrice);
                    setTotalPrice(rates);
                    checkVisiblityofMakeSaleButton(schemeDetails.getmRemainingQty().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedCommodities.containsKey(position)) {
                        mSelectedCommodities.remove(position);
                        schemeDetails.setmTotalAmount("0");
                        schemeDetails.setmSoldQty("0");
                        vT_aci_lblPrice.setText("0");
                        vE_air_QuantityForSale.setText("");
                        vE_air_QuantityForSale.setHint("0");
                        vE_air_QuantityForSale.setEnabled(false);
                        vI_afm_selectedRow.setImageResource(R.drawable.ir_unselected);
                    } else {
                        mSelectedCommodities.put(position,"0");
                        vE_air_QuantityForSale.setEnabled(true);
                        vI_afm_selectedRow.setImageResource(R.drawable.ir_selected);
                    }
                    setTotalPrice(schemeDetails.getmRates());
                    checkVisiblityofMakeSaleButton(schemeDetails.getmRemainingQty());
                }
            });
            return convertView;
        }

        private double calculateTotalAmount(int position,String actlRate) {
            double commodityPrice = 0;
            if(actlRate != null && actlRate.trim().length() >0){
                double actualRate = Double.parseDouble(actlRate);
                if(mSelectedCommodities.containsKey(position)) {
                    String qty = mSelectedCommodities.get(position);
                    commodityPrice = Double.parseDouble(qty) * actualRate;
                }
            }
            return commodityPrice;
        }

        private void setTotalPrice(String actlRate) {
            mTotalPrice = 0;
            Set<Integer> keys = mSelectedCommodities.keySet();
            for(Integer key: keys){
                String enteredValues = mSelectedCommodities.get(key);
                if(actlRate != null && actlRate.length()  > 0 && enteredValues.length() > 0) {
                    mTotalPrice += (Double.parseDouble(enteredValues) * Double.parseDouble(actlRate));
                }
            }
            vT_air_TotalValue.setText(UtilsClass.df.format(mTotalPrice));
        }

        private void checkVisiblityofMakeSaleButton(String fpsStockQty) {
            double stock = (fpsStockQty != null && fpsStockQty.length() > 0)? Double.parseDouble(fpsStockQty):0;
            if(/*stock < 1 || */mSelectedCommodities.size() == 0 || mTotalPrice <= 0){
                vT_air_btnMakeSale.setVisibility(View.GONE);
            }else {
                vT_air_btnMakeSale.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    protected void updateCaptions() {
        //Toast.makeText(IssueRationActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_air_lblRCNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATION_NUMBER)){
            vT_air_lblAllocationNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATION_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATION_MONTH)){
            vT_air_lblAllocationMonth.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATION_MONTH));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_GET_DETAILS)){
            vT_air_btnGetDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_GET_DETAILS));
        }
    }

}
