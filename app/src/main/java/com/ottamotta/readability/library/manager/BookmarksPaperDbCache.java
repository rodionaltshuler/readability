package com.ottamotta.readability.library.manager;

import android.content.Context;

import com.google.common.collect.Lists;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

import io.paperdb.Paper;

class BookmarksPaperDbCache implements BookmarksCache {

    private static final String DEFAULT_BOOK_NAME = "bookmarks";

    private String bookmarksBook;

    public BookmarksPaperDbCache(Context context) {
        Paper.init(context);
        this.bookmarksBook = DEFAULT_BOOK_NAME;
    }

    public BookmarksPaperDbCache(Context context, String bookName) {
        this(context);
        this.bookmarksBook = bookName;
    }

    @Override
    public void addAll(List<Bookmark> bookmarks) {
        for (Bookmark bookmark : bookmarks) {
            Paper.book(bookmarksBook).write(String.valueOf(bookmark.getId()), bookmark);
        }
    }

    @Override
    public List<Bookmark> getAll() {
        List<String> keys =  Paper.book(bookmarksBook).getAllKeys();
        List<Bookmark> result = Lists.newArrayList();
        for (String key : keys) {
            result.add((Bookmark) Paper.book(bookmarksBook).read(key));
        }
        return result;
    }

    @Override
    public void clear() {
        Paper.book(bookmarksBook).destroy();
    }
}
