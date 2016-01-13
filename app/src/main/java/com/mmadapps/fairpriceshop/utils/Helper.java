package com.mmadapps.fairpriceshop.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;
import com.mmadapps.fairpriceshop.bean.MenuDetails;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Baskar on 12/15/2015.
 */
public class Helper extends SQLiteOpenHelper {

    private static final String DB_PATH = "/data/data/com.mmadapps.fairpriceshop/databases/";
    private static final String DB_NAME = "FPSDatabase.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    Cursor cursorGetData;

    public Helper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * creating database at first time of application Setup
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * checking the database Availability based on Availability copying database
     * to the device data
     *
     * @return true (if Available)
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error is" + e.toString());
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * copying database from asserts to package location in mobile data
     *
     * @throws IOException
     */
    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Opening database for retrieving/inserting information
     *
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Closing database after operation done
     */
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    /**
     * getting information based on SQL Query
     *
     * @param sql
     * @return Output of Query
     */
    private Cursor getData(String sql) {
        openDataBase();
        cursorGetData = getReadableDatabase().rawQuery(sql, null);
        return cursorGetData;
    }

    /**
     * Inserting information based on table name and values
     *
     * @param tableName
     * @param values
     * @return
     */
    private long insertData(String tableName, ContentValues values) {
        openDataBase();
        return myDataBase.insert(tableName, null, values);
    }

    public void insertLanguages(List<LanguageDetails> languageDetailses){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_language,null,null);
            Log.e(TAG,tbl_language+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            for (LanguageDetails language : languageDetailses){
                ContentValues cv = new ContentValues();
                cv.put("language_code",language.getmCode());
                cv.put("language_id",language.getmId());
                cv.put("language",language.getmName());
                cv.put("language_x",language.getmNameL1());
                cv.put("state_language_id",language.getmStateLanguageId());
                cv.put("is_default",language.getmIsDefault());

                long rowId = insertData(tbl_language, cv);
                if(rowId > 0){
                    mTotalInsertedRows++;
                }
            }
            Log.e(TAG,"InsertLanguage TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<LanguageDetails> getLanguageList(){
        List<LanguageDetails> languageDetails = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_language);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            languageDetails = new ArrayList<>();
            int len = cursor.getCount();
            for(int i=0; i<len; i++){
                LanguageDetails language = new LanguageDetails();
                language.setmId(cursor.getString(0));
                language.setmName(cursor.getString(1));
                language.setmNameL1(cursor.getString(2));
                language.setmCode(cursor.getString(3));
                language.setmStateLanguageId(cursor.getString(4));
                language.setmIsDefault(cursor.getString(5));
                languageDetails.add(language);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return languageDetails;
    }

    public void insertAgentDetails(AgentDetails agentDetails){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_agent_details,null,null);
            Log.e(TAG,tbl_agent_details+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            ContentValues cv = new ContentValues();
            cv.put("user_detail_id", agentDetails.getmUserDetailId());
            cv.put("user_details",agentDetails.getmUserDetails());
            cv.put("state",agentDetails.getmState());
            cv.put("state_name",agentDetails.getmStateName());
            cv.put("district",agentDetails.getmDistrict());
            cv.put("district_name",agentDetails.getmDistrictName());
            cv.put("tahsil",agentDetails.getmTahsil());
            cv.put("tahsil_name",agentDetails.getmTahsilName());
            cv.put("plc",agentDetails.getmPLC());
            cv.put("plc_name",agentDetails.getmPLCName());
            cv.put("fps_code",agentDetails.getmFpsCode());
            cv.put("fps_name",agentDetails.getmFpsName());
            cv.put("user_email",agentDetails.getmUserEmail());
            cv.put("first_name",agentDetails.getmFirstName());
            cv.put("last_name",agentDetails.getmLastName());
            cv.put("user_mobile_no",agentDetails.getmUserMobileNo());
            cv.put("user_name",agentDetails.getmUserName());
            cv.put("role_name",agentDetails.getmRoleName());
            cv.put("user_id",agentDetails.getmUserId());
            cv.put("role_id",agentDetails.getmRoleId());
            cv.put("office_id",agentDetails.getmOfficeId());
            cv.put("office_tag",agentDetails.getmOfficeTag());
            cv.put("role_desc",agentDetails.getmRoleDesc());
            cv.put("can_change_data",agentDetails.getmCanChangeData());
            cv.put("is_sys_admin",agentDetails.getmIsSysAdmin());
            cv.put("page_id",agentDetails.getmPageId());
            cv.put("page_url",agentDetails.getmPageUrl());
            cv.put("base_image_data",agentDetails.getmBase64ImageData());
            cv.put("has_many_roles",agentDetails.getmHasManyRoles());

            long rowId = insertData(tbl_agent_details, cv);
            if(rowId > 0){
                mTotalInsertedRows++;
            }
        Log.e(TAG,"InsertAgentDetails TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public AgentDetails getAgentDetails(){
        AgentDetails agentDetails = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_agent_details);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            agentDetails = new AgentDetails();
            cursor.moveToFirst();
            agentDetails.setmUserDetailId(cursor.getString(0));
            agentDetails.setmUserDetails(cursor.getString(1));
            agentDetails.setmUserEmail(cursor.getString(2));
            agentDetails.setmFirstName(cursor.getString(3));
            agentDetails.setmLastName(cursor.getString(4));
            agentDetails.setmUserMobileNo(cursor.getString(5));
            agentDetails.setmUserName(cursor.getString(6));
            agentDetails.setmRoleName(cursor.getString(7));
            agentDetails.setmUserId(cursor.getString(8));
            agentDetails.setmRoleId(cursor.getString(9));
            agentDetails.setmOfficeId(cursor.getString(10));
            agentDetails.setmOfficeTag(cursor.getString(11));
            agentDetails.setmRoleDesc(cursor.getString(12));
            agentDetails.setmCanChangeData(cursor.getString(13));
            agentDetails.setmIsSysAdmin(cursor.getString(14));
            agentDetails.setmPageId(cursor.getString(15));
            agentDetails.setmPageUrl(cursor.getString(16));
            agentDetails.setmBase64ImageData(cursor.getString(17));
            agentDetails.setmHasManyRoles(cursor.getString(18));
            agentDetails.setmState(cursor.getString(19));
            agentDetails.setmStateName(cursor.getString(20));
            agentDetails.setmDistrict(cursor.getString(21));
            agentDetails.setmDistrictName(cursor.getString(22));
            agentDetails.setmTahsil(cursor.getString(23));
            agentDetails.setmTahsilName(cursor.getString(24));
            agentDetails.setmPLC(cursor.getString(25));
            agentDetails.setmPLCName(cursor.getString(26));
            agentDetails.setmFpsCode(cursor.getString(27));
            agentDetails.setmFpsName(cursor.getString(28));
        }
        cursor.close();
        return agentDetails;
    }

