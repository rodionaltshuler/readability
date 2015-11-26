package com.ottamotta.readability.library.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ottamotta.readability.ReadabilityApp;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.common.network.GsonWrapper;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.network.BookmarkRestServiceWrapper;

import java.util.List;

public class BookmarksRepository {

    private static final String TAG = BookmarksRepository.class.getSimpleName();

    private List<Bookmark> bookmarks = Lists.newArrayList();

    private BookmarkRestServiceWrapper restServiceWrapper = BookmarkRestServiceWrapper.getInstance();

    private Gson gson = GsonWrapper.getGson();

    private static final String KEY_BOOKMARKS = "key_bookmarks";

    BookmarksRepository() {

    }

    @WorkerThread
    private @NonNull List<Bookmark> loadFromCache() {
        String bookmarks = getPrefs().getString(KEY_BOOKMARKS, null);
        if (bookmarks != null) {
            return gson.fromJson(bookmarks, new TypeToken<List<Bookmark>>() {}.getType());
        }
        return Lists.newArrayList();

    }

    @WorkerThread
    private void saveToCache() {
        String bookmarksString = gson.toJson(bookmarks);
        getPrefs().edit().putString(KEY_BOOKMARKS, bookmarksString).commit();
    }

    @WorkerThread
    private void clearCache() {
        bookmarks.clear();
        getPrefs().edit().clear().commit();
    }

    @WorkerThread
    private List<Bookmark> loadFromServer() throws ReadabilityException {
        return restServiceWrapper.getBookmarks();
    }


    @WorkerThread
    public List<Bookmark> getAll(boolean invalidateCache) throws ReadabilityException {
        if (bookmarks.isEmpty() && !invalidateCache) {
            Log.d(TAG, "Bookmarks is empty - loading from cache");
            bookmarks = loadFromCache();
            Log.d(TAG, "Bookmarks size after loading from cache: " + bookmarks.size());
        }
        if (bookmarks.isEmpty() || invalidateCache) {
            Log.d(TAG, "Bookmarks cache is empty - loading from server");
            bookmarks = loadFromServer();
            Log.d(TAG, "Bookmarks size after loading from server: " + bookmarks.size());
            saveToCache();
        }
        return Lists.newArrayList(bookmarks);
    }


    private SharedPreferences getPrefs() {
        return ReadabilityApp.getInstance().getSharedPreferences(BookmarksRepository.class.getSimpleName(), Context.MODE_PRIVATE);
    }


}
