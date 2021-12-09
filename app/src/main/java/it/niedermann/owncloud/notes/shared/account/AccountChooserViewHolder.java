package it.niedermann.owncloud.notes.shared.account;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import it.niedermann.owncloud.notes.databinding.ItemAccountChooseBinding;
import it.niedermann.owncloud.notes.main.AccountHelper;
import it.niedermann.owncloud.notes.persistence.entity.Account;

public class AccountChooserViewHolder extends RecyclerView.ViewHolder {
    private final ItemAccountChooseBinding binding;

    protected AccountChooserViewHolder(ItemAccountChooseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Account localAccount, Consumer<Account> targetAccountConsumer) {

        AccountHelper.loadAccountInfo(
                binding.accountItemAvatar.getContext(),
                localAccount,
                binding.accountItemAvatar,
                binding.accountHost,
                binding.accountName);


        binding.accountLayout.setOnClickListener((v) -> targetAccountConsumer.accept(localAccount));
    }
}