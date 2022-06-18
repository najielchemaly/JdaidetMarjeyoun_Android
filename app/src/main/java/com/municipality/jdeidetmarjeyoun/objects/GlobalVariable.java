package com.municipality.jdeidetmarjeyoun.objects;

/**
 * Created by Charbel on 11/7/2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalVariable {

    @SerializedName("PlaceCategories")
    @Expose
    private List<PlaceCategory> placeCategories = null;
    @SerializedName("ComplaintsType")
    @Expose
    private List<ComplaintsType> complaintsType = null;
    @SerializedName("DirectoryCategories")
    @Expose
    private List<DirectoryCategory> directoryCategories = null;
    @SerializedName("mediaUrl")
    @Expose
    private String mediaUrl;
    @SerializedName("mediaDefaultImage")
    @Expose
    private String mediaDefaultImage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PlaceCategory> getPlaceCategories() {
        return placeCategories;
    }

    public void setPlaceCategories(List<PlaceCategory> placeCategories) {
        this.placeCategories = placeCategories;
    }

    public List<ComplaintsType> getComplaintsType() {
        return complaintsType;
    }

    public void setComplaintsType(List<ComplaintsType> complaintsType) {
        this.complaintsType = complaintsType;
    }

    public List<DirectoryCategory> getDirectoryCategories() {
        return directoryCategories;
    }

    public void setDirectoryCategories(List<DirectoryCategory> directoryCategories) {
        this.directoryCategories = directoryCategories;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaDefaultImage() {
        return mediaDefaultImage;
    }

    public void setMediaDefaultImage(String mediaDefaultImage) {
        this.mediaDefaultImage = mediaDefaultImage;
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