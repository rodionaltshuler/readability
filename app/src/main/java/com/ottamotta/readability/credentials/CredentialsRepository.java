package com.ottamotta.readability.credentials;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

class CredentialsRepository {

    private static final String PREFS_NAME = CredentialsRepository.class.getSimpleName();

    private static final String ME_KEY = "me_user";

    private Context context;

    private Gson gson = new Gson();

    CredentialsRepository(Context context) {
        this.context = context;
    }

    public void save(OAuthCredentials credentials) {
        getPrefs().edit().putString(ME_KEY, gson.toJson(credentials, OAuthCredentials.class)).commit();
    }

    @Nullable
    public OAuthCredentials getCredentials() {
        String json = getPrefs().getString(ME_KEY, null);
        if (json != null) {
            return gson.fromJson(json, OAuthCredentials.class);
        }
        return null;
    }

    public void clear() {
        getPrefs().edit().clear().apply();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

}
