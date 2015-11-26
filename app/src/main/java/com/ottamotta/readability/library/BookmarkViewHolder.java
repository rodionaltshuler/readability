package com.ottamotta.readability.library;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ottamotta.readability.R;
import com.ottamotta.readability.library.entity.Bookmark;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.domain)
    TextView domain;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.author)
    TextView author;

    private Bookmark bookmark;

    public static BookmarkViewHolder create(ViewGroup container) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.bookmark_view_holder, container, false);
        return new BookmarkViewHolder(itemView);
    }

    public BookmarkViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void fill(Bookmark bookmark) {
        this.bookmark = bookmark;
        domain.setText(bookmark.getArticle().getDomain());
        author.setText(bookmark.getArticle().getAuthor());
        title.setText(bookmark.getArticle().getTitle());
    }
}
