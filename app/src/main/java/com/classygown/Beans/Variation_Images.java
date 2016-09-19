package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/11/16.
 */
public class Variation_Images implements Serializable {

    private String image_alt;
    private String image_created_at;
    private String image_id;
    private String image_position;
    private String image_src;
    private String image_title;
    private String image_updated_at;

    public Variation_Images(String _image_alt, String _image_created_at, String _image_id, String _image_position, String _image_src, String _image_title, String _image_updated_at) {
        this.image_alt = _image_alt;
        this.image_created_at = _image_created_at;
        this.image_id = _image_id;
        this.image_position = _image_position;
        this.image_src = _image_src;
        this.image_title = _image_title;
        this.image_updated_at = _image_updated_at;
    }

    public String getImage_alt() {
        return image_alt;
    }

    public void setImage_alt(String _image_alt) {
        this.image_alt = _image_alt;
    }

    public String getImage_created_at() {
        return image_created_at;
    }

    public void setImage_created_at(String _image_created_at) {
        this.image_created_at = _image_created_at;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String _image_id) {
        this.image_id = _image_id;
    }

    public String getImage_position() {
        return image_position;
    }

    public void setImage_position(String _image_position) {
        this.image_position = _image_position;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String _image_src) {
        this.image_src = _image_src;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String _image_title) {
        this.image_title = _image_title;
    }

    public String getImage_updated_at() {
        return image_updated_at;
    }

    public void setImage_updated_at(String _image_updated_at) {
        this.image_updated_at = _image_updated_at;
    }
}
