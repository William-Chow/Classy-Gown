package com.classygown.Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Chow on 1/11/16.
 */
public class Products implements Serializable {

    private ArrayList<Products_Attributes> products_attributes;
    private ArrayList<Images_Products_Featured> images_products_featured;
    private String average_rating;
    private String categories;
    private String created_at;
    private String description;

    private Image_Dimensions image_dimensions;

    private String featured_src;
    private String in_stock;
    private String managing_stock;
    private String on_sale;
    private String permalink;
    private String price;
    private String price_html;
    private String purchase_able;
    private String rating_count;
    private String regular_price;
    private String reviews_allowed;
    private String shipping_class;
    private String shipping_class_id;
    private String shipping_required;
    private String shipping_taxable;
    private String short_description;
    private String sku;
    private String sold_individually;
    private String status;
    private String stock_quantity;
    private String tags;
    private String tax_class;
    private String tax_status;
    private String taxable;
    private String title;
    private String total_sales;
    private String type;
    private String updated_at;

    private Variation_Attributes variation_attributes;

    public Products(ArrayList<Products_Attributes> _products_attributes, ArrayList<Images_Products_Featured> _images_products_featured, String _average_rating, String _categories, String _created_at, String _description, Image_Dimensions _image_dimensions, String _featured_src, String _in_stock, String _managing_stock, String _on_sale, String _permalink, String _price, String _price_html, String _purchase_able, String _rating_count, String _regular_price, String _reviews_allowed, String _shipping_class, String _shipping_class_id, String _shipping_required, String _shipping_taxable, String _short_description, String _sku, String _sold_individually, String _status, String _stock_quantity, String _tags, String _tax_class, String _tax_status, String _taxable, String _title, String _total_sales, String _type, String _updated_at, Variation_Attributes _variation_attributes) {
        this.products_attributes = _products_attributes;
        this.images_products_featured = _images_products_featured;
        this.average_rating = _average_rating;
        this.categories = _categories;
        this.created_at = _created_at;
        this.description = _description;
        this.image_dimensions = _image_dimensions;
        this.featured_src = _featured_src;
        this.in_stock = _in_stock;
        this.managing_stock = _managing_stock;
        this.on_sale = _on_sale;
        this.permalink = _permalink;
        this.price = _price;
        this.price_html = _price_html;
        this.purchase_able = _purchase_able;
        this.rating_count = _rating_count;
        this.regular_price = _regular_price;
        this.reviews_allowed = _reviews_allowed;
        this.shipping_class = _shipping_class;
        this.shipping_class_id = _shipping_class_id;
        this.shipping_required = _shipping_required;
        this.shipping_taxable = _shipping_taxable;
        this.short_description = _short_description;
        this.sku = _sku;
        this.sold_individually = _sold_individually;
        this.status = _status;
        this.stock_quantity = _stock_quantity;
        this.tags = _tags;
        this.tax_class = _tax_class;
        this.tax_status = _tax_status;
        this.taxable = _taxable;
        this.title = _title;
        this.total_sales = _total_sales;
        this.type = _type;
        this.updated_at = _updated_at;
        this.variation_attributes = _variation_attributes;
    }

    public ArrayList<Products_Attributes> getProducts_attributes() {
        return products_attributes;
    }

    public void setProducts_attributes(ArrayList<Products_Attributes> _products_attributes) {
        this.products_attributes = _products_attributes;
    }

    public ArrayList<Images_Products_Featured> getImages_products_featured() {
        return images_products_featured;
    }

    public void setImages_products_featured(ArrayList<Images_Products_Featured> _images_products_featured) {
        this.images_products_featured = _images_products_featured;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String _average_rating) {
        this.average_rating = _average_rating;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String _categories) {
        this.categories = _categories;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String _created_at) {
        this.created_at = _created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public Image_Dimensions getImage_dimensions() {
        return image_dimensions;
    }

    public void setImage_dimensions(Image_Dimensions _image_dimensions) {
        this.image_dimensions = _image_dimensions;
    }

    public String getFeatured_src() {
        return featured_src;
    }

    public void setFeatured_src(String _featured_src) {
        this.featured_src = _featured_src;
    }

    public String getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(String _in_stock) {
        this.in_stock = _in_stock;
    }

    public String getManaging_stock() {
        return managing_stock;
    }

    public void setManaging_stock(String _managing_stock) {
        this.managing_stock = _managing_stock;
    }

    public String getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(String _on_sale) {
        this.on_sale = _on_sale;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String _permalink) {
        this.permalink = _permalink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String _price) {
        this.price = _price;
    }

    public String getPrice_html() {
        return price_html;
    }

    public void setPrice_html(String _price_html) {
        this.price_html = _price_html;
    }

    public String getPurchase_able() {
        return purchase_able;
    }

    public void setPurchase_able(String _purchase_able) {
        this.purchase_able = _purchase_able;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String _rating_count) {
        this.rating_count = _rating_count;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String _regular_price) {
        this.regular_price = _regular_price;
    }

    public String getReviews_allowed() {
        return reviews_allowed;
    }

    public void setReviews_allowed(String _reviews_allowed) {
        this.reviews_allowed = _reviews_allowed;
    }

    public String getShipping_class() {
        return shipping_class;
    }

    public void setShipping_class(String _shipping_class) {
        this.shipping_class = _shipping_class;
    }

    public String getShipping_class_id() {
        return shipping_class_id;
    }

    public void setShipping_class_id(String _shipping_class_id) {
        this.shipping_class_id = _shipping_class_id;
    }

    public String getShipping_required() {
        return shipping_required;
    }

    public void setShipping_required(String _shipping_required) {
        this.shipping_required = _shipping_required;
    }

    public String getShipping_taxable() {
        return shipping_taxable;
    }

    public void setShipping_taxable(String _shipping_taxable) {
        this.shipping_taxable = _shipping_taxable;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String _short_description) {
        this.short_description = _short_description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String _sku) {
        this.sku = _sku;
    }

    public String getSold_individually() {
        return sold_individually;
    }

    public void setSold_individually(String _sold_individually) {
        this.sold_individually = _sold_individually;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String _status) {
        this.status = _status;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String _stock_quantity) {
        this.stock_quantity = _stock_quantity;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String _tags) {
        this.tags = _tags;
    }

    public String getTax_class() {
        return tax_class;
    }

    public void setTax_class(String _tax_class) {
        this.tax_class = _tax_class;
    }

    public String getTax_status() {
        return tax_status;
    }

    public void setTax_status(String _tax_status) {
        this.tax_status = _tax_status;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String _taxable) {
        this.taxable = _taxable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public String getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(String _total_sales) {
        this.total_sales = _total_sales;
    }

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        this.type = _type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String _updated_at) {
        this.updated_at = _updated_at;
    }

    public Variation_Attributes getVariation_attributes() {
        return variation_attributes;
    }

    public void setVariation_attributes(Variation_Attributes _variation_attributes) {
        this.variation_attributes = _variation_attributes;
    }
}
