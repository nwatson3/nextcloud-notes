package it.niedermann.owncloud.notes.branding;

import static it.niedermann.owncloud.notes.branding.BrandingUtil.readBrandColors;
import static it.niedermann.owncloud.notes.branding.BrandingUtil.tintMenuIcon;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.Menu;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.niedermann.owncloud.notes.R;

public abstract class BrandedActivity extends AppCompatActivity implements Branded {

    @ColorInt
    protected int colorAccent;

    public static void applyBrandToFAB(@ColorInt int mainColor, @ColorInt int textColor, @NonNull FloatingActionButton fab) {
        fab.setSupportBackgroundTintList(ColorStateList.valueOf(mainColor));
        fab.setColorFilter(textColor);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final var typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        colorAccent = typedValue.data;

        readBrandColors(this).observe(this, (pair) -> applyBrand(pair.first, pair.second));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            tintMenuIcon(menu.getItem(i), colorAccent);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void applyBrandToPrimaryToolbar(@NonNull AppBarLayout appBarLayout, @NonNull Toolbar toolbar) {
        // FIXME Workaround for https://github.com/stefan-niedermann/nextcloud-notes/issues/889
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));

        final var overflowDrawable = toolbar.getOverflowIcon();
        if (overflowDrawable != null) {
            overflowDrawable.setColorFilter(colorAccent, PorterDuff.Mode.SRC_ATOP);
            toolbar.setOverflowIcon(overflowDrawable);
        }

        final var navigationDrawable = toolbar.getNavigationIcon();
        if (navigationDrawable != null) {
            navigationDrawable.setColorFilter(colorAccent, PorterDuff.Mode.SRC_ATOP);
            toolbar.setNavigationIcon(navigationDrawable);
        }
    }
}
