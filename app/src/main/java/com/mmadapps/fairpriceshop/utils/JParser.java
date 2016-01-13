package com.mmadapps.fairpriceshop.utils;

import android.util.Log;

import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.CommodityDetails;
import com.mmadapps.fairpriceshop.bean.FeedBackAnswerDetails;
import com.mmadapps.fairpriceshop.bean.FeedBackDetails;
import com.mmadapps.fairpriceshop.bean.HardwareComplaintReport;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;
import com.mmadapps.fairpriceshop.bean.LoginDetails;
import com.mmadapps.fairpriceshop.bean.MasterComplaints;
import com.mmadapps.fairpriceshop.bean.MenuDetails;
import com.mmadapps.fairpriceshop.bean.NetworkDownTimeReport;
import com.mmadapps.fairpriceshop.bean.RationCardHolderDetails;
import com.mmadapps.fairpriceshop.bean.RationCardMemberDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SalesReport;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.bean.StockReport;
import com.mmadapps.fairpriceshop.bean.TruckChallanDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Baskar on 12/21/2015.
 */
public class JParser {

    private String getObjectvalue(JSONObject listObj, String keyOfObject) {
        try {
            try {
                String mKeyValue = listObj.getString(keyOfObject).toString();
                if (mKeyValue == null || mKeyValue.length() == 0 || mKeyValue.equals("null")) {
                } else {
                    return mKeyValue;
                }
            } catch (NullPointerException nullExp) {
            }
        } catch (JSONException e) {
        }
        return "";
    }

    /*-----------------------------------GetRation Lifting Member Details------------------------------------------------------------*/

