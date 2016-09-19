package com.classygown.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.view.WindowManager;

import com.classygown.R;

/**
 * Created by William Chow on 12/17/15.
 */
public class UtilActivity {

    /**
     * Hide the soft keyboard programatically
     ***/
    public static void hideSoftKeyboard(Activity _activity) {
        _activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Check device installed app or not
     *
     * @param _activity
     * @param uri
     * @return
     */
    public static boolean appInstalledOrNot(Activity _activity, String uri) {
        PackageManager pm = _activity.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
