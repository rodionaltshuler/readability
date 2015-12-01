package com.ottamotta.readability.library.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ottamotta.readability.common.network.GsonWrapper;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.Collections;
import java.util.List;

class BookmarksSharedPrefsBookmarksCache implements BookmarksCache {

    private List<Bookmark> bookmarks;

    private Gson gson = GsonWrapper.getGson();

    private static final String KEY_BOOKMARKS = "key_bookmarks";

    private Context context;

    BookmarksSharedPrefsBookmarksCache(Context context) {
        this.context = context;
    }

    @Override
    public void addAll(List<Bookmark> bookmarks) {
        this.bookmarks.addAll(bookmarks);
        saveToL2Cache();
    }

    @Override
    public List<Bookmark> getAll() {
        if (bookmarks == null) {
            bookmarks = loadFromL2Cache();
        }
        return bookmarks;
    }

    @Override
    public void clear() {
        if (bookmarks != null) {
            this.bookmarks.clear();
        }
        clearL2Cache();
    }

    private @NonNull List<Bookmark> loadFromL2Cache() {
        String bookmarks = getPrefs().getString(KEY_BOOKMARKS, null);
        if (bookmarks != null) {
            return gson.fromJson(bookmarks, new TypeToken<List<Bookmark>>() {
            }.getType());
        }
        return Collections.emptyList();
    }

    private void saveToL2Cache() {
        String bookmarksString = gson.toJson(bookmarks);
        getPrefs().edit().putString(KEY_BOOKMARKS, bookmarksString).commit();
    }

    private void clearL2Cache() {
        getPrefs().edit().clear().commit();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(BookmarksRepository.class.getSimpleName(), Context.MODE_PRIVATE);
    }
}
