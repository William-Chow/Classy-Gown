package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/11/16.
 */
public class Variation_Dimensions_Image implements Serializable {

    private String variation_height;
    private String variation_length;
    private String variation_unit;
    private String variation_width;

    public Variation_Dimensions_Image(String _variation_height, String _variation_length, String _variation_unit, String _variation_width) {
        this.variation_height = _variation_height;
        this.variation_length = _variation_length;
        this.variation_unit = _variation_unit;
        this.variation_width = _variation_width;
    }

    public String getVariation_height() {
        return variation_height;
    }

    public void setVariation_height(String _variation_height) {
        this.variation_height = _variation_height;
    }

    public String getVariation_length() {
        return variation_length;
    }

    public void setVariation_length(String _variation_length) {
        this.variation_length = _variation_length;
    }

    public String getVariation_unit() {
        return variation_unit;
    }

    public void setVariation_unit(String _variation_unit) {
        this.variation_unit = _variation_unit;
    }

    public String getVariation_width() {
        return variation_width;
    }

    public void setVariation_width(String _variation_width) {
        this.variation_width = _variation_width;
    }
}
