package com.classygown.HomePage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.classygown.Adapter.RecyclerShopGridViewAdapter;
import com.classygown.Beans.Image_Dimensions;
import com.classygown.Beans.Images_Products_Featured;
import com.classygown.Beans.Products;
import com.classygown.Beans.Products_Attributes;
import com.classygown.Beans.Products_Options;
import com.classygown.Beans.ShopObject;
import com.classygown.Beans.Variation_Attributes;
import com.classygown.Beans.Variation_Attributes_Obj;
import com.classygown.Beans.Variation_Dimensions_Image;
import com.classygown.Beans.Variation_Images;
import com.classygown.Interface.OnLoadMoreListener;
import com.classygown.OAuth.WooCommerceWS;
import com.classygown.R;
import com.classygown.Util.ConnectionDetector;
import com.classygown.Util.SweetAlertDialogConfig;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by William Chow on 12/18/15.
 */
public class Shop extends Fragment {

    private ProgressBar pbLoadingShopProduct, pbLoadingMoreShopProduct;
    private SwipeRefreshLayout mShopSwipeRefreshLayout;
    private RelativeLayout rlShopRecycleView;
    private View shop;
    private GridLayoutManager lGridShopLayout;
    private RecyclerView rViewGrid;
    private RecyclerShopGridViewAdapter rcAdapter;

    private List<Products> array_shop_products = null;
    private List<Products> total_array = null;

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

    private List<ShopObject> allItems;
    private List<ShopObject> rowListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shop = inflater.inflate(R.layout.activity_home_page_classy_gown_shop, container, false);

        init();
        initArrayList();
        refreshShopListener();

        getShopDataCallResponse();

