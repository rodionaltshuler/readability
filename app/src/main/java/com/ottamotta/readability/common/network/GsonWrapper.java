package com.ottamotta.readability.common.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonWrapper {

    private static final Gson gson = new GsonBuilder().
            setDateFormat("yyyy-MM-DD hh:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }
}
