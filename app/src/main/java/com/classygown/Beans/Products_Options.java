package com.classygown.Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Chow on 1/20/2016.
 */
public class Products_Options implements Serializable {

    private String name;
    private ArrayList<String> option;

    public Products_Options(String name, ArrayList<String> option) {
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOption() {
        return option;
    }

    public void setOption(ArrayList<String> option) {
        this.option = option;
    }
}
