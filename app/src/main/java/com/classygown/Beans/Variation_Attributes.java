package com.classygown.Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Chow on 1/11/16.
 */
public class Variation_Attributes implements Serializable {

    private ArrayList<Variation_Attributes_Obj> variation_attributes_obj;

    private String variation_created_at;
    private String back_ordered;

    private Variation_Dimensions_Image variation_dimensions_image;

    private String download_expiry;
    private String download_limit;
    private String id;

    private ArrayList<Variation_Images> variation_images;

    private String variation_in_stock;
    private String variation_managing_stock;
    private String variation_on_sale;
    private String variation_permalink;
    private String variation_price;
    private String variation_purchaseable;
    private String variation_regular_price;
    private String variation_sale_price;
    private String variation_shipping_class;
    private String variation_shipping_class_id;
    private String variation_sku;
    private String variation_stock_quantity;
    private String variation_tax_class;
    private String variation_tax_status;
    private String variation_taxable;
    private String variation_updated_at;
    private String variation_weight;

    public Variation_Attributes(ArrayList<Variation_Attributes_Obj> _variation_attributes_obj, String _variation_created_at, String _back_ordered, Variation_Dimensions_Image _variation_dimensions_image, String _download_expiry, String _download_limit, String _id, ArrayList<Variation_Images> _variation_images, String _variation_in_stock, String _variation_managing_stock, String _variation_on_sale, String _variation_permalink, String _variation_price, String _variation_purchaseable, String _variation_regular_price, String _variation_sale_price, String _variation_shipping_class, String _variation_shipping_class_id, String _variation_sku, String _variation_stock_quantity, String _variation_tax_class, String _variation_tax_status, String _variation_taxable, String _variation_updated_at, String _variation_weight) {
        this.variation_attributes_obj = _variation_attributes_obj;
        this.variation_created_at = _variation_created_at;
        this.back_ordered = _back_ordered;
        this.variation_dimensions_image = _variation_dimensions_image;
        this.download_expiry = _download_expiry;
        this.download_limit = _download_limit;
        this.id = _id;
        this.variation_images = _variation_images;
        this.variation_in_stock = _variation_in_stock;
        this.variation_managing_stock = _variation_managing_stock;
        this.variation_on_sale = _variation_on_sale;
        this.variation_permalink = _variation_permalink;
        this.variation_price = _variation_price;
        this.variation_purchaseable = _variation_purchaseable;
        this.variation_regular_price = _variation_regular_price;
        this.variation_sale_price = _variation_sale_price;
        this.variation_shipping_class = _variation_shipping_class;
        this.variation_shipping_class_id = _variation_shipping_class_id;
        this.variation_sku = _variation_sku;
        this.variation_stock_quantity = _variation_stock_quantity;
        this.variation_tax_class = _variation_tax_class;
        this.variation_tax_status = _variation_tax_status;
        this.variation_taxable = _variation_taxable;
        this.variation_updated_at = _variation_updated_at;
        this.variation_weight = _variation_weight;
    }

    public ArrayList<Variation_Attributes_Obj> getVariation_attributes_obj() {
        return variation_attributes_obj;
    }

    public void setVariation_attributes_obj(ArrayList<Variation_Attributes_Obj> _variation_attributes_obj) {
        this.variation_attributes_obj = _variation_attributes_obj;
    }

    public String getVariation_created_at() {
        return variation_created_at;
    }

    public void setVariation_created_at(String _variation_created_at) {
        this.variation_created_at = _variation_created_at;
    }

    public String getBack_ordered() {
        return back_ordered;
    }

    public void setBack_ordered(String _back_ordered) {
        this.back_ordered = _back_ordered;
    }

    public Variation_Dimensions_Image getVariation_dimensions_image() {
        return variation_dimensions_image;
    }

    public void setVariation_dimensions_image(Variation_Dimensions_Image _variation_dimensions_image) {
        this.variation_dimensions_image = _variation_dimensions_image;
    }

