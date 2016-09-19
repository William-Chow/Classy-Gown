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
 * Created by William Chow on 12/23/15.
 */
public class SignUpActivity extends BaseActivityWithoutExit implements View.OnClickListener {

    private TextView tvHomePageMoreSubHeaderTitle, tvBackOrCancelOrClose, tvAddCart;
    private RelativeLayout rlBack, rlRegister;
    private ImageView ivBack, ivLogout;

    private EditText etEmailAddress, etPassword;
    private String email = "", password = "", validateErrorTitle = "", validateErrorContent = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_classy_gown_sign_up);

        UtilActivity.hideSoftKeyboard(SignUpActivity.this);

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

        rlRegister = (RelativeLayout) findViewById(R.id.rlRegister);

        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    private void setOnClickButtonListener() {
        rlBack.setOnClickListener(this);
        rlRegister.setOnClickListener(this);
    }

    private void setupLayout() {
        tvHomePageMoreSubHeaderTitle.setText(getResources().getText(R.string.home_page_more_account_sign_up_title_sub_header));
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
        switch (v.getId()) {
            case R.id.rlBack:
                clearText();
                intentBack();
                break;
            case R.id.rlRegister:
                if (validateRegister()) {
                    Toast.makeText(SignUpActivity.this, "Register Done!", Toast.LENGTH_LONG).show();
                } else {
                    SweetAlertDialogConfig.SweetAlertDialogRegisterError(SignUpActivity.this, validateErrorTitle, validateErrorContent, getResources().getString(R.string.home_page_more_account_sign_up_register_error_email_messages_button));
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
        SignUpActivity.this.finish();
        SignUpActivity.this.overridePendingTransition(R.anim.animation_slide_in_left, R.anim.animation_slide_out_right);
    }

    private Boolean validateRegister() {
        email = etEmailAddress.getText().toString();
        password = etPassword.getText().toString();

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

    private void clearText(){
        etEmailAddress.setText("");
        etPassword.setText("");
    }
}
