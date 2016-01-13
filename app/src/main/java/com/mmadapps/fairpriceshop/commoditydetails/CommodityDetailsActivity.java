package com.mmadapps.fairpriceshop.commoditydetails;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.CommodityDetails;
import com.mmadapps.fairpriceshop.bean.TruckChallanDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.posprinter.FPSCommodityPrinter;
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
public class CommodityDetailsActivity extends ActionbarActivity {

    private final String TAG = "CommodityDetailsCls";

    private ListView vL_acd_commodityList;
    private LinearLayout vL_acd_contentsLayout,vL_acd_truckDetailsLayout;
    private TextView vT_acd_btnGetDetails,vT_acd_btnUpdateStock,vT_acd_DONumberValue,vT_acd_DODateValue,vT_acd_DOValidityValue,vT_acd_lbltruckDetails,vT_acd_lblTChallenNumber,
            vT_acd_lblTransporterId,vT_acd_lblMobileNumber,vT_acd_lblSGCName,vT_acd_lblTruckNumber,vT_acd_lblDispatchDate,vT_acd_lblDriverName;

    private TextView vT_acd_TChallenNumberValue,vT_acd_SGCNameValue,vT_acd_DispatchDateValue,vT_acd_TransporterIdValue,vT_acd_TruckNumberValue,
            vT_acd_DriverNameValue;
    private EditText vE_acd_challanNumber,vE_acd_truckChallanNumber;
    //Language views
    private TextView vT_acd_lblPCNumber,vT_acd_lblTCNumber,vT_acd_lblCommodityDetails,vT_acd_lblDONumber,vT_acd_lblDODate,vT_acd_lblDOValidity,
            vT_acd_lblCommodity,vT_acd_lblDOQty,vT_acd_lblQtyRcvd,vT_acd_lblQtyInTruck,vT_acd_lblLossGain,vT_acd_lblBalance,vT_acd_lblQtyRcvdInTruck;