    public List<RationLiftingMemberDetails> parseRationLiftingMembersDetails(String jResult) {

        List<RationLiftingMemberDetails> rationLiftingMemberDetailses = null;
        try {
            JSONObject jsonObject = new JSONObject(jResult);
            JSONArray jResponseObject = jsonObject.getJSONArray("ResponseObject");
            if (jResponseObject == null || jResponseObject.length() == 0) {
            } else {
                String isError = getObjectvalue(jsonObject, "IsError");
                if (isError.equalsIgnoreCase("false")) {
                    rationLiftingMemberDetailses = new ArrayList<>();
                    int len = jResponseObject.length();
                    for (int i = 0; i < len; i++) {
                        RationLiftingMemberDetails rationLiftingMemberDetails = new RationLiftingMemberDetails();
                        JSONObject memberDetails = jResponseObject.getJSONObject(i);
                        rationLiftingMemberDetails.setmMemberId(getObjectvalue(memberDetails, "MemberId"));
                        rationLiftingMemberDetails.setmMember_Name_EN(getObjectvalue(memberDetails, "Member_Name_EN"));
                        rationLiftingMemberDetails.setmMember_Name_LL(getObjectvalue(memberDetails, "Member_Name_LL"));
                        rationLiftingMemberDetails.setmMother_Name_EN(getObjectvalue(memberDetails, "Mother_Name_EN"));
                        rationLiftingMemberDetails.setmMother_Name_LL(getObjectvalue(memberDetails, "Mother_Name_LL"));
                        rationLiftingMemberDetails.setmFather_Name_EN(getObjectvalue(memberDetails, "Father_Name_EN"));
                        rationLiftingMemberDetails.setmFather_Name_LL(getObjectvalue(memberDetails, "Father_Name_LL"));
                        rationLiftingMemberDetails.setmSpouse_Name_EN(getObjectvalue(memberDetails, "Spouse_Name_EN"));
                        rationLiftingMemberDetails.setmSpouse_Name_LL(getObjectvalue(memberDetails, "Spouse_Name_LL"));
                        rationLiftingMemberDetails.setmMaritalStatusCode(getObjectvalue(memberDetails, "MaritalStatusCode"));
                        rationLiftingMemberDetails.setmMaritalStatusName(getObjectvalue(memberDetails, "MaritalStatusName"));
                        rationLiftingMemberDetails.setmActualOrDeclaredDOB(getObjectvalue(memberDetails, "ActualOrDeclaredDOB"));
                        rationLiftingMemberDetails.setmDateOfBirth(getObjectvalue(memberDetails, "DateOfBirth"));
                        rationLiftingMemberDetails.setmAgeInYears(getObjectvalue(memberDetails, "AgeInYears"));
                        rationLiftingMemberDetails.setmGender(getObjectvalue(memberDetails, "Gender"));
                        rationLiftingMemberDetails.setmGenderName(getObjectvalue(memberDetails, "GenderName"));
                        rationLiftingMemberDetails.setmNationality(getObjectvalue(memberDetails, "Nationality"));
                        rationLiftingMemberDetails.setmNationalityName(getObjectvalue(memberDetails, "NationalityName"));
                        rationLiftingMemberDetails.setmMobileNo(getObjectvalue(memberDetails, "MobileNo"));
                        rationLiftingMemberDetails.setmPhoneNo(getObjectvalue(memberDetails, "PhoneNo"));
                        rationLiftingMemberDetails.setmEmailId(getObjectvalue(memberDetails, "EmailId"));
                        rationLiftingMemberDetails.setmEpicNo(getObjectvalue(memberDetails, "EpicNo"));
                        rationLiftingMemberDetails.setmNPRNo(getObjectvalue(memberDetails, "NPRNo"));
                        rationLiftingMemberDetails.setmOccupationCode(getObjectvalue(memberDetails, "OccupationCode"));
                        rationLiftingMemberDetails.setmOccupation(getObjectvalue(memberDetails, "Occupation"));
                        rationLiftingMemberDetails.setmMonthlyIncome(getObjectvalue(memberDetails, "MonthlyIncome"));
                        rationLiftingMemberDetails.setmOptingToLiftCommodity(getObjectvalue(memberDetails, "OptingToLiftCommodity"));
                        rationLiftingMemberDetails.setmRSCode(getObjectvalue(memberDetails, "RSCode"));
                        rationLiftingMemberDetails.setmRelation(getObjectvalue(memberDetails, "Relation"));
                        rationLiftingMemberDetails.setmCCatCode(getObjectvalue(memberDetails, "CCatCode"));
                        rationLiftingMemberDetails.setmCastCategory(getObjectvalue(memberDetails, "CastCategory"));
                        rationLiftingMemberDetails.setmIsPhysicallyChallenged(getObjectvalue(memberDetails, "IsPhysicallyChallenged"));
                        rationLiftingMemberDetails.setmPhysicallyChallenged_Name_EN(getObjectvalue(memberDetails, "PhysicallyChallenged_Name_EN"));
                        rationLiftingMemberDetails.setmPhysicallyChallengedCode(getObjectvalue(memberDetails, "PhysicallyChallengedCode"));
                        rationLiftingMemberDetails.setmPhysicallyChallengedPercentage(getObjectvalue(memberDetails, "PhysicallyChallengedPercentage"));
                        String status = getObjectvalue(memberDetails, "Status");
                        if (status.equalsIgnoreCase("A") && i < len - 3) {
                            rationLiftingMemberDetails.setmStatus("Active");
                        } else {
                            rationLiftingMemberDetails.setmStatus("Pending");
                        }
                        rationLiftingMemberDetails.setmBankACNo(getObjectvalue(memberDetails, "BankACNo"));
                        rationLiftingMemberDetails.setmBankCode(getObjectvalue(memberDetails, "BankCode"));
                        rationLiftingMemberDetails.setmBankName(getObjectvalue(memberDetails, "BankName"));
                        rationLiftingMemberDetails.setmBranchCode(getObjectvalue(memberDetails, "BranchCode"));
                        rationLiftingMemberDetails.setmBranchName(getObjectvalue(memberDetails, "BranchName"));
                        rationLiftingMemberDetails.setmIFSCCode(getObjectvalue(memberDetails, "IFSCCode"));
                        rationLiftingMemberDetails.setmBaseSixtyFourPhoto(getObjectvalue(memberDetails, "Base64Photo"));
                        rationLiftingMemberDetails.setmDocumentId(getObjectvalue(memberDetails, "DocumentId"));
                        rationLiftingMemberDetails.setmIsApplicant(getObjectvalue(memberDetails, "IsApplicant"));
                        rationLiftingMemberDetails.setmIsHOF(getObjectvalue(memberDetails, "IsHOF"));
                        rationLiftingMemberDetails.setmIsNFSA(getObjectvalue(memberDetails, "IsNFSA"));
                        rationLiftingMemberDetails.setmUIDAIStatus(getObjectvalue(memberDetails, "UIDAIStatus"));
                        rationLiftingMemberDetails.setmUIDAIEnrolmentNo(getObjectvalue(memberDetails, "UIDAIEnrolmentNo"));
                        rationLiftingMemberDetails.setmUIDAINo(getObjectvalue(memberDetails, "UIDAINo"));
                        rationLiftingMemberDetails.setmSeedingStatus(getObjectvalue(memberDetails, "SeedingStatus"));
                        rationLiftingMemberDetails.setmSeedingStatusName(getObjectvalue(memberDetails, "SeedingStatusName"));
                        rationLiftingMemberDetails.setmRemarks(getObjectvalue(memberDetails, "Remarks"));
                        rationLiftingMemberDetails.setmRejectionCounter(getObjectvalue(memberDetails, "RejectionCounter"));
                        rationLiftingMemberDetailses.add(rationLiftingMemberDetails);
                    }
                } else {
                    String error = getObjectvalue(jsonObject, "ExceptionObject");
                    Log.e("JParser RtnLftngMbrDtls", error);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rationLiftingMemberDetailses;
    }


    //--------------------Login Authentiction parsing---------------------------------------------------


    public LoginDetails parsingLoginAuthentications(String jResult) {
        LoginDetails loginDetails = null;
        try {
            JSONObject loginObject = new JSONObject(jResult);
            String IsError = getObjectvalue(loginObject, "IsError");
            if (IsError.equalsIgnoreCase("false")) {
                JSONObject loginResponseObject = loginObject.getJSONObject("ResponseObject");
                if (loginResponseObject == null || loginResponseObject.length() == 0) {
                } else {
                    loginDetails = new LoginDetails();
                    loginDetails.setmStatus(getObjectvalue(loginResponseObject, "Status"));
                    loginDetails.setmRemarks(getObjectvalue(loginResponseObject, "Remarks"));
                    loginDetails.setmIsOtpRequired(getObjectvalue(loginResponseObject, "IsOtpRequired"));
                    loginDetails.setmOtpValidtityDuration(getObjectvalue(loginResponseObject, "OtpValidtityDuration"));
                }
            } else {

            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return loginDetails;
    }


    //----------------------------------Agent Details Parsing-------------------------------------------------------------


    public AgentDetails parsingAgentDetails(String jResult) {
        AgentDetails agentDetails = null;
        try {
            JSONObject agentObject = new JSONObject(jResult);
            JSONObject agentResponseObject = agentObject.getJSONObject("ResponseObject");
            if (agentResponseObject == null || agentResponseObject.length() == 0) {
            } else {
                String IsError = getObjectvalue(agentObject, "IsError");
                if (IsError.equalsIgnoreCase("false")) {
                    agentDetails = new AgentDetails();
                    agentDetails.setmUserDetailId(getObjectvalue(agentResponseObject, "UserDetailId"));
                    agentDetails.setmUserDetails(getObjectvalue(agentResponseObject, "UserDetails"));
                    parseXMLData(agentDetails);
                    agentDetails.setmUserEmail(getObjectvalue(agentResponseObject, "UserEmail"));
                    agentDetails.setmFirstName(getObjectvalue(agentResponseObject, "FirstName"));
                    agentDetails.setmLastName(getObjectvalue(agentResponseObject, "LastName"));
                    agentDetails.setmUserMobileNo(getObjectvalue(agentResponseObject, "UserMobileNo"));
                    agentDetails.setmUserName(getObjectvalue(agentResponseObject, "UserName"));
                    agentDetails.setmRoleName(getObjectvalue(agentResponseObject, "RoleName"));
                    agentDetails.setmUserId(getObjectvalue(agentResponseObject, "UserId"));
                    agentDetails.setmRoleId(getObjectvalue(agentResponseObject, "RoleId"));
                    agentDetails.setmOfficeId(getObjectvalue(agentResponseObject, "OfficeId"));
                    agentDetails.setmOfficeTag(getObjectvalue(agentResponseObject, "OfficeTag"));
                    agentDetails.setmRoleDesc(getObjectvalue(agentResponseObject, "RoleDesc"));
                    agentDetails.setmCanChangeData(getObjectvalue(agentResponseObject, "CanChangeData"));
                    agentDetails.setmIsSysAdmin(getObjectvalue(agentResponseObject, "IsSysAdmin"));
                    agentDetails.setmPageId(getObjectvalue(agentResponseObject, "PageId"));
                    agentDetails.setmPageUrl(getObjectvalue(agentResponseObject, "PageUrl"));
                    agentDetails.setmBase64ImageData(getObjectvalue(agentResponseObject, "Base64ImageData"));
                    agentDetails.setmHasManyRoles(getObjectvalue(agentResponseObject, "HasManyRoles"));
                } else {
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return agentDetails;
    }

    private void parseXMLData(AgentDetails agentDetails) {
        /*<Attributes>
            <State>02</State>
            <StateName>HIMACHAL PRADESH</StateName>
            <District>033</District>
            <DistrictName>Shimla</DistrictName>
            <Tahsil>00182</Tahsil>
            <TahsilName>Shimla (urban)</TahsilName>
            <PLC>800137</PLC>
            <PLCName>Shimla (M Corp.)</PLCName>
            <FPSCode>103301100002</FPSCode>
            <FPSName>CAS Super Bazar B. R  Nabha</FPSName>
        </Attributes>;*/

        Document document = getDomElement(agentDetails.getmUserDetails());
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("State")) {
                agentDetails.setmState(node.getTextContent());
            } else if (node.getNodeName().equals("StateName")) {
                agentDetails.setmStateName(node.getTextContent());
            } else if (node.getNodeName().equals("District")) {
                agentDetails.setmDistrict(node.getTextContent());
            } else if (node.getNodeName().equals("DistrictName")) {
                agentDetails.setmDistrictName(node.getTextContent());
            } else if (node.getNodeName().equals("Tahsil")) {
                agentDetails.setmTahsil(node.getTextContent());
            } else if (node.getNodeName().equals("TahsilName")) {
                agentDetails.setmTahsilName(node.getTextContent());
            } else if (node.getNodeName().equals("PLC")) {
                agentDetails.setmPLC(node.getTextContent());
            } else if (node.getNodeName().equals("PLCName")) {
                agentDetails.setmPLCName(node.getTextContent());
            } else if (node.getNodeName().equals("FPSCode")) {
                agentDetails.setmFpsCode(node.getTextContent());
            } else if (node.getNodeName().equals("FPSName")) {
                agentDetails.setmFpsName(node.getTextContent());
            }
        }
    }

    private Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    //----------------------------------Allocation Details parsing-----------------------------------------------


    public List<AllocationDetails> parsingAllocationDetails(String jResult) {
        List<AllocationDetails> allocationDetailsList = null;
        try {
            JSONObject allocationObject = new JSONObject(jResult);
            String IsError = getObjectvalue(allocationObject, "IsError");
            if (IsError.equalsIgnoreCase("false")) {
                JSONArray allocationArray = allocationObject.getJSONArray("ResponseObject");
                if (allocationArray == null || allocationArray.length() == 0) {
                } else {
                    allocationDetailsList = new ArrayList<AllocationDetails>();
                    for (int i = 0; i < allocationArray.length(); i++) {
                        JSONObject allocationArrayObject = allocationArray.getJSONObject(i);
                        AllocationDetails allocationDetails = new AllocationDetails();
                        allocationDetails.setmFpsSaleStatusId(getObjectvalue(allocationArrayObject, "FpsSaleStatusId"));
                        allocationDetails.setmAllocationOrderNo(getObjectvalue(allocationArrayObject, "AllocationOrderNo"));
                        allocationDetails.setmAllocationDetails(getObjectvalue(allocationArrayObject, "AllocationDetails"));
                        allocationDetails.setmFpsCode(getObjectvalue(allocationArrayObject, "FpsCode"));
                        allocationDetails.setmAllocationMonth(getObjectvalue(allocationArrayObject, "AllocationMonth"));
                        allocationDetails.setmFromDate(getObjectvalue(allocationArrayObject, "FromDate"));
                        allocationDetails.setmToDate(getObjectvalue(allocationArrayObject, "ToDate"));
                        allocationDetails.setmSaleStatus(getObjectvalue(allocationArrayObject, "SaleStatus"));
                        allocationDetails.setmStatus(getObjectvalue(allocationArrayObject, "Status"));
                        allocationDetailsList.add(allocationDetails);
                    }
                }
            } else {
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return allocationDetailsList;
    }


//------------------------------------RationCard Holder Details Parsing-----------------------------------------------------


    public RationCardHolderDetails parsingRationcardholderDetails(String jResult) {
        RationCardHolderDetails holderDetails = null;
        try {
            JSONObject holderObject = new JSONObject(jResult);
            JSONObject holderResponseObject = holderObject.getJSONObject("ResponseObject");
            if (holderResponseObject == null || holderResponseObject.length() == 0) {
            } else {
                String IsError = getObjectvalue(holderObject, "IsError");
                if (IsError.equalsIgnoreCase("false")) {
                    holderDetails = new RationCardHolderDetails();
                    holderDetails.setmCardNumber(getObjectvalue(holderResponseObject, "CardNumber"));
                    holderDetails.setmSchemeCode(getObjectvalue(holderResponseObject, "SchemeCode"));
                    holderDetails.setmSchemeName(getObjectvalue(holderResponseObject, "SchemeName"));
                    holderDetails.setmMemberId(getObjectvalue(holderResponseObject, "MemberId"));
                    holderDetails.setmMemberName(getObjectvalue(holderResponseObject, "MemberName"));
                    holderDetails.setmAddress(getObjectvalue(holderResponseObject, "Address"));
                    holderDetails.setmIsHof(getObjectvalue(holderResponseObject, "IsHof"));
                    holderDetails.setmPresentHouseNo(getObjectvalue(holderResponseObject, "PresentHouseNo"));
                    holderDetails.setmPresentLandmarkLocalityColony(getObjectvalue(holderResponseObject, "PresentLandmarkLocalityColony"));
                    holderDetails.setmPresentPin(getObjectvalue(holderResponseObject, "PresentPin"));
                    holderDetails.setmPresentPlcCode(getObjectvalue(holderResponseObject, "PresentPlcCode"));
                    holderDetails.setmState(getObjectvalue(holderResponseObject, "State"));
                    holderDetails.setmDistrict(getObjectvalue(holderResponseObject, "District"));
                    holderDetails.setmTahsil(getObjectvalue(holderResponseObject, "Tahsil"));
                    holderDetails.setmTownVill(getObjectvalue(holderResponseObject, "TownVill"));
                    holderDetails.setmStateName(getObjectvalue(holderResponseObject, "StateName"));
                    holderDetails.setmDistrictName(getObjectvalue(holderResponseObject, "DistrictName"));
                    holderDetails.setmTahsilName(getObjectvalue(holderResponseObject, "TahsilName"));
                    holderDetails.setmVillageName(getObjectvalue(holderResponseObject, "VillageName"));
                    holderDetails.setmGender(getObjectvalue(holderResponseObject, "Gender"));
                    holderDetails.setmGenderName(getObjectvalue(holderResponseObject, "GenderName"));
                    holderDetails.setmApprovedWaiverCounter(getObjectvalue(holderResponseObject, "ApprovedWaiverCounter"));
                    holderDetails.setmWaiverCountsUtilised(getObjectvalue(holderResponseObject, "WaiverCountsUtilised"));
                    holderDetails.setmBalancedWaiverCounts(getObjectvalue(holderResponseObject, "BalancedWaiverCounts"));
                } else {
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return holderDetails;
    }


    //------------------------------------Scheme Details on Ration-Card Number parsing-------------------------


    public List<SchemeDetailsOnRationCardNumber> parsingScemeDetailsOnRationCardNumber(String jResult) {

        List<SchemeDetailsOnRationCardNumber> schemeDetailsOnRationCardNumbersList = null;
        try {
            JSONObject schemeObject = new JSONObject(jResult);
            JSONArray schemeDetailsArray = schemeObject.getJSONArray("ResponseObject");
            if (schemeDetailsArray == null || schemeDetailsArray.length() == 0) {
            } else {
                String IsError = getObjectvalue(schemeObject, "IsError");
                if (IsError.equalsIgnoreCase("false")) {
                    schemeDetailsOnRationCardNumbersList = new ArrayList<SchemeDetailsOnRationCardNumber>();
                    for (int i = 0; i < schemeDetailsArray.length(); i++) {
                        JSONObject schemeDetails = schemeDetailsArray.getJSONObject(i);
                        SchemeDetailsOnRationCardNumber schemeDetailsOnRationCardNumber = new SchemeDetailsOnRationCardNumber();
                        schemeDetailsOnRationCardNumber.setmRationCardNo(getObjectvalue(schemeDetails, "RationCardNo"));
                        schemeDetailsOnRationCardNumber.setmSchemeCode(getObjectvalue(schemeDetails, "SchemeCode"));
                        schemeDetailsOnRationCardNumber.setmSchemeName(getObjectvalue(schemeDetails, "SchemeName"));
                        schemeDetailsOnRationCardNumber.setmCommodityCode(getObjectvalue(schemeDetails, "CommodityCode"));
                        schemeDetailsOnRationCardNumber.setmCommodityName(getObjectvalue(schemeDetails, "CommodityName"));
                        schemeDetailsOnRationCardNumber.setmEntitlementQty(getObjectvalue(schemeDetails, "EntitlementQty"));
                        schemeDetailsOnRationCardNumber.setmLiftedQty(getObjectvalue(schemeDetails, "LiftedQty"));
                        schemeDetailsOnRationCardNumber.setmBalancedQty(getObjectvalue(schemeDetails, "BalancedQty"));
                        schemeDetailsOnRationCardNumber.setmAllotedQty(getObjectvalue(schemeDetails, "AllotedQty"));
                        schemeDetailsOnRationCardNumber.setmRemainingQty(getObjectvalue(schemeDetails, "RemainingQty"));
                        schemeDetailsOnRationCardNumber.setmRates(getObjectvalue(schemeDetails, "Rates"));
                        schemeDetailsOnRationCardNumber.setmAmount(getObjectvalue(schemeDetails, "Amount"));
                        schemeDetailsOnRationCardNumber.setmRcUnits(getObjectvalue(schemeDetails, "RcUnits"));
                        schemeDetailsOnRationCardNumbersList.add(schemeDetailsOnRationCardNumber);
                    }
                } else {
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return schemeDetailsOnRationCardNumbersList;
    }

    //-------------------------------------getLanguage Details parsing------------------------------------------------

    public List<LanguageDetails> parsingLanguageDetails(String jResult) {

        List<LanguageDetails> languageDetailsList = null;
        try {
            JSONObject languageObject = new JSONObject(jResult);
            String IsError = getObjectvalue(languageObject, "IsError");
            if (IsError.equalsIgnoreCase("false")) {
            JSONArray languageArrayResponseObject = languageObject.getJSONArray("ResponseObject");
            if (languageArrayResponseObject == null || languageArrayResponseObject.length() == 0) {
            } else {
                    languageDetailsList = new ArrayList<>();
                    for (int i = 0; i < languageArrayResponseObject.length(); i++) {
                        JSONObject languageArrayObject = languageArrayResponseObject.getJSONObject(i);
                        LanguageDetails languageDetails = new LanguageDetails();
                        languageDetails.setmId(getObjectvalue(languageArrayObject, "Id"));
                        languageDetails.setmName(getObjectvalue(languageArrayObject, "Name"));
                        languageDetails.setmNameL1(getObjectvalue(languageArrayObject, "NameLl"));
                        languageDetails.setmCode(getObjectvalue(languageArrayObject, "Code"));
                        languageDetails.setmStateLanguageId(getObjectvalue(languageArrayObject, "StateLanguageId"));
                        languageDetails.setmIsDefault(getObjectvalue(languageArrayObject, "IsDefault"));
                        languageDetailsList.add(languageDetails);
                    }
                }
            } else {
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return languageDetailsList;
    }

    //-----------------------------------MasterComplaints Details----------------------------------------------

    public List<MasterComplaints> parsingMasterComplaints(String jResult) {

        List<MasterComplaints> masterComplaintsList = null;
        try {
            JSONObject masterObject = new JSONObject(jResult);
            JSONArray masterArray = masterObject.getJSONArray("ResponseObject");
            if (masterArray == null || masterArray.length() == 0) {
            } else {
                String IsError = getObjectvalue(masterObject, "IsError");
                if (IsError.equalsIgnoreCase("false")) {
                    masterComplaintsList = new ArrayList<MasterComplaints>();
                    for (int i = 0; i < masterArray.length(); i++) {
                        JSONObject masterArrayObject = masterArray.getJSONObject(i);
                        MasterComplaints masterComplaints = new MasterComplaints();
                        masterComplaints.setmTitleId(getObjectvalue(masterArrayObject, "TitleId"));
                        masterComplaints.setmTitleName(getObjectvalue(masterArrayObject, "TitleName"));
                        masterComplaintsList.add(masterComplaints);
                    }
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return masterComplaintsList;
    }


    //---------------------------------FeedBack Details----------------------------------------------------------


    public List<FeedBackDetails> parsingFeedbackdetails(String jResult) {

        List<FeedBackDetails> feedBackDetailsList = null;
        try {
            JSONObject feedbackObject = new JSONObject(jResult);
            String IsError = getObjectvalue(feedbackObject, "IsError");
            if (IsError.equalsIgnoreCase("false")) {
                JSONArray feedbackArray = feedbackObject.getJSONArray("ResponseObject");
                if (feedbackArray == null || feedbackArray.length() == 0) {
                } else {
                    feedBackDetailsList = new ArrayList<>();
                    for (int i = 0; i < feedbackArray.length(); i++) {
                        JSONObject feedbackArrayObject = feedbackArray.getJSONObject(i);
                        FeedBackDetails feedBackDetails = new FeedBackDetails();
                        feedBackDetails.setmId(getObjectvalue(feedbackArrayObject, "Id"));
                        feedBackDetails.setmQuestion(getObjectvalue(feedbackArrayObject, "Question"));
                        feedBackDetails.setmAnswerFormat(getObjectvalue(feedbackArrayObject, "AnswerFormat"));
                        feedBackDetails.setmAnswerFormatName(getObjectvalue(feedbackArrayObject, "AnswerFormatName"));
                        feedBackDetailsList.add(feedBackDetails);
                    }
                }
            } else {
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return feedBackDetailsList;
    }

    //-----------------------------------FeedBack Answer Details----------------------------------------------------------

    public List<FeedBackAnswerDetails> parsingAnswerFormatDetails(String jResult) {

        List<FeedBackAnswerDetails> feedbackanswerDetailsList = null;
        try {
            JSONObject answerObject = new JSONObject(jResult);
            String IsError = getObjectvalue(answerObject, "IsError");
            if (IsError.equalsIgnoreCase("false")) {
                JSONArray answerArray = answerObject.getJSONArray("ResponseObject");
                if (answerArray == null || answerArray.length() == 0) {
                } else {
                    feedbackanswerDetailsList = new ArrayList<>();
                    for (int i = 0; i < answerArray.length(); i++) {
                        JSONObject answerArrayObject = answerArray.getJSONObject(i);
                        FeedBackAnswerDetails feedBackAnswerDetails = new FeedBackAnswerDetails();
                        feedBackAnswerDetails.setmId(getObjectvalue(answerArrayObject, "Id"));
                        feedBackAnswerDetails.setmAnswer(getObjectvalue(answerArrayObject, "Answer"));
                        feedbackanswerDetailsList.add(feedBackAnswerDetails);
                    }
                }
            } else {
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return feedbackanswerDetailsList;
    }


    //-----------------------------------Network Down Time Report Details----------------------------------------------------------

    public List<NetworkDownTimeReport> parsingNDTResult(String jResult) {
        List<NetworkDownTimeReport> networkDowntimeReportsList = null;
        try {
            JSONObject ndtrObject = new JSONObject(jResult);
            String isError = getObjectvalue(ndtrObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = ndtrObject.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("NDTResultParsing", "ResponseArray is null");
                } else {
                    networkDowntimeReportsList = new ArrayList<>();
                    int len = responseArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jObject = responseArray.getJSONObject(i);
                        NetworkDownTimeReport nd = new NetworkDownTimeReport();
                        nd.setmSyncId(getObjectvalue(jObject, "SyncId"));
                        nd.setmReporType(getObjectvalue(jObject, "ReporType"));
                        nd.setmIpAddress(getObjectvalue(jObject, "IpAddress"));
                        nd.setmImeiNumber(getObjectvalue(jObject, "ImeiNumber"));
                        nd.setmFpsCode(getObjectvalue(jObject, "FpsCode"));
                        nd.setmStartTime(getObjectvalue(jObject, "StartTime"));
                        nd.setmEndTime(getObjectvalue(jObject, "EndTime"));
                        nd.setmModeOfSync(getObjectvalue(jObject, "ModeOfSync"));
                        nd.setmNetworkProvider(getObjectvalue(jObject, "NetworkProvider"));
                        nd.setmDownTimeType(getObjectvalue(jObject, "DownTimeType"));
                        nd.setmStatus(getObjectvalue(jObject, "Status"));
                        nd.setmCreatedDate(getObjectvalue(jObject, "CreatedDate"));
                        nd.setmPlatform(getObjectvalue(jObject, "Platform("));
                        networkDowntimeReportsList.add(nd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkDowntimeReportsList;
    }

    //-----------------------------------Hardware Complaint Report Details----------------------------------------------------------

    public List<HardwareComplaintReport> parsingHardwareComplaintsReport(String jResult) {
        List<HardwareComplaintReport> hardwareComplaintReports = null;
        try {
            JSONObject hwObject = new JSONObject(jResult);
            String isError = getObjectvalue(hwObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = hwObject.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("HWResultParsing", "ResponseArray is null");
                } else {
                    hardwareComplaintReports = new ArrayList<>();
                    int len = responseArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jObject = responseArray.getJSONObject(i);
                        HardwareComplaintReport hd = new HardwareComplaintReport();
                        hd.setmId(getObjectvalue(jObject, "Id"));
                        hd.setmTitleId(getObjectvalue(jObject, "TitleId"));
                        hd.setmSubject(getObjectvalue(jObject, "Subject"));
                        hd.setmDescription(getObjectvalue(jObject, "Description"));
                        hd.setmStatus(getObjectvalue(jObject, "Status"));
                        hd.setmCreatedBy(getObjectvalue(jObject, "CreatedBy"));
                        hd.setmCreatedByName(getObjectvalue(jObject, "CreatedByName"));
                        hd.setmIpAddress(getObjectvalue(jObject, "IpAddress"));
                        hd.setmCreatedDate(getObjectvalue(jObject, "CreatedDate"));
                        hardwareComplaintReports.add(hd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hardwareComplaintReports;
    }

    //-----------------------------------Daily Sales Report Details----------------------------------------------------------

    public List<SalesReport> parsingDailySalesReport(String jResult) {
        List<SalesReport> dailySalesReports = null;
        try {
            JSONObject hwObject = new JSONObject(jResult);
            String isError = getObjectvalue(hwObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = hwObject.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("DailySalesResultParsing", "ResponseArray is null");
                } else {
                    dailySalesReports = new ArrayList<>();
                    int len = responseArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jObject = responseArray.getJSONObject(i);
                        SalesReport sr = new SalesReport();
                        sr.setmRationCardNo(getObjectvalue(jObject, "RationCardNo"));
                        sr.setmCommodityName(getObjectvalue(jObject, "CommodityName"));
                        sr.setmCommodityColorCode(getObjectvalue(jObject, "CommodityColorCode"));
                        sr.setmMeasurementUnitName(getObjectvalue(jObject, "MeasurementUnitName"));
                        sr.setmSchemeName(getObjectvalue(jObject, "SchemeName"));
                        sr.setmSchemeColorCode(getObjectvalue(jObject, "SchemeColorCode"));
                        sr.setmLiftingMemberId(getObjectvalue(jObject, "LiftingMemberId"));
                        sr.setmSoldQty(getObjectvalue(jObject, "SoldQty"));
                        sr.setmPricePerUnit(getObjectvalue(jObject, "PricePerUnit"));
                        sr.setmTotalAmount(getObjectvalue(jObject, "TotalAmount"));
                        dailySalesReports.add(sr);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dailySalesReports;
    }

    /*-----------------------------------GetRation Member Details------------------------------------------------------------*/

    public List<RationCardMemberDetails> parsingMemberDetails(String jResult) {
        List<RationCardMemberDetails> rationCardMemberDetails = null;
        try {
            JSONObject hwObject = new JSONObject(jResult);
            String isError = getObjectvalue(hwObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = hwObject.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("MemberDetails Parsing", "ResponseArray is null");
                } else {
                    rationCardMemberDetails = new ArrayList<>();
                    int len = responseArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jObject = responseArray.getJSONObject(i);
                        RationCardMemberDetails rd = new RationCardMemberDetails();
                        rd.setmName(getObjectvalue(jObject, "Name"));
                        rd.setmGender(getObjectvalue(jObject, "Gender"));
                        rd.setmAge(getObjectvalue(jObject, "Age"));
                        rd.setmRelation(getObjectvalue(jObject, "Relation"));
                        rd.setmUidaiNo(getObjectvalue(jObject, "UidaiNo"));
                        rd.setmDateofBirth(getObjectvalue(jObject, "DateofBirth"));
                        rd.setmIsHof(getObjectvalue(jObject, "IsHof"));
                        rd.setmSchemeName(getObjectvalue(jObject, "SchemeName"));
                        rationCardMemberDetails.add(rd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rationCardMemberDetails;
    }

    /*-----------------------------------Stock Report Details------------------------------------------------------------*/

    public List<StockReport> parsingStockReport(String jResult) {
        List<StockReport> stockReports = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = resultObj.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("StockReport Parsing", "ResponseArray is null");
                } else {
                    int len = responseArray.length();
                    stockReports = new ArrayList<>();
                    for (int i = 0; i < len; i++) {
                        JSONObject stockObject = responseArray.getJSONObject(i);
                        StockReport sr = new StockReport();
                        sr.setmSchemeName(getObjectvalue(stockObject, "SchemeName"));
                        sr.setmCommodityCode(getObjectvalue(stockObject, "CommodityCode"));
                        sr.setmCommodityName(getObjectvalue(stockObject, "CommodityName"));
                        sr.setmRcCount(getObjectvalue(stockObject, "RcCount"));
                        sr.setmUnits(getObjectvalue(stockObject, "Units"));
                        sr.setmAllocQty(getObjectvalue(stockObject, "AllocQty"));
                        sr.setmClosingBalance(getObjectvalue(stockObject, "ClosingBalance"));
                        sr.setmEntitledQty(getObjectvalue(stockObject, "EntitledQty"));
                        sr.setmPaymentDoneForQty(getObjectvalue(stockObject, "PaymentDoneForQty"));
                        sr.setmRemainingLifitingQty(getObjectvalue(stockObject, "RemainingLifitingQty"));
                        sr.setmLiftedQty(getObjectvalue(stockObject, "LiftedQty"));
                        sr.setmSalesQty(getObjectvalue(stockObject, "SalesQty"));
                        sr.setmRemainingQty(getObjectvalue(stockObject, "RemainingQty"));
                        stockReports.add(sr);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockReports;
    }

     /*-----------------------------------Commodity Receiving Details------------------------------------------------------------*/

    public TruckChallanDetails parsingCommodityReceivingDetails(String jResult) {
        TruckChallanDetails truckChallanDetails = null;
        try {
            JSONObject resultObject = new JSONObject(jResult);
            String isError = getObjectvalue(resultObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONObject responseObject = resultObject.getJSONObject("ResponseObject");
                if (responseObject == null) {
                    Log.e("ComdtyRcvgDtls Parsing", "ResponseArray is null");
                } else {
                    truckChallanDetails = new TruckChallanDetails();
                    truckChallanDetails.setmTruckChalanNumber(getObjectvalue(responseObject, "TruckChalanNumber"));
                    truckChallanDetails.setmDeliveryOrderNo(getObjectvalue(responseObject, "DeliveryOrderNo"));
                    truckChallanDetails.setmDeliveryOrderDate(getObjectvalue(responseObject, "DeliveryOrderDate"));
                    truckChallanDetails.setmStateGodownCode(getObjectvalue(responseObject, "StateGodownCode"));
                    truckChallanDetails.setmStateGodownName(getObjectvalue(responseObject, "StateGodownName"));
                    truckChallanDetails.setmDispatchDate(getObjectvalue(responseObject, "DispatchDate"));
                    truckChallanDetails.setmTransporterId(getObjectvalue(responseObject, "TransporterId"));
                    truckChallanDetails.setmTransporterName(getObjectvalue(responseObject, "TransporterName"));
                    truckChallanDetails.setmTruckNumber(getObjectvalue(responseObject, "TruckNumber"));
                    truckChallanDetails.setmDriverName(getObjectvalue(responseObject, "DriverName"));
                    truckChallanDetails.setmDriverMobile(getObjectvalue(responseObject, "DriverMobile"));

                    JSONArray truckChalanDetails = responseObject.getJSONArray("TruckChalanDetails");
                    List<CommodityDetails> commodityDetailsList = null;
                    if (truckChalanDetails == null || truckChalanDetails.length() == 0) {
                        Log.e("ComdtyRcvgDtls Parsing", "TruckChalanDetails is null");
                    } else {
                        int len = truckChalanDetails.length();
                        commodityDetailsList = new ArrayList<>(len);
                        for (int i = 0; i < len; i++) {
                            JSONObject commodityObject = truckChalanDetails.getJSONObject(i);
                            CommodityDetails commodity = new CommodityDetails();
                            commodity.setmId(getObjectvalue(commodityObject, "Id"));
                            commodity.setmSchemeCode(getObjectvalue(commodityObject, "SchemeCode"));
                            commodity.setmSchemeName(getObjectvalue(commodityObject, "SchemeName"));
                            commodity.setmCommodityCode(getObjectvalue(commodityObject, "CommodityCode"));
                            commodity.setmCommodityName(getObjectvalue(commodityObject, "CommodityName"));
                            commodity.setmCommodityQty(getObjectvalue(commodityObject, "CommodityQty"));
                            commodity.setmCommodityRate(getObjectvalue(commodityObject, "CommodityRate"));
                            commodity.setmCommodityCost(getObjectvalue(commodityObject, "CommodityCost"));
                            commodity.setmCommodityReceived(getObjectvalue(commodityObject, "ReceivedQty"));
                            commodity.setmAllotedQty(getObjectvalue(commodityObject, "AllotedQty"));

                            commodityDetailsList.add(commodity);
                        }
                    }
                    truckChallanDetails.setmTruckChalanDetails(commodityDetailsList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return truckChallanDetails;
    }

      /*-----------------------------------Caption Details------------------------------------------------------------*/

    public Map<String, String> parsingCaptionsLanguage(String jResult){
        Map<String, String> captionsLanguageMap = null;
        try{
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj,"IsError");
            if(isError != null && isError.equalsIgnoreCase("false")){
                JSONArray responseArray = resultObj.getJSONArray("ResponseObject");
                if(responseArray != null && responseArray.length() > 0){
                    captionsLanguageMap = new HashMap<>();
                    int len = responseArray.length();
                    for(int i=0; i<len; i++){
                        JSONObject captions = responseArray.getJSONObject(i);
                        String key = getObjectvalue(captions,"Key");
                        String language = getObjectvalue(captions,"Value");

                        captionsLanguageMap.put(key,language);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return captionsLanguageMap;
    }

    //-----------------------------------------getMenudetails-----------------------------------------------------------
    public List<MenuDetails> parsingMenuDetails(String jResult) {
        List<MenuDetails> Longmenudetails = null;
        try {
            JSONObject hwObject = new JSONObject(jResult);
            String isError = getObjectvalue(hwObject, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONArray responseArray = hwObject.getJSONArray("ResponseObject");
                if (responseArray == null || responseArray.length() == 0) {
                    Log.e("HWResultParsing", "ResponseArray is null");
                } else {
                    Longmenudetails = new ArrayList<>();

                    int len = responseArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jObject = responseArray.getJSONObject(i);
                        MenuDetails menuDetails = new MenuDetails();
                        menuDetails.setmId(getObjectvalue(jObject, "Id"));
                        menuDetails.setmTitle(getObjectvalue(jObject, "Title"));
                        menuDetails.setmName(getObjectvalue(jObject, "Name"));
                        menuDetails.setmParentId(getObjectvalue(jObject, "ParentId"));
                        menuDetails.setmDisplayOrder(getObjectvalue(jObject, "DisplayOrder"));


                        Longmenudetails.add(menuDetails);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Longmenudetails;
    }







            /* ------------------------------------- POST SERVICE RESULTS PARSING -------------------------------*/

    /*-----------------------------------Sale Create Result Details------------------------------------------------------------*/

    // {"ResponseObject":{"Status":1,"Value":"103"},"IsError":false,"ExceptionObject":null}

    public String parsingSaleCreateResult(String jResult) {
        String salesResult = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONObject response = resultObj.getJSONObject("ResponseObject");
                String status = getObjectvalue(response, "Status");
                String value = getObjectvalue(resultObj, "Value");
                salesResult = status + "/" + value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesResult;
    }

    //------------------------------------- Aadhaar Seeding Result Details ---------------------------------------------------------*/

    // {"ResponseObject":true, "IsError":false, "ExceptionObject":null}
    public String parsingUidAuthendication(String jResult) {
        String respose = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                respose = getObjectvalue(resultObj, "ResponseObject");
            } else {
                respose = "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respose;
    }

     /*--------------------------------createComplaint-----------------------------*/

    //{"ResponseObject":1, "IsError":false, "ExceptionObject":null}
    public String parsingCreateComplaint(String jResult) {
        String respose = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                respose = getObjectvalue(resultObj, "ResponseObject");
            } else {
                respose = "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respose;
    }
    /*-------------------------------- Save feedback details-----------------------------*/

    //{"ResponseObject":true, "IsError":false, "ExceptionObject":null}
    public String parsingSavefeedBackDetails(String jResult) {
        String respose = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            //if (isError.equalsIgnoreCase("false")) {
            respose = getObjectvalue(resultObj, "ResponseObject");
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respose;
    }

    /*--------------------------------Save commodity receiving detail -----------------------------*/

    //{"ResponseObject":{"Status":1,"Value":"0200000000230123420920151223"},"IsError":false,"ExceptionObject":null}
    public String parsingSaveCommodityReceivingDetailsResult(String jResult) {
        String salesResult = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equalsIgnoreCase("false")) {
                JSONObject response = resultObj.getJSONObject("ResponseObject");
                String status = getObjectvalue(response, "Status");
                String value = getObjectvalue(resultObj, "Value");
                salesResult = status + "/" + value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesResult;
    }

    /*---------------------------------------- Feedback Save -------------------------------------------*/

    //{"ResponseObject": true, "IsError": false, "ExceptionObject": null }
    public String parsingFeedbackSaveResults(String jResult) {
        String responseString = null;
        try {
            JSONObject resultObj = new JSONObject(jResult);
            String isError = getObjectvalue(resultObj, "IsError");
            if (isError.equals("false")) {
                responseString = getObjectvalue(resultObj, "ResponseObject");
            }
        } catch (Exception e) {

        }
        return responseString;
    }
}
