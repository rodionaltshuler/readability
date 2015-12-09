package com.ottamotta.readability.library.manager;

import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.test.AndroidTestCase;

import com.ottamotta.readability.library.entity.Article;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

import io.paperdb.Paper;

public class BookmarksPaperDbCacheTest extends AndroidTestCase {

    private static final String BOOK_NAME = "testBook";

    private BookmarksPaperDbCache cache;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Paper.init(getContext());
        cache = new BookmarksPaperDbCache(getContext(), BOOK_NAME);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        Paper.book(BOOK_NAME).destroy();
    }

    public void testInitialRepositoryIsEmpty() {
        assertTrue(cache.getAll().isEmpty());
    }

    public void testSaveAndLoadSingleBookmark() throws Exception {
        Bookmark singleBookmark = getTestBookmark(1);
        List<Bookmark> saveList = Lists.newArrayList(singleBookmark);
        cache.addAll(saveList);
        List<Bookmark> loadList = cache.getAll();
        assertTrue(saveList.size() == loadList.size());
        assertTrue(loadList.size() == 1);
        assertTrue(loadList.get(0) != null);
        assertTrue(loadList.get(0).equals(saveList.get(0)));
    }

    public void testBookmarkWithExistingIdOverwritten() throws Exception {
        Bookmark first = getTestBookmark(1);
        first.setArticleHref("firstHRef");
        Bookmark second = getTestBookmark(1);
        second.setArticleHref("secondHref");
        assertTrue(!first.equals(second));

        cache.addAll(Lists.newArrayList(first));
        cache.addAll(Lists.newArrayList(second));

        assertTrue(cache.getAll().size() == 1);
    }

    private Bookmark getTestBookmark(int id) {
        Bookmark bookmark = new Bookmark();
        Article article = new Article();
        article.setAuthor("Author")
                .setUrl("http://author.com/1")
                .setId("12345")
                .setTitle("A test article here");

        bookmark.setId(id)
                .setUserId(123)
                .setArticleHref("www.articles.com/test")
                .setArticle(article);

        return bookmark;
    }
}
