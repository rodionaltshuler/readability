package com.ottamotta.readability.library.manager;

import android.os.Handler;
import android.os.Looper;

import com.google.common.collect.Sets;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.network.AddBookmarkResponse;
import com.ottamotta.readability.library.manager.network.BookmarkRestServiceWrapper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarksManager {

    private boolean isLoading;

    private static BookmarksManager instance;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Set<Listener> listeners = Sets.newConcurrentHashSet();

    private BookmarksRepository repository = new BookmarksRepository();

    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    private BookmarkRestServiceWrapper restServiceWrapper = BookmarkRestServiceWrapper.getInstance();

    public static abstract class Listener {
        public void onBookmarkAdded(AddBookmarkResponse addBookmarkResponse) {
        }

        public void onBookmarkAddFailed(ReadabilityException e) {
        }

        public void onBookmarksLoaded(List<Bookmark> bookmarks) {
        }

        public void onBookmarksLoadingFailed(ReadabilityException e) {
        }

        public void onBookmarksLoadingStatusChanged(boolean isLoading) {}

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
        loadBookmarksInternal(false);
    }

    public void loadBookmarksForce(){
        loadBookmarksInternal(true);
    }

    private void loadBookmarksInternal(final boolean force) {
        if (isLoading) return;
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    setLoading(true);
                    List<Bookmark> bookmarks = repository.getAll(force);
                    notifyBookmarksLoaded(bookmarks);
                } catch (ReadabilityException e) {
                    notifyBookmarksLoadingFailed(e);
                } finally {
                    setLoading(false);
                }
            }
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        notifyBookmarkLoadingStatusChanged(loading);
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void notifyBookmarkLoadingStatusChanged(final boolean isLoading) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Listener l : listeners) {
                    l.onBookmarksLoadingStatusChanged(isLoading);
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

    private void notifyBookmarksLoadingFailed(final ReadabilityException e) {
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
