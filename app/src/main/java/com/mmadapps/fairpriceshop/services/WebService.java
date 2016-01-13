package com.mmadapps.fairpriceshop.services;

import android.util.Base64;
import android.util.Log;

import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.CommodityDetails;
import com.mmadapps.fairpriceshop.bean.MasterComplaints;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.bean.TruckChallanDetails;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Baskar on 12/21/2015.
 */
public class WebService {
    private static final String TAG = "WebService";

    public enum ServiceAPI {
        GET_RATION_LIFTING_MEMBERS_DETAILS, GET_AGENT_AUTHENTICATE, GET_AGENT_DETAILS, GET_AGENT_CONFIGURATIONS,
        GET_ALLOCATION, GET_RATION_CARD_HOLDER_DETAILS, GET_SCHEME_DETAILS_FOR_RATION_CARD, GET_AUTHENTICATION_DETAILS,
        GET_LANGUAGES, GET_MASTER_COMPLAINTS, GET_FEEDBACK_DETAILS, GET_FEEDBACK_ANSWER_DETAILS, GET_NW_DOWNTIME_REPORT,
        GET_ONLINE_SYNC_REPORT, GET_HARDWARE_COMPLAINT,GET_DAILY_SALES_REPORT, GET_MEMBER_DETAILS_REPORT, GET_STOCK_BALANCE_REPORT,
        GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO, GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE,GET_MENUS_BY_ROLE_ID,
        POST_COMMODITY_RECEIVE, POST_UIDAI_AUTHENTICATE, POST_CREATE_AADHAARSEED, POST_SALE_CREATE, POST_SAVE_COMPLAINT, POST_SAVE_FEEDBACK
    }

    public String callService(ServiceAPI service, String param) {
        String mResultString = null;
        String mUrl;
        switch (service) {
            case GET_RATION_LIFTING_MEMBERS_DETAILS:
                mUrl = UtilsClass.GET_RATION_LIFTING_MEMBERS_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_AGENT_AUTHENTICATE:
                String[] params = param.split("/");
                String password = params[1];
                String secretPass = UtilsClass.getShaIString(password);
                Log.e("SecretPassword",secretPass);
                if(secretPass.length()>39) {
                    param = "loginId="+params[0] + "&password=" + secretPass.substring(5,35);
                    Log.e("SecretPassword","Substring - "+secretPass.substring(5,35));
                    mUrl = UtilsClass.GET_AGENT_AUTHENTICATE + param;
                    mResultString = getData(mUrl);
                }
                break;
            case GET_AGENT_DETAILS:
                mUrl = UtilsClass.GET_AGENT_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_AGENT_CONFIGURATIONS:
                mUrl = UtilsClass.GET_AGENT_CONFIGURATIONS + param;
                mResultString = getData(mUrl);
                break;
            case GET_ALLOCATION:
                mUrl = UtilsClass.GET_ALLOCATION + param;
                mResultString = getData(mUrl);
                break;
            case GET_RATION_CARD_HOLDER_DETAILS:
                mUrl = UtilsClass.GET_RATION_CARD_HOLDER_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_SCHEME_DETAILS_FOR_RATION_CARD:
                mUrl = UtilsClass.GET_SCHEME_DETAILS_FOR_RATION_CARD + param;
                mResultString = getData(mUrl);
                break;
            case GET_AUTHENTICATION_DETAILS:
                mUrl = UtilsClass.GET_AUTHENTICATION_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_LANGUAGES:
                mUrl = UtilsClass.GET_LANGUAGES + param;
                mResultString = getData(mUrl);
                break;
            case GET_MASTER_COMPLAINTS:
                mUrl = UtilsClass.GET_MASTER_COMPLAINTS;
                mResultString = getData(mUrl);
                break;
            case GET_FEEDBACK_DETAILS:
                mUrl = UtilsClass.GET_FEEDBACK_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_FEEDBACK_ANSWER_DETAILS:
                mUrl = UtilsClass.GET_FEEDBACK_ANSWER_DETAILS + param;
                mResultString = getData(mUrl);
                break;
            case GET_NW_DOWNTIME_REPORT:
                mUrl = UtilsClass.GET_NW_DOWNTIME_REPORT + param;
                mResultString = getData(mUrl);
                break;
            case GET_ONLINE_SYNC_REPORT:
                mUrl = UtilsClass.GET_ONLINE_SYNC_REPORT + param;
                mResultString = getData(mUrl);
                break;
            case GET_HARDWARE_COMPLAINT:
                mUrl = UtilsClass.GET_HARDWARE_COMPLAINT + param;
                mResultString = getData(mUrl);
                break;
            case GET_DAILY_SALES_REPORT:
                mUrl = UtilsClass.GET_DAILY_SALES_REPORT + param;
                mResultString = getData(mUrl);
                break;
            case GET_MEMBER_DETAILS_REPORT:
                mUrl = UtilsClass.GET_MEMBER_DETAILS_REPORT + param;
                mResultString = getData(mUrl);
                break;
            case GET_STOCK_BALANCE_REPORT:
                mUrl = UtilsClass.GET_STOCK_BALANCE_REPORT + param;
                mResultString = getData(mUrl);
                break;
            case GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO:
                mUrl = UtilsClass.GET_TRUCK_CHALLAN_DETAIL_BY_CHALLAN_NO + param;
                mResultString = getData(mUrl);
                break;
            case GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE:
                mUrl = UtilsClass.GET_CAPTION_DETAILS_BY_PAGE_STATE_LANGUAGE + param;
                mResultString = getData(mUrl);
                break;
            case GET_MENUS_BY_ROLE_ID:
                mUrl = UtilsClass.GET_MENUS_BY_ROLE_ID + param;
                mResultString = getData(mUrl);
                break;
            case POST_UIDAI_AUTHENTICATE:
                mUrl = UtilsClass.POST_UIDAI_AUTHENTICATE;
                mResultString = postData(mUrl,param);
                break;
            case POST_COMMODITY_RECEIVE:
                mUrl = UtilsClass.POST_COMMODITY_RECEIVE;
                mResultString = postData(mUrl,param);
                break;
            case POST_CREATE_AADHAARSEED:
                mUrl = UtilsClass.POST_CREATE_AADHAARSEED;
                mResultString = postData(mUrl, param);
                break;
            case POST_SALE_CREATE:
                mUrl = UtilsClass.POST_SALE_CREATE;
                mResultString = postData(mUrl, param);
                break;
            case POST_SAVE_FEEDBACK:
                mUrl = UtilsClass.POST_SAVE_FEEDBACK;
                mResultString = postData(mUrl, param);
                break;
            case POST_SAVE_COMPLAINT:
                mUrl = UtilsClass.POST_SAVE_COMPLAINT;
                mResultString = postData(mUrl, param);
                break;
        }

        return mResultString;
    }

