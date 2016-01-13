package com.mmadapps.fairpriceshop.stockbalancereport;

import android.os.Bundle;
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
 * Created by Baskar on 1/9/2016.
 */
public class StockBalanceActivity extends ActionbarActivity implements ServiceInteractor {

    //Languages view
    private TextView vT_as_lblFPSCode,vT_as_lblSBReport,vT_as_lblCommodityName,vT_as_lblAllocatedQty,vT_as_lblReceivedQty,
            vT_as_lblTotalSoldQty,vT_as_lblTBQty;

    private ListView vL_as_reportsList;
    private TextView vT_as_btnSubmit;
    private LinearLayout vL_as_contentsLayout;
    private EditText vT_as_FPSCodeValue;

    //variables
    private final String TAG = "StockBalanceActivity";
    private List<StockReport> mStockReportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_stockbalance);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_closing_balance,true);
        initializeViews();
        updateCaptions();
        setFont();
        setActionEvents();
        setDefaultVaues();
    }

    private void setDefaultVaues() {
        Helper helper = new Helper(this);
        helper.openDataBase();
        AgentDetails agentDetails = helper.getAgentDetails();
        helper.close();
        vT_as_FPSCodeValue.setText(agentDetails.getmFpsCode());
    }

    private void setFont() {
        new Runnable() {
            @Override
            public void run() {
                vT_as_lblFPSCode= (TextView) findViewById(R.id.vT_as_lblFPSCode);
                vT_as_lblSBReport=(TextView) findViewById(R.id.vT_as_lblSBReport);
                vT_as_lblCommodityName =(TextView) findViewById(R.id.vT_as_lblCommodityName);
                vT_as_lblAllocatedQty =(TextView) findViewById(R.id.vT_as_lblAllocatedQty);
                vT_as_lblReceivedQty=(TextView) findViewById(R.id.vT_as_lblReceivedQty);
                vT_as_lblTotalSoldQty =(TextView) findViewById(R.id.vT_as_lblTotalSoldQty);
                vT_as_lblTBQty = (TextView) findViewById(R.id.vT_as_lblTBQty);

                vT_as_lblFPSCode.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblSBReport.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblCommodityName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblAllocatedQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblReceivedQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblTotalSoldQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_as_lblTBQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();

    }

    private void setActionEvents() {
        vT_as_btnSubmit.setOnClickListener(this);
    }

    private void setValues() {
        vL_as_contentsLayout.setVisibility(View.VISIBLE);
        vL_as_reportsList.setAdapter(new StockReportAdapter());
        setListViewHeightBasedOnChildren(vL_as_reportsList);
    }

    private void initializeViews() {
        vT_as_FPSCodeValue = (EditText) findViewById(R.id.vT_as_FPSCodeValue);
        vL_as_contentsLayout = (LinearLayout) findViewById(R.id.vL_as_contentsLayout);
        vL_as_reportsList = (ListView)findViewById(R.id.vL_as_reportsList);
        vT_as_btnSubmit = (TextView) findViewById(R.id.vT_as_btnSubmit);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_as_btnSubmit:
                callServiceClass(UtilsClass.GET_STOCK_BALANCE_REPORT);
                break;
        }
    }

    private void callServiceClass(String serviceMethodName) {
        new AsyncServiceCall(StockBalanceActivity.this,this,serviceMethodName);
    }

    @Override
    public boolean callService(String mService) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(mService.equals(UtilsClass.GET_STOCK_BALANCE_REPORT)) {
            Helper helper = new Helper(StockBalanceActivity.this);
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
                }
                return true;
            }
        }
        return super.callService(mService);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted){
            if(mService.equals(UtilsClass.GET_STOCK_BALANCE_REPORT)){
                setValues();
            }
        }else{
            Toast.makeText(StockBalanceActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


    private class StockReportAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mStockReportsList == null || mStockReportsList.size() == 0) {
                Toast.makeText(StockBalanceActivity.this, "No Data", Toast.LENGTH_SHORT).show();
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
                convertView = getLayoutInflater().inflate(R.layout.adapter_stock_report,parent,false);
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
    protected void updateCaptions() {
        //Toast.makeText(StockBalanceActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_as_lblFPSCode.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_REPORTS)){
            vT_as_lblSBReport.setText(languageCaptionsMap.get(CaptionsKey.KEY_REPORTS));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_as_lblCommodityName.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATED_QUANTITY )){
            vT_as_lblAllocatedQty.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATED_QUANTITY ));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RECEIVED_QTY )){
            vT_as_lblReceivedQty.setText(languageCaptionsMap.get(CaptionsKey.KEY_RECEIVED_QTY ));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL_SOLD_QTY )){
            vT_as_lblTotalSoldQty.setText(languageCaptionsMap.get(CaptionsKey.KEY_TOTAL_SOLD_QTY ));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL_BALANCE_QTY )){
            vT_as_lblTBQty.setText(languageCaptionsMap.get(CaptionsKey.KEY_TOTAL_BALANCE_QTY ));
    }

}}
