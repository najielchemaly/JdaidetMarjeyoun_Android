
package com.municipality.jdeidetmarjeyoun.news;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.municipality.jdeidetmarjeyoun.objects.News;

public class ObjectNews implements Serializable, Parcelable
{

    @SerializedName("news")
    @Expose
    private List<News> news = null;
    public final static Creator<ObjectNews> CREATOR = new Creator<ObjectNews>() {

        @SuppressWarnings({
            "unchecked"
        })
        public ObjectNews createFromParcel(Parcel in) {
            return new ObjectNews(in);
        }

        public ObjectNews[] newArray(int size) {
            return (new ObjectNews[size]);
        }

    };

    private final static long serialVersionUID = -2902666167374722994L;

    protected ObjectNews(Parcel in) {
        in.readList(this.news, (News.class.getClassLoader()));
    }

    public ObjectNews() {
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(news);
    }

    public int describeContents() {
        return  0;
    }

}
