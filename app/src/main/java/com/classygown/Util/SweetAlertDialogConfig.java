package com.classygown.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by William Chow on 12/17/15.
 */
public class SweetAlertDialogConfig {

//    public static void SweetAlertDialogOpenProgressDialog(final Activity _activity, SweetAlertDialog sDialog) {
//        sDialog = new SweetAlertDialog(_activity, SweetAlertDialog.PROGRESS_TYPE);
//        sDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        sDialog.setTitleText("Loading...");
//        sDialog.setCancelable(false);
//        sDialog.show();
//    }
//
//    public static void SweetAlertDialogDismissProgressDialog(final Activity _activity, SweetAlertDialog sDialog){
//        sDialog.dismiss();
//    }

    public static void SweetAlertDialogNoInternetConnectionError(final Activity _activity, String _title, String _description, String _buttonText) {
        new SweetAlertDialog(_activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_buttonText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        _activity.finish();
                    }
                })
                .show();
    }

    public static void SweetAlertDialogRegisterError(final Activity _activity, String _title, String _description, String _buttonText) {
        new SweetAlertDialog(_activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_buttonText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                }).show();
    }

    public static void SweetAlertDialogQuestionSocialMedia(final Activity _activity, String _title, String _description, String _positveButton, String _negativeButton, final Intent intent, final Boolean type) {
        new SweetAlertDialog(_activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_positveButton)
                .setCancelText(_negativeButton)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (type) {
                            intent.setPackage("com.facebook.katana");
                        } else {
                            intent.setPackage("com.instagram.android");
                        }
                        _activity.startActivity(intent);
                        sDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                }).show();
    }

    public static void SweetAlertDialogQuestionGmail(final Activity _activity, String _title, String _description, String _positiveButton, String _negativeButton, final Intent emailIntent){
        new SweetAlertDialog(_activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_positiveButton)
                .setCancelText(_negativeButton)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.setType("plain/text");
                        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"classydressgown@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Enquiry - Classy Gown App");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n\n"+ "Sent from my Android");
                        _activity.startActivity(emailIntent);
                        sDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                }).show();
    }

    public static void SweetAlertDialogExceptionError(final Activity _activity, String _title, String _description, String _buttonText) {
        new SweetAlertDialog(_activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_buttonText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        _activity.finish();
                    }
                })
                .show();
    }

    public static void SweetAlertDialogValidateError(final Activity _activity, String _title, String _description, String _buttonText){
        new SweetAlertDialog(_activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(_title)
                .setContentText(_description)
                .setConfirmText(_buttonText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

    }
}
