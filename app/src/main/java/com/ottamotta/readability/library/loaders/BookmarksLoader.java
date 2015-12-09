package com.ottamotta.readability.library.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.network.BookmarkRestServiceWrapper;

import java.util.Collections;
import java.util.List;

public class BookmarksLoader extends AsyncTaskLoader<List<Bookmark>> {

    private BookmarkRestServiceWrapper restServiceWrapper;

    public BookmarksLoader(Context context) {
        super(context);
        restServiceWrapper = BookmarkRestServiceWrapper.getInstance();
    }

    @Override
    public List<Bookmark> loadInBackground() {
        try {
            List<Bookmark> bookmarks = restServiceWrapper.getBookmarks();
            return bookmarks;
        } catch (ReadabilityException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
