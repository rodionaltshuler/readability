package com.ottamotta.readability.common.network;

import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.ottamotta.readability.auth.AuthManager;
import com.ottamotta.readability.credentials.CredentialsManager;
import com.ottamotta.readability.credentials.OAuthCredentials;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ReadabilityRestAdapter {

    private static final String TAG = ReadabilityRestAdapter.class.getSimpleName();

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
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(GsonWrapper.getGson()))
                .build();
    }

    private Client getClient() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        httpClient.setReadTimeout(10, TimeUnit.SECONDS);
        httpClient.networkInterceptors().add(new StethoInterceptor());

        OAuthCredentials credentials = CredentialsManager.getInstance().getoAuthCredentials();
        if (credentials != null) {
            OkHttpOAuthConsumer consumer = fromOAuthCreds(credentials, AuthManager.getClientKey(), AuthManager.getClientSecret());
            httpClient.interceptors().add(new SigningInterceptor(consumer));
        } else {
            Log.d(TAG, "Credentials is NULL");
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
