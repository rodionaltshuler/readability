package com.ottamotta.readability.user;

import com.google.gson.annotations.Expose;

public class OAuthCredentials {

    @Expose
    private final String token;

    @Expose
    private final String tokenSecret;

    public OAuthCredentials(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public String toString() {
        return "OAuthCredentials{" +
                "token='" + token + '\'' +
                ", tokenSecret='" + tokenSecret + '\'' +
                '}';
    }
}
