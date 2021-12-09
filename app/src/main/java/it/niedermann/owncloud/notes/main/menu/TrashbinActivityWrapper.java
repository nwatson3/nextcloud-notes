package it.niedermann.owncloud.notes.main.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nextcloud.android.sso.Constants;

public class TrashbinActivityWrapper extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
        String accountName = getIntent().getStringExtra(Intent.EXTRA_USER);
        setResult(RESULT_OK);
        if(accountName.equals("offline_account"))
        {
            Log.i("nwatson3", "Unavailable for offline accounts");
            setResult(RESULT_CANCELED);
            finish();
        }
        else
        {
            Context context = getBaseContext();
            final var packageManager = context.getPackageManager();
            final boolean prod = getIntent().getBooleanExtra("PROD", true);
            final String packageName = prod ? Constants.PACKAGE_NAME_PROD : Constants.PACKAGE_NAME_DEV;

            Intent intent = new Intent();

            intent.setClassName(packageName, "com.owncloud.android.ui.trashbin.TrashbinActivity");
            if (packageManager.resolveActivity(intent, 0) != null) {
                Log.i("nwatson3", "TEST1");
                intent.setFlags(0);
                startActivity(intent);
            }
        }
        Log.i("nwatson3", "abcdef");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setResult(RESULT_OK);
        finish();
    }
}
