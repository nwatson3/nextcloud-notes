package it.niedermann.owncloud.notes.main.items.selection;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ItemIdKeyProvider extends ItemKeyProvider<Long> {
    private final RecyclerView recyclerView;

    public ItemIdKeyProvider(RecyclerView recyclerView) {
        super(SCOPE_MAPPED);
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        final var adapter = recyclerView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("RecyclerView adapter is not set!");
        }
        return adapter.getItemId(position);
    }

    @Override
    public int getPosition(@NonNull Long key) {
        final var viewHolder = recyclerView.findViewHolderForItemId(key);
        return viewHolder == null ? NO_POSITION : viewHolder.getLayoutPosition();
    }
}