    //Variables
    private TruckChallanDetails mTruckChallanDetails;
    private AgentDetails mAgentDetails;
    private String mTruckChallanNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_commodity_details);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_comodity_details,true);
        initializeViews();
        updateCaptions();
        setActionEvents();
        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        mAgentDetails = mHelper.getAgentDetails();
        mHelper.close();
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vT_acd_btnGetDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_btnUpdateStock.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DONumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DODateValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DOValidityValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DONumberValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DODateValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_DOValidityValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vE_acd_challanNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vE_acd_truckChallanNumber.setTypeface(FPSApplication.fMyriadPro_Regular);

                vT_acd_TChallenNumberValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_SGCNameValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_DispatchDateValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_TransporterIdValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_TruckNumberValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_DriverNameValue.setTypeface(FPSApplication.fMyriadPro_Regular);



                vT_acd_lblPCNumber = (TextView) findViewById(R.id.vT_acd_lblPCNumber);
                vT_acd_lblTCNumber = (TextView) findViewById(R.id.vT_acd_lblTCNumber);
                vT_acd_lblCommodityDetails = (TextView) findViewById(R.id.vT_acd_lblCommodityDetails);
                vT_acd_lblDONumber = (TextView) findViewById(R.id.vT_acd_lblDONumber);
                vT_acd_lblDODate = (TextView) findViewById(R.id.vT_acd_lblDODate);
                vT_acd_lblDOValidity = (TextView) findViewById(R.id.vT_acd_lblDOValidity);
                vT_acd_lblCommodity = (TextView) findViewById(R.id.vT_acd_lblCommodity);
                vT_acd_lblDOQty = (TextView) findViewById(R.id.vT_acd_lblDOQty);
                vT_acd_lblQtyRcvd = (TextView) findViewById(R.id.vT_acd_lblQtyRcvd);
                vT_acd_lblQtyInTruck = (TextView) findViewById(R.id.vT_acd_lblQtyInTruck);
                vT_acd_lblLossGain = (TextView) findViewById(R.id.vT_acd_lblLossGain);
                vT_acd_lblBalance = (TextView) findViewById(R.id.vT_acd_lblBalance);
                vT_acd_lblQtyRcvdInTruck = (TextView) findViewById(R.id.vT_acd_lblQtyRcvdInTruck);

                vT_acd_lblPCNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblTCNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblCommodityDetails.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_lblDONumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_lblDODate.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_lblDOValidity.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_acd_lblCommodity.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblDOQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblQtyRcvd.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblQtyInTruck.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblLossGain.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblBalance.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_acd_lblQtyRcvdInTruck.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void setActionEvents() {
        vT_acd_btnGetDetails.setOnClickListener(this);
        vT_acd_btnUpdateStock.setOnClickListener(this);
    }

    private void setValues() {
        if(mTruckChallanDetails == null){
            Toast.makeText(CommodityDetailsActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Truck Cln Dtls is null");
        }else{
            setTruckDetails();
            if(mTruckChallanDetails.getmTruckChalanDetails() == null || mTruckChallanDetails.getmTruckChalanDetails().size() == 0){
                Toast.makeText(CommodityDetailsActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Commodity List is null");
            }else{
                vL_acd_contentsLayout.setVisibility(View.VISIBLE);
                vT_acd_btnUpdateStock.setVisibility(View.VISIBLE);
                vL_acd_truckDetailsLayout.setVisibility(View.VISIBLE);

                vL_acd_commodityList.setAdapter(new CommodityDetailsAdapter());
                setListViewHeightBasedOnChildren(vL_acd_commodityList);
            }
        }
    }

    private void setTruckDetails() {
        vT_acd_TChallenNumberValue.setText(mTruckChallanDetails.getmTruckChalanNumber());
        vT_acd_SGCNameValue.setText(mTruckChallanDetails.getmStateGodownName());
        vT_acd_DispatchDateValue.setText(mTruckChallanDetails.getmDispatchDate());
        vT_acd_TransporterIdValue.setText(mTruckChallanDetails.getmTransporterName());
        vT_acd_TruckNumberValue.setText(mTruckChallanDetails.getmTruckNumber());
        vT_acd_DriverNameValue.setText(mTruckChallanDetails.getmDriverName());

        vT_acd_DONumberValue.setText(mTruckChallanDetails.getmDeliveryOrderNo());
        vT_acd_DODateValue.setText(mTruckChallanDetails.getmDeliveryOrderDate());
        vT_acd_DOValidityValue.setText(mTruckChallanDetails.getmDispatchDate());
    }

    private void initializeViews() {
        vT_acd_TChallenNumberValue = (TextView) findViewById(R.id.vT_acd_TChallenNumberValue);
        vT_acd_SGCNameValue = (TextView) findViewById(R.id.vT_acd_SGCNameValue);
        vT_acd_DispatchDateValue = (TextView) findViewById(R.id.vT_acd_DispatchDateValue);
        vT_acd_TransporterIdValue = (TextView) findViewById(R.id.vT_acd_TransporterIdValue);
        vT_acd_TruckNumberValue = (TextView) findViewById(R.id.vT_acd_TruckNumberValue);
        vT_acd_DriverNameValue = (TextView) findViewById(R.id.vT_acd_DriverNameValue);

        vL_acd_commodityList = (ListView) findViewById(R.id.vL_acd_commodityList);
        vT_acd_btnGetDetails = (TextView) findViewById(R.id.vT_acd_btnGetDetails);
        vT_acd_btnUpdateStock = (TextView) findViewById(R.id.vT_acd_btnUpdateStock);
        vL_acd_contentsLayout = (LinearLayout) findViewById(R.id.vL_acd_contentsLayout);
        vL_acd_truckDetailsLayout = (LinearLayout) findViewById(R.id.vL_acd_truckDetailsLayout);

        vT_acd_DONumberValue = (TextView) findViewById(R.id.vT_acd_DONumberValue);
        vT_acd_DODateValue = (TextView) findViewById(R.id.vT_acd_DODateValue) ;
        vT_acd_DOValidityValue = (TextView) findViewById(R.id.vT_acd_DOValidityValue) ;
        vE_acd_challanNumber = (EditText) findViewById(R.id.vE_acd_challanNumber);
        vE_acd_truckChallanNumber = (EditText) findViewById(R.id.vE_acd_truckChallanNumber);
        vT_acd_lbltruckDetails= (TextView) findViewById(R.id.vT_acd_lbltruckDetails);
        vT_acd_lblTChallenNumber= (TextView) findViewById(R.id.vT_acd_lblTChallenNumber);
        vT_acd_lblTransporterId= (TextView) findViewById(R.id.vT_acd_lblTransporterId);
        vT_acd_lblMobileNumber= (TextView) findViewById(R.id.vT_acd_lblMobileNumber);
        vT_acd_lblSGCName= (TextView) findViewById(R.id.vT_acd_lblSGCName);
        vT_acd_lblTruckNumber= (TextView) findViewById(R.id.vT_acd_lblTruckNumber);
        vT_acd_lblDispatchDate= (TextView) findViewById(R.id.vT_acd_lblDispatchDate);
        vT_acd_lblDriverName= (TextView) findViewById(R.id.vT_acd_lblDriverName);

        vE_acd_truckChallanNumber.setText("0104201615151033011000021");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_acd_btnGetDetails:
                vT_acd_btnGetDetails.setEnabled(false);
                mTruckChallanNumber = ""+vE_acd_truckChallanNumber.getText().toString();
                if(mTruckChallanNumber.length() == 0){
                    Toast.makeText(CommodityDetailsActivity.this, "Please enter truck challan number", Toast.LENGTH_SHORT).show();
                }else {
                    callServiceClass(UtilsClass.GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO);
                }
                vT_acd_btnGetDetails.setEnabled(true);
                break;
            case R.id.vT_acd_btnUpdateStock:
                vT_acd_btnUpdateStock.setEnabled(false);
                callServiceClass(UtilsClass.POST_COMMODITY_RECEIVE);
                vT_acd_btnUpdateStock.setEnabled(true);
                break;
        }
    }

    private void callServiceClass(String mService) {
        new AsyncServiceCall(this, this, mService);
    }

    @Override
    public boolean callService(String mService) {
        WebService service = new WebService();
        JParser jParser = new JParser();
        String inParam;
        if(mService.equals(UtilsClass.GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO)){
            inParam = "truckChalanNo="+mTruckChallanNumber+"&fpsCode="+mAgentDetails.getmFpsCode();
            //"truckChalanNo=0104201615151033011000021&fpsCode=103301100002";
            String result = service.callService(WebService.ServiceAPI.GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO, inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG, "result is null");
            }else{
                mTruckChallanDetails = jParser.parsingCommodityReceivingDetails(result);
                if(mTruckChallanDetails == null ){
                    Log.e(TAG,"truck challan is null");
                }else{
                    return true;
                }
            }
        }else if(mService.equals(UtilsClass.POST_COMMODITY_RECEIVE)){
            Helper mHelper = new Helper(this);
            mHelper.openDataBase();
            AgentDetails mAgentDetails = mHelper.getAgentDetails();
            mHelper.close();
            inParam = service.CreateJsonForSaveCommodityReceiced(mAgentDetails, mTruckChallanDetails);
            String result = service.callService(WebService.ServiceAPI.POST_COMMODITY_RECEIVE, inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG, "post cmdt recv result is null");
            }else{
                String responseString = jParser.parsingSaveCommodityReceivingDetailsResult(result);
                if(responseString == null || responseString.length() == 0){
                    Log.e(TAG,"parse save cmdt recv is null");
                }else{
                    return true;
                }
            }
        }
        return super.callService(mService);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted) {
            if (mService.equals(UtilsClass.GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO)) {
                setValues();
            }else if(mService.equals(UtilsClass.POST_COMMODITY_RECEIVE)){
                Toast.makeText(CommodityDetailsActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                UtilsClass.mTruckChallanDetails = mTruckChallanDetails;
                Intent commodityPrinter = new Intent(CommodityDetailsActivity.this, FPSCommodityPrinter.class);
                startActivityForResult(commodityPrinter, 0);
            }
        }else{
            Toast.makeText(CommodityDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            UtilsClass.mTruckChallanDetails = null;
            UtilsClass.mSelectedMember = null;

            Toast.makeText(CommodityDetailsActivity.this, "completed", Toast.LENGTH_SHORT).show();
        }
    }

    private class CommodityDetailsAdapter extends BaseAdapter {

        private List<CommodityDetails> commodityDetailsList;
        public CommodityDetailsAdapter(){
            commodityDetailsList = mTruckChallanDetails.getmTruckChalanDetails();
        }

        @Override
        public int getCount() {
            return commodityDetailsList.size();
        }

        @Override
        public CommodityDetails getItem(int position) {
            return commodityDetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.adapter_commodity_details,parent,false);
            }
            LinearLayout vL_apcd_commodityDetails = (LinearLayout) convertView.findViewById(R.id.vL_apcd_commodityDetails);
            if(position%2 == 0){
                vL_apcd_commodityDetails.setBackgroundColor(getResources().getColor(R.color.white));
            }else{
                vL_apcd_commodityDetails.setBackgroundColor(getResources().getColor(R.color.green_light));
            }
            final TextView vT_apcd_lblCommodity,vT_apcd_lblDOQty,vT_apcd_lblQtyRcvd,vT_apcd_lblQtyintruck,vT_apcd_lblLossGain,vT_apcd_lblBalance,vT_apcd_lblKgs;
            final EditText vE_apcd_QtyValue;

            vT_apcd_lblCommodity = (TextView) convertView.findViewById(R.id.vT_apcd_lblCommodity) ;
            vT_apcd_lblDOQty = (TextView) convertView.findViewById(R.id.vT_apcd_lblDOQty) ;
            vT_apcd_lblQtyRcvd = (TextView) convertView.findViewById(R.id.vT_apcd_lblQtyRcvd) ;
            vT_apcd_lblQtyintruck = (TextView) convertView.findViewById(R.id.vT_apcd_lblQtyintruck) ;
            vT_apcd_lblLossGain = (TextView) convertView.findViewById(R.id.vT_apcd_lblLossGain);
            vT_apcd_lblBalance = (TextView) convertView.findViewById(R.id.vT_apcd_lblBalance) ;
            vT_apcd_lblKgs = (TextView) convertView.findViewById(R.id.vT_apcd_lblKgs);
            vE_apcd_QtyValue = (EditText) convertView.findViewById(R.id.vE_apcd_QtyValue);

            new Runnable() {
                @Override
                public void run() {
                    vT_apcd_lblCommodity.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblDOQty.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblQtyRcvd.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblQtyintruck.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblLossGain.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblBalance.setTypeface(FPSApplication.fSegoeui);
                    vT_apcd_lblKgs.setTypeface(FPSApplication.fSegoeui);
                    vE_apcd_QtyValue.setTypeface(FPSApplication.fSegoeui);
                }
            }.run();

            final CommodityDetails commodityDetails = getItem(position);
            commodityDetails.setmCommodityReceived("0");

            String altQty,comRecvd,comQty;
            double alloctQty = 0, commRecvd = 0, comdtQty = 0;

            altQty = commodityDetails.getmAllotedQty().trim();
            comRecvd = commodityDetails.getmCommodityReceived().trim();
            comQty = commodityDetails.getmCommodityQty().trim();

            if(altQty != null && altQty.length()>0){
                alloctQty = Double.parseDouble(altQty);
                altQty = UtilsClass.df.format(alloctQty);
            }else{
                altQty = "0";
            }
            if(comRecvd != null && comRecvd.length() >0){
                commRecvd = Double.parseDouble(comRecvd);
                comRecvd = UtilsClass.df.format(commRecvd);
            }else{
                comRecvd = "0";
            }
            if(comQty != null && comQty.length() > 0){
                comdtQty = Double.parseDouble(comQty);
                comQty = UtilsClass.df.format(comdtQty);
            }else{
                comQty = "0";
            }

            vT_apcd_lblCommodity.setText(commodityDetails.getmCommodityName());
            vT_apcd_lblDOQty.setText(altQty);
            vT_apcd_lblQtyRcvd.setText(comRecvd);
            vT_apcd_lblQtyintruck.setText(comQty);
            vT_apcd_lblLossGain.setText("0");
            vT_apcd_lblBalance.setText("0");

            vE_apcd_QtyValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s== null || s.length() == 0){
                        Log.e(TAG,"no data entered in qty");
                        vE_apcd_QtyValue.setHint("0");
                        vT_apcd_lblLossGain.setText("0");
                        vT_apcd_lblBalance.setText("0z");
                    }else {
                        String enterdQty = s.toString();
                        commodityDetails.setmCommodityReceived(enterdQty);
                        String balanceQty = calculateBalanceQty(enterdQty,commodityDetails);
                        vT_apcd_lblBalance.setText(balanceQty);
                        String lossRgain = calculateLossOrGain(enterdQty,commodityDetails);
                        vT_apcd_lblLossGain.setText(lossRgain);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            return convertView;
        }

        private String calculateLossOrGain(String enterdQty, CommodityDetails commodityDetails) {
            double cmdtQty=0;
            String comdQty = commodityDetails.getmCommodityQty();
            if(comdQty != null && comdQty.length()>0) {
                cmdtQty = Double.parseDouble(comdQty);
            }
            double entrQty = Double.parseDouble(enterdQty);
            return UtilsClass.df.format(cmdtQty-entrQty);
        }

        private String calculateBalanceQty(String enterdQty, CommodityDetails commodityDetails) {
            double alctQty = 0,cmdtQty =0;
            String allQty = commodityDetails.getmAllotedQty();
            String comdQty = commodityDetails.getmCommodityQty();
            if(allQty != null && allQty.length()>0) {
                alctQty = Double.parseDouble(allQty);
            }if(comdQty != null && comdQty.length()>0) {
                cmdtQty = Double.parseDouble(comdQty);
            }
            double entrQty = Double.parseDouble(enterdQty);
            return UtilsClass.df.format(alctQty-entrQty+cmdtQty);
        }

    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(CommodityDetailsActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_PENDING_CHALLAN_NUMBER)){
            vT_acd_lblPCNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_PENDING_CHALLAN_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER)){
            vT_acd_lblTCNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_GET_DETAILS)){
            vT_acd_btnGetDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_GET_DETAILS));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRUCK_DETAILS)){
            vT_acd_lbltruckDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_GET_DETAILS));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER)){
            vT_acd_lblTChallenNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRANSPORTER_ID_NAME)){
            vT_acd_lblTransporterId.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRANSPORTER_ID_NAME));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_Mobile_NUMBER)){
            vT_acd_lblMobileNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_Mobile_NUMBER));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_STATE_GODOWN_CODE_NAME)){
            vT_acd_lblSGCName.setText(languageCaptionsMap.get(CaptionsKey.KEY_STATE_GODOWN_CODE_NAME));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRUCK_NUMBER)){
            vT_acd_lblTruckNumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRUCK_NUMBER));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DISPATCHED_DATE)){
            vT_acd_lblDispatchDate.setText(languageCaptionsMap.get(CaptionsKey.KEY_DISPATCHED_DATE));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DRIVER_NAME)){
            vT_acd_lblDriverName.setText(languageCaptionsMap.get(CaptionsKey.KEY_DRIVER_NAME));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY_DETAILS)){
            vT_acd_lblCommodityDetails.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY_DETAILS));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_NO)){
            vT_acd_lblDONumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_NO));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_DATE)){
            vT_acd_lblDODate.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_DATE));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_DATE)){
            vT_acd_lblDODate.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_DATE));

        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_QTY)){
            vT_acd_lblDOValidity.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_QTY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_acd_lblCommodity.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_QTY)){
            vT_acd_lblDOQty.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_QTY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_QTY_RCVD)){
            vT_acd_lblQtyRcvd.setText(languageCaptionsMap.get(CaptionsKey.KEY_QTY_RCVD));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_QTY_IN_TRUCK)){
            vT_acd_lblQtyInTruck.setText(languageCaptionsMap.get(CaptionsKey.KEY_QTY_IN_TRUCK));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_LOSS_GAIN)){
            vT_acd_lblLossGain.setText(languageCaptionsMap.get(CaptionsKey.KEY_LOSS_GAIN));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_BALANCE)){
            vT_acd_lblBalance.setText(languageCaptionsMap.get(CaptionsKey.KEY_BALANCE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_QTY_RCVD_IN_TRUCK)){
            vT_acd_lblQtyRcvdInTruck.setText(languageCaptionsMap.get(CaptionsKey.KEY_QTY_RCVD_IN_TRUCK));
        }
    }

}
