package com.ottamotta.readability.library;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.ottamotta.readability.library.entity.Bookmark;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bookmark> items = Lists.newArrayList();

    private static final int EMPTY_ITEM_VIEW_TYPE = 0;
    private static final int BOOKMARK_VIEW_TYPE = 1;


    public BookmarksAdapter() {

    }

    public void addAll(List<Bookmark> bookmarkList) {
        items.addAll(bookmarkList);
        notifyItemRangeInserted(items.size(), bookmarkList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_ITEM_VIEW_TYPE:
                return EmptyBookmarksViewHolder.create(parent);
            case BOOKMARK_VIEW_TYPE:
            default:
                return BookmarkViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookmarkViewHolder) {
            ((BookmarkViewHolder) holder).fill(getItem(position));
        }
    }

    private Bookmark getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return items.isEmpty() ? EMPTY_ITEM_VIEW_TYPE : BOOKMARK_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 1 : items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
