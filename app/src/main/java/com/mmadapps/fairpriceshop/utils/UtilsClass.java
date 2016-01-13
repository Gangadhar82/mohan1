package com.mmadapps.fairpriceshop.utils;

import android.util.Log;

import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.bean.TruckChallanDetails;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by Baskar on 12/15/2015.
 */
public class UtilsClass {

    //Shared preference variables
    public static final String SP_LANGUAGE_CODE = "language";

    public static final String PRINTER_KEY = "key";
    public static final String PRINTER_SUCCESS = "Connected";
    public static final String AADHAR_SEEDING = "Aadhar_Seeding";

    public static final String COMMODITY_RECEIVING ="Commodity_Receiving";

    // LOGIN SHA1 BEGINS

    public static String getShaIString(String pass){
        MessageDigest cript;
        String shaHashString = null;
        try {
            cript = MessageDigest.getInstance("SHA-1");
            cript.reset();
            byte[] raw = cript.digest(pass.getBytes());
            shaHashString = byteArrayToHexString(raw);
            Log.e("shaHashString", shaHashString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return shaHashString;
    }

    private static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    //LOGIN SHA1 ENDS

    //AadharSeedingActivity Constants
    public static final String VALIDATING_AADHAR = "I";
    public static final String VALIDATING_AADHAR_AND_UIDIA = "O";

    //Create Sale Constants
    public static final String WITH_AADHAR = "UID";
    public static final String WITH_OTP = "OTP";
    public static final String WITHOUT_AUTHENTICATION = "NA";

    //Post uid authenticate constants
    public static final String UERTYPE_FPSAGENT = "1";
    public static final String UERTYPE_BUYER = "2";

    public static final DecimalFormat df = new DecimalFormat(".##");
    public static final DecimalFormat df_Three = new DecimalFormat(".###");
    public static final SimpleDateFormat commodityDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat salesDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    public static final SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static String FPS_CODE = null;

    // webservice class starts here
    public static final String Autherization_Key = "Authorization";
    public static final String Autherization_Value = "Android:611c1aab-97c6-4580-b5b0-9d2d988e3534";
    private static final String SERVER_LOCAL = "http://MmadDev4AllProj.cloudapp.net/NIC.FPS.Service/";
    private static final String SERVER_NIC = "http://10.103.6.36/pdsappapi/";
    public static final String SERVER = SERVER_LOCAL;

    // Methods names

    //Get Method Names
    public static final String GET_RATION_LIFTING_MEMBERS_DETAILS = SERVER+"GetRationLiftingMemberDetails/?";
    public static final String GET_AGENT_AUTHENTICATE = SERVER+"AgentAuthenticate/?";
    public static final String GET_AGENT_DETAILS = SERVER+"AgentDetails/?";
    public static final String GET_AGENT_CONFIGURATIONS = SERVER+"AgentConfigurations/?";
    public static final String GET_ALLOCATION = SERVER+"GetAllocation/?";
    public static final String GET_RATION_CARD_HOLDER_DETAILS = SERVER+"GetRationCardHolderDetails/?";
    public static final String GET_SCHEME_DETAILS_FOR_RATION_CARD = SERVER+"GetSchemaDetailsForRationCard/?";
    public static final String GET_AUTHENTICATION_DETAILS = SERVER+"AuthenticationDetails?";
    public static final String GET_LANGUAGES = SERVER+"Languages/?";
    public static final String GET_MASTER_COMPLAINTS = SERVER+"MasterComplains";
    public static final String GET_FEEDBACK_DETAILS = SERVER+"GetFeedBackDetails/?";
    public static final String GET_FEEDBACK_ANSWER_DETAILS = SERVER+"GetFeeadBackAnswerDetails/?";
    public static final String GET_NW_DOWNTIME_REPORT = SERVER+"Reports/SyncORNetwork?reportType=NDR&";
    public static final String GET_ONLINE_SYNC_REPORT = SERVER+"Reports/SyncORNetwork?reportType=OSR&";
    public static final String GET_HARDWARE_COMPLAINT = SERVER+"Reports/HardwareComplaint/?";
    public static final String GET_MEMBER_DETAILS_REPORT = SERVER+"Reports/MemberDetails?";
    public static final String GET_DAILY_SALES_REPORT =SERVER+"Reports/DailySales?";
    public static final String GET_STOCK_BALANCE_REPORT = SERVER+"Reports/GetStockBalanceReport?fpsCode=";
    public static final String GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO = SERVER+ "GetTruckChalanDetailByChalanNo?";
    public static final String GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE = SERVER+ "GetCaptionDetailsByPageStateLang?";
    public static final String GET_MENUS_BY_ROLE_ID = SERVER+"GetMenusByRoleId/?";

    //Post Method Names
    public static final String POST_UIDAI_AUTHENTICATE = SERVER+"UidaiAuthenticate";
    public static final String POST_CREATE_AADHAARSEED = SERVER+"CreateAadhaarSeed";
    public static final String POST_SALE_CREATE = SERVER+"SaleCreate";
    public static final String POST_SAVE_COMPLAINT = SERVER+"SaveComplain";
    public static final String POST_COMMODITY_RECEIVE = SERVER+"CommodityReceive";
    public static final String POST_SAVE_FEEDBACK = SERVER + "SaveFeedBack";

    // webservice class variables end

    public static final String UID_SAMPLE_DATA = "Rk1SACAyMAAAAAE+AAABPAFiAMUAxQEAAAAoMEChAMP3ZEBrAOalZIC2ARTeXYCUAJcCV4DNARbTZEBQAPGnZEBFALqbZIB3ATG7ZEDIATezQ0DkAS3IV4BEASGyZEEQAQXfXUEWALJpPECSAEuBZEA2AGUSZECGADJ7ZEC1AM15ZEBpALeZZEDGAQhuZEBoAP8hZICVAIn/V0CuASrGNUD9ANTkZEDQASxcQ4EFAOhkZICwAUNLNUApANCfZICvAVFKPEEGAIjrZED6AU6/Q0C6AEB5ZEAaAUSuZEChAKyFZEDeAOHmZECQARmuV4CbASCkSUCPASYmV0BuAI2RZECZATbNSUA6AN0fZEC+AHR3ZIC/AUM/L0CNAVDPL0D1AH5wZECoAE73ZEApAHaaZIDuAE5kQ0BKADuSZAAA";

    //SMS
    public static final String SENT = "SMS_SENT";
    public static final String DELIVERED = "SMS_DELIVERED";

    public static int generateOTP(){
        int otp=0;
        try {
            Random random = new Random(System.currentTimeMillis());
            int fst = random.nextInt(2);
            int scnd = random.nextInt(10000);
            otp = (1+fst*10000+scnd);
        }catch (Exception e){
            e.printStackTrace();
        }
        return otp;
    }

    // Constants
    public static final String[] monthsArray = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    public static final String[] languageArray ={"English","Hindi","Kannada","Malayalam","Odisa","Tamil"};

    //Field Inspection

    public static final String Question_Format_MC = "MC";
    public static final String Question_Format_SC = "SC";
    public static final String Question_Format_SQ = "SQ";


    //Printer Variables
    public static int selectedPrinterSeries = -1;
    public static int selectedPrinterLang = -1;
    public static String selectedPrinterIpAddress = null;

    // Issue Ration Printer
    public static List<SchemeDetailsOnRationCardNumber> mSelectedCommodities = null;
    public static AllocationDetails mAllocationDetails = null;

    // Issue Ration and Aadhar Seeding
    public static RationCardHolderDetails mCardHolderDetails = null;
    public static RationLiftingMemberDetails mSelectedMember = null;

    // Commodity Receiving Pringer
    public static TruckChallanDetails mTruckChallanDetails = null;

    // Activity names
    public static final String ISSUERATION = "issueRation";
    public static final String AADHARSEEDING = "AadharSeeding";

    // RationCardLiftingMemberList parsing constants
}
