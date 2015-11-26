package com.ottamotta.readability.library.network;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

public class BookmarksResponse {

    @Expose
    private List<Bookmark> bookmarks = Lists.newArrayList();

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }
}
