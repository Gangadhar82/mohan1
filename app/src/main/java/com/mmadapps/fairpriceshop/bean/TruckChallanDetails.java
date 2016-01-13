package com.mmadapps.fairpriceshop.bean;

import java.util.List;

/**
 * Created by Baskar on 1/4/2016.
 */
public class TruckChallanDetails {
    private String mTruckChalanNumber;
    private String mDeliveryOrderNo;
    private String mDeliveryOrderDate;
    private String mStateGodownCode;
    private String mStateGodownName;
    private String mDispatchDate;
    private String mTransporterId;
    private String mTransporterName;
    private String mTruckNumber;
    private String mDriverName;
    private String mDriverMobile;
    private List<CommodityDetails> mTruckChalanDetails;

    public String getmTruckChalanNumber() {
        return mTruckChalanNumber;
    }

    public void setmTruckChalanNumber(String mTruckChalanNumber) {
        this.mTruckChalanNumber = mTruckChalanNumber;
    }

    public String getmDeliveryOrderNo() {
        return mDeliveryOrderNo;
    }

    public void setmDeliveryOrderNo(String mDeliveryOrderNo) {
        this.mDeliveryOrderNo = mDeliveryOrderNo;
    }

    public String getmDeliveryOrderDate() {
        return mDeliveryOrderDate;
    }

    public void setmDeliveryOrderDate(String mDeliveryOrderDate) {
        this.mDeliveryOrderDate = mDeliveryOrderDate;
    }

    public String getmStateGodownCode() {
        return mStateGodownCode;
    }

    public void setmStateGodownCode(String mStateGodownCode) {
        this.mStateGodownCode = mStateGodownCode;
    }

    public String getmStateGodownName() {
        return mStateGodownName;
    }

    public void setmStateGodownName(String mStateGodownName) {
        this.mStateGodownName = mStateGodownName;
    }

    public String getmDispatchDate() {
        return mDispatchDate;
    }

    public void setmDispatchDate(String mDispatchDate) {
        this.mDispatchDate = mDispatchDate;
    }

    public String getmTransporterId() {
        return mTransporterId;
    }

    public void setmTransporterId(String mTransporterId) {
        this.mTransporterId = mTransporterId;
    }

    public String getmTransporterName() {
        return mTransporterName;
    }

    public void setmTransporterName(String mTransporterName) {
        this.mTransporterName = mTransporterName;
    }

    public String getmTruckNumber() {
        return mTruckNumber;
    }

    public void setmTruckNumber(String mTruckNumber) {
        this.mTruckNumber = mTruckNumber;
    }

    public String getmDriverName() {
        return mDriverName;
    }

    public void setmDriverName(String mDriverName) {
        this.mDriverName = mDriverName;
    }

    public String getmDriverMobile() {
        return mDriverMobile;
    }

    public void setmDriverMobile(String mDriverMobile) {
        this.mDriverMobile = mDriverMobile;
    }

    public List<CommodityDetails> getmTruckChalanDetails() {
        return mTruckChalanDetails;
    }

    public void setmTruckChalanDetails(List<CommodityDetails> mTruckChalanDetails) {
        this.mTruckChalanDetails = mTruckChalanDetails;
    }
}
