package com.ottamotta.readability.library;

import android.os.Handler;
import android.os.Looper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.network.AddBookmarkResponse;
import com.ottamotta.readability.library.network.BookmarkRestServiceWrapper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarksManager {

    private static BookmarksManager instance;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Set<Listener> listeners = Sets.newConcurrentHashSet();

    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    private BookmarkRestServiceWrapper restServiceWrapper = BookmarkRestServiceWrapper.getInstance();

    private List<Bookmark> loadedBookmarks = Lists.newArrayList();

    public static abstract class Listener {
        void onBookmarkAdded(AddBookmarkResponse addBookmarkResponse) {}
        void onBookmarkAddFailed(ReadabilityException e) {}
        void onBookmarksLoaded(List<Bookmark> bookmarks) {}
        void onBookmarksLoadingFailed(ReadabilityException e) {}
    }

    public static synchronized BookmarksManager getInstance() {
        if (null == instance) {
            instance = new BookmarksManager();
        }
        return instance;
    }

    private BookmarksManager() {

    }

    public void register(Listener listener) {
        listeners.add(listener);
    }

    public void unregister(Listener listener) {
        listeners.remove(listener);
    }

    public void addBookmark(final String url) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    AddBookmarkResponse addBookmarkResponse = restServiceWrapper.bookmark(url);
                    notifyBookmarkAdded(addBookmarkResponse);
                } catch (ReadabilityException e) {
                    notifyBookmarkAddFailed(e);
                }
            }
        });
    }

    public void loadBookmarks() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Bookmark> bookmarks = restServiceWrapper.getBookmarks();
                    loadedBookmarks.addAll(bookmarks);
                    notifyBookmarksLoaded(bookmarks);
                } catch (ReadabilityException e) {
                    notifyBookmarksLoadingFailed(e);
                }
            }
        });
    }

    private void notifyBookmarkAdded(final AddBookmarkResponse addBookmarkResponse) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Listener l : listeners) {
                    l.onBookmarkAdded(addBookmarkResponse);
                }
            }
        });
    }


    private void notifyBookmarkAddFailed(final ReadabilityException e) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Listener l : listeners) {
                    l.onBookmarkAddFailed(e);
                }
            }
        });
    }


    private void notifyBookmarksLoaded(final List<Bookmark> bookmarks) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Listener l : listeners) {
                    l.onBookmarksLoaded(bookmarks);
                }
            }
        });
    }

    private void notifyBookmarksLoadingFailed(final  ReadabilityException e) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Listener l : listeners) {
                    l.onBookmarksLoadingFailed(e);
                }
            }
        });
    }


}
