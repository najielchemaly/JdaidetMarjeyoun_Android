package com.municipality.jdeidetmarjeyoun.common;

/**
 * Created by Charbel on 11/23/2017.
 */

public abstract class AbstractNameable implements Nameable {

    private String label;

    @Override
    public String getSelectLabel() {
        return label;
    }

    @Override
    public void setSelectLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
