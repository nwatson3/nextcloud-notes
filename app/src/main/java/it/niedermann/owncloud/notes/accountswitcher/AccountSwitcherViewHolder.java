package it.niedermann.owncloud.notes.accountswitcher;

import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import it.niedermann.nextcloud.sso.glide.SingleSignOnUrl;
import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.databinding.ItemAccountChooseBinding;
import it.niedermann.owncloud.notes.main.AccountHelper;
import it.niedermann.owncloud.notes.persistence.entity.Account;

public class AccountSwitcherViewHolder extends RecyclerView.ViewHolder {

    ItemAccountChooseBinding binding;

    public AccountSwitcherViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemAccountChooseBinding.bind(itemView);
    }

    public void bind(@NonNull Account localAccount, @NonNull Consumer<Account> onAccountClick) {
        AccountHelper.loadAccountInfo(
                itemView.getContext(),
                localAccount,
                binding.accountItemAvatar,
                binding.accountHost,
                binding.accountName);
        itemView.setOnClickListener((v) -> onAccountClick.accept(localAccount));
        binding.accountContextMenu.setVisibility(View.GONE);
    }
}
