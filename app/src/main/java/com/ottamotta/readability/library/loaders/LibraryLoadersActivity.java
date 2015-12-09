package com.ottamotta.readability.library.loaders;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ottamotta.readability.R;
import com.ottamotta.readability.auth.AuthActivity;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.common.ui.BaseActivity;
import com.ottamotta.readability.common.ui.SelectionListener;
import com.ottamotta.readability.credentials.CredentialsManager;
import com.ottamotta.readability.library.BookmarkDetailsActivity;
import com.ottamotta.readability.library.BookmarksAdapter;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.BookmarksManager;
import com.ottamotta.readability.library.manager.network.AddBookmarkResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LibraryLoadersActivity extends BaseActivity {

    private static final String TAG = LibraryLoadersActivity.class.getSimpleName();

    private static final int REFRESH_DELAY_AFTER_BOOKMARK_ADD_MS = 3000;
    public static final int BOOKMARKS_LOADER_ID = 1;

    @Bind(android.R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(android.R.id.title)
    TextView toolbarTitle;

    @Bind(R.id.add_bookmark_input)
    EditText addBookmarkInput;

    @BindInt(R.integer.bookmark_columns_count)
    int columns;

    private BookmarksAdapter adapter;

    private String lastSharedUrl;

    private static final String KEY_LAST_SHARED_URL = "last_shared_url";

    public static void start(Activity src) {
        src.startActivity(new Intent(src, LibraryLoadersActivity.class));
    }

    private SelectionListener<Bookmark> bookmarkSelectionListener = new SelectionListener<Bookmark>() {
        @Override
        public void onItemSelected(Bookmark item) {
            BookmarkDetailsActivity.start(LibraryLoadersActivity.this, item);
        }
    };

    private LoaderManager.LoaderCallbacks<List<Bookmark>> bookmarksCallback = new LoaderManager.LoaderCallbacks<List<Bookmark>>() {
        @Override
        public Loader<List<Bookmark>> onCreateLoader(int id, Bundle args) {
            return new BookmarksLoader(LibraryLoadersActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Bookmark>> loader, List<Bookmark> data) {
            showLoadedBookmarks(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Bookmark>> loader) {
            showLoadedBookmarks(new ArrayList<Bookmark>());
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            recyclerView.removeCallbacks(loadBookmarksForceRunnable);
            recyclerView.post(loadBookmarksForceRunnable);
        }
    };

    private Runnable loadBookmarksForceRunnable = new Runnable() {
        @Override
        public void run() {
            //TODO
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            lastSharedUrl = savedInstanceState.getString(KEY_LAST_SHARED_URL);
        }
        setContentView(R.layout.library_activity);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(getLayoutManager());
        adapter = new BookmarksAdapter(bookmarkSelectionListener);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        handleSendText(getIntent());

        addBookmarkInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addBookmarkFromInput();
                    return true;
                }
                return false;
            }
        });

        getLoaderManager().initLoader(BOOKMARKS_LOADER_ID, null, bookmarksCallback).forceLoad();
    }

    private void addBookmarkFromInput() {
        String url = addBookmarkInput.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            addBookmark(url);
        }
    }

    @OnClick(R.id.add_bookmark_button)
    void onAddBookmarkClicked() {
        addBookmarkFromInput();
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        toolbarTitle.setText(R.string.library);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (lastSharedUrl != null) {
            outState.putString(KEY_LAST_SHARED_URL, lastSharedUrl);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSendText(intent);
    }

    void handleSendText(Intent intent) {
        if (isAuthorized()) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (sharedText == null || sharedText.equals(lastSharedUrl)) {
                        return;
                    }
                    addBookmark(sharedText);
                    lastSharedUrl = sharedText;
                }
            }
        } else {
            showMessage("Please authorize before sharing links");
            AuthActivity.start(this);
        }
    }

    private void addBookmark(String url) {
        showMessage("Bookmarking link: " + url);
        //TODO
    }

    private boolean isAuthorized() {
        return CredentialsManager.getInstance().isAuthorized();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, columns);
    }

    private void showLoadedBookmarks(List<Bookmark> bookmarks) {
        if (adapter.isEmpty()) {
            adapter.addAll(bookmarks);
        } else {
            adapter = new BookmarksAdapter(bookmarkSelectionListener);
            adapter.addAll(bookmarks);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

}

