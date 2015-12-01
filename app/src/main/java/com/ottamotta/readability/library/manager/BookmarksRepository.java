package com.ottamotta.readability.library.manager;

import android.support.annotation.WorkerThread;

import com.ottamotta.readability.ReadabilityApp;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.network.BookmarkRestServiceWrapper;

import java.util.List;

public class BookmarksRepository {

    private static final String TAG = BookmarksRepository.class.getSimpleName();

    private BookmarkRestServiceWrapper restServiceWrapper;

    private BookmarksCache bookmarksCache;

    BookmarksRepository() {
        this.restServiceWrapper = BookmarkRestServiceWrapper.getInstance();
        bookmarksCache = new BookmarksSharedPrefsBookmarksCache(ReadabilityApp.getInstance());
    }

    BookmarksRepository(BookmarkRestServiceWrapper restServiceWrapper, BookmarksCache bookmarksCache) {
        this.restServiceWrapper = restServiceWrapper;
        this.bookmarksCache = bookmarksCache;
    }

    @WorkerThread
    private List<Bookmark> loadFromServer() throws ReadabilityException {
        return restServiceWrapper.getBookmarks();
    }


    @WorkerThread
    public List<Bookmark> getAll(boolean invalidateCache) throws ReadabilityException {
        if (invalidateCache) {
            List<Bookmark> bookmarks = loadFromServer();
            bookmarksCache.clear();
            bookmarksCache.addAll(bookmarks);
            return bookmarksCache.getAll();
        } else {
            List<Bookmark> bookmarks = bookmarksCache.getAll();
            if (bookmarks.isEmpty()) {
                bookmarks = loadFromServer();
                bookmarksCache.addAll(bookmarks);
            }
        }
        return bookmarksCache.getAll();
    }
}
