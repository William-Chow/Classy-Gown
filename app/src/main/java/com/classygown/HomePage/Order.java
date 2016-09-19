package com.classygown.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.classygown.R;

/**
 * Created by William Chow on 12/18/15.
 */
public class Order extends Fragment implements View.OnClickListener {

    private View order;
    private RelativeLayout rlLogin;

    public static long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        order = inflater.inflate(R.layout.activity_home_page_classy_gown_order, container, false);

        init();
        onClickButtonListener();

        return order;
    }


    private void init() {
        rlLogin = (RelativeLayout) order.findViewById(R.id.rlLogin);
    }

    private void onClickButtonListener() {
        rlLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(isFastDoubleClick()){
            return;
        }
        switch (v.getId()) {
            case R.id.rlLogin:
                intent();
                break;
            default:
                break;
        }
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - lastClickTime) < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    
    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_up, R.anim.animation_slide_down);
    }
}
