package it.niedermann.owncloud.notes.main;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import it.niedermann.owncloud.notes.NotesApplication;
import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.persistence.NotesRepository;
import it.niedermann.owncloud.notes.persistence.entity.Account;

/**
 * Keeps track of which account is current. Functionality currently handled by SingleAccountHelper,
 * but we need to allow for other types of accounts - i.e., an offline account.
 */
public class AccountHelper {

    private static Application app = null;
    private static NotesRepository repo = null;

    public static void init(Application application)
    {
        app = application;
        repo = NotesRepository.getInstance(application);
    }

    public static Account getCurrentAccount()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
        String currentAccountName = sharedPref.getString("current_account", null);
        Account currentAccount = repo.getAccountByName(currentAccountName);
        return currentAccount;
    }
    public static void setCurrentAccount(String accountName)
    {
        assert(app != null);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_account", accountName);
        editor.apply();
    }

    public static void loadAccountInfo(Context context, Account account, AppCompatImageView avatarView, TextView urlView, TextView accountNameView)
    {
        if(accountNameView != null)
        {
            accountNameView.setText(account.getDisplayName());
        }
        if(account.getAccountName().equals("offline_account"))
        {
            if(urlView != null)
            {
                urlView.setText(account.getUrl());
            }
            Glide.with(context)
                    .load(R.drawable.ic_baseline_warning_24)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarView);
        }
        else
        {
            if(urlView != null)
            {
                urlView.setText(Uri.parse(account.getUrl()).getHost());
            }
            Glide.with(context)
                    .load(account.getUrl() + "/index.php/avatar/" + Uri.encode(account.getUserName()) + "/64")
                    .error(R.drawable.ic_account_circle_grey_24dp)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarView);
        }
    }
}
