package com.municipality.jdeidetmarjeyoun.objects;

/**
 * Created by Charbel on 11/17/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Directory {

    @SerializedName("directories")
    @Expose
    private List<Directory_> directories = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Directory_> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory_> directories) {
        this.directories = directories;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}