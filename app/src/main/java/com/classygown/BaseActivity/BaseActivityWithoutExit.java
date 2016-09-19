package com.classygown.BaseActivity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by William Chow on 12/20/15.
 */
public class BaseActivityWithoutExit extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        return;
    }
}
