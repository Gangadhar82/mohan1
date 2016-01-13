package com.mmadapps.fairpriceshop.posprinter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.CommodityDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.Date;
import java.util.List;

/**
 * Created by Baskar on 1/5/2016.
 */
public class FPSCommodityPrinter extends ActionbarActivity implements ReceiveListener {

    private TextView vT_RCR_transactionIdDate,vT_RCR_fpsCode,vT_RCR_fpsAddress1,vT_RCR_fpsAddress2,vT_RCR_fpsAddress3,vT_RCR_modeOfTransaction,
            vT_RCR_truckChallanNumber,vT_RCR_truckNumber,vT_RCR_mobileNumber,vT_RCR_godownName,vT_RCR_dispatchNumber,vT_RCR_driverName;

    private TextView vT_RCR_printButton,vT_RCR_cancelButton;
    //Languages views
    private TextView vT_arcr_transactionIdDate,vT_arcr_fpscode,vT_arcr_fpsnameandaddress,vT_arcr_modeoftransaction,vT_arcr_truckchallenno,
            vT_arcr_truckno,vT_arcr_mobileno,vT_arcr_stateGodowncode_name,vT_arcr_dispatchdate,vT_arcr_drivername,vT_arcr_rationtaken,
            vT_arcr_commodity,vT_arcr_d_o_qty,vT_arcr_qty_rcvd,vT_arcr_balance,vT_arcr_qty_rcvd_in_truck,vT_arcr_departmentoffood_civilsupplies_consumer_affairs;

    // Printer variables
    private Printer mPrinter = null;
    private Context mContext;

