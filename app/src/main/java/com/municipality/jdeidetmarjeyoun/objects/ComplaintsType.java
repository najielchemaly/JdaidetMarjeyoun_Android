package com.municipality.jdeidetmarjeyoun.objects;

/**
 * Created by Charbel on 11/7/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.municipality.jdeidetmarjeyoun.common.AbstractNameable;
import com.municipality.jdeidetmarjeyoun.common.DefaultNameable;
import com.municipality.jdeidetmarjeyoun.common.Nameable;

public class ComplaintsType extends AbstractNameable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("language")
    @Expose
    private String language;


    public ComplaintsType(){
        setSelectLabel(title);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return title;
    }
}