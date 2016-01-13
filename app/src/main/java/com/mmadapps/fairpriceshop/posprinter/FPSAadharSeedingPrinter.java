package com.mmadapps.fairpriceshop.posprinter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

/**
 * Created by Baskar on 1/5/2016.
 */
public class FPSAadharSeedingPrinter extends ActionbarActivity implements ReceiveListener {

    //Views
    private TextView vT_RAS_transactionNumber, vT_RAS_fpsCode, vT_RAS_fpsAddress1, vT_RAS_fpsAddress2, vT_RAS_fpsAddress3, vT_RAS_rationCardNumber,
            vT_RAS_rationCardType, vT_RAS_modeOfTransaction, vT_RAS_memberName, vT_RAS_aadharNumber, vT_RAS_seedStatus;
    private TextView vT_RAS_printButton,vT_RAS_cancelButton;
    // Printer variables
    private Printer mPrinter = null;
    private Context mContext;
    //language Textviews

    private TextView vT_aRAS_departmentoffood_civilsupplies_consumeraffaris,vT_aRAS_aadhar_seeding_receipt,vT_aRAS_transaction_id_date,
            vT_aRAS_fps_code,vT_aRAS_fps_name_address,vT_aRAS_ration_cardno,vT_aRAS_cardType,vT_aRAS_modeoftransaction,vT_aRAS_name,
            vT_aRAS_aadharno,vT_aRAS_seedstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_aadhar_seeding);
        initializeViews();
        setActionEvent();
        setValues();
    }



    private void initializeViews() {
        vT_RAS_printButton = (TextView) findViewById(R.id.vT_RAS_printButton);
        vT_RAS_cancelButton= (TextView) findViewById(R.id.vT_RAS_cancelButton);

        vT_RAS_transactionNumber = (TextView) findViewById(R.id.vT_RAS_transactionNumber);
        vT_RAS_fpsCode = (TextView) findViewById(R.id.vT_RAS_fpsCode);
        vT_RAS_fpsAddress1 = (TextView) findViewById(R.id.vT_RAS_fpsAddress1);
        vT_RAS_fpsAddress2 = (TextView) findViewById(R.id.vT_RAS_fpsAddress2);
        vT_RAS_fpsAddress3 = (TextView) findViewById(R.id.vT_RAS_fpsAddress3);
        vT_RAS_rationCardNumber = (TextView) findViewById(R.id.vT_RAS_rationCardNumber);
        vT_RAS_rationCardType = (TextView) findViewById(R.id.vT_RAS_rationCardType);
        vT_RAS_modeOfTransaction = (TextView) findViewById(R.id.vT_RAS_modeOfTransaction);
        vT_RAS_memberName = (TextView) findViewById(R.id.vT_RAS_memberName);
        vT_RAS_aadharNumber = (TextView) findViewById(R.id.vT_RAS_aadharNumber);
        vT_RAS_seedStatus = (TextView) findViewById(R.id.vT_RAS_seedStatus);
        //languages views

        vT_aRAS_departmentoffood_civilsupplies_consumeraffaris= (TextView) findViewById(R.id.vT_aRAS_departmentoffood_civilsupplies_consumeraffaris);
        vT_aRAS_aadhar_seeding_receipt= (TextView) findViewById(R.id.vT_aRAS_aadhar_seeding_receipt);
        vT_aRAS_transaction_id_date= (TextView) findViewById(R.id.vT_aRAS_transaction_id_date);
        vT_aRAS_fps_code= (TextView) findViewById(R.id.vT_aRAS_fps_code);
        vT_aRAS_fps_name_address= (TextView) findViewById(R.id.vT_aRAS_fps_name_address);
        vT_aRAS_ration_cardno= (TextView) findViewById(R.id.vT_aRAS_ration_cardno);
        vT_aRAS_cardType= (TextView) findViewById(R.id.vT_aRAS_cardType);
        vT_aRAS_modeoftransaction= (TextView) findViewById(R.id.vT_aRAS_modeoftransaction);
        vT_aRAS_name= (TextView) findViewById(R.id.vT_aRAS_name);
        vT_aRAS_aadharno= (TextView) findViewById(R.id.vT_aRAS_aadharno);
        vT_aRAS_seedstatus= (TextView) findViewById(R.id.vT_aRAS_seedstatus);



    }

    private void setValues() {
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        AgentDetails mAgentDetails = mHelper.getAgentDetails();
        mHelper.close();

        vT_RAS_transactionNumber.setText("Null");
        vT_RAS_fpsCode.setText(mAgentDetails.getmFpsCode());
        vT_RAS_fpsAddress1.setText(mAgentDetails.getmFpsName());
        vT_RAS_fpsAddress2.setText(mAgentDetails.getmPLCName()+","+mAgentDetails.getmTahsilName());
        vT_RAS_fpsAddress3.setText(mAgentDetails.getmDistrict()+","+mAgentDetails.getmDistrict()+","+mAgentDetails.getmState());
        vT_RAS_rationCardNumber.setText(UtilsClass.mCardHolderDetails.getmCardNumber());
        vT_RAS_rationCardType.setText(UtilsClass.mCardHolderDetails.getmSchemeName());
        vT_RAS_modeOfTransaction.setText("App");
        vT_RAS_memberName.setText(UtilsClass.mSelectedMember.getmMember_Name_EN());
        vT_RAS_aadharNumber.setText(UtilsClass.mSelectedMember.getmUIDAINo());
        vT_RAS_seedStatus.setText(UtilsClass.mSelectedMember.getmSeedingStatus());
    }

    private void setActionEvent() {
        vT_RAS_printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsClass.selectedPrinterLang == -1 || UtilsClass.selectedPrinterSeries == -1) {
                    Intent intent = new Intent(FPSAadharSeedingPrinter.this, MainActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    printReceipt();
                }
            }
        });

        vT_RAS_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void printReceipt() {
        if (initializeObject()) {
            if (createAadharSeedingData()) {
                if (printData()) {
                    finish();
                    Log.e("Printing", "Print Success");
                }
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

    private boolean createAadharSeedingData() {
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
    protected void finalizeObject() {
        if (mPrinter == null) {
            return;
        }
        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
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

    @Override
    protected void updateCaptions() {
        // Set textViews

        Toast.makeText(FPSAadharSeedingPrinter.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF)){
            vT_aRAS_departmentoffood_civilsupplies_consumeraffaris.setText(languageCaptionsMap.get(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF)){
            vT_aRAS_aadhar_seeding_receipt.setText(languageCaptionsMap.get(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRANSPORTER_ID_NAME)){
            vT_aRAS_transaction_id_date.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRANSPORTER_ID_NAME));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_aRAS_fps_code.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_aRAS_fps_name_address.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_aRAS_ration_cardno.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_CARD_TYPE)){
            vT_aRAS_cardType.setText(languageCaptionsMap.get(CaptionsKey.KEY_CARD_TYPE));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_MODE_OF_SYNC)){
            vT_aRAS_modeoftransaction.setText(languageCaptionsMap.get(CaptionsKey.KEY_MODE_OF_SYNC));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_aRAS_name.setText(languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }
      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_aRAS_aadharno.setText(languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }*/
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_NAME)){
            vT_aRAS_seedstatus.setText(languageCaptionsMap.get(CaptionsKey.KEY_NAME));
        }*/
    }
}
