package com.ottamotta.readability.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ottamotta.readability.R;
import com.ottamotta.readability.ReadabilityApp;
import com.ottamotta.readability.common.ui.BaseActivity;
import com.ottamotta.readability.library.entity.Bookmark;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookmarkDetailsActivity extends BaseActivity {

    private static final String EXTRA_BOOKMARK = "extra_bookmark";

    private Bookmark bookmark;

    @Bind(R.id.domain)
    TextView domain;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.image)
    ImageView imageView;

    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Bind(android.R.id.title)
    TextView toolbarTitle;

    public static void start(Activity src, Bookmark bookmark) {
        Intent i = new Intent(src, BookmarkDetailsActivity.class);
        i.putExtra(EXTRA_BOOKMARK, bookmark);
        src.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_details_activity);
        ButterKnife.bind(this);
        bookmark = (Bookmark) getIntent().getSerializableExtra(EXTRA_BOOKMARK);
        domain.setText(bookmark.getArticle().getDomain());
        author.setText(bookmark.getArticle().getAuthor());
        title.setText(bookmark.getArticle().getTitle());
        Picasso.with(ReadabilityApp.getInstance()).load(bookmark.getArticle().getLeadImageUrl()).placeholder(R.drawable.readability_logo).fit().centerCrop().into(imageView);
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) Math.abs(verticalOffset) / (float) toolbar.getHeight();
                alpha = Math.min(alpha, 1);
                int color = Color.argb((int) (alpha * 255), 255, 255, 255);
                toolbarTitle.setTextColor(color);
            }
        });
        toolbarTitle.setText(bookmark.getArticle().getTitle());
    }

    @OnClick(R.id.open_link_button)
    void onOpenLinkClicked() {
        //TODO
    }
}