    public void insertAllocation(List<AllocationDetails> allocationDetailsList){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_allocation,null,null);
            Log.e(TAG,tbl_allocation+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            for(AllocationDetails allocationDetails : allocationDetailsList) {
                ContentValues cv = new ContentValues();
                cv.put("fps_sale_status_id", allocationDetails.getmFpsSaleStatusId());
                cv.put("allocation_order_no", allocationDetails.getmAllocationOrderNo());
                cv.put("allocation_details", allocationDetails.getmAllocationDetails());
                cv.put("fps_code", allocationDetails.getmFpsCode());
                cv.put("allocation_month", allocationDetails.getmAllocationMonth());
                cv.put("from_date", allocationDetails.getmFromDate());
                cv.put("to_date", allocationDetails.getmToDate());
                cv.put("sale_status", allocationDetails.getmSaleStatus());
                cv.put("status", allocationDetails.getmStatus());

                long rowId = insertData(tbl_allocation, cv);
                if (rowId > 0) {
                    mTotalInsertedRows++;
                }
            }
            Log.e(TAG,"InsertAllocation TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<AllocationDetails> getAllocationDetails(){
        List<AllocationDetails> allocationDetailsList = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_allocation);
        if(cursor != null || cursor.getCount() > 0){
            allocationDetailsList = new ArrayList<>();
            int len = cursor.getCount();
            cursor.moveToFirst();
            for(int i = 0; i<len; i++) {
                AllocationDetails allocationDetails = new AllocationDetails();
                allocationDetails.setmFpsSaleStatusId(cursor.getString(0));
                allocationDetails.setmAllocationOrderNo(cursor.getString(1));
                allocationDetails.setmAllocationDetails(cursor.getString(2));
                allocationDetails.setmFpsCode(cursor.getString(3));
                allocationDetails.setmAllocationMonth(cursor.getString(4));
                allocationDetails.setmFromDate(cursor.getString(5));
                allocationDetails.setmToDate(cursor.getString(6));
                allocationDetails.setmSaleStatus(cursor.getString(7));
                allocationDetails.setmStatus(cursor.getString(8));

                allocationDetailsList.add(allocationDetails);
                cursor.moveToNext();
            }
        }
        return allocationDetailsList;
    }

