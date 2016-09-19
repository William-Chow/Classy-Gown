package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/26/2016.
 */
public class ShopObject implements Serializable {

    private String shop_title;
    private String shop_price;
    private String shop_image;
    private String shop_on_sale;

    public ShopObject(String shop_title, String shop_price, String shop_image, String shop_on_sale) {
        this.shop_title = shop_title;
        this.shop_price = shop_price;
        this.shop_image = shop_image;
        this.shop_on_sale = shop_on_sale;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getShop_on_sale() {
        return shop_on_sale;
    }

    public void setShop_on_sale(String shop_on_sale) {
        this.shop_on_sale = shop_on_sale;
    }
}
