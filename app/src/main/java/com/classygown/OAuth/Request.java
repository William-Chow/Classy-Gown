package com.classygown.OAuth;

import android.util.Log;

import com.classygown.Util.Constant;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by William Chow on 12/17/15.
 */
public class Request {

    public static String read(String url) {
        StringBuffer buffer = new StringBuffer();
        try {
            /**
             * get the time - note: value below zero
             * the millisecond value is used for oauth_nonce later on
             */
//            int millis = (int) System.currentTimeMillis() * -1;
//            int time = (int) millis / 1000;

            /**
             * Listing of all parameters necessary to retrieve a token
             * (sorted lexicographically as demanded)
             */
            String[][] data = {
                    {"oauth_consumer_key", Constant.classy_gown_consumer_key},
                    {"oauth_nonce", getNonce()},
                    {"oauth_signature", ""},
                    {"oauth_signature_method", "HMAC-SHA1"},
                    {"oauth_timestamp", getTimestamp()}
            };

            /**
             * Generation of the signature base string
             */
            String signature_base_string =
                    "GET&" + URLEncoder.encode(url, "UTF-8") + "&";
            for (int i = 0; i < data.length; i++) {
                // ignore the empty oauth_signature field
                if (i != 3) {
                    signature_base_string +=
                            URLEncoder.encode(data[i][0], "UTF-8") + "%3D" +
                                    URLEncoder.encode(data[i][1], "UTF-8") + "%26";
                }
            }
            // cut the last appended %26
            signature_base_string = signature_base_string.substring(0,
                    signature_base_string.length() - 3);

            /**
             * Sign the request
             */
            Mac m = Mac.getInstance("HmacSHA1");
            m.init(new SecretKeySpec(Constant.classy_gown_consumer_secret.getBytes(), "HmacSHA1"));
            m.update(signature_base_string.getBytes());
            byte[] res = m.doFinal();
            String sig = String.valueOf(Base64Coder.encode(res));
            data[3][1] = sig;

            /**
             * Create the header for the request
             */
            String header = "OAuth ";
            for (String[] item : data) {
                header += item[0] + "=\"" + item[1] + "\", ";
            }
            // cut off last appended comma
            header = header.substring(0, header.length() - 2);

            System.out.println("Signature Base String: " + signature_base_string);
            System.out.println("Authorization Header: " + header);
            System.out.println("Signature: " + sig);
            Log.i("William", "Result :: " + "\n" + "Signature :: " + sig + "\n" + "Signature Base :: " + signature_base_string);

            String charset = "UTF-8";
            URLConnection connection = new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            connection.setRequestProperty("Authorization", header);
            connection.setRequestProperty("User-Agent", "XXXX");
            OutputStream output = connection.getOutputStream();
            output.write(header.getBytes(charset));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.i("William", "Buffer Reader :: " + reader);
            String read;
            while ((read = reader.readLine()) != null) {
                buffer.append(read);
            }
            Log.i("William", "Reader :: " + reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("William", "Error :: " + e.toString());
        }

        return buffer.toString();
    }

    /**
     * Generates a random nonce
     *
     * @return A unique identifier for the request
     */
    private static String getNonce() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    /**
     * Generates an integer representing the number of seconds since the unix epoch using the
     * date/time the request is issued
     *
     * @return A timestamp for the request
     */
    private static String getTimestamp() {
        return Long.toString((System.currentTimeMillis() / 1000));
    }

//    public static void main(String[] args) {
//        System.out.println(Request.read("http://api.twitter.com/oauth/request_token"));
//    }
}
