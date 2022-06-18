package com.municipality.jdeidetmarjeyoun.services;


import com.municipality.jdeidetmarjeyoun.news.ObjectNews;
import com.municipality.jdeidetmarjeyoun.objects.AboutUs;
import com.municipality.jdeidetmarjeyoun.objects.Directory;
import com.municipality.jdeidetmarjeyoun.objects.Fees;
import com.municipality.jdeidetmarjeyoun.objects.FeesInfo;
import com.municipality.jdeidetmarjeyoun.objects.HighlightedNews;
import com.municipality.jdeidetmarjeyoun.objects.GlobalVariable;
import com.municipality.jdeidetmarjeyoun.objects.Link;
import com.municipality.jdeidetmarjeyoun.objects.LoginUser;
import com.municipality.jdeidetmarjeyoun.objects.Notification;
import com.municipality.jdeidetmarjeyoun.objects.Notifications;
import com.municipality.jdeidetmarjeyoun.objects.Profile;
import com.municipality.jdeidetmarjeyoun.objects.RegisteredUser;
import com.municipality.jdeidetmarjeyoun.objects.SimpleResponse;
import com.municipality.jdeidetmarjeyoun.objects.SocialNews;
import com.municipality.jdeidetmarjeyoun.objects.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Charbel on 8/7/2017.
 */

public interface ServiceInterface {

    @GET("getNews/")
    Call<ObjectNews> getNews(@Query("type") String type, @Query("lang") String language);

    @GET("getGlobalVariables/")
    Call<GlobalVariable> getGlobalVariables(@Query("lang") String language);

    @GET("getHighlightedNews/")
    Call<HighlightedNews> getHighlightedNews(@Query("lang") String language);

    @FormUrlEncoded
    @POST("getMunicipalityFees/")
    Call<FeesInfo> getFees(@Query("lang") String language,
                           @Field("blocknumber") String blocknumber,
                           @Field("section") String section,
                           @Field("year") String year);

    @GET("getAboutUs/")
    Call<AboutUs> getAboutUs(@Query("lang") String language);

    @FormUrlEncoded
    @POST("registerUser/")
    Call<Profile> registerUser(@Field("userName") String userName, @Field("fullName") String fullName,
                               @Field("phoneNumber") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("sendComplaint/")
    Call<SimpleResponse> sendComplaint(@Field("dataId") String dataId, @Field("fullName") String fullName, @Field("phoneNumber") String phoneNumber,
                                       @Field("compaintType") String compaintType, @Field("description") String description);

    @GET("getDirectory/")
    Call<Directory> getDirectory();

    @GET("getNotifications/")
    Call<Notifications> getNotifications(@Query("lang") String language);

    @FormUrlEncoded
    @POST("changePassword/")
    Call<SimpleResponse> changePassword(@Field("id") String id, @Field("password") String password);

    @FormUrlEncoded
    @POST("editProfile/")
    Call<RegisteredUser> editProfile(@Field("id") String id, @Field("fullName") String fullName, @Field("phoneNumber") String phoneNumber,
                           @Field("email") String email, @Field("address") String address);

    @FormUrlEncoded
    @POST("login/")
    Call<RegisteredUser> login(@Field("userName") String userName, @Field("password") String password);

    @GET("getSocialNews/")
    Call<SocialNews> getSocialNews(@Query("lang") String language);

    @GET("getUsefullLinks/")
    Call<Link> getUsefullLinks();

    @Multipart
    @POST("uploadImage/")
    Call<Object> uploadImage(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("forgotPassword/")
    Call<SimpleResponse> forgotPassword(@Field("email") String email);

}
