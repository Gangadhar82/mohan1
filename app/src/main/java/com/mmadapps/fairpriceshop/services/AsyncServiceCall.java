package com.mmadapps.fairpriceshop.services;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Baskar on 12/22/2015.
 *
 * This Class is used to call when the downloaded data is stored in database,
 * Because by this class the ui is updated only using database
 */
public class AsyncServiceCall extends AsyncTask<Void,Void,Boolean> {
    private ServiceInteractor interactor;
    private ProgressDialogWindow progress;
    private String mService;

    public AsyncServiceCall(Context mContext, ServiceInteractor serviceInteractor, String service){
        this.progress = ProgressDialogWindow.getMyObject(mContext);
        if(progress == null) {
            //TODO call offline methods
        }else{
            this.interactor = serviceInteractor;
            this.mService = service;
            this.execute();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return interactor.callService(this.mService);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progress.cancelProgress();
        interactor.updateUI(aBoolean,mService);
    }
}
