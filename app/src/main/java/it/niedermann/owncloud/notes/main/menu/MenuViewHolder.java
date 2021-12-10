package it.niedermann.owncloud.notes.main.menu;

import static android.view.View.GONE;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.databinding.ItemNavigationBinding;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    private final ItemNavigationBinding binding;

    public MenuViewHolder(@NonNull ItemNavigationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull MenuItem menuItem, @NonNull Consumer<MenuItem> onClick) {
        @NonNull Context context = itemView.getContext();
        binding.navigationItemLabel.setText(context.getString(menuItem.getLabelResource()));
        binding.navigationItemLabel.setTextColor(binding.getRoot().getResources().getColor(R.color.fg_default));
        binding.navigationItemIcon.setImageDrawable(ContextCompat.getDrawable(context, menuItem.getDrawableResource()));
        binding.navigationItemCount.setVisibility(GONE);
        binding.getRoot().setOnClickListener((v) -> onClick.accept(menuItem));
    }
}
