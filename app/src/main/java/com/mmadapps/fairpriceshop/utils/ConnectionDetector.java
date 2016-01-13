package com.mmadapps.fairpriceshop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Baskar on 12/15/2015.
 */
public class ConnectionDetector {
	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (NetworkInfo inf: info)
					if (inf.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}
