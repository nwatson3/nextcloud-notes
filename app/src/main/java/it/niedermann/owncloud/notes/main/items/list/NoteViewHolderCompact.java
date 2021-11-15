package it.niedermann.owncloud.notes.main.items.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.databinding.ItemNotesListNoteItemCompactBinding;
import it.niedermann.owncloud.notes.main.items.NoteViewHolder;
import it.niedermann.owncloud.notes.persistence.entity.Note;
import it.niedermann.owncloud.notes.shared.model.DBStatus;
import it.niedermann.owncloud.notes.shared.model.NoteClickListener;

public class NoteViewHolderCompact extends NoteViewHolder {
    @NonNull
    private final ItemNotesListNoteItemCompactBinding binding;

    public NoteViewHolderCompact(@NonNull ItemNotesListNoteItemCompactBinding binding, @NonNull NoteClickListener noteClickListener) {
        super(binding.getRoot(), noteClickListener);
        this.binding = binding;
    }

    public void showSwipe(boolean left) {
        binding.noteFavoriteLeft.setVisibility(left ? View.VISIBLE : View.INVISIBLE);
        binding.noteDeleteRight.setVisibility(left ? View.INVISIBLE : View.VISIBLE);
        binding.noteSwipeFrame.setBackgroundResource(left ? R.color.bg_warning : R.color.bg_attention);
    }

    public void bind(boolean isSelected, @NonNull Note note, boolean showCategory, int mainColor, int textColor, @Nullable CharSequence searchQuery) {
        super.bind(isSelected, note, showCategory, mainColor, textColor, searchQuery);
        @NonNull final var context = itemView.getContext();
        binding.noteSwipeable.setAlpha(DBStatus.LOCAL_DELETED.equals(note.getStatus()) ? 0.5f : 1.0f);
        bindStatus(binding.noteStatus, note.getStatus(), mainColor);
        bindFavorite(binding.noteFavorite, note.getFavorite());

        bindSearchableContent(context, binding.noteTitle, searchQuery, note.getTitle(), mainColor);
    }

    @NonNull
    public View getNoteSwipeable() {
        return binding.noteSwipeable;
    }
}
