package com.classygown.HomePage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classygown.BaseActivity.BaseActivityWithoutExit;
import com.classygown.R;

/**
 * Created by William Chow on 12/21/15.
 */
public class AboutActivity extends BaseActivityWithoutExit implements View.OnClickListener{

    private TextView tvHomePageMoreSubHeaderTitle, tvBackOrCancelOrClose, tvAddCart;
    private RelativeLayout rlBack;
    private ImageView ivBack, ivLogout;
    private LinearLayout llMainBar;

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_more_about);

        init();
        setOnClickButtonListener();
        setupLayout();
    }

    private void init(){
        tvHomePageMoreSubHeaderTitle = (TextView) findViewById(R.id.tvHomePageMoreSubHeaderTitle);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvBackOrCancelOrClose = (TextView) findViewById(R.id.tvBackOrCancelOrClose);
        tvAddCart = (TextView) findViewById(R.id.tvAddCart);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        llMainBar = (LinearLayout) findViewById(R.id.llMainBar);
    }

    private void setOnClickButtonListener(){
        rlBack.setOnClickListener(this);
    }

    private void setupLayout(){
        tvHomePageMoreSubHeaderTitle.setText(getResources().getText(R.string.home_page_more_title_sub_header_title));
        llMainBar.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setBackgroundResource(R.drawable.back_icon);
        tvBackOrCancelOrClose.setVisibility(View.GONE);
        tvAddCart.setVisibility(View.GONE);
        ivLogout.setVisibility(View.INVISIBLE);
        ivLogout.setBackgroundResource(R.drawable.shopping_cart);
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
        AboutActivity.this.finish();
        AboutActivity.this.overridePendingTransition(R.anim.animation_slide_in_left, R.anim.animation_slide_out_right);
    }
}
