package it.niedermann.owncloud.notes.main.menu;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TrashbinActivityWrapperTest extends TestCase {

    @Test
    public void testOfflineAccountCancels() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TrashbinActivityWrapper.class)
                .putExtra(Intent.EXTRA_USER, "offline_account")
                .setFlags(0);
        ActivityScenario<TrashbinActivityWrapper> as = ActivityScenario.launch(intent);
        Instrumentation.ActivityResult result = as.getResult();
        assertEquals(result.getResultCode(), Activity.RESULT_CANCELED);
    }
}