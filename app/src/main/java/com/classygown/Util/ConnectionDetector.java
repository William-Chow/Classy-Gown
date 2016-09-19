package com.classygown.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by William Chow on 12/17/15.
 */
public class ConnectionDetector {

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info == null || !info.isConnected()) {
                return false;
            }
            return true;
        }
        return false;
    }
}