    //----------------------------------inserting ration lifting member details-------------------------------------


    public void insertRationLiftingMemberDetails(List<RationLiftingMemberDetails> rationLiftingMemberDetailsList){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_ration_lifting_member_details,null,null);
            Log.e(TAG,tbl_ration_lifting_member_details+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            for(RationLiftingMemberDetails rationLiftingMemberDetails : rationLiftingMemberDetailsList) {
                ContentValues cv = new ContentValues();
                cv.put("member_id",rationLiftingMemberDetails.getmMemberId());
                cv.put("member_name_en",rationLiftingMemberDetails.getmMember_Name_EN());
                cv.put("member_name_ll",rationLiftingMemberDetails.getmMember_Name_LL());
                cv.put("mother_name_en",rationLiftingMemberDetails.getmMother_Name_EN());
                cv.put("mother_name_ll",rationLiftingMemberDetails.getmMother_Name_LL());
                cv.put("father_name_en",rationLiftingMemberDetails.getmFather_Name_EN());
                cv.put("father_name_ll",rationLiftingMemberDetails.getmFather_Name_LL());
                cv.put("spouse_name_en",rationLiftingMemberDetails.getmSpouse_Name_EN());
                cv.put("spouse_name_ll",rationLiftingMemberDetails.getmSpouse_Name_LL());
                cv.put("marital_status_code",rationLiftingMemberDetails.getmMaritalStatusCode());
                cv.put("marital_status_name",rationLiftingMemberDetails.getmMaritalStatusName());
                cv.put("actual_or_declared_dob",rationLiftingMemberDetails.getmActualOrDeclaredDOB());
                cv.put("date_of_birth",rationLiftingMemberDetails.getmDateOfBirth());
                cv.put("age_in_years",rationLiftingMemberDetails.getmAgeInYears());
                cv.put("gender",rationLiftingMemberDetails.getmGender());
                cv.put("gender_name",rationLiftingMemberDetails.getmGenderName());
                cv.put("nationality",rationLiftingMemberDetails.getmNationality());
                cv.put("nationality_name",rationLiftingMemberDetails.getmNationalityName());
                cv.put("mobile_no",rationLiftingMemberDetails.getmMobileNo());
                cv.put("phone_no",rationLiftingMemberDetails.getmPhoneNo());
                cv.put("email_id",rationLiftingMemberDetails.getmEmailId());
                cv.put("epic_no",rationLiftingMemberDetails.getmEpicNo());
                cv.put("npr_no",rationLiftingMemberDetails.getmNPRNo());
                cv.put("occupation_code",rationLiftingMemberDetails.getmOccupationCode());
                cv.put("occupation",rationLiftingMemberDetails.getmOccupation());
                cv.put("montly_income",rationLiftingMemberDetails.getmMonthlyIncome());
                cv.put("opting_to_lift_commodity",rationLiftingMemberDetails.getmOptingToLiftCommodity());
                cv.put("rs_code",rationLiftingMemberDetails.getmRSCode());
                cv.put("relation",rationLiftingMemberDetails.getmRelation());
                cv.put("ccat_code",rationLiftingMemberDetails.getmCCatCode());
                cv.put("cast_category",rationLiftingMemberDetails.getmCastCategory());
                cv.put("is_physically_challenged",rationLiftingMemberDetails.getmIsPhysicallyChallenged());
                cv.put("physically_challenged_code",rationLiftingMemberDetails.getmPhysicallyChallengedCode());
                cv.put("physically_challenged_name_en",rationLiftingMemberDetails.getmPhysicallyChallenged_Name_EN());
                cv.put("physically_challenged_percentage",rationLiftingMemberDetails.getmPhysicallyChallengedPercentage());
                cv.put("status",rationLiftingMemberDetails.getmStatus());
                cv.put("bank_ac_no",rationLiftingMemberDetails.getmBankACNo());
                cv.put("bank_code",rationLiftingMemberDetails.getmBankCode());
                cv.put("bank_name",rationLiftingMemberDetails.getmBankName());
                cv.put("branch_code",rationLiftingMemberDetails.getmBranchCode());
                cv.put("branch_name",rationLiftingMemberDetails.getmBranchName());
                cv.put("ifsc_code",rationLiftingMemberDetails.getmIFSCCode());
                cv.put("base_64_photo",rationLiftingMemberDetails.getmBaseSixtyFourPhoto());
                cv.put("document_id",rationLiftingMemberDetails.getmDocumentId());
                cv.put("is_applicant",rationLiftingMemberDetails.getmIsApplicant());
                cv.put("is_hof",rationLiftingMemberDetails.getmIsHOF());
                cv.put("is_nfsa",rationLiftingMemberDetails.getmIsNFSA());
                cv.put("uidai_status",rationLiftingMemberDetails.getmUIDAIStatus());
                cv.put("uidai_no",rationLiftingMemberDetails.getmUIDAINo());
                cv.put("uidai_enrolment_no",rationLiftingMemberDetails.getmUIDAIEnrolmentNo());
                cv.put("seeding_status",rationLiftingMemberDetails.getmSeedingStatus());
                cv.put("seeding_staus_name",rationLiftingMemberDetails.getmSeedingStatusName());
                cv.put("remarks",rationLiftingMemberDetails.getmRemarks());
                cv.put("rejection_counter",rationLiftingMemberDetails.getmRejectionCounter());
                long rowId = insertData(tbl_ration_lifting_member_details, cv);
                if (rowId > 0) {
                    mTotalInsertedRows++;
                }
            }
            Log.e(TAG,"InsertAllocation TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //----------------------------getting ration lifting member details----------------------------------

    public List<RationLiftingMemberDetails> getRationLiftingMemberDetails(){

        List<RationLiftingMemberDetails> rationLiftingMemberDetailsList = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_ration_lifting_member_details);
        if(cursor != null || cursor.getCount() > 0){
            rationLiftingMemberDetailsList = new ArrayList<>();
            int len = cursor.getCount();
            cursor.moveToFirst();
            for(int i = 0; i<len; i++) {
                RationLiftingMemberDetails rationLiftingMemberDetails = new RationLiftingMemberDetails();
                rationLiftingMemberDetails.setmMemberId(cursor.getString(0));
                rationLiftingMemberDetails.setmMember_Name_EN(cursor.getString(1));
                rationLiftingMemberDetails.setmMember_Name_LL(cursor.getString(2));
                rationLiftingMemberDetails.setmMother_Name_EN(cursor.getString(3));
                rationLiftingMemberDetails.setmMother_Name_LL(cursor.getString(4));
                rationLiftingMemberDetails.setmFather_Name_EN(cursor.getString(5));
                rationLiftingMemberDetails.setmFather_Name_LL(cursor.getString(6));
                rationLiftingMemberDetails.setmSpouse_Name_EN(cursor.getString(7));
                rationLiftingMemberDetails.setmSpouse_Name_LL(cursor.getString(8));
                rationLiftingMemberDetails.setmMaritalStatusCode(cursor.getString(9));
                rationLiftingMemberDetails.setmMaritalStatusName(cursor.getString(10));
                rationLiftingMemberDetails.setmActualOrDeclaredDOB(cursor.getString(11));
                rationLiftingMemberDetails.setmDateOfBirth(cursor.getString(12));
                rationLiftingMemberDetails.setmAgeInYears(cursor.getString(13));
                rationLiftingMemberDetails.setmGender(cursor.getString(14));
                rationLiftingMemberDetails.setmGenderName(cursor.getString(15));
                rationLiftingMemberDetails.setmNationality(cursor.getString(16));
                rationLiftingMemberDetails.setmNationalityName(cursor.getString(17));
                rationLiftingMemberDetails.setmMobileNo(cursor.getString(18));
                rationLiftingMemberDetails.setmPhoneNo(cursor.getString(19));
                rationLiftingMemberDetails.setmEmailId(cursor.getString(20));
                rationLiftingMemberDetails.setmEpicNo(cursor.getString(21));
                rationLiftingMemberDetails.setmNPRNo(cursor.getString(22));
                rationLiftingMemberDetails.setmOccupationCode(cursor.getString(23));
                rationLiftingMemberDetails.setmOccupation(cursor.getString(24));
                rationLiftingMemberDetails.setmMonthlyIncome(cursor.getString(25));
                rationLiftingMemberDetails.setmOptingToLiftCommodity(cursor.getString(26));
                rationLiftingMemberDetails.setmRSCode(cursor.getString(27));
                rationLiftingMemberDetails.setmRelation(cursor.getString(28));
                rationLiftingMemberDetails.setmCCatCode(cursor.getString(29));
                rationLiftingMemberDetails.setmCastCategory(cursor.getString(30));
                rationLiftingMemberDetails.setmIsPhysicallyChallenged(cursor.getString(31));
                rationLiftingMemberDetails.setmPhysicallyChallengedCode(cursor.getString(32));
                rationLiftingMemberDetails.setmPhysicallyChallenged_Name_EN(cursor.getString(33));
                rationLiftingMemberDetails.setmPhysicallyChallengedPercentage(cursor.getString(34));
                rationLiftingMemberDetails.setmStatus(cursor.getString(35));
                rationLiftingMemberDetails.setmBankACNo(cursor.getString(36));
                rationLiftingMemberDetails.setmBankCode(cursor.getString(37));
                rationLiftingMemberDetails.setmBankName(cursor.getString(38));
                rationLiftingMemberDetails.setmBranchCode(cursor.getString(39));
                rationLiftingMemberDetails.setmBranchName(cursor.getString(40));
                rationLiftingMemberDetails.setmIFSCCode(cursor.getString(41));
                rationLiftingMemberDetails.setmBaseSixtyFourPhoto(cursor.getString(42));
                rationLiftingMemberDetails.setmDocumentId(cursor.getString(43));
                rationLiftingMemberDetails.setmIsApplicant(cursor.getString(44));
                rationLiftingMemberDetails.setmIsHOF(cursor.getString(45));
                rationLiftingMemberDetails.setmIsNFSA(cursor.getString(46));
                rationLiftingMemberDetails.setmUIDAIStatus(cursor.getString(47));
                rationLiftingMemberDetails.setmUIDAINo(cursor.getString(48));
                rationLiftingMemberDetails.setmUIDAIEnrolmentNo(cursor.getString(49));
                rationLiftingMemberDetails.setmSeedingStatus(cursor.getString(50));
                rationLiftingMemberDetails.setmSeedingStatus(cursor.getString(51));
                rationLiftingMemberDetails.setmRemarks(cursor.getString(52));
                rationLiftingMemberDetails.setmRejectionCounter(cursor.getString(53));

                rationLiftingMemberDetailsList.add(rationLiftingMemberDetails);
                cursor.moveToNext();
            }
        }
        return rationLiftingMemberDetailsList;
    }


    //----------------------inserting scheme details for ration card-------------------------------------

    public void insertSchemeDetailsForRationcard(List<SchemeDetailsOnRationCardNumber> schemeDetailsOnRationCardNumberList){

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_scheme_details_for_ration_card,null,null);
            Log.e(TAG,tbl_scheme_details_for_ration_card+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            for(SchemeDetailsOnRationCardNumber schemeDetailsOnRationCardNumber : schemeDetailsOnRationCardNumberList) {
                ContentValues cv = new ContentValues();
                cv.put("rationcard_no",schemeDetailsOnRationCardNumber.getmRationCardNo());
                cv.put("scheme_code",schemeDetailsOnRationCardNumber.getmSchemeCode());
                cv.put("scheme_name",schemeDetailsOnRationCardNumber.getmSchemeName());
                cv.put("commodity_code",schemeDetailsOnRationCardNumber.getmCommodityCode());
                cv.put("commodity_name",schemeDetailsOnRationCardNumber.getmCommodityName());
                cv.put("entitlement_qty",schemeDetailsOnRationCardNumber.getmEntitlementQty());
                cv.put("lifted_qty",schemeDetailsOnRationCardNumber.getmLiftedQty());
                cv.put("balance_qty",schemeDetailsOnRationCardNumber.getmBalancedQty());
                cv.put("alloted_qty",schemeDetailsOnRationCardNumber.getmAllotedQty());
                cv.put("remaining_qty",schemeDetailsOnRationCardNumber.getmRemainingQty());
                cv.put("rates",schemeDetailsOnRationCardNumber.getmRates());
                cv.put("amount",schemeDetailsOnRationCardNumber.getmAmount());
                cv.put("rc_units",schemeDetailsOnRationCardNumber.getmRcUnits());

                long rowId = insertData(tbl_scheme_details_for_ration_card, cv);
                if (rowId > 0) {
                    mTotalInsertedRows++;
                }
            }
            Log.e(TAG,"InsertAllocation TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //----------------------------getting Scheme details for ration card--------------------------------



    public List<SchemeDetailsOnRationCardNumber> getSchemeDetailsForRationcard(){

        List<SchemeDetailsOnRationCardNumber> schemeDetailsOnRationCardNumberList = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_scheme_details_for_ration_card);
        if(cursor != null || cursor.getCount() > 0){
            schemeDetailsOnRationCardNumberList = new ArrayList<>();
            int len = cursor.getCount();
            cursor.moveToFirst();
            for(int i = 0; i<len; i++) {
                SchemeDetailsOnRationCardNumber schemeDetailsOnRationCardNumber = new SchemeDetailsOnRationCardNumber();
                schemeDetailsOnRationCardNumber.setmRationCardNo(cursor.getString(0));
                schemeDetailsOnRationCardNumber.setmSchemeCode(cursor.getString(1));
                schemeDetailsOnRationCardNumber.setmSchemeName(cursor.getString(2));
                schemeDetailsOnRationCardNumber.setmCommodityCode(cursor.getString(3));
                schemeDetailsOnRationCardNumber.setmCommodityName(cursor.getString(4));
                schemeDetailsOnRationCardNumber.setmEntitlementQty(cursor.getString(5));
                schemeDetailsOnRationCardNumber.setmLiftedQty(cursor.getString(6));
                schemeDetailsOnRationCardNumber.setmBalancedQty(cursor.getString(7));
                schemeDetailsOnRationCardNumber.setmAllotedQty(cursor.getString(8));
                schemeDetailsOnRationCardNumber.setmRemainingQty(cursor.getString(9));
                schemeDetailsOnRationCardNumber.setmRates(cursor.getString(10));
                schemeDetailsOnRationCardNumber.setmAmount(cursor.getString(11));
                schemeDetailsOnRationCardNumber.setmRcUnits(cursor.getString(12));

                schemeDetailsOnRationCardNumberList.add(schemeDetailsOnRationCardNumber);
                cursor.moveToNext();
            }
        }
        return schemeDetailsOnRationCardNumberList;
    }

    //---------------------------------------inserting Menu details------------------------------------------------


    public void insertingMenudetails(List<MenuDetails> menudetailsList){

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tbl_scheme_details_for_ration_card,null,null);
            Log.e(TAG,tbl_scheme_details_for_ration_card+" Deleted successfully");
            db.close();
            int mTotalInsertedRows = 0;
            for(MenuDetails menudetails : menudetailsList) {
                ContentValues cv = new ContentValues();
                cv.put("Id",menudetails.getmId());
                cv.put("Title",menudetails.getmTitle());
                cv.put("Name",menudetails.getmName());
                cv.put("ParentId",menudetails.getmParentId());
                cv.put("DisplayOrder",menudetails.getmDisplayOrder());

                long rowId = insertData(tbl_fbs_menu, cv);
                if (rowId > 0) {
                    mTotalInsertedRows++;
                }
            }
            Log.e(TAG,"InsertAllocation TotalRows"+mTotalInsertedRows);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------getting details of menu ---------------------------------------


    public List<MenuDetails> getMenudetails(){

        List<MenuDetails> menuList = null;
        Cursor cursor = getData("SELECT * FROM "+tbl_scheme_details_for_ration_card);
        if(cursor != null || cursor.getCount() > 0){
            menuList = new ArrayList<>();
            int len = cursor.getCount();
            cursor.moveToFirst();
            for(int i = 0; i<len; i++) {
                MenuDetails menudetails = new MenuDetails();
                menudetails.setmId(cursor.getString(0));
                menudetails.setmTitle(cursor.getString(1));
                menudetails.setmName(cursor.getString(2));
                menudetails.setmParentId(cursor.getString(3));
                menudetails.setmDisplayOrder(cursor.getString(4));


                menuList.add(menudetails);
                cursor.moveToNext();
            }
        }
        return menuList;
    }







    private static final String TAG = "Helper Class";

    //Tables
    private static final String tbl_language = "tbl_language_master";
    private static final String tbl_agent_details = "tbl_agent_details_master";
    private static final String tbl_allocation = "tbl_allocation_master";
    private static final String tbl_ration_lifting_member_details = "tbl_ration_lifting_member_details_master";
    private static final String tbl_scheme_details_for_ration_card = "tbl_scheme_details_for_ration_card_master";
    private static final String tbl_fbs_menu="tbl_fbs_menu";

}
