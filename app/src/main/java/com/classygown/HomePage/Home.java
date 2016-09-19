package com.classygown.HomePage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.classygown.Adapter.RecyclerHomeGridViewAdapter;
import com.classygown.Beans.Image_Dimensions;
import com.classygown.Beans.Images_Products_Featured;
import com.classygown.Beans.Products;
import com.classygown.Beans.Products_Attributes;
import com.classygown.Beans.Products_Options;
import com.classygown.Beans.ShopItemObject;
import com.classygown.Beans.Variation_Attributes;
import com.classygown.Beans.Variation_Attributes_Obj;
import com.classygown.Beans.Variation_Dimensions_Image;
import com.classygown.Beans.Variation_Images;
import com.classygown.OAuth.WooCommerceWS;
import com.classygown.R;
import com.classygown.Util.ConnectionDetector;
import com.classygown.Util.SweetAlertDialogConfig;
import com.classygown.Util.WrappableGridLayoutManager;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by William Chow on 12/18/15.
 */
public class Home extends Fragment implements ViewPagerEx.OnPageChangeListener {

    private ProgressBar _pbLoadingRecentProduct;
    private RelativeLayout rlRecycleView;
    private View home;
    private SliderLayout mDemoSlider;
    private TextSliderView textSliderView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ScrollView svScrollView;

    private int position = 0;
    private long mLastClickTime = 0;
    private WrappableGridLayoutManager lLayout;
//    private GridLayoutManager lLayout;

    // Image Slider
    private List<Products> products = new ArrayList<Products>();

    // Recent Products
    private JSONObject _recent_response;
    private List<Products> array_recent_products = null;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        home = inflater.inflate(R.layout.activity_home_page_classy_gown_home, container, false);

        init();
        refreshListener();
        initArrayList();
        populateImageData();
        showImageSliderData();
        // Check Internet and show Grid View Content
        checkInternet();

        return home;
    }

    private void init() {
        mDemoSlider = (SliderLayout) home.findViewById(R.id.slider);
        _pbLoadingRecentProduct = (ProgressBar) home.findViewById(R.id.pbLoadingRecentProduct);
        rlRecycleView = (RelativeLayout) home.findViewById(R.id.rlRecycleView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) home.findViewById(R.id.mSwipeRefreshLayout);
        svScrollView = (ScrollView) home.findViewById(R.id.svScrollView);
        hide_disable_progress_and_recycler();
    }

    private void refreshListener() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccentOther);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                array_recent_products = null;
                array_recent_products = new ArrayList<Products>();
                hide_disable_progress_and_recycler();
                checkInternet();
                home.findViewById(R.id.grid_view_recycle_view).setFocusable(false);
                mDemoSlider.startAutoCycle();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initArrayList() {
        array_recent_products = new ArrayList<Products>();
    }

    private void populateImageData() {
        // Image Slider Populate
        Intent i = this.getActivity().getIntent();
        products = (List<Products>) i.getSerializableExtra("array_products");
    }

    private void showImageSliderData() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < products.size(); i++) {
            url_maps.put("", products.get(i).getFeatured_src());

            for (String name : url_maps.keySet()) {
                position = i;
                textSliderView = new TextSliderView(getActivity());
                // initialize a SliderLayout
                textSliderView.image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);

                sliderListener(position);

                mDemoSlider.addSlider(textSliderView);
            }
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void sliderListener(final int position) {
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 100) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getActivity(), ProductDescription.class);
                intent.putExtra("array_products", (Serializable) products);
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_slide_out_left);
            }
        });
    }

    private void checkInternet() {
        if (ConnectionDetector.isConnectingToInternet(getActivity())) {
            handleFirstRecentData();
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                SweetAlertDialogConfig.SweetAlertDialogNoInternetConnectionError(getActivity(), getResources().getString(R.string.splash_screen_no_internet_connection_title), getResources().getString(R.string.splash_screen_no_internet_connection_description), getResources().getString(R.string.splash_screen_no_internet_connection_ok));
            }
        }
    }

    private void handleFirstRecentData() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                WooCommerceWS.get("products", recent_product_request_param(), new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        _recent_response = response;
                        recent_product(_recent_response);

                        Activity activity = getActivity();
                        if (activity != null) {
                            gridViewManager();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            Log.i("William", "Error :: " + errorResponse.toString());
                        } catch (Exception e) {
                            Activity activity = getActivity();
                            if (activity != null) {
                                Log.i("William", "home onFailure");
                                SweetAlertDialogConfig.SweetAlertDialogExceptionError(getActivity(), getResources().getString(R.string.splash_screen_exception_e_title), getResources().getString(R.string.splash_screen_exception_e_description), getResources().getString(R.string.splash_screen_exception_e_button_text));
                            }
                        }
                    }
                });
            }
        }, 5000);
    }

    private RequestParams recent_product_request_param() {
        RequestParams param = new RequestParams();
        param.add("status", "publish");
        param.add("filter[limit]", "20");
        param.add("filter[orderby]", "date");
        param.add("filter[order]", "DESC");

        return param;
    }

    private List<ShopItemObject> getAllItemList() {
        List<ShopItemObject> allItems = new ArrayList<ShopItemObject>();

        for (int i = 0; i < array_recent_products.size(); i++) {
            allItems.add(new ShopItemObject(array_recent_products.get(i).getTitle(), "" + array_recent_products.get(i).getPrice_html(), array_recent_products.get(i).getFeatured_src(), array_recent_products.get(i).getOn_sale()));
        }
        return allItems;
    }

    private void gridViewManager() {
        List<ShopItemObject> rowListItem = getAllItemList();
        lLayout = new WrappableGridLayoutManager(getActivity(), 2);
        lLayout.setOrientation(WrappableGridLayoutManager.VERTICAL);
//        lLayout = new GridLayoutManager(getActivity(), 2);
//        lLayout.setOrientation(GridLayoutManager.VERTICAL);

        final RecyclerView rViewGrid = (RecyclerView) home.findViewById(R.id.grid_view_recycle_view);
//        rViewGrid.setNestedScrollingEnabled(false);
        rViewGrid.setHasFixedSize(true);
        rViewGrid.setLayoutManager(lLayout);
        rViewGrid.setItemAnimator(new DefaultItemAnimator());

        RecyclerHomeGridViewAdapter rcAdapter = new RecyclerHomeGridViewAdapter(getActivity(), getActivity(), rowListItem, array_recent_products);
        rViewGrid.setAdapter(rcAdapter);
//        int valueInPixels = (int) getResources().getDimension(R.dimen._142sdp);
//        int viewHeight = valueInPixels * rowListItem.size();
//        rViewGrid.getLayoutParams().height = viewHeight;
        show_enable_progress_and_recycler();
    }

    private void recent_product(JSONObject _response) {
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
                    for (int count_options = 0; count_options < array_option.length(); count_options++) {
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
                array_recent_products.add(products);
            }
        } catch (JSONException e) {
            Log.i("William", "JSON Error" + e.toString());
        }
    }

    private void hide_disable_progress_and_recycler() {
        _pbLoadingRecentProduct.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setEnabled(false);
        rlRecycleView.setVisibility(View.GONE);
        home.findViewById(R.id.grid_view_recycle_view).setFocusable(false);
    }

    private void show_enable_progress_and_recycler() {
        _pbLoadingRecentProduct.setVisibility(View.GONE);
        rlRecycleView.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onStart() {
        mDemoSlider.startAutoCycle();
        super.onStart();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
