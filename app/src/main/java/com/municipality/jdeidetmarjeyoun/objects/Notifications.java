
package com.municipality.jdeidetmarjeyoun.objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notifications {

    @SerializedName("notifications")
    @Expose
    private List<Notification> notifcations = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Notification> getNotifcations() {
        return notifcations;
    }

    public void setNotifcations(List<Notification> notifcations) {
        this.notifcations = notifcations;
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