    private String postData(String mUrl, String param) {
        String mData = null;
        HttpURLConnection mURLConnection = null;
        try {
            URL url = new URL(mUrl);
            mURLConnection = (HttpURLConnection) url.openConnection();
            mURLConnection.setRequestMethod("POST");
            mURLConnection.setRequestProperty("Accept", "application/json");
            mURLConnection.setRequestProperty("Content-Type", "application/json");
            mURLConnection.setRequestProperty(UtilsClass.Autherization_Key, UtilsClass.Autherization_Value);
            PrintWriter writer = new PrintWriter(mURLConnection.getOutputStream());
            writer.write(param);
            writer.close();
            int statusCode = mURLConnection.getResponseCode();
            if (statusCode != 200 && statusCode != 201) {
                return null;
            }
            mData = convertToString(mURLConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mURLConnection != null)
                mURLConnection.disconnect();
        }
        Base64.encodeToString("".getBytes(), 0);
        if (mData != null && mData.length() > 0)
            Log.e(mUrl, mData);
        return mData;
    }

    private String getData(String mUrl) {
        String mData = null;
        HttpURLConnection mURLConnection = null;
        try {
            URL url = new URL(mUrl);
            mURLConnection = (HttpURLConnection) url.openConnection();
            mURLConnection.setRequestMethod("GET");
            mURLConnection.setRequestProperty("Accept", "application/json");
            mURLConnection.setRequestProperty("Content-Type", "application/json");
            mURLConnection.setRequestProperty(UtilsClass.Autherization_Key, UtilsClass.Autherization_Value);
            int statusCode = mURLConnection.getResponseCode();
            if (statusCode != 200 && statusCode != 201) {
                return null;
            }
            mData = convertToString(mURLConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mURLConnection != null)
                mURLConnection.disconnect();
        }
        if (mData != null && mData.length() > 0)
            Log.e(mUrl, mData);
        return mData;
    }

