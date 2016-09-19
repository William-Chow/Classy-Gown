package com.classygown.Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Chow on 1/11/16.
 */
public class Products_Attributes implements Serializable {

    private String products_attributes_name;
    private ArrayList<Products_Options> products_attributes_option;
    private String products_attributes_position;
    private String products_attributes_slug;
    private String products_attributes_variation;
    private String products_attributes_visible;

    public Products_Attributes(String _products_attributes_name, ArrayList<Products_Options> _products_attributes_option, String _products_attributes_position, String _products_attributes_slug, String _products_attributes_variation, String _products_attributes_visible) {
        this.products_attributes_name = _products_attributes_name;
        this.products_attributes_option = _products_attributes_option;
        this.products_attributes_position = _products_attributes_position;
        this.products_attributes_slug = _products_attributes_slug;
        this.products_attributes_variation = _products_attributes_variation;
        this.products_attributes_visible = _products_attributes_visible;
    }

    public String getProducts_attributes_name() {
        return products_attributes_name;
    }

    public void setProducts_attributes_name(String _products_attributes_name) {
        this.products_attributes_name = _products_attributes_name;
    }

    public ArrayList<Products_Options> getProducts_attributes_option() {
        return products_attributes_option;
    }

    public void setProducts_attributes_option(ArrayList<Products_Options> products_attributes_option) {
        this.products_attributes_option = products_attributes_option;
    }

    public String getProducts_attributes_position() {
        return products_attributes_position;
    }

    public void setProducts_attributes_position(String _products_attributes_position) {
        this.products_attributes_position = _products_attributes_position;
    }

    public String getProducts_attributes_slug() {
        return products_attributes_slug;
    }

    public void setProducts_attributes_slug(String _products_attributes_slug) {
        this.products_attributes_slug = _products_attributes_slug;
    }

    public String getProducts_attributes_variation() {
        return products_attributes_variation;
    }

    public void setProducts_attributes_variation(String _products_attributes_variation) {
        this.products_attributes_variation = _products_attributes_variation;
    }

    public String getProducts_attributes_visible() {
        return products_attributes_visible;
    }

    public void setProducts_attributes_visible(String _products_attributes_visible) {
        this.products_attributes_visible = _products_attributes_visible;
    }
}