        return shop;
    }

    private void init() {
        pbLoadingShopProduct = (ProgressBar) shop.findViewById(R.id.pbLoadingShopProduct);
        pbLoadingMoreShopProduct = (ProgressBar) shop.findViewById(R.id.pbLoadingMoreShopProduct);
        pbLoadingMoreShopProduct.setVisibility(View.GONE);
        mShopSwipeRefreshLayout = (SwipeRefreshLayout) shop.findViewById(R.id.mShopSwipeRefreshLayout);
        rlShopRecycleView = (RelativeLayout) shop.findViewById(R.id.rlShopRecycleView);
        hide_recycle_view();
    }

    private void initArrayList() {
        array_shop_products = new ArrayList<Products>();
        total_array = new ArrayList<Products>();
    }

    private void refreshShopListener() {
        mShopSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccentOther);
        mShopSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                array_shop_products = null;
                array_shop_products = new ArrayList<Products>();
                hide_recycle_view();
                getShopDataCallResponse();
            }
        });
    }

    private void onLoadShopListener() {
        rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pbLoadingMoreShopProduct.setVisibility(View.VISIBLE);
                allItems.add(null);
                rcAdapter.notifyItemInserted(allItems.size() - 1);
                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMoreShopDataCallResponse();
                    }
                }, 5000);
            }
        });
    }

    private void getShopDataCallResponse() {
        if (ConnectionDetector.isConnectingToInternet(getActivity())) {
            getShopData();
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                SweetAlertDialogConfig.SweetAlertDialogNoInternetConnectionError(getActivity(), getResources().getString(R.string.splash_screen_no_internet_connection_title), getResources().getString(R.string.splash_screen_no_internet_connection_description), getResources().getString(R.string.splash_screen_no_internet_connection_ok));
            }
        }
    }

    private void getShopData() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                WooCommerceWS.get("products", shop_product_request_param(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        shop_product(response);

                        gridviewShopManager();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            Log.i("William", "Error :: " + errorResponse.toString());
                        } catch (Exception e) {
                            Activity activity = getActivity();
                            if (activity != null) {
                                SweetAlertDialogConfig.SweetAlertDialogExceptionError(getActivity(), getResources().getString(R.string.splash_screen_exception_e_title), getResources().getString(R.string.splash_screen_exception_e_description), getResources().getString(R.string.splash_screen_exception_e_button_text));
                            }
                        }
                    }
                });
            }
        }, 2000);
    }

    private RequestParams shop_product_request_param() {
        RequestParams param = new RequestParams();
        param.add("status", "publish");
        param.add("filter[limit]", "20");
        param.add("filter[orderby]", "date");
        param.add("filter[order]", "DESC");

        return param;
    }

    private void getMoreShopDataCallResponse() {
        if (ConnectionDetector.isConnectingToInternet(getActivity())) {
            getMoreShopData();
        } else {
            Activity activity = getActivity();
            if(activity != null) {
                SweetAlertDialogConfig.SweetAlertDialogNoInternetConnectionError(getActivity(), getResources().getString(R.string.splash_screen_no_internet_connection_title), getResources().getString(R.string.splash_screen_no_internet_connection_description), getResources().getString(R.string.splash_screen_no_internet_connection_ok));
            }
        }
    }

    private void getMoreShopData() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                WooCommerceWS.get("products", loadMoreRequestParam(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        shop_product(response);

                        addData();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            Log.i("William", "Error :: " + errorResponse.toString());
                        } catch (Exception e) {
                            Activity activity = getActivity();
                            if(activity != null) {
                                SweetAlertDialogConfig.SweetAlertDialogExceptionError(getActivity(), getResources().getString(R.string.splash_screen_exception_e_title), getResources().getString(R.string.splash_screen_exception_e_description), getResources().getString(R.string.splash_screen_exception_e_button_text));
                            }
                        }
                    }
                });
            }
        }, 2000);
    }

    private RequestParams loadMoreRequestParam() {
        String value = String.valueOf(total_array.size() + 20);
//        Log.i("William", "My Load More Value :: " + value);

        RequestParams param = new RequestParams();
        param.add("status", "publish");
        param.add("filter[limit]", "20");
        param.add("filter[offset]", value);
        return param;
    }

    private void gridviewShopManager() {
        rowListItem = getAllItemList();
        lGridShopLayout = new GridLayoutManager(getActivity(), 2);
        lGridShopLayout.setOrientation(GridLayoutManager.VERTICAL);

        rViewGrid = (RecyclerView) shop.findViewById(R.id.shop_grid_view_recycle_view);
        rViewGrid.setNestedScrollingEnabled(false);
        rViewGrid.setHasFixedSize(true);
        rViewGrid.setLayoutManager(lGridShopLayout);
        rViewGrid.setItemAnimator(new DefaultItemAnimator());

        rcAdapter = new RecyclerShopGridViewAdapter(getActivity(), getActivity(), rowListItem, array_shop_products, rViewGrid);
        rViewGrid.setAdapter(rcAdapter);

        onLoadShopListener();

        show_recycle_view();
    }

    private List<ShopObject> getAllItemList() {
        allItems = new ArrayList<ShopObject>();
        total_array = array_shop_products;

        for (int i = 0; i < array_shop_products.size(); i++) {
            allItems.add(new ShopObject(array_shop_products.get(i).getTitle(), "" + array_shop_products.get(i).getPrice_html(), array_shop_products.get(i).getFeatured_src(), array_shop_products.get(i).getOn_sale()));
        }
        return allItems;
    }

    private void addData() {
        rowListItem = getAllItemList();

        Log.i("William", "Total :: " + total_array.size() + " Count :: Total of " + array_shop_products.size());
        Log.i("William", "My AllItems :: " + allItems.size());

//        for (int j = 0; j < total_array.size(); j++) {
//            Log.i("William", "Here total of J :: " + j + " :: here " + total_array.get(j).getTitle() + total_array.get(j).getPrice_html() + total_array.get(j).getFeatured_src() + total_array.get(j).getOn_sale());
//        }

        result();
    }

    private void result() {
        allItems.remove(allItems.size() - 1);
//        Log.i("William", "My AllItems REMOVE :: " + allItems.size());
        rcAdapter.notifyItemRemoved(allItems.size());

        //Load data
        int index = allItems.size();
        int end = index;
        Log.i("William", " Index :: " + index + " end :: " + end);

//        Log.i("William", "My AllItems HERE :: " + allItems.size());
        for (int i = index; i <= end; i++) {
            allItems.add(new ShopObject(array_shop_products.get(i).getTitle(), "" + array_shop_products.get(i).getPrice_html(), array_shop_products.get(i).getFeatured_src(), array_shop_products.get(i).getOn_sale()));
        }

        rViewGrid.scrollToPosition(rowListItem.size() - 22);
        rcAdapter = new RecyclerShopGridViewAdapter(getActivity(), getActivity(), rowListItem, array_shop_products, rViewGrid);
        rViewGrid.setAdapter(rcAdapter);

        onLoadShopListener();

        pbLoadingMoreShopProduct.setVisibility(View.GONE);
        rcAdapter.notifyDataSetChanged();
        rcAdapter.setLoaded();
    }

    private void hide_recycle_view() {
        pbLoadingShopProduct.setVisibility(View.VISIBLE);
        rlShopRecycleView.setVisibility(View.GONE);
        mShopSwipeRefreshLayout.setEnabled(false);
    }

    private void show_recycle_view() {
        pbLoadingShopProduct.setVisibility(View.GONE);
        rlShopRecycleView.setVisibility(View.VISIBLE);
        mShopSwipeRefreshLayout.setEnabled(true);
        mShopSwipeRefreshLayout.setRefreshing(false);
    }

    private void shop_product(JSONObject _response) {
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
                array_shop_products.add(products);
            }
        } catch (JSONException e) {
            Log.i("William", "JSON Error" + e.toString());
        }
    }
}
