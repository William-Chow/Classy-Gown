package com.classygown.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.classygown.BaseActivity.BaseActivity;
import com.classygown.Beans.Image_Dimensions;
import com.classygown.Beans.Images_Products_Featured;
import com.classygown.Beans.Products;
import com.classygown.Beans.Products_Attributes;
import com.classygown.Beans.Products_Options;
import com.classygown.Beans.Variation_Attributes;
import com.classygown.Beans.Variation_Attributes_Obj;
import com.classygown.Beans.Variation_Dimensions_Image;
import com.classygown.Beans.Variation_Images;
import com.classygown.HomePage.HomePageActivity;
import com.classygown.OAuth.WooCommerceWS;
import com.classygown.R;
import com.classygown.Util.ConnectionDetector;
import com.classygown.Util.SweetAlertDialogConfig;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SplashScreenClassyGownActivity extends BaseActivity {

    private ProgressBar _pbSplashScreenLoading;
    private JSONObject _response;
    private List<Products> array_products = null;

    private Products_Attributes products_attributes = null;
    private ArrayList<Products_Attributes> array_products_attributes = new ArrayList<Products_Attributes>();

    private Products_Options product_options = null;
    private ArrayList<Products_Options> array_product_options = new ArrayList<Products_Options>();

    private ArrayList<String> options = new ArrayList<>();

    private Images_Products_Featured images_products_featured = null;
    private ArrayList<Images_Products_Featured> array_images_products_featured = new ArrayList<Images_Products_Featured>();

    private Image_Dimensions image_dimensions = null;
    private Variation_Attributes variation_attributes = null;

    private Variation_Attributes_Obj variation_attributes_obj = null;
    private ArrayList<Variation_Attributes_Obj> array_variation_attributes_obj = new ArrayList<Variation_Attributes_Obj>();

    private Variation_Dimensions_Image variation_dimensions_images = null;

    private Variation_Images variation_images = null;
    private ArrayList<Variation_Images> array_variation_images = new ArrayList<Variation_Images>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_classy_gown);

        init();
        initArrayList();
        checkInternet();
    }

    private void init() {
        _pbSplashScreenLoading = (ProgressBar) findViewById(R.id.pbSplashScreenLoading);
    }

    private void initArrayList() {
        array_products = new ArrayList<Products>();
    }

    private void checkInternet() {
        if (ConnectionDetector.isConnectingToInternet(getApplicationContext())) {
            handle();
        } else {
            SweetAlertDialogConfig.SweetAlertDialogNoInternetConnectionError(SplashScreenClassyGownActivity.this, getResources().getString(R.string.splash_screen_no_internet_connection_title), getResources().getString(R.string.splash_screen_no_internet_connection_description), getResources().getString(R.string.splash_screen_no_internet_connection_ok));
        }
    }

    private void handle() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                WooCommerceWS.get("products", featured_product_request_param(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        _response = response;
                        featured_product(_response);

                        Intent intentHome = new Intent(SplashScreenClassyGownActivity.this, HomePageActivity.class);
                        intentHome.putExtra("array_products", (Serializable) array_products);
                        SplashScreenClassyGownActivity.this.startActivity(intentHome);
                        SplashScreenClassyGownActivity.this.finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            Log.i("William", "Error :: " + errorResponse.toString());
                        } catch (Exception e) {
                            Log.i("William", "Splash Screen Here");
                            SweetAlertDialogConfig.SweetAlertDialogExceptionError(SplashScreenClassyGownActivity.this, getResources().getString(R.string.splash_screen_exception_e_title), getResources().getString(R.string.splash_screen_exception_e_description), getResources().getString(R.string.splash_screen_exception_e_button_text));
                        }
                    }
                });
            }
        }, 5000);
    }

    private RequestParams featured_product_request_param() {
        RequestParams param = new RequestParams();
        param.add("status", "publish");
        param.add("filter[limit]", "6");
        param.add("filter[orderby]", "meta_value");
        param.add("filter[orderby_meta_key]", "_featured");
        param.add("filter[order]", "DESC");

        return param;
    }

    private void featured_product(JSONObject _response) {
        try {
            JSONObject json = new JSONObject(_response.toString());

            JSONArray myArray = json.getJSONArray("products");
            for (int i = 0; i < myArray.length(); i++) {
                array_products_attributes = new ArrayList<Products_Attributes>();
                array_images_products_featured = new ArrayList<Images_Products_Featured>();
                JSONObject main_obj = myArray.getJSONObject(i);

                JSONArray attributes_array = main_obj.getJSONArray("attributes");
                for (int attributes = 0; attributes < attributes_array.length(); attributes++) {
                    JSONObject obj = attributes_array.getJSONObject(attributes);
                    String name = obj.getString("name");

                    array_product_options = new ArrayList<Products_Options>();
                    options = new ArrayList<>();
                    JSONArray array_option = obj.getJSONArray("options");
                    for(int count_options = 0; count_options < array_option.length(); count_options++){
                        String option = array_option.getString(count_options);
                        options.add(option);
                        product_options = new Products_Options(name, options);
                        array_product_options.add(product_options);
                    }
                    String position = obj.getString("position");
                    String slug = obj.getString("slug");
                    String variation = obj.getString("variation");
                    String visible = obj.getString("visible");
                    products_attributes = new Products_Attributes(name, array_product_options, position, slug, variation, visible);
                    array_products_attributes.add(products_attributes);
                }

                JSONArray image_array = main_obj.getJSONArray("images");
                for (int j = 0; j < image_array.length(); j++) {
                    JSONObject obj = image_array.getJSONObject(j);
                    String alt = obj.getString("alt");
                    String created_at = obj.getString("created_at");
                    String id = obj.getString("id");
                    String position = obj.getString("position");
                    String src = obj.getString("src");
                    String title = obj.getString("title");
                    String updated_at = obj.getString("updated_at");
                    images_products_featured = new Images_Products_Featured(alt, created_at, id, position, src, title, updated_at);
                    array_images_products_featured.add(images_products_featured);
                }
                String average_rating = main_obj.getString("average_rating");
                String categories = main_obj.getString("categories");
                String created_at = main_obj.getString("created_at");
                String description = main_obj.getString("description");

                JSONObject dimension_obj = main_obj.getJSONObject("dimensions");
                String height = dimension_obj.getString("height");
                String length = dimension_obj.getString("length");
                String unit = dimension_obj.getString("unit");
                String width = dimension_obj.getString("width");
                image_dimensions = new Image_Dimensions(height, length, unit, width);

                String featured_src = main_obj.getString("featured_src");
                String in_stock = main_obj.getString("in_stock");
                String managing_stock = main_obj.getString("managing_stock");
                String on_sale = main_obj.getString("on_sale");
                String permalink = main_obj.getString("permalink");
                String price = main_obj.getString("price");
                String price_html = main_obj.getString("price_html");
                String purchase_able = main_obj.getString("purchaseable");
                String rating_count = main_obj.getString("rating_count");
                String regular_price = main_obj.getString("regular_price");
                String reviews_allowed = main_obj.getString("reviews_allowed");
                String shipping_class = main_obj.getString("shipping_class");
                String shipping_class_id = main_obj.getString("shipping_class_id");
                String shipping_required = main_obj.getString("shipping_required");
                String shipping_taxable = main_obj.getString("shipping_taxable");
                String short_description = main_obj.getString("short_description");
                String sku = main_obj.getString("sku");
                String sold_individually = main_obj.getString("sold_individually");
                String status = main_obj.getString("status");
                String stock_quantity = main_obj.getString("stock_quantity");
                String tags = main_obj.getString("tags");
                String tax_class = main_obj.getString("tax_class");
                String tax_status = main_obj.getString("tax_status");
                String taxable = main_obj.getString("taxable");
                String title = main_obj.getString("title");
                String total_sales = main_obj.getString("total_sales");
                String type = main_obj.getString("type");
                String updated_at = main_obj.getString("updated_at");

                JSONArray variation_attributes_array = main_obj.getJSONArray("variations");
                for (int variation_att_array = 0; variation_att_array < variation_attributes_array.length(); variation_att_array++) {
                    array_variation_attributes_obj = new ArrayList<Variation_Attributes_Obj>();
                    array_variation_images = new ArrayList<Variation_Images>();
                    JSONObject variation_obj = variation_attributes_array.getJSONObject(variation_att_array);

                    JSONArray variation_attributes_array_obj = variation_obj.getJSONArray("attributes");
                    for (int attributes_obj = 0; attributes_obj < variation_attributes_array_obj.length(); attributes_obj++) {
                        JSONObject obj = attributes_array.getJSONObject(attributes_obj);
                        String name = obj.getString("name");
                        String option = obj.getString("options");
                        String slug = obj.getString("slug");
                        variation_attributes_obj = new Variation_Attributes_Obj(name, option, slug);
                        array_variation_attributes_obj.add(variation_attributes_obj);
                    }

                    String variation_created_at = variation_obj.getString("created_at");
                    String back_ordered = variation_obj.getString("backordered");

                    JSONObject variation_dimensions_obj = variation_obj.getJSONObject("dimensions");
                    String variation_height = variation_dimensions_obj.getString("height");
                    String variation_length = variation_dimensions_obj.getString("length");
                    String variation_unit = variation_dimensions_obj.getString("unit");
                    String variation_width = variation_dimensions_obj.getString("width");
                    variation_dimensions_images = new Variation_Dimensions_Image(variation_height, variation_length, variation_unit, variation_width);

                    String download_expiry = variation_obj.getString("download_expiry");
                    String download_limit = variation_obj.getString("download_limit");
                    String id = variation_obj.getString("id");

                    JSONArray variation_image = variation_obj.getJSONArray("image");
                    for (int variation_image_array = 0; variation_image_array < variation_image.length(); variation_image_array++) {
                        JSONObject obj = variation_image.getJSONObject(variation_image_array);
                        String image_alt = obj.getString("alt");
                        String image_created_at = obj.getString("created_at");
                        String image_id = obj.getString("id");
                        String image_position = obj.getString("position");
                        String image_src = obj.getString("src");
                        String image_title = obj.getString("title");
                        String image_updated_at = obj.getString("updated_at");
                        variation_images = new Variation_Images(image_alt, image_created_at, image_id, image_position, image_src, image_title, image_updated_at);
                        array_variation_images.add(variation_images);
                    }

                    String variation_in_stock = variation_obj.getString("in_stock");
                    String variation_managing_stock = variation_obj.getString("managing_stock");
                    String variation_on_sale = variation_obj.getString("on_sale");
                    String variation_permalink = variation_obj.getString("permalink");
                    String variation_price = variation_obj.getString("price");
                    String variation_purchaseable = variation_obj.getString("purchaseable");
                    String variation_regular_price = variation_obj.getString("regular_price");
                    String variation_sale_price = variation_obj.getString("sale_price");
                    String variation_shipping_class = variation_obj.getString("shipping_class");
                    String variation_shipping_class_id = variation_obj.getString("shipping_class_id");
                    String variation_sku = variation_obj.getString("sku");
                    String variation_stock_quantity = variation_obj.getString("stock_quantity");
                    String variation_tax_class = variation_obj.getString("tax_class");
                    String variation_tax_status = variation_obj.getString("tax_status");
                    String variation_taxable = variation_obj.getString("taxable");
                    String variation_updated_at = variation_obj.getString("updated_at");
                    String variation_weight = variation_obj.getString("weight");

                    variation_attributes = new Variation_Attributes(array_variation_attributes_obj, variation_created_at, back_ordered, variation_dimensions_images
                            , download_expiry, download_limit, id, array_variation_images, variation_in_stock, variation_managing_stock, variation_on_sale, variation_permalink
                            , variation_price, variation_purchaseable, variation_regular_price, variation_sale_price, variation_shipping_class, variation_shipping_class_id
                            , variation_sku, variation_stock_quantity, variation_tax_class, variation_tax_status, variation_taxable, variation_updated_at, variation_weight);
                }
                Products products = new Products(array_products_attributes, array_images_products_featured, average_rating, categories, created_at, description, image_dimensions
                        , featured_src, in_stock, managing_stock, on_sale, permalink, price, price_html, purchase_able, rating_count, regular_price, reviews_allowed, shipping_class
                        , shipping_class_id, shipping_required, shipping_taxable, short_description, sku, sold_individually, status, stock_quantity, tags, tax_class, tax_status
                        , taxable, title, total_sales, type, updated_at, variation_attributes);
                array_products.add(products);

            }
        } catch (JSONException e) {
            Log.i("William", "JSON Error" + e.toString());
        }
    }
}
