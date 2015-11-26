package com.ottamotta.readability.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ottamotta.readability.R;
import com.ottamotta.readability.ReadabilityApp;
import com.ottamotta.readability.common.ui.SelectionListener;
import com.ottamotta.readability.library.entity.Bookmark;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.domain)
    TextView domain;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.image)
    ImageView imageView;

    private Bookmark bookmark;

    private Context context = ReadabilityApp.getInstance();

    public static BookmarkViewHolder create(ViewGroup container, SelectionListener<Bookmark> selectionListener) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.bookmark_view_holder, container, false);
        return new BookmarkViewHolder(itemView, selectionListener);
    }

    public BookmarkViewHolder(View itemView, final SelectionListener<Bookmark> selectionListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.onItemSelected(bookmark);
            }
        });
    }

    public void fill(Bookmark bookmark) {
        this.bookmark = bookmark;
        domain.setText(bookmark.getArticle().getDomain());
        author.setText(bookmark.getArticle().getAuthor());
        title.setText(bookmark.getArticle().getTitle());
        Picasso.with(context).load(bookmark.getArticle().getLeadImageUrl()).placeholder(R.drawable.readability_logo).fit().centerCrop().into(imageView);
    }
}
