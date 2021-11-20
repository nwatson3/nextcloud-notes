package it.niedermann.owncloud.notes.main;

import android.content.Context;
import android.content.SharedPreferences;

import it.niedermann.owncloud.notes.NotesApplication;
import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.persistence.NotesRepository;
import it.niedermann.owncloud.notes.persistence.entity.Account;

/**
 * Keeps track of which account is current. Functionality currently handled by SingleAccountHelper,
 * but we need to allow for other types of accounts - i.e., an offline account.
 */
public class AccountHelper {

    private static NotesApplication app = null;
    private static NotesRepository repo = null;

    public static void init(NotesApplication application)
    {
        app = application;
        repo = NotesRepository.getInstance(application);
    }

    public static Account getCurrentAccount()
    {
        SharedPreferences sharedPref = app.getSharedPreferences(app.getString(R.string.pref_file_key), Context.MODE_PRIVATE);
        String currentAccountName = sharedPref.getString("current_account", null);
        Account currentAccount = repo.getAccountByName(currentAccountName);
        return currentAccount;
    }
    public static void setCurrentAccount(String accountName)
    {
        SharedPreferences sharedPref = app.getSharedPreferences(app.getString(R.string.pref_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_account", accountName);
        editor.apply();
    }
}
