package com.mmadapps.fairpriceshop.bean;

/**
 * Created by bhaskara.reddy on 21-12-2015.
 */
public class LoginDetails {
    private String mStatus;
    private String mRemarks;
    private String mIsOtpRequired;
    private String mOtpValidtityDuration;
    private String mExceptionObject;

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }

    public String getmIsOtpRequired() {
        return mIsOtpRequired;
    }

    public void setmIsOtpRequired(String mIsOtpRequired) {
        this.mIsOtpRequired = mIsOtpRequired;
    }

    public String getmOtpValidtityDuration() {
        return mOtpValidtityDuration;
    }

    public void setmOtpValidtityDuration(String mOtpValidtityDuration) {
        this.mOtpValidtityDuration = mOtpValidtityDuration;
    }

    public String getmExceptionObject() {
        return mExceptionObject;
    }

    public void setmExceptionObject(String mExceptionObject) {
        this.mExceptionObject = mExceptionObject;
    }
}
