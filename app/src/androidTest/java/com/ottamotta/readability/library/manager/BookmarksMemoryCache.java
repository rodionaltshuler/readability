package com.ottamotta.readability.library.manager;

import com.google.common.collect.Lists;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

public class BookmarksMemoryCache implements BookmarksCache {

    private List<Bookmark> bookmarks = Lists.newArrayList();

    @Override
    public void addAll(List<Bookmark> bookmarks) {
        this.bookmarks.addAll(bookmarks);
    }

    @Override
    public List<Bookmark> getAll() {
        return bookmarks;
    }

    @Override
    public void clear() {
        bookmarks.clear();
    }
}
