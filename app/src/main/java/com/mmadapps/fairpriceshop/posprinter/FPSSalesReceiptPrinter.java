package com.mmadapps.fairpriceshop.posprinter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.adapters.CommoditySalesReceiptAdapter;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 1/5/2016.
 */
public class FPSSalesReceiptPrinter extends ActionbarActivity implements ReceiveListener {

    private TextView vT_RSC_transactionNumber, vT_RSC_fpsCode, vT_RSC_fpsAddress1, vT_RSC_fpsAddress2, vT_RSC_fpsAddress3, vT_RSC_rationCardNumber,
            vT_RSC_schemeName, vT_RSC_headofNFSA, vT_RSC_purchasingMember, vT_RSC_uidResponse, vT_RSC_salesAllocationMonth, vT_RSC_modeOfTransaction;

    private ListView vL_RSC_rationTakenList, vL_RSC_rationBalanceList;

    private TextView vT_RSC_printButton, vT_RSC_cancelButton;
    private ImageView vI_RSC_qrCodeImage;

    // Printer variables
    private Printer mPrinter = null;
    private Context mContext;

    //common fields
    private final String TRANS_ID_DATE = "Transaction Id,Date*";
    private final String FPS_CODE = "FPS CODE";
    private final String FPS_NAME_ADDRESS = "FPS Name & Address";
    private final String RATION_CARD_NUMBER = "Ration Card Number";
    private final String SCHEME_NAME = "Scheme Name";
    private final String HEAD_OF_NFSA = "Head Of NFSA";
    private final String PURCHASING_MEMBER = "Purchasing Member";
    private final String UID_AUTHORIZATION_RESPONSE = "UID Authorization Response";
    private final String SALES_FOR_ALLOCATION_MONTH="Sales for Allocation # month";
    private final String MODE_OF_TRANSACTION = "Mode of transaction";
    //ration taken
    private final String RATION_TAKEN = "Ration Taken";
    private final String COMMODITY = "Commodity";
    private final String SOLD_QTY = "Sold Qty";
    private final String ECO_PRICE = "Eco Price";
    private final String CIP_SIP_RS = "CIP/SIP Rs";
    private final String SUBSIDY_RS = "Subsidy Rs";
    private final String TOTAL = "Total";
    //ration balance
    private final String ALLOCATE_QTY = "Allocate Qty";
    private final String VALIDATE_DATE = "Validate Date";
    private final String BALANCE_QTY= "Balance Qty";
    //Languages views
    private TextView vT_aRSC_departmentoffood_civilsupplies_consumeraffairs,vT_aRSC_transactionid_date,vT_aRSC_qrcode,vT_aRSC_fpscode,
            vT_aRSC_fps_name_address,vT_aRSC_rationcardnumber,vT_aRSC_uid_authentication_response,vT_aRSC_scheme_name,vT_aRSC_salefor_allocationmonth,
            vT_aRSC_headof_nfsa,vT_aRSC_modeoftransaction,vT_aRSC_purchasingmember,vT_aRSC_rationtaken_commidty,vT_aRSC_rationtaken_sold_qty,
            vT_aRSC_rationtaken_eco_price,vT_aRSC_rationtaken_cip_sip_rs,vT_aRSC_rationtaken_subsidy_rs,vT_aRSC_rationtaken_total,
            vT_aRSC_rationbalance,vT_aRSC_rationbalance_commodity,vT_aRSC_rationbalance_allocate_qty,vT_aRSC_rationbalance_soldqty,
            vT_aRSC_rationbalance_validitydate,vT_aRSC_rationbalance_balanceqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.receipt_sales_commodity);
        super.onCreate(savedInstanceState);
        initializeViews();
        setActionEvent();

        //sample(new WebView(this));
    }

    /*private void sample(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printerAdapter = webView.createPrintDocumentAdapter("Receipt");
        PrintJob printJob = printManager.print(getString(R.string.app_name),printerAdapter, new PrintAttributes.Builder().build());
    }*/

    private void initializeViews() {
        mContext = this;

        vT_RSC_transactionNumber = (TextView) findViewById(R.id.vT_RSC_transactionNumber);
        vT_RSC_fpsCode = (TextView) findViewById(R.id.vT_RSC_fpsCode);
        vT_RSC_fpsAddress1 = (TextView) findViewById(R.id.vT_RSC_fpsAddress1);
        vT_RSC_fpsAddress2 = (TextView) findViewById(R.id.vT_RSC_fpsAddress2);
        vT_RSC_fpsAddress3 = (TextView) findViewById(R.id.vT_RSC_fpsAddress3);
        vT_RSC_rationCardNumber = (TextView) findViewById(R.id.vT_RSC_rationCardNumber);
        vT_RSC_schemeName = (TextView) findViewById(R.id.vT_RSC_schemeName);
        vT_RSC_headofNFSA = (TextView) findViewById(R.id.vT_RSC_headofNFSA);
        vT_RSC_purchasingMember = (TextView) findViewById(R.id.vT_RSC_purchasingMember);
        vT_RSC_uidResponse = (TextView) findViewById(R.id.vT_RSC_uidResponse);
        vT_RSC_salesAllocationMonth = (TextView) findViewById(R.id.vT_RSC_salesAllocationMonth);
        vT_RSC_modeOfTransaction = (TextView) findViewById(R.id.vT_RSC_modeOfTransaction);

        vI_RSC_qrCodeImage = (ImageView) findViewById(R.id.vI_RSC_qrCodeImage);

        vL_RSC_rationTakenList = (ListView) findViewById(R.id.vL_RSC_rationTakenList);
        vL_RSC_rationBalanceList = (ListView) findViewById(R.id.vL_RSC_rationBalanceList);

        vT_RSC_printButton = (TextView) findViewById(R.id.vT_RSC_printButton);
        vT_RSC_cancelButton = (TextView) findViewById(R.id.vT_RSC_cancelButton);

        //
        vT_aRSC_departmentoffood_civilsupplies_consumeraffairs= (TextView) findViewById(R.id.vT_aRSC_departmentoffood_civilsupplies_consumeraffairs);
        vT_aRSC_transactionid_date= (TextView) findViewById(R.id.vT_aRSC_transactionid_date);
        vT_aRSC_qrcode= (TextView) findViewById(R.id.vT_aRSC_qrcode);
        vT_aRSC_fpscode= (TextView) findViewById(R.id.vT_aRSC_fpscode);
        vT_aRSC_fps_name_address= (TextView) findViewById(R.id.vT_aRSC_fps_name_address);
        vT_aRSC_rationcardnumber= (TextView) findViewById(R.id.vT_aRSC_rationcardnumber);
        vT_aRSC_uid_authentication_response= (TextView) findViewById(R.id.vT_aRSC_uid_authentication_response);
        vT_aRSC_scheme_name= (TextView) findViewById(R.id.vT_aRSC_scheme_name);
        vT_aRSC_salefor_allocationmonth= (TextView) findViewById(R.id.vT_aRSC_salefor_allocationmonth);
        vT_aRSC_headof_nfsa= (TextView) findViewById(R.id.vT_aRSC_headof_nfsa);
        vT_aRSC_modeoftransaction= (TextView) findViewById(R.id.vT_aRSC_modeoftransaction);
        vT_aRSC_purchasingmember= (TextView) findViewById(R.id.vT_aRSC_purchasingmember);
        vT_aRSC_rationtaken_commidty= (TextView) findViewById(R.id.vT_aRSC_rationtaken_commidty);
        vT_aRSC_rationtaken_sold_qty= (TextView) findViewById(R.id.vT_aRSC_rationtaken_sold_qty);
        vT_aRSC_rationtaken_eco_price= (TextView) findViewById(R.id.vT_aRSC_rationtaken_eco_price);
        vT_aRSC_rationtaken_cip_sip_rs= (TextView) findViewById(R.id.vT_aRSC_rationtaken_cip_sip_rs);
        vT_aRSC_rationtaken_subsidy_rs= (TextView) findViewById(R.id.vT_aRSC_rationtaken_subsidy_rs);
        vT_aRSC_rationtaken_total= (TextView) findViewById(R.id.vT_aRSC_rationtaken_total);
        vT_aRSC_rationbalance= (TextView) findViewById(R.id.vT_aRSC_rationbalance);
        vT_aRSC_rationbalance_commodity= (TextView) findViewById(R.id.vT_aRSC_rationbalance_commodity);
        vT_aRSC_rationbalance_allocate_qty= (TextView) findViewById(R.id.vT_aRSC_rationbalance_allocate_qty);
        vT_aRSC_rationbalance_soldqty= (TextView) findViewById(R.id.vT_aRSC_rationbalance_soldqty);
        vT_aRSC_rationbalance_validitydate= (TextView) findViewById(R.id.vT_aRSC_rationbalance_validitydate);
        vT_aRSC_rationbalance_balanceqty= (TextView) findViewById(R.id.vT_aRSC_rationbalance_balanceqty);


    }

    private void setActionEvent() {
        vT_RSC_printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsClass.selectedPrinterLang == -1 || UtilsClass.selectedPrinterSeries == -1) {
                    Intent intent = new Intent(FPSSalesReceiptPrinter.this, MainActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    printReceipt();
                }
            }
        });
        vT_RSC_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            String target = data.getStringExtra(UtilsClass.PRINTER_KEY);
            if (target != null) {
                //printReceipt();
                Toast.makeText(FPSSalesReceiptPrinter.this, "Printer connected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void printReceipt() {
        if (initializeObject()) {
            if (createSalesReceiptData()) {
                if (printData()) {
                    Intent result = new Intent();
                    setResult(RESULT_OK, result);
                    Log.e("Printing", "Print Success");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    private void setValues() {
        Helper mHelper = new Helper(this);
        mHelper.openDataBase();
        AgentDetails mAgentDetails = mHelper.getAgentDetails();
        mHelper.close();

        AllocationDetails allocationDetails = UtilsClass.mAllocationDetails;
        List<SchemeDetailsOnRationCardNumber> selectedCommodities = UtilsClass.mSelectedCommodities;
        RationLiftingMemberDetails memberDetails = UtilsClass.mSelectedMember;
        if (allocationDetails != null && selectedCommodities != null && selectedCommodities.size() > 0 && memberDetails != null) {
            vT_RSC_transactionNumber.setText(allocationDetails.getmAllocationMonth());
            vT_RSC_fpsCode.setText(mAgentDetails.getmFpsCode());
            vT_RSC_fpsAddress1.setText(mAgentDetails.getmFpsName());
            vT_RSC_fpsAddress2.setText(mAgentDetails.getmPLCName()+","+mAgentDetails.getmTahsilName());
            vT_RSC_fpsAddress3.setText(mAgentDetails.getmDistrict()+","+mAgentDetails.getmDistrict()+","+mAgentDetails.getmState());
            vT_RSC_rationCardNumber.setText(selectedCommodities.get(0).getmRationCardNo());
            vT_RSC_schemeName.setText(selectedCommodities.get(0).getmSchemeName());
            vT_RSC_headofNFSA.setText("NFSA");
            vT_RSC_purchasingMember.setText(memberDetails.getmMember_Name_EN());
            vT_RSC_uidResponse.setText(memberDetails.getmSeedingStatus());
            vT_RSC_salesAllocationMonth.setText(allocationDetails.getmAllocationMonth());
            vT_RSC_modeOfTransaction.setText("Mob");

            vL_RSC_rationBalanceList.setAdapter(new CommoditySalesReceiptAdapter(this, selectedCommodities));
            vL_RSC_rationTakenList.setAdapter(new CommoditySalesReceiptAdapter(this, selectedCommodities));

            setListViewHeightBasedOnChildren(vL_RSC_rationBalanceList);
            setListViewHeightBasedOnChildren(vL_RSC_rationTakenList);
        }
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

    protected boolean createSalesReceiptData() {
        String method = "";
        Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.coffee);
        StringBuilder textData = new StringBuilder();
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;
        final int width = 65535/2;
        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);

            method = "addImage";
            mPrinter.addImage(logoData, 0, 0,
                    logoData.getWidth(),
                    logoData.getHeight(),
                    Printer.COLOR_1,
                    Printer.MODE_MONO,
                    Printer.HALFTONE_DITHER,
                    Printer.PARAM_DEFAULT,
                    Printer.COMPRESS_AUTO);

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            //QR Code
            String qrCode = new String();
            // QR code size
            final int qrcodeWidth = 5;
            final int qrcodeHeight = 5;

            try {
                // create QR code data from EasySelect library
                method = "createQR";
                qrCode = "This is FPS Project QR code for Sales commodity receipt";

                method = "addTextAlign";
                mPrinter.addTextAlign(Printer.ALIGN_LEFT);

                method = "addSymbol";
                mPrinter.addSymbol(qrCode,
                        Printer.SYMBOL_QRCODE_MODEL_2,
                        Printer.LEVEL_L,
                        qrcodeWidth,
                        qrcodeHeight,
                        0);


            } catch (Exception e) {
                ShowMsg.showException(e, method, mContext);
                return false;
            }

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            textData.append("THE STORE 123 (555) 555 – 5555\n");
            textData.append("STORE DIRECTOR – John Smith\n");
            textData.append("\n");
            textData.append("7/01/07 16:58 6153 05 0191 134\n");
            textData.append("ST# 21 OP# 001 TE# 01 TR# 747\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("400 OHEIDA 3PK SPRINGF  9.99 R\n");
            textData.append("410 3 CUP BLK TEAPOT    9.99 R\n");
            textData.append("445 EMERIL GRIDDLE/PAN 17.99 R\n");
            textData.append("438 CANDYMAKER ASSORT   4.99 R\n");
            textData.append("474 TRIPOD              8.99 R\n");
            textData.append("433 BLK LOGO PRNTED ZO  7.99 R\n");
            textData.append("458 AQUA MICROTERRY SC  6.99 R\n");
            textData.append("493 30L BLK FF DRESS   16.99 R\n");
            textData.append("407 LEVITATING DESKTOP  7.99 R\n");
            textData.append("441 **Blue Overprint P  2.99 R\n");
            textData.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
            textData.append("461 WESTGATE BLACK 25  59.99 R\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("SUBTOTAL                160.38\n");
            textData.append("TAX                      14.43\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addTextSize";
            mPrinter.addTextSize(2, 2);
            method = "addText";
            mPrinter.addText("TOTAL    174.81\n");
            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            textData.append("CASH                    200.00\n");
            textData.append("CHANGE                   25.19\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("Purchased item total number\n");
            textData.append("Sign Up and Save !\n");
            textData.append("With Preferred Saving Card\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            method = "addFeedLine";
            mPrinter.addFeedLine(2);
/*

            method = "addBarcode";
            mPrinter.addBarcode("01209457",
                    Printer.SYMBOL_QRCODE_MODEL_1,
                    Printer.HRI_BOTH,
                    Printer.FONT_A,
                    2,
                    200);
*/

            // feed & cut
            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Epos2Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        textData = null;
        return true;
    }

    @Override
    protected void updateCaptions() {
        // Set textViews
        Toast.makeText(FPSSalesReceiptPrinter.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF)){
            vT_aRSC_departmentoffood_civilsupplies_consumeraffairs.setText(languageCaptionsMap.get(CaptionsKey.KEY_DEPARTMENT_OF_FOOD_CIVIL_SUPPLIES_AND_CONSUMER_AFF));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRANSPORTER_ID_NAME)){
            vT_aRSC_transactionid_date.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRANSPORTER_ID_NAME));
        }
     /*   if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TRANSPORTER_ID_NAME)){
            vT_aRSC_qrcode.setText(languageCaptionsMap.get(CaptionsKey.KEY_TRANSPORTER_ID_NAME));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_aRSC_fpscode.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_FPS_CODE)){
            vT_aRSC_fps_name_address.setText(languageCaptionsMap.get(CaptionsKey.KEY_FPS_CODE));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_aRSC_rationcardnumber.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_aRSC_uid_authentication_response.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }*/
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_RATION_CARD_NUMBER)){
            vT_aRSC_scheme_name.setText(languageCaptionsMap.get(CaptionsKey.KEY_RATION_CARD_NUMBER));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATION_MONTH)){
            vT_aRSC_salefor_allocationmonth.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATION_MONTH));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATION_MONTH)){
            vT_aRSC_headof_nfsa.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATION_MONTH));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_MODE_OF_SYNC)){
            vT_aRSC_modeoftransaction.setText(languageCaptionsMap.get(CaptionsKey.KEY_MODE_OF_SYNC));
        }
      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_MODE_OF_SYNC)){
            vT_aRSC_purchasingmember.setText(languageCaptionsMap.get(CaptionsKey.KEY_MODE_OF_SYNC));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationtaken_commidty.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationtaken_sold_qty.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }*/
      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationtaken_eco_price.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }*/
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationtaken_cip_sip_rs.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }*/
      /*  if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationtaken_subsidy_rs.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL)){
            vT_aRSC_rationtaken_total.setText(languageCaptionsMap.get(CaptionsKey.KEY_TOTAL));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_TOTAL)){
            vT_aRSC_rationbalance.setText(languageCaptionsMap.get(CaptionsKey.KEY_TOTAL));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_COMMODITY)){
            vT_aRSC_rationbalance_commodity.setText(languageCaptionsMap.get(CaptionsKey.KEY_COMMODITY));
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATED_QUANTITY)){
            vT_aRSC_rationbalance_allocate_qty.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATED_QUANTITY));
        }
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATED_QUANTITY)){
            vT_aRSC_rationbalance_soldqty.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATED_QUANTITY));
        }*/
       /* if(languageCaptionsMap.containsKey(CaptionsKey.KEY_ALLOCATED_QUANTITY)){
            vT_aRSC_rationbalance_validitydate.setText(languageCaptionsMap.get(CaptionsKey.KEY_ALLOCATED_QUANTITY));
        }*/
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_BALANCE_QTY)){
            vT_aRSC_rationbalance_balanceqty.setText(languageCaptionsMap.get(CaptionsKey.KEY_BALANCE_QTY));
        }
    }

}
