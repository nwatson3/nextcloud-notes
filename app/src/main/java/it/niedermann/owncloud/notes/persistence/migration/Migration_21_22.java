package it.niedermann.owncloud.notes.persistence.migration;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import it.niedermann.owncloud.notes.persistence.SyncWorker;

/**
 * Enabling backgroundSync, set from {@link String} values to {@link Boolean} values
 * https://github.com/stefan-niedermann/nextcloud-notes/issues/1168
 */
public class Migration_21_22 extends Migration {
    @NonNull
    private final Context context;

    public Migration_21_22(@NonNull Context context) {
        super(21, 22);
        this.context = context;
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        final var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final var editor = sharedPreferences.edit();
        if (sharedPreferences.contains("backgroundSync")) {
            editor.remove("backgroundSync");
            if (sharedPreferences.getString("backgroundSync", "").equals("off")) {
                editor.putBoolean("backgroundSync", false);
            } else {
                editor.putBoolean("backgroundSync", true);
                SyncWorker.update(context, true);
            }
        } else {
            SyncWorker.update(context, true);
        }
        editor.apply();
    }
}