    public String getDownload_expiry() {
        return download_expiry;
    }

    public void setDownload_expiry(String _download_expiry) {
        this.download_expiry = _download_expiry;
    }

    public String getDownload_limit() {
        return download_limit;
    }

    public void setDownload_limit(String _download_limit) {
        this.download_limit = _download_limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public ArrayList<Variation_Images> getVariation_images() {
        return variation_images;
    }

    public void setVariation_images(ArrayList<Variation_Images> _variation_images) {
        this.variation_images = _variation_images;
    }

    public String getVariation_in_stock() {
        return variation_in_stock;
    }

    public void setVariation_in_stock(String _variation_in_stock) {
        this.variation_in_stock = _variation_in_stock;
    }

    public String getVariation_managing_stock() {
        return variation_managing_stock;
    }

    public void setVariation_managing_stock(String _variation_managing_stock) {
        this.variation_managing_stock = _variation_managing_stock;
    }

    public String getVariation_on_sale() {
        return variation_on_sale;
    }

    public void setVariation_on_sale(String _variation_on_sale) {
        this.variation_on_sale = _variation_on_sale;
    }

    public String getVariation_permalink() {
        return variation_permalink;
    }

    public void setVariation_permalink(String _variation_permalink) {
        this.variation_permalink = _variation_permalink;
    }

    public String getVariation_price() {
        return variation_price;
    }

    public void setVariation_price(String _variation_price) {
        this.variation_price = _variation_price;
    }

    public String getVariation_purchaseable() {
        return variation_purchaseable;
    }

    public void setVariation_purchaseable(String _variation_purchaseable) {
        this.variation_purchaseable = _variation_purchaseable;
    }

    public String getVariation_regular_price() {
        return variation_regular_price;
    }

    public void setVariation_regular_price(String _variation_regular_price) {
        this.variation_regular_price = _variation_regular_price;
    }

    public String getVariation_sale_price() {
        return variation_sale_price;
    }

    public void setVariation_sale_price(String _variation_sale_price) {
        this.variation_sale_price = _variation_sale_price;
    }

    public String getVariation_shipping_class() {
        return variation_shipping_class;
    }

    public void setVariation_shipping_class(String _variation_shipping_class) {
        this.variation_shipping_class = _variation_shipping_class;
    }

    public String getVariation_shipping_class_id() {
        return variation_shipping_class_id;
    }

    public void setVariation_shipping_class_id(String _variation_shipping_class_id) {
        this.variation_shipping_class_id = _variation_shipping_class_id;
    }

    public String getVariation_sku() {
        return variation_sku;
    }

    public void setVariation_sku(String _variation_sku) {
        this.variation_sku = _variation_sku;
    }

    public String getVariation_stock_quantity() {
        return variation_stock_quantity;
    }

    public void setVariation_stock_quantity(String _variation_stock_quantity) {
        this.variation_stock_quantity = _variation_stock_quantity;
    }

    public String getVariation_tax_class() {
        return variation_tax_class;
    }

    public void setVariation_tax_class(String _variation_tax_class) {
        this.variation_tax_class = _variation_tax_class;
    }

    public String getVariation_tax_status() {
        return variation_tax_status;
    }

    public void setVariation_tax_status(String _variation_tax_status) {
        this.variation_tax_status = _variation_tax_status;
    }

    public String getVariation_taxable() {
        return variation_taxable;
    }

    public void setVariation_taxable(String _variation_taxable) {
        this.variation_taxable = _variation_taxable;
    }

    public String getVariation_updated_at() {
        return variation_updated_at;
    }

    public void setVariation_updated_at(String _variation_updated_at) {
        this.variation_updated_at = _variation_updated_at;
    }

    public String getVariation_weight() {
        return variation_weight;
    }

    public void setVariation_weight(String _variation_weight) {
        this.variation_weight = _variation_weight;
    }
}
