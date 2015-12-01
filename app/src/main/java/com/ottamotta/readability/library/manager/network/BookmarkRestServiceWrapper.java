package com.ottamotta.readability.library.manager.network;

import android.support.annotation.WorkerThread;
import android.util.Log;

import com.ottamotta.readability.common.network.ReadabilityRestAdapter;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class BookmarkRestServiceWrapper {

    BookmarksRestService restService;

    private static final String TAG = BookmarksRestService.class.getSimpleName();

    private static BookmarkRestServiceWrapper instance;

    public static synchronized BookmarkRestServiceWrapper getInstance() {
        if (null == instance) {
            instance = new BookmarkRestServiceWrapper();
        }
        return instance;
    }

    BookmarkRestServiceWrapper() {
        restService = ReadabilityRestAdapter.getInstance().getAdapter().create(BookmarksRestService.class);
    }

    public BookmarkRestServiceWrapper(ReadabilityRestAdapter readabilityRestAdapter) {
        restService = readabilityRestAdapter.getAdapter().create(BookmarksRestService.class);
    }

    @WorkerThread
    public AddBookmarkResponse bookmark(String url) throws ReadabilityException {
        try {
            Response response = restService.postBookmark(url);
            String location = null;
            String xArticleLocation = null;
            for (Header header : response.getHeaders()) {
                if (header.getName().equals("Location")) {
                    location = header.getValue();
                }
                if (header.getName().equals("X-Article-Location")) {
                    xArticleLocation = header.getValue();
                }
            }
            AddBookmarkResponse bookmarkResponse = new AddBookmarkResponse(response.getStatus(), location, xArticleLocation);
            Log.d(TAG, "Bookmark response: " + bookmarkResponse.toString());
            return bookmarkResponse;
        } catch (RetrofitError e) {
            throw new ReadabilityException(e);
        }
    }

    @WorkerThread
    public List<Bookmark> getBookmarks() throws ReadabilityException {
        try {
            BookmarksResponse bookmarksResponse = restService.getBookmarks();
            return bookmarksResponse.getBookmarks();
        } catch (RetrofitError e) {
            throw new ReadabilityException(e);
        }
    }


}
