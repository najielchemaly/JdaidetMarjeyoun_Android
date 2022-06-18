package com.municipality.jdeidetmarjeyoun.location;

import java.io.Serializable;

/**
 * Created by Charbel on 10/21/2017.
 */

public class Location implements Serializable {
    private String name;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
