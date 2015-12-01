package com.ottamotta.readability.library.manager.network;

import com.ottamotta.readability.common.network.ReadabilityRestAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MockRetrofitClient implements Client {

    private static final String DEFAULT_RESPONSE_STRING = "{}";
    private static final int DEFAULT_STATUS_CODE = 200;
    private static final String DEFAULT_STATUS_REASON = "Ok";
    private static final String DEFAULT_URL = "https://www.readability.com";
    private static final List<Header> DEFAULT_HEADERS = Collections.emptyList();

    private String responseString = DEFAULT_RESPONSE_STRING;
    private int statusCode = DEFAULT_STATUS_CODE;
    private String statusReason = DEFAULT_STATUS_REASON;
    private String url = DEFAULT_URL;
    private List<Header> headers = DEFAULT_HEADERS;

    @Override
    public Response execute(Request request) throws IOException {
        return new Response(url, statusCode, statusReason, headers, new TypedByteArray("application/json", responseString.getBytes()));
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }
}
