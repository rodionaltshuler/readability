package com.ottamotta.readability.library;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ottamotta.readability.R;
import com.ottamotta.readability.library.entity.Bookmark;

import butterknife.ButterKnife;

public class EmptyBookmarksViewHolder extends RecyclerView.ViewHolder {

    public static EmptyBookmarksViewHolder create(ViewGroup container) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.empty_bookmarks_view_holder, container, false);
        return new EmptyBookmarksViewHolder(itemView);
    }

    public EmptyBookmarksViewHolder(View itemView) {
        super(itemView);
    }

}
