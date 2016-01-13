package com.mmadapps.fairpriceshop.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by Baskar on 1/7/2016.
 */
public class SmsClass {

    public static void sendSMS(Context mContext, String phoneNumber, String message) {
        String SENT = "SEND_SMS";
        /*String DELIVERED = "SMS_DELIVERED";*/
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
