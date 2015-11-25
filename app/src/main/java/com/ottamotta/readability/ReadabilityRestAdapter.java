package com.ottamotta.readability;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

public class ReadabilityRestAdapter {

    private RestAdapter restAdapter;

    private static final String serverUrl = "https://www.readability.com";

    private static ReadabilityRestAdapter instance;

    public static synchronized ReadabilityRestAdapter getInstance() {
        if (null == instance) {
            instance = new ReadabilityRestAdapter();
        }
        return instance;
    }

    private ReadabilityRestAdapter() {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(serverUrl)
                .setClient(getClient())
                .build();
    }

    private Client getClient(){
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        httpClient.setReadTimeout(10, TimeUnit.SECONDS);
        httpClient.networkInterceptors().add(new StethoInterceptor());
        return new OkClient(httpClient);
    }

    public retrofit.RestAdapter getAdapter() {
        return restAdapter;
    }

}
