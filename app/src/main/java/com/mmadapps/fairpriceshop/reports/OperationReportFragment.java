package com.mmadapps.fairpriceshop.reports;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.HardwareComplaintReport;
import com.mmadapps.fairpriceshop.bean.NetworkDownTimeReport;
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
public class OperationReportFragment extends Fragment implements View.OnClickListener,ServiceInteractor,LanguageInteractor {

    private final String[] operationsReportTypesArray= {"Online Synchronized Report","Network Downtime Report","Hardware/Network Complaint"};

    //views
    private LinearLayout vL_for_onlineReport,vL_for_complaintNetwork,vL_for_networkDowntimeReport,vL_for_contentsLayout;
    private Spinner vS_for_reportTypeSpinner;
    private TextView vT_for_btnSubmit,vE_for_FPSCodeValue;
    private ListView vL_for_reportsList;
    //languages views
    private  TextView vT_for_lblRType,vT_for_FPSCode,vT_for_lblReportType,vT_for_lblSyncId,vT_for_lblimei,vT_for_lblSyncActivated,
            vT_for_lblIpAddress,vT_for_lblDate,vT_for_lblSyncStart,vT_for_lblSyncEnd,vT_for_lblStatus,vT_for_lblModeofsync,
            vT_for_lblIPAddress,vT_for_lblComplaintNumber,vT_for_lblComplaintBy,vT_for_lblComplaint,vT_for_lblRaisedDate,
            vT_for_lblstatus,vT_for_lblIPAdress,vT_for_lblcomplaintNumber,vT_for_lblDowntimeType,vT_for_lblNetworkProvider,
            vT_for_lblDowntimeStart,vT_for_lblDowntimeEnd;

