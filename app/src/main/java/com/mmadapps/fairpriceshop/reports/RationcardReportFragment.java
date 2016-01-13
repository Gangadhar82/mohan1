package com.mmadapps.fairpriceshop.reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationCardMemberDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.ServiceInteractor;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 12/11/2015.
 */
public class RationcardReportFragment extends Fragment implements View.OnClickListener,ServiceInteractor,LanguageInteractor {

    //Views
    private ListView vL_frr_reportsList;
    private TextView vT_frr_btnSubmit,vE_frr_lblRCNumberValue,vE_frr_RHNameValue,vT_frr_RHAgeValue,vT_frr_RHRelationValue,vT_frr_RHDateofbirthValue,vT_frr_UIDValue,vT_frr_RCTypeValue;
    private LinearLayout vL_frr_cardHolderDetailsLayout,vL_frr_contentsLayout;

    //Variables
    private String rationCardNumber;
    private RationCardHolderDetails cardHolderDetails;
    private List<RationCardMemberDetails> memberDetailsList;
    private AgentDetails mAgentDetails;
    private final String TAG = "RationCardReport";

    //languages views
    private   TextView vT_frr_lblRCNumber,vT_frr_lblMemberDetails,vT_frr_lblName,vT_frr_lblGender,vT_frr_lblAge,vT_frr_lblRelation,
            vT_frr_lblUid,vT_frr_lblRCDetails,vT_frr_lblRHName,vT_frr_lblRHAge,vT_frr_lblRHRelation,vT_frr_lblRHDateofbirth,
            vT_frr_lblUID,vT_frr_lblRCType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportFragmentActivity.languageReseter = this;
        View rootView = inflater.inflate(R.layout.fragment_rationcard_report,container,false);
        initializeViews(rootView);
        setFont(rootView);
        setActionEvents();
        return rootView;
    }

    private void setFont(final View rootView) {
        new Runnable(){
            @Override
            public void run() {


                vT_frr_lblRCNumber= (TextView) rootView.findViewById(R.id.vT_frr_lblRCNumber);
                vT_frr_lblMemberDetails=(TextView) rootView.findViewById(R.id.vT_frr_lblMemberDetails);
                vT_frr_lblName=(TextView) rootView.findViewById(R.id.vT_frr_lblName);
                vT_frr_lblGender=(TextView) rootView.findViewById(R.id.vT_frr_lblGender);
                vT_frr_lblAge=(TextView) rootView.findViewById(R.id.vT_frr_lblAge);
                vT_frr_lblRelation=(TextView) rootView.findViewById(R.id.vT_frr_lblRelation);
                vT_frr_lblUid=(TextView) rootView.findViewById(R.id.vT_frr_lblUid);
                vT_frr_lblRCDetails=(TextView) rootView.findViewById(R.id.vT_frr_lblRCDetails);
                vT_frr_lblRHName=(TextView) rootView.findViewById(R.id.vT_frr_lblRHName);
                vT_frr_lblRHAge=(TextView) rootView.findViewById(R.id.vT_frr_lblRHAge);
                vT_frr_lblRHRelation=(TextView) rootView.findViewById(R.id.vT_frr_lblRHRelation);
                vT_frr_lblRHDateofbirth=(TextView) rootView.findViewById(R.id.vT_frr_lblRHDateofbirth);
                vT_frr_lblUID=(TextView) rootView.findViewById(R.id.vT_frr_lblUID);
                vT_frr_lblRCType=(TextView) rootView.findViewById(R.id.vT_frr_lblRCType);

                vT_frr_lblRCNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblMemberDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblGender.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblAge.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRelation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblUid.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRCDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRHName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRHAge.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRHRelation.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRHDateofbirth.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblUID.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_frr_lblRCType.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vE_frr_lblRCNumberValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vE_frr_RHNameValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_frr_RHAgeValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_frr_RHRelationValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_frr_RHDateofbirthValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_frr_UIDValue.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_frr_RCTypeValue.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        }.run();
    }

    private void setActionEvents() {
        vT_frr_btnSubmit.setOnClickListener(this);
    }

    private void initializeViews(View rootView) {
        vE_frr_lblRCNumberValue = (TextView) rootView.findViewById(R.id.vE_frr_lblRCNumberValue);
        vT_frr_btnSubmit = (TextView) rootView.findViewById(R.id.vT_frr_btnSubmit);
        vL_frr_reportsList = (ListView) rootView.findViewById(R.id.vL_frr_reportsList);
        vL_frr_cardHolderDetailsLayout = (LinearLayout) rootView.findViewById(R.id.vL_frr_cardHolderDetailsLayout);
        vL_frr_contentsLayout = (LinearLayout) rootView.findViewById(R.id.vL_frr_contentsLayout);

        vE_frr_RHNameValue = (TextView) rootView.findViewById(R.id.vE_frr_RHNameValue);
        vT_frr_RHAgeValue = (TextView) rootView.findViewById(R.id.vT_frr_RHAgeValue);
        vT_frr_RHRelationValue = (TextView) rootView.findViewById(R.id.vT_frr_RHRelationValue);
        vT_frr_RHDateofbirthValue = (TextView) rootView.findViewById(R.id.vT_frr_RHDateofbirthValue);
        vT_frr_UIDValue = (TextView) rootView.findViewById(R.id.vT_frr_UIDValue);
        vT_frr_RCTypeValue = (TextView) rootView.findViewById(R.id.vT_frr_RCTypeValue);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCaptions();
        getAgentDetailsFromDB();
    }

    private void getAgentDetailsFromDB() {
        Helper helper = new Helper(getActivity());
        helper.openDataBase();
        mAgentDetails = helper.getAgentDetails();
        helper.close();
    }

    private void setValues() {
        if(cardHolderDetails == null) {
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
        }else{
            vL_frr_cardHolderDetailsLayout.setVisibility(View.VISIBLE);
            vL_frr_contentsLayout.setVisibility(View.VISIBLE);
            vE_frr_RHNameValue.setText(cardHolderDetails.getmMemberName());
            vT_frr_RHAgeValue.setText("Null");
            vT_frr_RHRelationValue.setText("Null");
            vT_frr_RHDateofbirthValue.setText("Null");
            vT_frr_UIDValue.setText("Null");
            vT_frr_RCTypeValue.setText("Null");
        }
        vL_frr_reportsList.setAdapter(new RationcardReportAdapter());
        ReportFragmentActivity.setListViewHeightBasedOnChild.resetListSize(vL_frr_reportsList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vT_frr_btnSubmit:
                rationCardNumber = ""+vE_frr_lblRCNumberValue.getText().toString().trim();
                if(rationCardNumber.length() == 0){
                    Toast.makeText(getActivity(), "Please enter ration card number", Toast.LENGTH_SHORT).show();
                }else {
                    vT_frr_btnSubmit.setEnabled(false);
                    //rationCardNumber = "272000000002";
                    if (rationCardNumber.length() == 0) {
                        Toast.makeText(getActivity(), R.string.empty_ration_card_number, Toast.LENGTH_SHORT).show();
                    } else {
                        callServiceClass(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS);
                    }
                    vT_frr_btnSubmit.setEnabled(true);
                }
                break;
        }
    }

    private void callServiceClass(String mService) {
        new AsyncServiceCall(getActivity(),this,mService);
    }

    @Override
    public boolean callService(String mService) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(mService.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)){
            inParam = "rationCardNo="+rationCardNumber+"&stateId="+mAgentDetails.getmState()+"&fpsCode="+mAgentDetails.getmFpsCode();
            result = webService.callService(WebService.ServiceAPI.GET_RATION_CARD_HOLDER_DETAILS,inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG,"Card Holder is null");
            }else{
                Log.e(TAG,result);
                cardHolderDetails = jParser.parsingRationcardholderDetails(result);
                if(cardHolderDetails == null){
                    Log.e(TAG,"Card Holder object null");
                }else{
                    return this.callService(UtilsClass.GET_MEMBER_DETAILS_REPORT);
                }
            }
        } else if (mService.equals(UtilsClass.GET_MEMBER_DETAILS_REPORT)) {
            inParam = "ractionCardNo=" + rationCardNumber + "&stateId="+mAgentDetails.getmState();
            result = webService.callService(WebService.ServiceAPI.GET_MEMBER_DETAILS_REPORT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG, "Member Details is null");
            } else {
                memberDetailsList = jParser.parsingMemberDetails(result);
                if (memberDetailsList == null || memberDetailsList.size() == 0) {
                    Log.e(TAG, "MemberDetails list null");
                }
                return true;
            }
        }
        return ReportFragmentActivity.setListViewHeightBasedOnChild.callService(mService);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        ReportFragmentActivity.setListViewHeightBasedOnChild.updateUI(isCompleted,mService);
        if(isCompleted) {
            if (mService.equals(UtilsClass.GET_RATION_CARD_HOLDER_DETAILS)) {
                setValues();
            }
        }else{
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private class RationcardReportAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(memberDetailsList == null || memberDetailsList.size() == 0){
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return memberDetailsList.size();
        }

        @Override
        public RationCardMemberDetails getItem(int position) {
            return memberDetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_stock_report,parent, false);
            }
            final TextView vT_asr_lblCommodityName,vT_asr_lblAllocatedQty,vT_asr_lblRcvdQty,vT_asr_lblTotalSoldQty,vT_asr_lblTotalBalanceQty;
            vT_asr_lblCommodityName = (TextView) convertView.findViewById(R.id.vT_asr_lblCommodityName);
            vT_asr_lblAllocatedQty = (TextView) convertView.findViewById(R.id.vT_asr_lblAllocatedQty);
            vT_asr_lblRcvdQty = (TextView) convertView.findViewById(R.id.vT_asr_lblRcvdQty);
            vT_asr_lblTotalSoldQty = (TextView) convertView.findViewById(R.id.vT_asr_lblTotalSoldQty);
            vT_asr_lblTotalBalanceQty = (TextView) convertView.findViewById(R.id.vT_asr_lblTotalBalanceQty);
            new Runnable() {
                @Override
                public void run() {
                    vT_asr_lblCommodityName.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_asr_lblAllocatedQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_asr_lblRcvdQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_asr_lblTotalSoldQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_asr_lblTotalBalanceQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            }.run();
            RationCardMemberDetails memberDetails = getItem(position);
            vT_asr_lblCommodityName.setText(memberDetails.getmName());
            vT_asr_lblAllocatedQty.setText(memberDetails.getmGender());
            vT_asr_lblRcvdQty.setText(memberDetails.getmAge()+" Yrs");
            vT_asr_lblTotalSoldQty.setText(memberDetails.getmRelation());
            vT_asr_lblTotalBalanceQty.setText(memberDetails.getmUidaiNo());
            return convertView;
        }
    }

    @Override
    public void updateCaptions() {
        //Toast.makeText(getActivity(), "Language is Applying", Toast.LENGTH_SHORT).show();
        if(ReportFragmentActivity.languageCaptionsMap == null || ReportFragmentActivity.languageCaptionsMap.size() == 0){
            return;
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_SUBMIT)){
            vT_frr_btnSubmit.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_MEMBER_DETAILS)){
            vT_frr_lblMemberDetails.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_MEMBER_DETAILS));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_frr_lblName.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_GENDER)){
            vT_frr_lblGender.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_GENDER));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_AGE_YRS)){
            vT_frr_lblAge.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_AGE_YRS));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RELATION)){
            vT_frr_lblRelation.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RELATION));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_UID)){
            vT_frr_lblUid.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_UID));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_REPORTS)){
            vT_frr_lblRCDetails.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_REPORTS));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_frr_lblRHName.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_DATE)){
            vT_frr_lblRHDateofbirth.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_D_O_DATE));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_AGE)){
            vT_frr_lblRHAge.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_AGE));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_UID)){
            vT_frr_lblUID.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_UID));

        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RELATION)){
            vT_frr_lblRHRelation.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RELATION));
        }
        /*if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RELATION)){
            vT_frr_lblRCType.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RELATION));
        }*/
    }

}
