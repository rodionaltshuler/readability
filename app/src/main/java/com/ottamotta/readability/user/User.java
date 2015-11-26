package com.ottamotta.readability.user;

import com.google.gson.annotations.Expose;
import com.ottamotta.readability.user.OAuthCredentials;

public class User {

    @Expose
    private String id;

    @Expose
    private String username;

    @Expose
    private OAuthCredentials oAuthCredentials;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OAuthCredentials getoAuthCredentials() {
        return oAuthCredentials;
    }

    public void setoAuthCredentials(OAuthCredentials oAuthCredentials) {
        this.oAuthCredentials = oAuthCredentials;
    }
}
