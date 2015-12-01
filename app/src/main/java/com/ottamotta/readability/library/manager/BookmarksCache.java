package com.ottamotta.readability.library.manager;

import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

public interface BookmarksCache {
    void addAll(List<Bookmark> bookmarks);

    List<Bookmark> getAll();

    void clear();
}