    //variables
    private ListView vL_RCR_commoditiesList;
    private List<CommodityDetails> commodityList;
    private AgentDetails mAgentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_commodity_receiving);
        initializeViews();
        setActionEvent();
    }

    private void initializeViews() {
        vT_RCR_printButton = (TextView) findViewById(R.id.vT_RCR_printButton);
        vT_RCR_cancelButton= (TextView) findViewById(R.id.vT_RCR_cancelButton);

        vT_RCR_transactionIdDate = (TextView) findViewById(R.id.vT_RCR_transactionIdDate);
        vT_RCR_fpsCode = (TextView) findViewById(R.id.vT_RCR_fpsCode);
        vT_RCR_fpsAddress1 = (TextView) findViewById(R.id.vT_RCR_fpsAddress1);
        vT_RCR_fpsAddress2 = (TextView) findViewById(R.id.vT_RCR_fpsAddress2);
        vT_RCR_fpsAddress3 = (TextView) findViewById(R.id.vT_RCR_fpsAddress3);
        vT_RCR_modeOfTransaction = (TextView) findViewById(R.id.vT_RCR_modeOfTransaction);
        vT_RCR_truckChallanNumber = (TextView) findViewById(R.id.vT_RCR_truckChallanNumber);
        vT_RCR_truckNumber = (TextView) findViewById(R.id.vT_RCR_truckNumber);
        vT_RCR_mobileNumber = (TextView) findViewById(R.id.vT_RCR_mobileNumber);
        vT_RCR_godownName = (TextView) findViewById(R.id.vT_RCR_godownName);
        vT_RCR_dispatchNumber = (TextView) findViewById(R.id.vT_RCR_dispatchNumber);
        vT_RCR_driverName = (TextView) findViewById(R.id.vT_RCR_driverName);

        vL_RCR_commoditiesList = (ListView) findViewById(R.id.vL_RCR_commoditiesList);
        vT_arcr_transactionIdDate= (TextView) findViewById(R.id.vT_arcr_transactionIdDate);
        vT_arcr_fpscode= (TextView) findViewById(R.id.vT_arcr_fpscode);
        vT_arcr_fpsnameandaddress= (TextView) findViewById(R.id.vT_arcr_fpsnameandaddress);
        vT_arcr_modeoftransaction= (TextView) findViewById(R.id.vT_arcr_modeoftransaction);
        vT_arcr_truckchallenno= (TextView) findViewById(R.id.vT_arcr_truckchallenno);
        vT_arcr_truckno= (TextView) findViewById(R.id.vT_arcr_truckno);
        vT_arcr_mobileno= (TextView) findViewById(R.id.vT_arcr_mobileno);
        vT_arcr_stateGodowncode_name= (TextView) findViewById(R.id.vT_arcr_stateGodowncode_name);
        vT_arcr_dispatchdate= (TextView) findViewById(R.id.vT_arcr_dispatchdate);
        vT_arcr_drivername= (TextView) findViewById(R.id.vT_arcr_drivername);
        vT_arcr_rationtaken= (TextView) findViewById(R.id.vT_arcr_rationtaken);
        vT_arcr_rationtaken= (TextView) findViewById(R.id.vT_arcr_rationtaken);
        vT_arcr_rationtaken= (TextView) findViewById(R.id.vT_arcr_rationtaken);
        vT_arcr_rationtaken= (TextView) findViewById(R.id.vT_arcr_rationtaken);
        vT_arcr_commodity= (TextView) findViewById(R.id.vT_arcr_commodity);
        vT_arcr_d_o_qty= (TextView) findViewById(R.id.vT_arcr_d_o_qty);
        vT_arcr_qty_rcvd= (TextView) findViewById(R.id.vT_arcr_qty_rcvd);
        vT_arcr_balance= (TextView) findViewById(R.id.vT_arcr_balance);
        vT_arcr_qty_rcvd_in_truck= (TextView) findViewById(R.id.vT_arcr_qty_rcvd_in_truck);
        vT_arcr_departmentoffood_civilsupplies_consumer_affairs= (TextView) findViewById(R.id.vT_arcr_departmentoffood_civilsupplies_consumer_affairs);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    private void setActionEvent() {
        vT_RCR_printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsClass.selectedPrinterLang == -1 || UtilsClass.selectedPrinterSeries == -1) {
                    Intent intent = new Intent(FPSCommodityPrinter.this, MainActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    printReceipt();
                }
            }
        });

        vT_RCR_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void printReceipt() {
        if (initializeObject()) {
            if (createCommodityReceivingData()) {
                if (printData()) {
                    finish();
                    Log.e("Printing", "Print Success");
                }
            }
        }
    }

    private void setValues() {
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        AgentDetails mAgentDetails = mHelper.getAgentDetails();
        mHelper.close();
        commodityList = UtilsClass.mTruckChallanDetails.getmTruckChalanDetails();

        vT_RCR_transactionIdDate.setText(UtilsClass.salesDateFormat.format(new Date()));
        vT_RCR_fpsCode.setText(mAgentDetails.getmFpsCode());
        vT_RCR_fpsAddress1.setText(mAgentDetails.getmFpsName());
        vT_RCR_fpsAddress2.setText(mAgentDetails.getmPLCName()+","+mAgentDetails.getmTahsilName());
        vT_RCR_fpsAddress3.setText(mAgentDetails.getmDistrict()+","+mAgentDetails.getmDistrict()+","+mAgentDetails.getmState());
        vT_RCR_modeOfTransaction.setText("APP");
        vT_RCR_truckChallanNumber.setText(UtilsClass.mTruckChallanDetails.getmTruckChalanNumber());
        vT_RCR_truckNumber.setText(UtilsClass.mTruckChallanDetails.getmTruckNumber());
        vT_RCR_mobileNumber.setText("null");
        vT_RCR_godownName.setText(UtilsClass.mTruckChallanDetails.getmStateGodownName());
        vT_RCR_dispatchNumber.setText("null");
        vT_RCR_driverName.setText(UtilsClass.mTruckChallanDetails.getmDriverMobile());

        vL_RCR_commoditiesList.setAdapter(new CommodityListAdapter());
        setListViewHeightBasedOnChildren(vL_RCR_commoditiesList);
    }


    protected boolean initializeObject() {
        try {
            mPrinter = new Printer(UtilsClass.selectedPrinterSeries, UtilsClass.selectedPrinterLang, mContext);
        } catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }

    protected void finalizeObject() {
        if (mPrinter == null) {
            return;
        }
        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void dispPrinterWarnings(PrinterStatusInfo status) {
        //EditText edtWarnings = (EditText)findViewById(R.id.edtWarnings);
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        Log.e("Warning Message", warningsMsg);
        Toast.makeText(getApplicationContext(), warningsMsg, Toast.LENGTH_SHORT).show();
        //edtWarnings.setText(warningsMsg);
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }


    private boolean createCommodityReceivingData() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            String target = data.getStringExtra(UtilsClass.PRINTER_KEY);
            if (target != null) {
                printReceipt();
            }
        }
    }


    protected boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        dispPrinterWarnings(status);

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "sendData", mContext);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(UtilsClass.selectedPrinterIpAddress, Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }


    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else if (status.getOnline() == Printer.FALSE) {
            return false;
        } else {
            ;//print available
        }

        return true;
    }

    @Override
    public void onPtrReceive(Printer printer, final int code, final PrinterStatusInfo status, String s) {
        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                //updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }


    private class CommodityListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(commodityList == null)
                return 0;
            return commodityList.size();
        }

        @Override
        public CommodityDetails getItem(int position) {
            return commodityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.adapter_commodity_printer,parent,false);
            }
            final TextView vT_ACP_commodity,vT_ACP_doQty,vT_ACP_qtyRecvd,vT_ACP_balanceQty,vT_ACP_qtyRecvdTruck;

            vT_ACP_commodity = (TextView) convertView.findViewById(R.id.vT_ACP_commodity);
            vT_ACP_doQty = (TextView) convertView.findViewById(R.id.vT_ACP_doQty);
            vT_ACP_qtyRecvd = (TextView) convertView.findViewById(R.id.vT_ACP_qtyRecvd);
            vT_ACP_balanceQty = (TextView) convertView.findViewById(R.id.vT_ACP_balanceQty);
            vT_ACP_qtyRecvdTruck = (TextView) convertView.findViewById(R.id.vT_ACP_qtyRecvdTruck);

            new Runnable() {
                @Override
                public void run() {
                    vT_ACP_commodity.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_ACP_doQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_ACP_qtyRecvd.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_ACP_balanceQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                    vT_ACP_qtyRecvdTruck.setTypeface(FPSApplication.fMyriadPro_Regular);
                }
            }.run();

            CommodityDetails commodity = getItem(position);
            vT_ACP_commodity.setText(commodity.getmCommodityName());
            vT_ACP_doQty.setText(commodity.getmCommodityQty());
            vT_ACP_qtyRecvd.setText(commodity.getmCommodityReceived());
            vT_ACP_balanceQty.setText(commodity.getmCommodityQty());
            vT_ACP_qtyRecvdTruck.setText(commodity.getmAllotedQty());

            return convertView;
        }
    }

    @Override
    protected void updateCaptions() {
        // Set textViews

        Toast.makeText(FPSCommodityPrinter.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRANSPORTER_ID_NAME)){
            vT_arcr_transactionIdDate.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRANSPORTER_ID_NAME));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_arcr_fpscode.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }
     /*   if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_arcr_fpsnameandaddress.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_MODE_OF_SYNC)){
            vT_arcr_modeoftransaction.setText(languageCaptionsMap.get(CaptionsKey.KEY_MODE_OF_SYNC));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER)){
            vT_arcr_truckchallenno.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRUCK_CHALLAN_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_Mobile_NUMBER)){
            vT_arcr_mobileno.setText(languageCaptionsMap.get(CaptionsKey.KEY_Mobile_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_STATE_GODOWN_CODE_NAME)){
            vT_arcr_stateGodowncode_name.setText(languageCaptionsMap.get(CaptionsKey.KEY_STATE_GODOWN_CODE_NAME));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DISPATCHED_DATE)){
            vT_arcr_dispatchdate.setText(languageCaptionsMap.get(CaptionsKey.KEY_DISPATCHED_DATE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DRIVER_NAME)){
            vT_arcr_drivername.setText(languageCaptionsMap.get(CaptionsKey.KEY_DRIVER_NAME));
        }
        /*if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DRIVER_NAME)){
            vT_arcr_rationtaken.setText(languageCaptionsMap.get(CaptionsKey.KEY_DRIVER_NAME));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_arcr_commodity.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_D_O_QTY)){
            vT_arcr_d_o_qty.setText(languageCaptionsMap.get(CaptionsKey.KEY_D_O_QTY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_QTY_RCVD)){
            vT_arcr_qty_rcvd.setText(languageCaptionsMap.get(CaptionsKey.KEY_QTY_RCVD));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_BALANCE)){
            vT_arcr_balance.setText(languageCaptionsMap.get(CaptionsKey.KEY_BALANCE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_QTY_RCVD_IN_TRUCK)){
            vT_arcr_qty_rcvd_in_truck.setText(languageCaptionsMap.get(CaptionsKey.KEY_QTY_RCVD_IN_TRUCK));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CANCEL)){
            vT_RCR_cancelButton.setText(languageCaptionsMap.get(CaptionsKey.KEY_CANCEL));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF)){
            vT_arcr_departmentoffood_civilsupplies_consumer_affairs.setText(languageCaptionsMap.get(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF));
        }
    }

}
