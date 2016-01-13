package com.mmadapps.fairpriceshop.complaintbooking;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.MasterComplaints;
import com.mmadapps.fairpriceshop.main.FPSApplication;
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
public class ComplaintBookingActivity extends ActionbarActivity {

    //views
    private Spinner vS_acb_complaintSpinner;
    private EditText vE_acb_addcomplaintdetails;
    private TextView vT_acb_btnSubmit;
    private  TextView vT_acb_lblComplaintList;

    //variable
    private static final String TAG = "ComplaintBookingAct";
    private List<MasterComplaints> masterComplaints;
    private MasterComplaintSpinner complaintSpinner;
    private String mComplaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complaint_booking);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_complaint_booking,true);
        initializeViews();
        updateCaptions();
        setActionEvents();
        setFonts();
        callServiceClass(UtilsClass.GET_MASTER_COMPLAINTS);
    }

    private void setActionEvents() {
        vT_acb_btnSubmit.setOnClickListener(this);

        vE_acb_addcomplaintdetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mComplaints = ""+s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vT_acb_btnSubmit.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vE_acb_addcomplaintdetails.setTypeface(FPSApplication.fMyriadPro_Regular);

                vT_acb_lblComplaintList= (TextView) findViewById(R.id.vT_acb_lblComplaintList);
                vT_acb_lblComplaintList.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void setComplaintSpinner() {
        complaintSpinner = new MasterComplaintSpinner(ComplaintBookingActivity.this, android.R.layout.simple_spinner_dropdown_item, masterComplaints);
        vS_acb_complaintSpinner.setAdapter(complaintSpinner);
    }

    private void initializeViews() {
        vE_acb_addcomplaintdetails = (EditText) findViewById(R.id.vE_acb_addcomplaintdetails);
        vS_acb_complaintSpinner = (Spinner) findViewById(R.id.vS_acb_complaintSpinner);
        vT_acb_btnSubmit = (TextView) findViewById(R.id.vT_acb_btnSubmit);
    }

    private void callServiceClass(String serviceMethodName){
        new AsyncServiceCall(this,this,serviceMethodName);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_acb_btnSubmit:
                if(mComplaints == null || mComplaints.length() == 0){
                    Toast.makeText(ComplaintBookingActivity.this, "Please enter comments", Toast.LENGTH_SHORT).show();
                }else {
                    vT_acb_btnSubmit.setEnabled(false);
                    callServiceClass(UtilsClass.POST_SAVE_COMPLAINT);
                    vT_acb_btnSubmit.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public boolean callService(String service) {
        WebService webService = new WebService();
        JParser parser = new JParser();
        if(service.equals(UtilsClass.POST_SAVE_COMPLAINT)){
            Helper mHelper = new Helper(this);
            mHelper.openDataBase();
            AgentDetails mAgentDetails = mHelper.getAgentDetails();
            mHelper.close();
            MasterComplaints mMasterComplaints = getSelectedComplaints();
            mMasterComplaints.setmDescription(mComplaints);
            String inParam = webService.CreateJsonForSaveComplaint(mAgentDetails.getmUserId(), mMasterComplaints);
            String result = webService.callService(WebService.ServiceAPI.POST_SAVE_COMPLAINT, inParam);
            if (result == null || result.length() == 0) {
                Log.e(TAG,"SaveComplaint service null");
            } else {
                String response = parser.parsingCreateComplaint(result);
                if(response == null || response.length() == 0 || response.equalsIgnoreCase("true")){
                    Log.e(TAG,"Save Compliant response is null");
                }else {
                    Log.e("PostSaveComplaint", response);
                    return true;
                }
            }
        }else if(service.equals(UtilsClass.GET_MASTER_COMPLAINTS)){
            String result = webService.callService(WebService.ServiceAPI.GET_MASTER_COMPLAINTS,"");
            Log.e("GetMasterComplaints",""+result);
            if(result == null || result.length() == 0){
                Log.e(TAG,"Master complaints service is null");
            }else{
                masterComplaints = parser.parsingMasterComplaints(result);
                if(masterComplaints == null || masterComplaints.size() == 0){
                    Log.e(TAG,"master complaints parse is null");
                }else{
                   return true;
                }
            }
        }
        return super.callService(service);
    }

    private MasterComplaints getSelectedComplaints() {
        int position = vS_acb_complaintSpinner.getSelectedItemPosition();
        return masterComplaints.get(position);
    }

    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted){
            if(mService.equals(UtilsClass.POST_SAVE_COMPLAINT)){
                Toast.makeText(ComplaintBookingActivity.this, "Complaint Saved", Toast.LENGTH_SHORT).show();
            }else if(mService.equals(UtilsClass.GET_MASTER_COMPLAINTS)){
                setComplaintSpinner();
            }
        }else if(mService.equals(UtilsClass.GET_MASTER_COMPLAINTS)){
            Toast.makeText(ComplaintBookingActivity.this, "No master complaints list", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ComplaintBookingActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }

    public class MasterComplaintSpinner extends ArrayAdapter<MasterComplaints> {
        private List<MasterComplaints> mAllocationDetailsList = null;
        private Context mContext;
        private LayoutInflater inflater;

        public MasterComplaintSpinner(Context context, int resource, List<MasterComplaints> objects) {
            super(context, resource, objects);
            mAllocationDetailsList = objects;
            this.mContext = context;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.adapter_language, parent, false);
            }
            TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
            MasterComplaints masterComplaints = mAllocationDetailsList.get(position);
            vT_apl_spinnerText.setText(masterComplaints.getmTitleName());
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.adapter_language, parent, false);
            }
            TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
            MasterComplaints masterComplaints = mAllocationDetailsList.get(position);
            vT_apl_spinnerText.setText(masterComplaints.getmTitleName());
            return convertView;
        }
    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(ComplaintBookingActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMPLAINT_LIST)){
            vT_acb_lblComplaintList.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMPLAINT_LIST));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_SUBMIT)){
            vT_acb_btnSubmit.setText(languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }
    }

}