    private String convertToString(InputStream in) {
        String mData = null;
        try {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                if (mData == null) {
                    mData = line;
                } else {
                    mData += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mData;
    }

    public String CreateJsonForCreateAadharSeeding(String agentId,RationCardHolderDetails mCardHolderDetails, RationLiftingMemberDetails mMemberDetails,String validationType) {
        String json = null;
        try {
            JSONObject jObject = new JSONObject();
            jObject.put("RationCardNo", mCardHolderDetails.getmCardNumber());
            jObject.put("MemberId", mMemberDetails.getmEmailId());
            jObject.put("State", mCardHolderDetails.getmState());
            jObject.put("UidaiNo", mMemberDetails.getmUIDAINo());
            jObject.put("Mode", "AB");
            jObject.put("Status", mMemberDetails.getmSeedingStatus());
            jObject.put("Type", validationType);
            jObject.put("Remarks", "Testing");
            jObject.put("CreatedBy", agentId);
            json = jObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("JsonForCreateAadrSdg", "" + json);
        return json;
    }

    public String CreateJsonForSaveComplaint(String mAgentId,MasterComplaints mMasterComplaints) {
        String json = null;
        try {
            JSONObject jObject = new JSONObject();
            jObject.put("TitleId", mMasterComplaints.getmTitleId());
            jObject.put("Subject", mMasterComplaints.getmTitleName());
            jObject.put("Description", mMasterComplaints.getmDescription());
            jObject.put("Status", "A");
            jObject.put("IPAddress", "127.0.0.1");
            jObject.put("CreatedBy", mAgentId);
            json = jObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("JsonForSaveComplaint", "" + json);
        return json;
    }

    public String CreateJsonForSalesCreate(double mTotalPrice, AgentDetails agentDetails, AllocationDetails mAllocation, RationLiftingMemberDetails memberDetails, List<SchemeDetailsOnRationCardNumber> purchasedCommoditiesList) {
        String json = null;
        try {
            if (agentDetails == null) {
                Log.e(TAG,"Agent Details is null");
                return json;
            } else {
                String mCreatedBy = agentDetails.getmUserId();
                String mAllocationOrderNo = mAllocation.getmAllocationOrderNo();
                String mStateId = agentDetails.getmState();
                String mFPSCode = agentDetails.getmFpsCode();

                String mLiftingMemberId = memberDetails.getmMemberId();

                String mSalesDate = UtilsClass.salesDateFormat.format(new Date());
                String mRationCardNumber = purchasedCommoditiesList.get(0).getmRationCardNo();
                JSONObject jObject = new JSONObject();
                jObject.put("RationCardNo", mRationCardNumber);
                jObject.put("LiftingMemberId", mLiftingMemberId);
                jObject.put("AllocationOrderNo", mAllocationOrderNo);
                jObject.put("AuthenticationTypeId", "00");
                jObject.put("SaleDate", mSalesDate);
                jObject.put("CreatedBy", mCreatedBy);
                jObject.put("State", "27");
                jObject.put("FpsCode", mFPSCode);
                jObject.put("AuthenticationStatus", UtilsClass.WITHOUT_AUTHENTICATION);
                jObject.put("TotalAmount", mTotalPrice);

                JSONArray purchasedCommoditiesArray = new JSONArray();
                for (SchemeDetailsOnRationCardNumber commodity : purchasedCommoditiesList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Id", 1);
                    jsonObject.put("State", "27");
                    jsonObject.put("AllocationOrderNo", mAllocationOrderNo);
                    jsonObject.put("FPSCode", mFPSCode);
                    jsonObject.put("SaleDate", mSalesDate);
                    jsonObject.put("RationCardNo", commodity.getmRationCardNo());
                    jsonObject.put("SchemeCode", commodity.getmSchemeCode());
                    jsonObject.put("SchemeName", commodity.getmSchemeName());
                    jsonObject.put("CommodityCode", commodity.getmCommodityCode());
                    jsonObject.put("CommodityName", commodity.getmCommodityName());
                    jsonObject.put("EntitlementQty", commodity.getmEntitlementQty());
                    jsonObject.put("LiftedQty", commodity.getmLiftedQty());
                    jsonObject.put("BalancedQty", commodity.getmBalancedQty());
                    jsonObject.put("AllotedQty", commodity.getmAllotedQty());
                    jsonObject.put("Rates", commodity.getmRates());
                    jsonObject.put("RemainingQty", commodity.getmRemainingQty());
                    jsonObject.put("Amount", commodity.getmAmount());
                    jsonObject.put("RCUnits", commodity.getmRcUnits());
                    jsonObject.put("Status", "A");
                    purchasedCommoditiesArray.put(jsonObject);
                }
                jObject.put("PurchasedCommodities", purchasedCommoditiesArray);
                json = jObject.toString();
                Log.e("JsonForSalesCreate", "" + json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public String CreateJsonForSaveCommodityReceiced(AgentDetails mAgentDetails, TruckChallanDetails mTruckChallanDetails){
        String json = null;
        try{
            List<CommodityDetails> mCommodityList = mTruckChallanDetails.getmTruckChalanDetails();
            JSONObject jObject = new JSONObject();

            /*DfsoCode is First four char of FpsCode.
              AfsoCode is  5 to 7 char of FpsCode.
              District is 2 to 4 char of FpsCode. */

            String mFpsCode = mAgentDetails.getmFpsCode();
            String mDfsoCode = mFpsCode.substring(0,4);
            String mAfsoCode = mFpsCode.substring(4,7);
            String mDistrict = mFpsCode.substring(1,4);

            Random random = new Random(30);
            int i = 1;
            jObject.put("TruckChalanNo",random.nextInt());
            jObject.put("FpsCode",mFpsCode);
            jObject.put("DfsoCode",mDfsoCode);
            jObject.put("AfsoCode",mAfsoCode);
            jObject.put("State",mAgentDetails.getmState());
            jObject.put("District",mDistrict);
            jObject.put("CommodityReceivedDate",UtilsClass.commodityDateFormat.format(new Date()));
            jObject.put("CreatedBy",mAgentDetails.getmUserId());
            jObject.put("CreatedByUserDetailId", mAgentDetails.getmUserDetailId());
            JSONArray commodityArray = new JSONArray();
            for(CommodityDetails cd:mCommodityList){
               /* if(cd.getmCommodityReceived() == null || cd.getmCommodityReceived().length() == 0){
                    Log.e("Json for cmdt rec","no cmdt recvd");
                }else {*/
                JSONObject commodity = new JSONObject();
                commodity.put("Id", i); //cd.getmId());
                commodity.put("CommodityCode", cd.getmCommodityCode());
                commodity.put("SchemeCode", cd.getmSchemeCode());
                commodity.put("QuanityReceived", cd.getmCommodityReceived()); // entered
                /*commodity.put("SchemeName",cd.getmSchemeName());
                commodity.put("CommodityName",cd.getmCommodityName());
                commodity.put("CommodityQty",cd.getmCommodityQty());
                commodity.put("CommodityRate",cd.getmCommodityRate());
                commodity.put("CommodityCost",cd.getmCommodityCost());
                commodity.put("AllotedQty",cd.getmAllotedQty());
                commodity.put("ReceivedQty",cd);
                commodity.put("LossGain",cd);
                commodity.put("BlalanceQuantity",cd);
                commodity.put("QtyRcvdInTruck",cd);*/
                commodityArray.put(commodity);
                i++;
                //}
            }
            jObject.put("CommodityReceiveDetails",commodityArray);
            json = jObject.toString();
            Log.e("JsonForCommodity",json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }

    public String CreateJsonForFeedBackDetails(AgentDetails mAgentDetails, Map<String, String> mAnswersMap){
        /*{
            "StateCode": "02",
                "FpsCode": "106001100001",
                "DfsoCode": "1060",
                "AfsoCode":"002",
                "InspectorCode":"002",
                "CreatedBy":"050000000000012",

                "FPSFeedbackDetails": [
            {
                "FPSFeedbackDetailId": 1,
                    "FPSFeedbackStateID": "02000001",
                    "FPSFeedbackDetails": "1",
                    "Remarks": "Insert"
            }
            ]
        }*/

        String mFpsCode = mAgentDetails.getmFpsCode();
        String mDfsoCode = mFpsCode.substring(0,4);
        String mAfsoCode = mFpsCode.substring(4,7);
        String mDistrict = mFpsCode.substring(1,4);
        String json = null;
        try{
            JSONObject jObject = new JSONObject();
            jObject.put("StateCode",mAgentDetails.getmState());
            jObject.put("FpsCode",mAgentDetails.getmFpsCode());
            jObject.put("DfsoCode",mDfsoCode);
            jObject.put("AfsoCode",mAfsoCode);
            jObject.put("InspectorCode","002");
            jObject.put("CreatedBy",mAgentDetails.getmUserDetailId());
            JSONArray feedBackArray = new JSONArray();
            Set<String> keys = mAnswersMap.keySet();
            for (String key:keys){
                JSONObject feedback = new JSONObject();
                feedback.put("FPSFeedbackDetailId","1");
                feedback.put("FPSFeedbackStateID",key);
                feedback.put("FPSFeedbackDetails", mAnswersMap.get(key));
                feedback.put("Remarks","Insert");
                feedBackArray.put(feedback);
            }
            jObject.put("FPSFeedbackDetails",feedBackArray);
            json = jObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }

    public String CreateJsonForUidAuthentication(String rawData,String aadharNo, String stateId, boolean isAgent){
        String json = null;
        try{
            JSONObject jObject = new JSONObject();
            jObject.put("AadhaarNo",aadharNo);
            jObject.put("Biometric",rawData);
            if(isAgent){
                jObject.put("UserType", UtilsClass.UERTYPE_FPSAGENT);
            }else{
                jObject.put("UserType", UtilsClass.UERTYPE_BUYER);
            }
            jObject.put("StateId",stateId);
            json = jObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}
