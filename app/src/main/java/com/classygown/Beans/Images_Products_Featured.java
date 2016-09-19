package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/11/16.
 */
public class Images_Products_Featured implements Serializable {

    private String images_products_featured_alt;
    private String images_products_featured_created_at;
    private String images_products_featured_id;
    private String images_products_featured_position;
    private String images_products_featured_src;
    private String images_products_featured_title;
    private String images_products_featured_updated_at;

    public Images_Products_Featured(String _images_products_featured_alt, String _images_products_featured_created_at, String _images_products_featured_id, String _images_products_featured_position, String _images_products_featured_src, String _images_products_featured_title, String _images_products_featured_updated_at) {
        this.images_products_featured_alt = _images_products_featured_alt;
        this.images_products_featured_created_at = _images_products_featured_created_at;
        this.images_products_featured_id = _images_products_featured_id;
        this.images_products_featured_position = _images_products_featured_position;
        this.images_products_featured_src = _images_products_featured_src;
        this.images_products_featured_title = _images_products_featured_title;
        this.images_products_featured_updated_at = _images_products_featured_updated_at;
    }

    public String getImages_products_featured_alt() {
        return images_products_featured_alt;
    }

    public void setImages_products_featured_alt(String _images_products_featured_alt) {
        this.images_products_featured_alt = _images_products_featured_alt;
    }

    public String getImages_products_featured_created_at() {
        return images_products_featured_created_at;
    }

    public void setImages_products_featured_created_at(String _images_products_featured_created_at) {
        this.images_products_featured_created_at = _images_products_featured_created_at;
    }

    public String getImages_products_featured_id() {
        return images_products_featured_id;
    }

    public void setImages_products_featured_id(String _images_products_featured_id) {
        this.images_products_featured_id = _images_products_featured_id;
    }

    public String getImages_products_featured_position() {
        return images_products_featured_position;
    }

    public void setImages_products_featured_position(String _images_products_featured_position) {
        this.images_products_featured_position = _images_products_featured_position;
    }

    public String getImages_products_featured_src() {
        return images_products_featured_src;
    }

    public void setImages_products_featured_src(String _images_products_featured_src) {
        this.images_products_featured_src = _images_products_featured_src;
    }

    public String getImages_products_featured_title() {
        return images_products_featured_title;
    }

    public void setImages_products_featured_title(String _images_products_featured_title) {
        this.images_products_featured_title = _images_products_featured_title;
    }

    public String getImages_products_featured_updated_at() {
        return images_products_featured_updated_at;
    }

    public void setImages_products_featured_updated_at(String _images_products_featured_updated_at) {
        this.images_products_featured_updated_at = _images_products_featured_updated_at;
    }
}
