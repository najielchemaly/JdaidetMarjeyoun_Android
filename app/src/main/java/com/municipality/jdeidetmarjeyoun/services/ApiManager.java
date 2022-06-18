package com.municipality.jdeidetmarjeyoun.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Charbel on 8/7/2017.
 */

public final class ApiManager {

    // DEMO
    private static final String API_URL = "http://jdeidetmarjeyoun.com/Api/";
//    private static final String API_URL = "http://192.168.0.105/Baladiyeh/Api/";

    // private static Retrofit retrofit = null;
    private static boolean isConfig;

    private static Retrofit retrofit = null;
    private static final ServiceInterface serviceInterface = getClient().create(ServiceInterface.class);

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static ServiceInterface getService() {
        return serviceInterface;
    }

//    public static Retrofit getClient() {
//        Gson gson = new GsonBuilder().setLenient().create();
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = null;
////                if (isConfig) {
////                    requestBuilder = original.newBuilder().header("ID", "1");
////                } else {
////                    //LoadingActivity.appConfig.getTOKEN()
////                    requestBuilder = original.newBuilder().header("TOKEN","3c05274b82c61a385103ae9372adb5d5" );
////                }
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//
//            OkHttpClient client = httpClient
//                    .readTimeout(300000, TimeUnit.MILLISECONDS)
//                    .writeTimeout(300000, TimeUnit.MILLISECONDS)
//                    .connectTimeout(300000, TimeUnit.MILLISECONDS)
//
//                    .build();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(API_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(client)
//                    .build();
//
//        return retrofit;
//    }
//
//    private static final ServiceInterface serviceInterface = getClient().create(ServiceInterface.class);
//
//    public static ServiceInterface getService() {
//        return getService(false);
//    }
//    public static ServiceInterface getService(boolean isConfig) {
//        ApiManager.isConfig = isConfig;
//        return serviceInterface;
//    }

    public static String getApiUrl() {
        return API_URL;
    }
}
