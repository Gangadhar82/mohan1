package com.mmadapps.fairpriceshop.reports;

import android.widget.ListView;

import com.mmadapps.fairpriceshop.services.ServiceInteractor;

/**
 * Created by Baskar on 12/17/2015.
 */
public interface FragmentInteractor extends ServiceInteractor{
    void resetListSize(ListView listView);
}
