package com.mmadapps.fairpriceshop.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.SalesReport;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.ServiceInteractor;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Baskar on 12/11/2015.
 */
public class TransactionReportFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,ServiceInteractor,LanguageInteractor {

    private final String[] reportFormatArray = {"Daily","Weekly","Monthly"};

    //views
    //private Spinner vS_ftr_formatSpinner;
    private int selectedDate = 0;
    private TextView vT_ftr_fromDateViewer,vT_ftr_btnSubmit,vT_ftr_toDateViewer;
    private ListView vL_ftr_reportsList;
    private LinearLayout vL_ftr_contentsLayout;
    //languages views
    private TextView vT_ftr_lblFormat,vT_ftr_lblSelect,vT_ftr_lblReportFormat,vT_ftr_lblCommodityName,vT_ftr_lblRCNumber,vT_ftr_lblSoldQty,
            vT_ftr_lblPriceQty,vT_ftr_lblTotalAmount;


    //Variables
    private final String TAG = "TRANSACTION REPORT";
    private List<SalesReport> mSalesReportsList;
    private AgentDetails mAgentDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportFragmentActivity.languageReseter = this;
        View rootView = inflater.inflate(R.layout.fragment_transaction_report, container, false);
        initializeViews(rootView);
        setFonts(rootView);
        setActionEvents();
        return rootView;
    }

    private void setFonts(final View rootview) {
        new Runnable() {
            @Override
            public void run() {

                vT_ftr_lblFormat= (TextView) rootview.findViewById(R.id.vT_ftr_lblFormat);
                vT_ftr_lblSelect= (TextView) rootview.findViewById(R.id.vT_ftr_lblSelect);
                vT_ftr_lblReportFormat= (TextView) rootview.findViewById(R.id.vT_ftr_lblReportFormat);
                vT_ftr_lblCommodityName= (TextView) rootview.findViewById(R.id.vT_ftr_lblCommodityName);
                vT_ftr_lblRCNumber= (TextView) rootview.findViewById(R.id.vT_ftr_lblRCNumber);
                vT_ftr_lblSoldQty= (TextView) rootview.findViewById(R.id.vT_ftr_lblSoldQty);
                vT_ftr_lblPriceQty= (TextView) rootview.findViewById(R.id.vT_ftr_lblPriceQty);
                vT_ftr_lblTotalAmount= (TextView) rootview.findViewById(R.id.vT_ftr_lblTotalAmount);

                vT_ftr_lblFormat.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblSelect.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblReportFormat.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblCommodityName.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblRCNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblSoldQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblPriceQty.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_ftr_lblTotalAmount.setTypeface(FPSApplication.fMyriadPro_Semibold);

            }
        }.run();
    }

    private void initializeViews(View rootView) {
        vT_ftr_btnSubmit = (TextView) rootView.findViewById(R.id.vT_ftr_btnSubmit);
        vT_ftr_fromDateViewer = (TextView) rootView.findViewById(R.id.vT_ftr_fromDateViewer);
        vT_ftr_toDateViewer = (TextView) rootView.findViewById(R.id.vT_ftr_toDateViewer);
        vT_ftr_lblReportFormat = (TextView) rootView.findViewById(R.id.vT_ftr_lblReportFormat);
        vL_ftr_reportsList = (ListView) rootView.findViewById(R.id.vL_ftr_reportsList);
        vL_ftr_contentsLayout = (LinearLayout) rootView.findViewById(R.id.vL_ftr_contentsLayout);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCaptions();
        getAgentDetailsFromDB();
        setDefaultValues();
        //setFormatSpinner();
    }

    private void setDefaultValues() {
        String curDate = UtilsClass.reportDateFormat.format(new Date());
        vT_ftr_fromDateViewer.setText(curDate);
        vT_ftr_toDateViewer.setText(curDate);
    }

    /*    private void setFormatSpinner() {
        vS_ftr_formatSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, reportFormatArray));
    }
*/

    private void getAgentDetailsFromDB() {
        Helper helper = new Helper(getActivity());
        helper.openDataBase();
        mAgentDetails = helper.getAgentDetails();
        helper.close();
    }

    private void setActionEvents() {
        vT_ftr_btnSubmit.setOnClickListener(this);
        vT_ftr_fromDateViewer.setOnClickListener(this);
        vT_ftr_toDateViewer.setOnClickListener(this);
        /*vS_ftr_formatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        vT_ftr_lblReportFormat.setText(R.string.daily_sales_report);
                        break;
                    case 1:
                        vT_ftr_lblReportFormat.setText(R.string.weekly_sales_report);
                        break;
                    case 2:
                        vT_ftr_lblReportFormat.setText(R.string.monthly_sales_report);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void setValues() {
        vL_ftr_contentsLayout.setVisibility(View.VISIBLE);
        vL_ftr_reportsList.setAdapter(new TransactionReportAdapter());
        ReportFragmentActivity.setListViewHeightBasedOnChild.resetListSize(vL_ftr_reportsList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vT_ftr_btnSubmit:
                callServiceClass(UtilsClass.GET_DAILY_SALES_REPORT);
                break;
            case R.id.vT_ftr_fromDateViewer:
                selectedDate = 0;
                showDatePicker();
                break;
            case R.id.vT_ftr_toDateViewer:
                selectedDate = 1;
                showDatePicker();
                break;
        }
    }

    private void callServiceClass(String mService) {
        new AsyncServiceCall(getActivity(),this,mService);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, yy,mm,dd);
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        String date = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
        if(selectedDate == 0) {
            vT_ftr_fromDateViewer.setText(date);
        }else{
            vT_ftr_toDateViewer.setText(date);
        }
    }

    @Override
    public boolean callService(String mService) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(mService.equals(UtilsClass.GET_DAILY_SALES_REPORT)){
            String fromDate = vT_ftr_fromDateViewer.getText().toString();
            String toDate = vT_ftr_toDateViewer.getText().toString();
            inParam="stateId="+mAgentDetails.getmState()+"&fpsCode="+mAgentDetails.getmFpsCode()+"&fromDate="+fromDate+"&toDate="+toDate;
            result = webService.callService(WebService.ServiceAPI.GET_DAILY_SALES_REPORT,inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG,"Daily Sales null");
            }else{
                mSalesReportsList = jParser.parsingDailySalesReport(result);
                if(mSalesReportsList == null || mSalesReportsList.size() == 0){
                    Log.e(TAG,"Daily Sales null");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        if(isCompleted) {
            if(mService.equals(UtilsClass.GET_DAILY_SALES_REPORT)) {
                setValues();
            }
        }else{
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private class TransactionReportAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mSalesReportsList == null || mSalesReportsList.size() == 0) {
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return mSalesReportsList.size();
        }

        @Override
        public SalesReport getItem(int position) {
            return mSalesReportsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_transaction_report,parent, false);
            }
            final TextView vT_atr_lblCommodityName,vT_atr_lblRCNumber,vT_atr_lblSoldQty,vT_atr_lblPriceQty,vT_atr_lblTotalAmount;
            vT_atr_lblCommodityName = (TextView) convertView.findViewById(R.id.vT_atr_lblCommodityName);
            vT_atr_lblRCNumber = (TextView) convertView.findViewById(R.id.vT_atr_lblRCNumber);
            vT_atr_lblSoldQty = (TextView) convertView.findViewById(R.id.vT_atr_lblSoldQty);
            vT_atr_lblPriceQty = (TextView) convertView.findViewById(R.id.vT_atr_lblPriceQty);
            vT_atr_lblTotalAmount = (TextView) convertView.findViewById(R.id.vT_atr_lblTotalAmount);

            SalesReport report = getItem(position);
            vT_atr_lblCommodityName.setText(report.getmCommodityName());
            vT_atr_lblRCNumber.setText(report.getmRationCardNo());
            vT_atr_lblSoldQty.setText(report.getmSoldQty());
            vT_atr_lblPriceQty.setText(report.getmPricePerUnit());
            vT_atr_lblTotalAmount.setText(report.getmTotalAmount());

            new Runnable() {
                @Override
                public void run() {
                    vT_atr_lblCommodityName.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_atr_lblRCNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_atr_lblSoldQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_atr_lblPriceQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_atr_lblTotalAmount.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            };
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
            vT_ftr_btnSubmit.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_ftr_lblCommodityName.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_ftr_lblRCNumber.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL_SOLD_QTY)){
            vT_ftr_lblSoldQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_TOTAL_SOLD_QTY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_PRICE_QTY)){
            vT_ftr_lblPriceQty.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_PRICE_QTY));
        }
        if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL)){
            vT_ftr_lblTotalAmount.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_TOTAL));
        }
    }
}
