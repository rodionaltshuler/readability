package com.ottamotta.readability.common.network;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ottamotta.readability.auth.AuthManager;
import com.ottamotta.readability.user.OAuthCredentials;
import com.ottamotta.readability.user.UserManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ReadabilityRestAdapter {

    private RestAdapter restAdapter;

    private static final String serverUrl = "https://www.readability.com";

    private static ReadabilityRestAdapter instance;

    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

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
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    private Client getClient() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        httpClient.setReadTimeout(10, TimeUnit.SECONDS);
        httpClient.networkInterceptors().add(new StethoInterceptor());

        OAuthCredentials credentials = UserManager.getInstance().getoAuthCredentials();
        if (credentials != null) {
            OkHttpOAuthConsumer consumer = fromOAuthCreds(credentials, AuthManager.getClientKey(), AuthManager.getClientSecret());
            httpClient.interceptors().add(new SigningInterceptor(consumer));
        }

        return new OkClient(httpClient);
    }

    public retrofit.RestAdapter getAdapter() {
        return restAdapter;
    }

    private OkHttpOAuthConsumer fromOAuthCreds(OAuthCredentials creds, String clientKey, String clientSecret) {
        OkHttpOAuthConsumer okConsumer = new OkHttpOAuthConsumer(clientKey, clientSecret);
        okConsumer.setTokenWithSecret(creds.getToken(), creds.getTokenSecret());
        return okConsumer;
    }

}
