package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/11/16.
 */
public class Variation_Attributes_Obj implements Serializable {

    private String name;
    private String option;
    private String slug;

    public Variation_Attributes_Obj(String _name, String _option, String _slug) {
        this.name = _name;
        this.option = _option;
        this.slug = _slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String _option) {
        this.option = _option;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String _slug) {
        this.slug = _slug;
    }
}
