package com.mmadapps.fairpriceshop.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.utils.ConnectionDetector;

/**
 * Created by Baskar on 12/21/2015.
 */
public class ProgressDialogWindow extends ProgressDialog {
    private static ProgressDialogWindow myObject = null;
    private ProgressDialogWindow(Context mContext){
        super(mContext);
    }

    public static ProgressDialogWindow getMyObject(Context mContext){
        ConnectionDetector connectionDetector = new ConnectionDetector(mContext);
        if(!connectionDetector.isConnectingToInternet()){
            Toast.makeText(mContext, "Please check internet", Toast.LENGTH_SHORT).show();
            return null;
        }else if(myObject == null){
            myObject = new ProgressDialogWindow(mContext);
            myObject.setMessage("Please wait...");
            myObject.setCancelable(false);
            myObject.setCanceledOnTouchOutside(false);
        }
        return myObject;
    }

    public void cancelProgress(){
        if(myObject!= null && myObject.isShowing()){
            myObject.dismiss();
            myObject = null;
        }
    }
}
