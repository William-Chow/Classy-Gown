package com.classygown.HomePage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.classygown.R;
import com.classygown.Util.SweetAlertDialogConfig;
import com.classygown.Util.UtilActivity;

/**
 * Created by William Chow on 12/18/15.
 */
public class More extends Fragment implements View.OnClickListener {

    private View more;
    private RelativeLayout rlLoginBar, rlSignUpBar, rlShippingBar, rlReturnAndExchanges, rlFacebook, rlInstagram, rlContactUs, rlAbout;


    private static String facebookUrl = "https://www.facebook.com/classygown";
    private static String instagramUrl = "https://www.instagram.com/classygown";
    private static String instagramProfile = "classygown";

    private Boolean installed = false;

    public static long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        more = inflater.inflate(R.layout.activity_home_page_classy_gown_more, container, false);

        init();
        setOnClickButtonListener();

        return more;
    }

    private void init() {
        rlLoginBar = (RelativeLayout) more.findViewById(R.id.rlLoginBar);
        rlSignUpBar = (RelativeLayout) more.findViewById(R.id.rlSignUpBar);
        rlShippingBar = (RelativeLayout) more.findViewById(R.id.rlShippingBar);
        rlReturnAndExchanges = (RelativeLayout) more.findViewById(R.id.rlReturnAndExchanges);
        rlFacebook = (RelativeLayout) more.findViewById(R.id.rlFacebook);
        rlInstagram = (RelativeLayout) more.findViewById(R.id.rlInstagram);
        rlContactUs = (RelativeLayout) more.findViewById(R.id.rlContactUs);
        rlAbout = (RelativeLayout) more.findViewById(R.id.rlAbout);
    }

    private void setOnClickButtonListener() {
        rlLoginBar.setOnClickListener(this);
        rlSignUpBar.setOnClickListener(this);
        rlShippingBar.setOnClickListener(this);
        rlReturnAndExchanges.setOnClickListener(this);
        rlFacebook.setOnClickListener(this);
        rlInstagram.setOnClickListener(this);
        rlContactUs.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(isFastDoubleClick()){
            return;
        }
        switch (v.getId()) {
            case R.id.rlLoginBar:
                intentLogin();
                break;
            case R.id.rlSignUpBar:
                intentAccountSignUp();
                break;
            case R.id.rlShippingBar:
                intentShippingTerms();
                break;
            case R.id.rlReturnAndExchanges:
                intentReturnAndExchanges();
                break;
            case R.id.rlFacebook:
                Intent facebookIntent = newFacebookIntent(getActivity().getPackageManager(), facebookUrl);
                installed = UtilActivity.appInstalledOrNot(getActivity(), "com.facebook.katana");
                if (installed) {
                    SweetAlertDialogConfig.SweetAlertDialogQuestionSocialMedia(getActivity(), getResources().getString(R.string.home_page_more_question_title), getResources().getString(R.string.home_page_more_question_description), getResources().getString(R.string.home_page_more_ok), getResources().getString(R.string.home_page_more_dismiss), facebookIntent, true);
                } else {
                    getActivity().startActivity(facebookIntent);
                }
                break;
            case R.id.rlInstagram:
                Intent instagramIntent = newInstagramIntent(getActivity().getPackageManager(), instagramUrl, instagramProfile);
                installed = UtilActivity.appInstalledOrNot(getActivity(), "com.instagram.android");
                if (installed) {
                    SweetAlertDialogConfig.SweetAlertDialogQuestionSocialMedia(getActivity(), getResources().getString(R.string.home_page_more_question_title), getResources().getString(R.string.home_page_more_question_description), getResources().getString(R.string.home_page_more_ok), getResources().getString(R.string.home_page_more_dismiss), instagramIntent, false);
                } else {
                    getActivity().startActivity(instagramIntent);
                }
                break;
            case R.id.rlContactUs:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                installed = UtilActivity.appInstalledOrNot(getActivity(), "com.google.android.gm");
                if (installed) {
                    SweetAlertDialogConfig.SweetAlertDialogQuestionGmail(getActivity(), getResources().getString(R.string.home_page_more_question_title) , getResources().getString(R.string.home_page_more_question_description), getResources().getString(R.string.home_page_more_ok), getResources().getString(R.string.home_page_more_dismiss), emailIntent);
                } else {
                    emailIntent.setType("plain/text");
                    getActivity().startActivity(emailIntent);
                }
                break;
            case R.id.rlAbout:
                intentAbout();
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

    private void intentLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_up, R.anim.animation_slide_down);
    }

    private void intentAccountSignUp() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }

    private void intentShippingTerms() {
        Intent intent = new Intent(getActivity(), ShippingTermsActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }

    private void intentReturnAndExchanges() {
        Intent intent = new Intent(getActivity(), ReturnAndExchangesActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri;
        try {
            pm.getPackageInfo("com.facebook.katana", 0);
            uri = Uri.parse("fb://facewebmodal/f?href=" + url);
        } catch (PackageManager.NameNotFoundException e) {
            uri = Uri.parse(url);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newInstagramIntent(PackageManager pm, String url, String myUrl) {
        Uri uri;
        try {
            pm.getPackageInfo("com.instagram.android", 0);
            uri = Uri.parse("http://instagram.com/_u/" + myUrl);
        } catch (PackageManager.NameNotFoundException e) {
            uri = Uri.parse(url);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private void intentAbout() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
    }
}
