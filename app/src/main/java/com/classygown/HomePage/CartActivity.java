package com.classygown.HomePage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classygown.BaseActivity.BaseActivityWithoutExit;
import com.classygown.R;

/**
 * Created by William Chow on 1/5/16.
 */
public class CartActivity extends BaseActivityWithoutExit implements View.OnClickListener{

    private TextView tvHomePageMoreSubHeaderTitle, tvBackOrCancelOrClose;
    private ImageView ivBack;
    private RelativeLayout rlBack;

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_cart);

        init();
        setOnClickButtonListener();
        setupLayout();
    }

    private void init() {
        tvHomePageMoreSubHeaderTitle = (TextView) findViewById(R.id.tvHomePageMoreSubHeaderTitle);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvBackOrCancelOrClose = (TextView) findViewById(R.id.tvBackOrCancelOrClose);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
    }

    private void setupLayout() {
        tvHomePageMoreSubHeaderTitle.setText(getResources().getText(R.string.home_page_cart_header));
        ivBack.setImageResource(R.drawable.back_icon);
        tvBackOrCancelOrClose.setText(getResources().getText(R.string.sub_header_close));
    }

    private void setOnClickButtonListener() {
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(isFastDoubleClick()){
            return;
        }
        switch (v.getId()){
            case R.id.rlBack:
                intentBack();
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

    private void intentBack(){
        CartActivity.this.finish();
        CartActivity.this.overridePendingTransition(R.anim.animation_slide_in_down, R.anim.animation_slide_out_down);
    }
}
