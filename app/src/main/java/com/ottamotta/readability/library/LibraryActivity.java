package com.ottamotta.readability.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ottamotta.readability.R;
import com.ottamotta.readability.common.ui.BaseActivity;
import com.ottamotta.readability.common.ReadabilityException;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.network.AddBookmarkResponse;

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

    @Bind(R.id.last_bookmark)
    TextView addedBookmark;

    private BookmarksManager bookmarksManager = BookmarksManager.getInstance();

    public static void start(Activity src) {
        src.startActivity(new Intent(src, LibraryActivity.class));
    }

    private BookmarksManager.Listener listener = new BookmarksManager.Listener() {
        @Override
        void onBookmarkAdded(AddBookmarkResponse addBookmarkResponse) {
            addedBookmark.setText("Status: " + addBookmarkResponse.getStatus());
        }

        @Override
        void onBookmarkAddFailed(ReadabilityException e) {
            addedBookmark.setText(e.getMessage());
        }

        @Override
        void onBookmarksLoaded(List<Bookmark> bookmarks) {
            //TODO implement
            addedBookmark.setText("Bookmarks loaded. Count: " + bookmarks.size());
        }

        @Override
        void onBookmarksLoadingFailed(ReadabilityException e) {
            addedBookmark.setText(e.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);
        ButterKnife.bind(this);
        bookmarksManager.loadBookmarks();
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
        bookmarksManager.register(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookmarksManager.unregister(listener);
    }


}

