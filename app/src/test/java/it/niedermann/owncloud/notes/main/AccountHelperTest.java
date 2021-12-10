package it.niedermann.owncloud.notes.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import it.niedermann.owncloud.notes.persistence.NotesRepository;
import it.niedermann.owncloud.notes.persistence.entity.Account;
import it.niedermann.owncloud.notes.shared.model.Capabilities;
import it.niedermann.owncloud.notes.shared.model.IResponseCallback;

@RunWith(RobolectricTestRunner.class)
public class AccountHelperTest extends TestCase {

    private static final String TEST_ACCOUNT_NAME = "testabc123";
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void testSetCurrentAccount() {
        AccountHelper.init(ApplicationProvider.getApplicationContext());
        AccountHelper.setCurrentAccount(TEST_ACCOUNT_NAME);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String currentSavedAccountName = sp.getString("current_account", null);
        assertEquals(TEST_ACCOUNT_NAME, currentSavedAccountName);
    }

    @Test
    public void testLoadCurrentAccount() {
        AccountHelper.init(ApplicationProvider.getApplicationContext());
        NotesRepository repo = NotesRepository.getInstance(context);
        repo.addAccount("url", TEST_ACCOUNT_NAME, "offline_account", new Capabilities(), "display name", new IResponseCallback<Account>() {
            @Override
            public void onSuccess(Account result) {
                assertNull(null);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                assertNotNull(null);
            }
        });
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("current_account", "offline_account");
        editor.apply();

        Account account = AccountHelper.getCurrentAccount();

        assertNotNull(account);
        assertEquals(TEST_ACCOUNT_NAME, account.getUserName());
    }

    @Test
    public void testLoadAccountInfo() {
        AppCompatImageView image = new AppCompatImageView(context);
        TextView url = new TextView(context);
        TextView displayName = new TextView(context);
        Account account = new Account("url", "username", "offline_account", "display name", new Capabilities());
        AccountHelper.loadAccountInfo(
                context,
                account,
                image,
                url,
                displayName );

        assertEquals("display name", displayName.getText());
        assertEquals("url", url.getText());
    }
}