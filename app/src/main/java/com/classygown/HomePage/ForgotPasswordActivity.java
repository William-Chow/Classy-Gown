package com.classygown.HomePage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classygown.BaseActivity.BaseActivityWithoutExit;
import com.classygown.R;
import com.classygown.Util.SweetAlertDialogConfig;
import com.classygown.Util.UtilActivity;

/**
 * Created by William Chow on 12/24/15.
 */
public class ForgotPasswordActivity extends BaseActivityWithoutExit implements View.OnClickListener {

    private TextView tvHomePageMoreSubHeaderTitle, tvBackOrCancelOrClose, tvAddCart;
    private RelativeLayout rlForgot, rlBack;
    private ImageView ivBack, ivLogout;
    private EditText etEmailAddressForForgot;

    private String email = "", validateErrorTitle = "", validateErrorContent = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_forgot_password);

        UtilActivity.hideSoftKeyboard(ForgotPasswordActivity.this);

        init();
        setOnClickButtonListener();
        setupLayout();

    }

    private void init() {
        tvHomePageMoreSubHeaderTitle = (TextView) findViewById(R.id.tvHomePageMoreSubHeaderTitle);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvBackOrCancelOrClose = (TextView) findViewById(R.id.tvBackOrCancelOrClose);
        tvAddCart = (TextView) findViewById(R.id.tvAddCart);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        rlForgot = (RelativeLayout) findViewById(R.id.rlForgot);
        etEmailAddressForForgot = (EditText) findViewById(R.id.etEmailAddressForForgot);
    }

    private void setOnClickButtonListener() {
        rlBack.setOnClickListener(this);
        rlForgot.setOnClickListener(this);
    }

    private void setupLayout() {
        tvHomePageMoreSubHeaderTitle.setText(getResources().getText(R.string.home_page_account_login_forgot_password_sub_header_title));
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setBackgroundResource(R.drawable.back_icon);
        tvBackOrCancelOrClose.setText(getResources().getText(R.string.sub_header_back));
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
        switch (v.getId()) {
            case R.id.rlBack:
                clearText();
                intentBack();
                break;
            case R.id.rlForgot:
                if (validateForgotPassword()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Reset Password Done!", Toast.LENGTH_LONG).show();
                } else {
                    SweetAlertDialogConfig.SweetAlertDialogRegisterError(ForgotPasswordActivity.this, validateErrorTitle, validateErrorContent, getResources().getString(R.string.home_page_more_account_forgot_password_error_email_messages_button));
                }
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

    private void intentBack() {
        ForgotPasswordActivity.this.finish();
        ForgotPasswordActivity.this.overridePendingTransition(R.anim.animation_slide_in_left, R.anim.animation_slide_out_right);
    }

    private Boolean validateForgotPassword() {
        email = etEmailAddressForForgot.getText().toString();

        if (email.length() == 0) {
            validateErrorTitle = getResources().getString(R.string.home_page_more_account_forgot_password_error_email_messages_title);
            validateErrorContent = getResources().getString(R.string.home_page_more_account_forgot_password_error_email_messages_content);
            return false;
        } else if (email.length() != 0) {
            // Pattern for Email
            if (email.matches(emailPattern)) {
                return true;
            } else {
                validateErrorTitle = getResources().getString(R.string.home_page_more_account_forgot_password_error_invalid_email_messages_title);
                validateErrorContent = getResources().getString(R.string.home_page_more_account_forgot_password_error_invalid_email_messages_content);
                return false;
            }
        }
        return true;
    }

    private void clearText() {
        etEmailAddressForForgot.setText("");
    }
}
