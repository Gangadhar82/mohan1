package com.mmadapps.fairpriceshop.reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.StockReport;
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
public class StockReportFragment extends Fragment implements View.OnClickListener,ServiceInteractor,LanguageInteractor {

    private ListView vL_fsr_reportsList;
    private TextView vT_fsr_btnSubmit;
    private LinearLayout vL_fsr_contentsLayout;
    private EditText vT_fsr_FPSCodeValue;

    //variables
    private final String TAG = "StockReportFragment";
    private List<StockReport> mStockReportsList;
    //Languages views
    private  TextView vT_fsr_lblFPSCode,vT_fsr_lblSBReport,vT_fsr_lblCommodityName,vT_fsr_lblAllocatedQty,vT_fsr_lblReceivedQty,
            vT_fsr_lblTotalSoldQty,vT_fsr_lblTBQty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportFragmentActivity.languageReseter = this;
        View rootView = inflater.inflate(R.layout.fragment_stock_report,container, false);
        initializeViews(rootView);
        setFont(rootView);
        setActionEvents();
        setDefaultVaues();
        return rootView;
    }

    private void setDefaultVaues() {
        Helper helper = new Helper(getActivity());
        helper.openDataBase();
        AgentDetails agentDetails = helper.getAgentDetails();
        helper.close();
        vT_fsr_FPSCodeValue.setText(agentDetails.getmFpsCode());
    }

    private void setFont(final View rootView) {
        new Runnable() {
            @Override
            public void run() {

                vT_fsr_lblFPSCode= (TextView) rootView.findViewById(R.id.vT_fsr_lblFPSCode);
                vT_fsr_lblSBReport=(TextView) rootView.findViewById(R.id.vT_fsr_lblSBReport);
                vT_fsr_lblCommodityName =(TextView) rootView.findViewById(R.id.vT_fsr_lblCommodityName);
                vT_fsr_lblAllocatedQty =(TextView) rootView.findViewById(R.id.vT_fsr_lblAllocatedQty);
                vT_fsr_lblReceivedQty=(TextView) rootView.findViewById(R.id.vT_fsr_lblReceivedQty);
                vT_fsr_lblTotalSoldQty =(TextView) rootView.findViewById(R.id.vT_fsr_lblTotalSoldQty);
                vT_fsr_lblTBQty = (TextView) rootView.findViewById(R.id.vT_fsr_lblTBQty);

                vT_fsr_lblFPSCode.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblSBReport.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblCommodityName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblAllocatedQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblReceivedQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblTotalSoldQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_fsr_lblTBQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();

    }

    private void setActionEvents() {
        vT_fsr_btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCaptions();
    }

    private void setValues() {
        vL_fsr_contentsLayout.setVisibility(View.VISIBLE);
        vL_fsr_reportsList.setAdapter(new StockReportAdapter());
        ReportFragmentActivity.setListViewHeightBasedOnChild.resetListSize(vL_fsr_reportsList);
    }

    private void initializeViews(View rootView) {
        vT_fsr_FPSCodeValue = (EditText) rootView.findViewById(R.id.vT_fsr_FPSCodeValue);
        vL_fsr_contentsLayout = (LinearLayout) rootView.findViewById(R.id.vL_fsr_contentsLayout);
        vL_fsr_reportsList = (ListView)rootView.findViewById(R.id.vL_fsr_reportsList);
        vT_fsr_btnSubmit = (TextView) rootView.findViewById(R.id.vT_fsr_btnSubmit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vT_fsr_btnSubmit:
                callServiceClass(UtilsClass.GET_STOCK_BALANCE_REPORT);
                break;
        }
    }

    private void callServiceClass(String serviceMethodName) {
        new AsyncServiceCall(getActivity(),this,serviceMethodName);
    }

    @Override
    public boolean callService(String mService) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(mService.equals(UtilsClass.GET_STOCK_BALANCE_REPORT)) {
            Helper helper = new Helper(getActivity());
            helper.openDataBase();
            AgentDetails mAgentDetails = helper.getAgentDetails();
            helper.close();
            inParam = mAgentDetails.getmFpsCode();
            result = webService.callService(WebService.ServiceAPI.GET_STOCK_BALANCE_REPORT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG, "Card Holder is null");
            } else {
                Log.e(TAG, result);
                mStockReportsList = jParser.parsingStockReport(result);
                if (mStockReportsList == null) {
                    Log.e(TAG, "Card Holder object null");
                }else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        if(isCompleted){
            if(mService.equals(UtilsClass.GET_STOCK_BALANCE_REPORT)){
                setValues();
            }
        }else{
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }


    private class StockReportAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if (mStockReportsList == null || mStockReportsList.size() == 0) {
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return mStockReportsList.size();
        }

        @Override
        public StockReport getItem(int position) {
            return mStockReportsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_stock_report,parent,false);
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
            };
            StockReport stockReport = getItem(position);
            vT_asr_lblCommodityName.setText(stockReport.getmCommodityName());
            vT_asr_lblAllocatedQty.setText(stockReport.getmAllocQty());
            vT_asr_lblRcvdQty.setText(stockReport.getmRcCount());
            vT_asr_lblTotalSoldQty.setText(stockReport.getmSalesQty());
            vT_asr_lblTotalBalanceQty.setText(stockReport.getmClosingBalance());

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
            vT_fsr_btnSubmit.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_fsr_lblCommodityName.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATED_QUANTITY)){
            vT_fsr_lblAllocatedQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATED_QUANTITY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RECEIVED_QTY)){
            vT_fsr_lblReceivedQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RECEIVED_QTY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL_SOLD_QTY)){
            vT_fsr_lblTotalSoldQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_TOTAL_SOLD_QTY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL_BALANCE_QTY)){
            vT_fsr_lblTBQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_TOTAL_BALANCE_QTY));
        }
    }
}
