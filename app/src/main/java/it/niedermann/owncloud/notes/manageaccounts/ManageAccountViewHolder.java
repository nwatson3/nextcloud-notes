package it.niedermann.owncloud.notes.manageaccounts;

import android.graphics.drawable.LayerDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.Stream;

import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.databinding.ItemAccountChooseBinding;
import it.niedermann.owncloud.notes.main.AccountHelper;
import it.niedermann.owncloud.notes.persistence.entity.Account;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static it.niedermann.owncloud.notes.branding.BrandingUtil.applyBrandToLayerDrawable;
import static it.niedermann.owncloud.notes.shared.util.ApiVersionUtil.getPreferredApiVersion;

public class ManageAccountViewHolder extends RecyclerView.ViewHolder {

    private final ItemAccountChooseBinding binding;

    public ManageAccountViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemAccountChooseBinding.bind(itemView);
    }

    public void bind(
            @NonNull Account localAccount,
            @NonNull Consumer<Account> onAccountClick,
            @NonNull Consumer<Account> onAccountDelete,
            @NonNull Consumer<Account> onChangeNotesPath,
            @NonNull Consumer<Account> onChangeFileSuffix,
            boolean isCurrentAccount
    ) {
        AccountHelper.loadAccountInfo(
                itemView.getContext(),
                localAccount,
                binding.accountItemAvatar,
                binding.accountHost,
                binding.accountName);
        itemView.setOnClickListener((v) -> onAccountClick.accept(localAccount));
        binding.accountContextMenu.setVisibility(VISIBLE);
        binding.accountContextMenu.setOnClickListener((v) -> {
            final var popup = new PopupMenu(itemView.getContext(), v);
            popup.inflate(R.menu.menu_account);
            final var preferredApiVersion = getPreferredApiVersion(localAccount.getApiVersion());
            if (preferredApiVersion != null && !preferredApiVersion.supportsSettings()) {
                final var menu = popup.getMenu();
                Stream.of(
                        R.id.notes_path,
                        R.id.file_suffix
                ).forEach((i) -> menu.removeItem(menu.findItem(i).getItemId()));
            }
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.notes_path) {
                    onChangeNotesPath.accept(localAccount);
                    return true;
                } else if (item.getItemId() == R.id.file_suffix) {
                    onChangeFileSuffix.accept(localAccount);
                    return true;
                } else if (item.getItemId() == R.id.delete) {
                    onAccountDelete.accept(localAccount);
                    return true;
                }
                return false;
            });
            popup.show();
        });
        if (isCurrentAccount) {
            binding.currentAccountIndicator.setVisibility(VISIBLE);
            applyBrandToLayerDrawable((LayerDrawable) binding.currentAccountIndicator.getDrawable(), R.id.area, localAccount.getColor());
        } else {
            binding.currentAccountIndicator.setVisibility(GONE);
        }
    }
}
