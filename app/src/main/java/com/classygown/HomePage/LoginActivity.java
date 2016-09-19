package com.classygown.HomePage;

import android.content.Intent;
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
 * Created by William Chow on 12/20/15.
 */
public class LoginActivity extends BaseActivityWithoutExit implements View.OnClickListener {

    private TextView tvHomePageMoreSubHeaderTitle, tvForgotPassword, tvSignUpText, tvBackOrCancelOrClose, tvAddCart;
    private RelativeLayout rlBack, rlLogin;
    private ImageView ivBack, ivLogout;
    private EditText etLoginEmailAddress, etLoginPassword;
    private String email = "", password = "", validateErrorTitle = "", validateErrorContent = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_login);

        UtilActivity.hideSoftKeyboard(LoginActivity.this);

        init();
        setOnClickButtonListener();
        setupLayout();
    }

    private void init() {
        tvHomePageMoreSubHeaderTitle = (TextView) findViewById(R.id.tvHomePageMoreSubHeaderTitle);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlLogin = (RelativeLayout) findViewById(R.id.rlLogin);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvBackOrCancelOrClose = (TextView) findViewById(R.id.tvBackOrCancelOrClose);
        tvAddCart = (TextView) findViewById(R.id.tvAddCart);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvSignUpText = (TextView) findViewById(R.id.tvSignUpText);

        etLoginEmailAddress = (EditText) findViewById(R.id.etLoginEmailAddress);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
    }

    private void setOnClickButtonListener() {
        rlBack.setOnClickListener(this);
        rlLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvSignUpText.setOnClickListener(this);
    }

    private void setupLayout() {
        tvHomePageMoreSubHeaderTitle.setText(getResources().getText(R.string.home_page_account_login_title));
//        ivBack.setImageResource(R.drawable.cancel);
        tvBackOrCancelOrClose.setText(getResources().getText(R.string.sub_header_cancel));
        tvAddCart.setText(getResources().getText(R.string.sub_header_cancel));
        tvAddCart.setVisibility(View.INVISIBLE);
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
            case R.id.rlLogin:
                if (validateRegister()) {
                    Toast.makeText(LoginActivity.this, "Login Done!", Toast.LENGTH_LONG).show();
                } else {
                    SweetAlertDialogConfig.SweetAlertDialogRegisterError(LoginActivity.this, validateErrorTitle, validateErrorContent, getResources().getString(R.string.home_page_more_account_sign_up_register_error_email_messages_button));
                }
                break;
            case R.id.tvForgotPassword:
                intentForgotPassword();
                break;
            case R.id.tvSignUpText:
                intentSignUp();
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
        LoginActivity.this.finish();
        LoginActivity.this.overridePendingTransition(R.anim.animation_slide_in_down, R.anim.animation_slide_out_down);
    }

    private void intentForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        LoginActivity.this.overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }

    private void intentSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        LoginActivity.this.overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }

    private Boolean validateRegister() {
        email = etLoginEmailAddress.getText().toString();
        password = etLoginPassword.getText().toString();

        if (email.length() == 0) {
            validateErrorTitle = getResources().getString(R.string.home_page_more_account_sign_up_register_error_email_messages_title);
            validateErrorContent = getResources().getString(R.string.home_page_more_account_sign_up_register_error_email_messages_content);
            return false;
        } else if (password.length() == 0) {
            validateErrorTitle = getResources().getString(R.string.home_page_more_account_sign_up_register_error_password_messages_title);
            validateErrorContent = getResources().getString(R.string.home_page_more_account_sign_up_register_error_password_messages_content);
            return false;
        } else if (email.length() != 0 && password.length() != 0) {
            // Pattern for Email
            if (email.matches(emailPattern)) {
                return true;
            } else {
                validateErrorTitle = getResources().getString(R.string.home_page_more_account_sign_up_register_error_invalid_email_messages_title);
                validateErrorContent = getResources().getString(R.string.home_page_more_account_sign_up_register_error_invalid_email_messages_content);
                return false;
            }
        }

        return true;
    }

    private void clearText() {
        etLoginEmailAddress.setText("");
        etLoginPassword.setText("");
    }
}
