package com.ottamotta.readability.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

class UserRepository {

    private static final String PREFS_NAME = UserRepository.class.getSimpleName();

    private static final String ME_KEY = "me_user";

    private Context context;

    private Gson gson = new Gson();

    private User lastUser;

    private User defaultUser = new User("rodion.altshuler@gmail.com", "Rodion Altshuler");

    UserRepository(Context context) {
        this.context = context;
    }

    public void save(User entity) {
        lastUser = entity;
        getPrefs().edit().putString(ME_KEY, gson.toJson(entity, User.class));
    }

    @Nullable
    public User getMe() {
        if (lastUser != null) {
            return lastUser;
        }
        String userJson = getPrefs().getString(ME_KEY, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }

        return defaultUser; //for debug only
    }

    public void clear() {
        lastUser = null;
        getPrefs().edit().clear().apply();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

}
