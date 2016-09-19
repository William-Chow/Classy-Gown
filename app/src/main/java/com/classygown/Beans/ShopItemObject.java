package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/22/2016.
 */
public class ShopItemObject implements Serializable {
    private String product_title;
    private String product_price;
    private String product_image;
    private String on_sale;

    public ShopItemObject(String product_title, String product_price, String product_image, String on_sale) {
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_image = product_image;
        this.on_sale = on_sale;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(String on_sale) {
        this.on_sale = on_sale;
    }
}
