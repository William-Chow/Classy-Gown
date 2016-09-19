package com.classygown.Beans;

import java.io.Serializable;

/**
 * Created by William Chow on 1/11/16.
 */
public class Image_Dimensions implements Serializable {

    private String height;
    private String length;
    private String unit;
    private String width;

    public Image_Dimensions(String _height, String _length, String _unit, String _width) {
        this.height = _height;
        this.length = _length;
        this.unit = _unit;
        this.width = _width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String _height) {
        this.height = _height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String _length) {
        this.length = _length;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String _unit) {
        this.unit = _unit;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String _width) {
        this.width = _width;
    }
}
