package com.ottamotta.readability.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ottamotta.readability.R;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.common.ui.BaseActivity;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.BookmarksManager;
import com.ottamotta.readability.library.manager.network.AddBookmarkResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LibraryActivity extends BaseActivity {

    private static final String TAG = LibraryActivity.class.getSimpleName();

    @Bind(R.id.bookmark_intput)
    EditText bookmarkInput;

    @Bind(R.id.add_bookmark_button)
    View addBookmarkButton;

    @Bind(android.R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private BookmarksManager bookmarksManager = BookmarksManager.getInstance();

    private BookmarksAdapter adapter;

    public static void start(Activity src) {
        src.startActivity(new Intent(src, LibraryActivity.class));
    }

    private BookmarksManager.Listener listener = new BookmarksManager.Listener() {
        @Override
        public void onBookmarkAdded(AddBookmarkResponse addBookmarkResponse) {
            //addedBookmark.setText("Status: " + addBookmarkResponse.getStatus());
        }

        @Override
        public void onBookmarkAddFailed(ReadabilityException e) {
            //addedBookmark.setText(e.getMessage());
        }

        @Override
        public void onBookmarksLoaded(List<Bookmark> bookmarks) {
            showLoadedBookmarks(bookmarks);
        }

        @Override
        public void onBookmarksLoadingFailed(ReadabilityException e) {
            //addedBookmark.setText(e.getMessage());
        }

        @Override
        public void onBookmarksLoadingStatusChanged(boolean isLoading) {
            setRefreshing(isLoading);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            bookmarksManager.loadBookmarksForce();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookmarksManager.loadBookmarks();
        adapter = new BookmarksAdapter();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    @OnClick(R.id.add_bookmark_button)
    void onAddBookmarkClicked() {
        String url = bookmarkInput.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            bookmarksManager.addBookmark(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRefreshing(bookmarksManager.isLoading());
        bookmarksManager.register(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookmarksManager.unregister(listener);
    }

    private void showLoadedBookmarks(List<Bookmark> bookmarks) {
        if (adapter.isEmpty()) {
            adapter.addAll(bookmarks);
        } else {
            adapter = new BookmarksAdapter();
            adapter.addAll(bookmarks);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

}

