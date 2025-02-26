package it.niedermann.owncloud.notes;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import it.niedermann.owncloud.notes.branding.BrandedActivity;
import it.niedermann.owncloud.notes.exception.ExceptionHandler;

public abstract class LockedActivity extends BrandedActivity {

    private static final String TAG = LockedActivity.class.getSimpleName();

    private static final int REQUEST_CODE_UNLOCK = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler(this));

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_key_prevent_screen_capture), false)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        if (isTaskRoot()) {
            askToUnlock();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isTaskRoot()) {
            askToUnlock();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isTaskRoot()) {
            NotesApplication.updateLastInteraction();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NotesApplication.updateLastInteraction();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        NotesApplication.updateLastInteraction();
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        NotesApplication.updateLastInteraction();
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(Intent intent) {
        NotesApplication.updateLastInteraction();
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        NotesApplication.updateLastInteraction();
        super.startActivity(intent, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UNLOCK) {
            if (resultCode == RESULT_OK) {
                Log.v(TAG, "Successfully unlocked device");
                NotesApplication.unlock();
            } else {
                Log.e(TAG, "Result code of unlocking was " + resultCode);
                finish();
            }
        }
    }

    private void askToUnlock() {
        if (NotesApplication.isLocked()) {
            final var keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                final var intent = keyguardManager.createConfirmDeviceCredentialIntent(getString(R.string.unlock_notes), null);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, REQUEST_CODE_UNLOCK);
            } else {
                Log.e(TAG, "Keyguard manager is null");
            }
        }
    }
}
