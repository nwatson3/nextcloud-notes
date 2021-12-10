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

    /**
     * Takes in and stores a reference interacting with the Application
     * @param application
     */
    public static void init(Application application)
    {
        app = application;
        repo = NotesRepository.getInstance(application);
    }

    /**
     * Get the currently signed in account
     * @return the account currently signed in
     */
    public static Account getCurrentAccount()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
        String currentAccountName = sharedPref.getString("current_account", null);
        return repo.getAccountByName(currentAccountName);
    }

    /**
     * Set the account by account name
     * @param accountName name of the account to set as currently logged in
     */
    public static void setCurrentAccount(String accountName)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_account", accountName);
        editor.apply();
    }

    /**
     * Load account's display name, url, and avatar. This was previously done at several places
     * in the app, but is now abstracted as this function because of the additional logic of
     * checking if the account is offline. (you cannot load from a URL if there is no associated
     * nextcloud account).
     * @param context
     * @param account Account to load information from
     * @param avatarView View to load the avatar into
     * @param urlView TextView to load the URL into
     * @param accountNameView TextView to load the display name into
     */
    public static void loadAccountInfo(Context context, Account account, AppCompatImageView avatarView, TextView urlView, TextView accountNameView)
    {
        if(accountNameView != null)
        {
            accountNameView.setText(account.getDisplayName());
        }
        if("offline_account".equals(account.getAccountName()))
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
