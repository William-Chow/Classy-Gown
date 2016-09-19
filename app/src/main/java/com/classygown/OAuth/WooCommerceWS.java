package com.classygown.OAuth;

import android.net.Uri;
import android.util.Log;

import com.classygown.Util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WooCommerceWS {
    private static final String BASE_URL = "http://classygown.com/wc-api/v3/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params = addOAuthParameter("GET", url, params);

        String test = params.toString().replace("[]"," ");

        client.setURLEncodingEnabled(false);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static RequestParams addOAuthParameter(String method, String url, RequestParams params) {
        String oAuthURL;
        String encodedBaseURL = "";
        String oAuthSignMethod = "HMAC-SHA1";
        String encodedParameter = "";
        String stringToBeSigned;
        String oAuthSignature = "";
        String oAuthTimeStamp;
        String oAuthNonce;
        String unencodedParameter = "";

        long timeStamp = System.currentTimeMillis() / 1000;
        oAuthTimeStamp = Long.toString(timeStamp);
        oAuthNonce = getNonce();

        //Base URL
        encodedBaseURL = Uri.encode(BASE_URL + url);

        //Encoded base URL
        System.out.println("Encoded base URL - " + encodedBaseURL);

        params.add("oauth_consumer_key", Constant.classy_gown_consumer_key);
        params.add("oauth_nonce", oAuthNonce);
        params.add("oauth_signature_method", oAuthSignMethod);
        params.add("oauth_timestamp", oAuthTimeStamp);

        try {
            unencodedParameter = params.toString().replace("[", "%5B").replace("]", "%5D");

            encodedParameter = URLEncoder.encode(unencodedParameter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        stringToBeSigned = method + "&" + encodedBaseURL + "&" + encodedParameter;

        try {
            oAuthSignature = sha1(stringToBeSigned, Constant.classy_gown_consumer_secret);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e.getMessage());
        }

        params.add("oauth_signature", oAuthSignature);

        oAuthURL = BASE_URL + url + "?" + params.toString();

        String test = params.toString();

        return params;
    }

    public static String sha1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        keyString = keyString + "&";

        Mac m = Mac.getInstance("HmacSHA1");
        m.init(new SecretKeySpec((keyString).getBytes(), "HmacSHA1"));
        m.update(s.getBytes());
        byte[] res = m.doFinal();
        String sig = String.valueOf(Base64Coder.encode(res));

        return sig;
    }

    public static String getNonce() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);

        return output;
    }
}