    //variables
    private final String TAG = "OperationReportFragment";
    private List<NetworkDownTimeReport> mNWdownTimeReports;
    private List<HardwareComplaintReport> mHardwareComplaintReports;
    private AgentDetails mAgentDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ReportFragmentActivity.languageReseter = this;
        View rootView = inflater.inflate(R.layout.fragment_operation_report,container,false);
        initializeViews(rootView);
        setFont(rootView);
        setActionEvents();
        return rootView;
    }

    private void setFont(final View rootView) {
        new Runnable(){
            @Override
            public void run() {
                try {


                            vT_for_lblRType= (TextView)rootView.findViewById(R.id.vT_for_lblRType);
                            vT_for_FPSCode=(TextView) rootView.findViewById(R.id.vT_for_FPSCode);
                            vT_for_lblReportType=(TextView) rootView.findViewById(R.id.vT_for_lblReportType);
                            vT_for_lblSyncId=(TextView) rootView.findViewById(R.id.vT_for_lblSyncId);
                            vT_for_lblimei=(TextView) rootView.findViewById(R.id.vT_for_lblimei);
                            vT_for_lblSyncActivated=(TextView) rootView.findViewById(R.id.vT_for_lblSyncActivated);
                            vT_for_lblIpAddress=(TextView) rootView.findViewById(R.id.vT_for_lblIpAddress);
                            vT_for_lblDate=(TextView) rootView.findViewById(R.id.vT_for_lblDate);
                            vT_for_lblSyncStart=(TextView) rootView.findViewById(R.id.vT_for_lblSyncStart);
                            vT_for_lblSyncEnd=(TextView) rootView.findViewById(R.id.vT_for_lblSyncEnd);
                            vT_for_lblStatus=(TextView) rootView.findViewById(R.id.vT_for_lblStatus);
                            vT_for_lblModeofsync=(TextView) rootView.findViewById(R.id.vT_for_lblModeofsync);
                            vT_for_lblIPAddress=(TextView) rootView.findViewById(R.id.vT_for_lblIPAddress);
                            vT_for_lblComplaintNumber=(TextView) rootView.findViewById(R.id.vT_for_lblComplaintNumber);
                            vT_for_lblComplaintBy=(TextView) rootView.findViewById(R.id.vT_for_lblComplaintBy);
                            vT_for_lblComplaint=(TextView) rootView.findViewById(R.id.vT_for_lblComplaint);
                            vT_for_lblRaisedDate=(TextView) rootView.findViewById(R.id.vT_for_lblRaisedDate);
                            vT_for_lblstatus=(TextView) rootView.findViewById(R.id.vT_for_lblstatus);
                            vT_for_lblIPAdress=(TextView) rootView.findViewById(R.id.vT_for_lblIPAdress);
                            vT_for_lblcomplaintNumber=(TextView) rootView.findViewById(R.id.vT_for_lblcomplaintNumber);
                            vT_for_lblDowntimeType=(TextView) rootView.findViewById(R.id.vT_for_lblDowntimeType);
                            vT_for_lblNetworkProvider=(TextView) rootView.findViewById(R.id.vT_for_lblNetworkProvider);
                            vT_for_lblDowntimeStart=(TextView) rootView.findViewById(R.id.vT_for_lblDowntimeStart);
                            vT_for_lblDowntimeEnd=(TextView) rootView.findViewById(R.id.vT_for_lblDowntimeEnd);

                            vT_for_lblRType.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_FPSCode.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblReportType.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblSyncId.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblimei.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblSyncActivated.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblIpAddress.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblDate.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblSyncStart.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblSyncEnd.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblStatus.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblModeofsync.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblIPAddress.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblComplaintNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblComplaintBy.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblComplaint.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblRaisedDate.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblstatus.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblIPAdress.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblcomplaintNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblDowntimeType.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblNetworkProvider.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblDowntimeStart.setTypeface(FPSApplication.fMyriadPro_Semibold);
                            vT_for_lblDowntimeEnd.setTypeface(FPSApplication.fMyriadPro_Semibold);
                    vE_for_FPSCodeValue.setTypeface(FPSApplication.fMyriadPro_Regular);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }

    private void initializeViews(View root) {
        vL_for_onlineReport = (LinearLayout) root.findViewById(R.id.vL_for_onlineReport);
        vL_for_complaintNetwork = (LinearLayout) root.findViewById(R.id.vL_for_complaintNetwork);
        vL_for_networkDowntimeReport = (LinearLayout) root.findViewById(R.id.vL_for_networkDowntimeReport);
        vT_for_btnSubmit = (TextView) root.findViewById(R.id.vT_for_btnSubmit);
        vS_for_reportTypeSpinner = (Spinner) root.findViewById(R.id.vS_for_reportTypeSpinner);
        vL_for_contentsLayout = (LinearLayout) root.findViewById(R.id.vL_for_contentsLayout);
        vT_for_lblReportType = (TextView) root.findViewById(R.id.vT_for_lblReportType);
        vL_for_reportsList = (ListView) root.findViewById(R.id.vL_for_reportsList);
        vE_for_FPSCodeValue = (TextView) root.findViewById(R.id.vE_for_FPSCodeValue);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCaptions();
        getAgentDetailsFromDB();
        setValues();
    }

    private void getAgentDetailsFromDB() {
        Helper helper = new Helper(getActivity());
        helper.openDataBase();
        mAgentDetails = helper.getAgentDetails();
        helper.close();
    }

    private void setValues() {
        vE_for_FPSCodeValue.setText(mAgentDetails.getmFpsCode());
        vS_for_reportTypeSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, operationsReportTypesArray));
    }

    private void setActionEvents() {
        vT_for_btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vT_for_btnSubmit:
                String fpsCode = ""+vE_for_FPSCodeValue.getText().toString().trim();
                if(fpsCode.length() == 0){
                    Toast.makeText(getActivity(), R.string.empty_fpscode, Toast.LENGTH_SHORT).show();
                }else {
                    vL_for_onlineReport.setVisibility(View.GONE);
                    vL_for_complaintNetwork.setVisibility(View.GONE);
                    vL_for_networkDowntimeReport.setVisibility(View.GONE);
                    vL_for_contentsLayout.setVisibility(View.GONE);
                    int selectedSpinner = vS_for_reportTypeSpinner.getSelectedItemPosition();
                    callServiceClass(selectedSpinner);
                }
                break;
        }
    }

    private void callServiceClass(int spinnerSelection){
        switch (spinnerSelection){
            case 0:
                new AsyncServiceCall(getActivity(),this, UtilsClass.GET_ONLINE_SYNC_REPORT);
                break;
            case 1:
                new AsyncServiceCall(getActivity(),this,UtilsClass.GET_NW_DOWNTIME_REPORT);
                break;
            case 2:
                new AsyncServiceCall(getActivity(),this,UtilsClass.GET_HARDWARE_COMPLAINT);
                break;
        }
    }

    @Override
    public boolean callService(String service) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(service.equals(UtilsClass.GET_ONLINE_SYNC_REPORT)){
            inParam = "fpsCode="+mAgentDetails.getmFpsCode();
            result = webService.callService(WebService.ServiceAPI.GET_ONLINE_SYNC_REPORT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"GetOnlineSyncReport service is null");
            } else {
                Log.e("GetOnlineSyncReport", result);
                mNWdownTimeReports = jParser.parsingNDTResult(result);
                if(mNWdownTimeReports == null || mNWdownTimeReports.size() == 0){
                    Log.e("GetRLMbrDtlList", "RLMbrDtlList is null");
                }else {
                    // TODO
                }
                return true;
            }
        }else if(service.equals(UtilsClass.GET_NW_DOWNTIME_REPORT)){
            inParam = "fpsCode="+mAgentDetails.getmFpsCode();
            result = webService.callService(WebService.ServiceAPI.GET_NW_DOWNTIME_REPORT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"GetNetworkDowntimeReport service is null");
            } else {
                Log.e("GetNwDowntimeReport", result);
                mNWdownTimeReports = jParser.parsingNDTResult(result);
                if(mNWdownTimeReports == null || mNWdownTimeReports.size() == 0){
                    Log.e("GetRLMbrDtlList", "RLMbrDtlList is null");
                }else {
                    // TODO
                }
                return true;
            }
        }else{
            inParam = "userId="+mAgentDetails.getmUserId();
            result = webService.callService(WebService.ServiceAPI.GET_HARDWARE_COMPLAINT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"GetHardwareComplaint service is null");
            } else {
                Log.e("GetHardwareComplaint", result);
                mHardwareComplaintReports = jParser.parsingHardwareComplaintsReport(result);
                if(mHardwareComplaintReports == null || mHardwareComplaintReports.size() == 0){
                    Log.e("GetRLMbrDtlList", "RLMbrDtlList is null");
                }else {
                    // TODO
                }
                return true;
            }
        }
        return false;
    }

    private void setListAdapter(int mReportType){
       switch (mReportType){
           case 0:
               vT_for_lblReportType.setText(R.string.online_synchronized_report);
               vL_for_onlineReport.setVisibility(View.VISIBLE);
               vL_for_reportsList.setAdapter(new OnlineSyncReportListAdapter());
               break;
           case 1:
               vT_for_lblReportType.setText(R.string.network_downtime_report);
               vL_for_networkDowntimeReport.setVisibility(View.VISIBLE);
               vL_for_reportsList.setAdapter(new NDTReportListAdapter());
               break;
           case 2:
               vT_for_lblReportType.setText(R.string.hardware_network_complaint);
               vL_for_complaintNetwork.setVisibility(View.VISIBLE);
               vL_for_reportsList.setAdapter(new HardwareReportListAdapter());
               break;
       }
        vL_for_contentsLayout.setVisibility(View.VISIBLE);
       ReportFragmentActivity.setListViewHeightBasedOnChild.resetListSize(vL_for_reportsList);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        if(isCompleted){
            if(mService.equals(UtilsClass.GET_ONLINE_SYNC_REPORT)){
                setListAdapter(0);
            }else if(mService.equals(UtilsClass.GET_NW_DOWNTIME_REPORT)){
                setListAdapter(1);
            }else{
                setListAdapter(2);
            }
        }else{
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private class NDTReportListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mNWdownTimeReports == null || mNWdownTimeReports.size() == 0){
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return mNWdownTimeReports.size();
        }

        @Override
        public NetworkDownTimeReport getItem(int position) {
            return mNWdownTimeReports.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
               convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_operation_online_report, parent, false);
            }
            final TextView vT_aoor_lblSyncId,vT_aoor_lblIMEI,vT_aoor_lblSyncActivated,vT_aoor_lblIPAdress,vT_aoor_lblDate,vT_aoor_lblSyncStart,
                    vT_aoor_lblSyncEnd,vT_aoor_lblStatus,vT_aoor_lblModeofSync;
            vT_aoor_lblSyncId = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncId);
            vT_aoor_lblIMEI = (TextView) convertView.findViewById(R.id.vT_aoor_lblIMEI);
            vT_aoor_lblSyncActivated = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncActivated);
            vT_aoor_lblIPAdress = (TextView) convertView.findViewById(R.id.vT_aoor_lblIPAdress);
            vT_aoor_lblDate = (TextView) convertView.findViewById(R.id.vT_aoor_lblDate);
            vT_aoor_lblSyncStart = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncStart);
            vT_aoor_lblSyncEnd = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncEnd);
            vT_aoor_lblStatus = (TextView) convertView.findViewById(R.id.vT_aoor_lblStatus);
            vT_aoor_lblModeofSync = (TextView) convertView.findViewById(R.id.vT_aoor_lblModeofSync);
            new Runnable() {
                @Override
                public void run() {
                    vT_aoor_lblSyncId.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblIMEI.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncActivated.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblIPAdress.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblDate.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncStart.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncEnd.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblStatus.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblModeofSync.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            }.run();
            NetworkDownTimeReport downTimeReport = getItem(position);
            vT_aoor_lblSyncId.setText(downTimeReport.getmSyncId());
            vT_aoor_lblIMEI.setText(downTimeReport.getmImeiNumber());
            vT_aoor_lblSyncActivated.setText(downTimeReport.getmStatus());
            vT_aoor_lblIPAdress.setText(downTimeReport.getmIpAddress());
            vT_aoor_lblDate.setText(downTimeReport.getmCreatedDate());
            vT_aoor_lblSyncStart.setText(downTimeReport.getmStartTime());
            vT_aoor_lblSyncEnd.setText(downTimeReport.getmEndTime());
            vT_aoor_lblStatus.setText(downTimeReport.getmStatus());
            vT_aoor_lblModeofSync.setText(downTimeReport.getmModeOfSync());

            return convertView;
        }
    }


    private class HardwareReportListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mHardwareComplaintReports == null || mHardwareComplaintReports.size() == 0){
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return mHardwareComplaintReports.size();
        }

        @Override
        public HardwareComplaintReport getItem(int position) {
            return mHardwareComplaintReports.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_operation_complaint_nw_report, parent, false);
            }
            final TextView vT_aocr_lblIPAddress,vT_aocr_lblComplaintNumber,vT_aocr_lblDowntimeType,vT_aocr_lblNetworkProvider,vT_aocr_lblDowntimeStart,
                    vT_aocr_lblDowntimeEnd;
            vT_aocr_lblIPAddress = (TextView) convertView.findViewById(R.id.vT_aocr_lblIPAddress);
            vT_aocr_lblComplaintNumber = (TextView) convertView.findViewById(R.id.vT_aocr_lblComplaintNumber);
            vT_aocr_lblDowntimeType = (TextView) convertView.findViewById(R.id.vT_aocr_lblDowntimeType);
            vT_aocr_lblNetworkProvider = (TextView) convertView.findViewById(R.id.vT_aocr_lblNetworkProvider);
            vT_aocr_lblDowntimeStart = (TextView) convertView.findViewById(R.id.vT_aocr_lblDowntimeStart);
            vT_aocr_lblDowntimeEnd = (TextView) convertView.findViewById(R.id.vT_aocr_lblDowntimeEnd);

            new Runnable() {
                @Override
                public void run() {
                    vT_aocr_lblIPAddress.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aocr_lblComplaintNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aocr_lblDowntimeType.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aocr_lblNetworkProvider.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aocr_lblDowntimeStart.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aocr_lblDowntimeEnd.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            };

            HardwareComplaintReport hardware = getItem(position);

            vT_aocr_lblIPAddress.setText(hardware.getmIpAddress());
            vT_aocr_lblComplaintNumber.setText(hardware.getmId());
            vT_aocr_lblDowntimeType.setText(hardware.getmCreatedByName());
            vT_aocr_lblNetworkProvider.setText(hardware.getmSubject());
            vT_aocr_lblDowntimeStart.setText(hardware.getmCreatedDate());
            vT_aocr_lblDowntimeEnd.setText(hardware.getmStatus());

            return convertView;
        }
    }


    private class OnlineSyncReportListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mNWdownTimeReports == null || mNWdownTimeReports.size() == 0){
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return mNWdownTimeReports.size();
        }

        @Override
        public NetworkDownTimeReport getItem(int position) {
            return mNWdownTimeReports.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_operation_online_report, parent, false);
            }
            NetworkDownTimeReport onlineSyncReport = getItem(position);

            final TextView vT_aoor_lblSyncId,vT_aoor_lblIMEI,vT_aoor_lblSyncActivated,vT_aoor_lblIPAdress,vT_aoor_lblDate,vT_aoor_lblSyncStart,
                    vT_aoor_lblSyncEnd,vT_aoor_lblStatus,vT_aoor_lblModeofSync;

            vT_aoor_lblSyncId = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncId) ;
            vT_aoor_lblIMEI = (TextView) convertView.findViewById(R.id.vT_aoor_lblIMEI);
            vT_aoor_lblSyncActivated = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncActivated);
            vT_aoor_lblIPAdress = (TextView) convertView.findViewById(R.id.vT_aoor_lblIPAdress);
            vT_aoor_lblDate = (TextView) convertView.findViewById(R.id.vT_aoor_lblDate);
            vT_aoor_lblSyncStart = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncStart);
            vT_aoor_lblSyncEnd = (TextView) convertView.findViewById(R.id.vT_aoor_lblSyncEnd);
            vT_aoor_lblStatus = (TextView) convertView.findViewById(R.id.vT_aoor_lblStatus);
            vT_aoor_lblModeofSync = (TextView) convertView.findViewById(R.id.vT_aoor_lblModeofSync);

            new Runnable() {
                @Override
                public void run() {
                    vT_aoor_lblSyncId.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblIMEI.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncActivated.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblIPAdress.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblDate.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncStart.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblSyncEnd.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblStatus.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_aoor_lblModeofSync.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            };

            vT_aoor_lblSyncId.setText(onlineSyncReport.getmSyncId());
            vT_aoor_lblIMEI.setText(onlineSyncReport.getmImeiNumber());
            vT_aoor_lblSyncActivated.setText(onlineSyncReport.getmStatus());
            vT_aoor_lblIPAdress.setText(onlineSyncReport.getmIpAddress());
            vT_aoor_lblDate.setText(onlineSyncReport.getmCreatedDate());
            vT_aoor_lblSyncStart.setText(onlineSyncReport.getmStartTime());
            vT_aoor_lblSyncEnd.setText(onlineSyncReport.getmEndTime());
            vT_aoor_lblStatus.setText(onlineSyncReport.getmStatus());
            vT_aoor_lblModeofSync.setText(onlineSyncReport.getmModeOfSync());

            return convertView;
        }
    }

    @Override
    public void updateCaptions() {
        int selectedSpinner = vS_for_reportTypeSpinner.getSelectedItemPosition();
        //Toast.makeText(getActivity(), "Language is Applying", Toast.LENGTH_SHORT).show();
        if(ReportFragmentActivity.languageCaptionsMap == null || ReportFragmentActivity.languageCaptionsMap.size() == 0){
            return;
        }
        if(selectedSpinner == 0){

            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_HARDWARE_NETWORK_COMPLAINT_NETWORK)){
                vT_for_lblReportType.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_HARDWARE_NETWORK_COMPLAINT_NETWORK));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_SYNC_ID)){
                vT_for_lblSyncId.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SYNC_ID));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_IMEI)){
                vT_for_lblimei.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_IMEI));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_SYNC_ACTIVATED)){
                vT_for_lblSyncActivated.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SYNC_ACTIVATED));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_IP_ADDRESS)){
                vT_for_lblIpAddress.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_IP_ADDRESS));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_DATE)){
                vT_for_lblDate.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_DATE));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_SYNC_START)){
                vT_for_lblSyncStart.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SYNC_START));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_SYNC_END)){
                vT_for_lblSyncEnd.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_SYNC_END));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_STATUS)){
                vT_for_lblStatus.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_STATUS));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_MODE_OF_SYNC)){
                vT_for_lblModeofsync.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_MODE_OF_SYNC));
            }

        }else if(selectedSpinner == 1){

            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_IP_ADDRESS)){
                vT_for_lblIPAdress.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_IP_ADDRESS));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT_NO)){
                vT_for_lblComplaintNumber.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT_NO));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT_BY)){
                vT_for_lblComplaintBy.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT_BY));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT)){
                vT_for_lblComplaint.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_RAISED_DATE)){
                vT_for_lblRaisedDate.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_RAISED_DATE));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_STATUS)){
                vT_for_lblstatus.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_STATUS));
            }

        }else if(selectedSpinner==2){
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_IP_ADDRESS)){
                vT_for_lblIPAdress.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_IP_ADDRESS));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT_NO)){
                vT_for_lblcomplaintNumber.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT_NO));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_DOWNTIME_TYPE)){
                vT_for_lblDowntimeType.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_DOWNTIME_TYPE));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_NETWORK_PROVIDER)){
                vT_for_lblNetworkProvider.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_NETWORK_PROVIDER));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_DOWNTIME_START)){
                vT_for_lblDowntimeStart.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_DOWNTIME_START));
            }
            if(ReportFragmentActivity.languageCaptionsMap.containsKey(CaptionsKey.KEY_DOWNTIME_END)){
                vT_for_lblDowntimeEnd.setText(ReportFragmentActivity.languageCaptionsMap.get(CaptionsKey.KEY_DOWNTIME_END));
            }

        }


    }

}
