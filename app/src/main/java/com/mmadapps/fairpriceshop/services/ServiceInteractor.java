package com.mmadapps.fairpriceshop.services;

/**
 * Created by Baskar on 12/22/2015.
 */
public interface ServiceInteractor {
    boolean callService(String service);
    void updateUI(boolean isCompleted,String mService);
}
