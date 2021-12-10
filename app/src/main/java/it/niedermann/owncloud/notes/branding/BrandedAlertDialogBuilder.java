package it.niedermann.owncloud.notes.branding;

import static it.niedermann.owncloud.notes.branding.BrandingUtil.getSecondaryForegroundColorDependingOnTheme;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class BrandedAlertDialogBuilder extends AlertDialog.Builder implements Branded {

    protected AlertDialog dialog;

    public BrandedAlertDialogBuilder(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public AlertDialog create() {
        this.dialog = super.create();

        @NonNull final var context = getContext();
        @ColorInt final int mainColor = BrandingUtil.readBrandMainColor(context);
        @ColorInt final int textColor = BrandingUtil.readBrandTextColor(context);
        applyBrand(mainColor, textColor);
        dialog.setOnShowListener(dialog -> applyBrand(mainColor, textColor));
        return dialog;
    }

    @CallSuper
    @Override
    public void applyBrand(int mainColor, int textColor) {
        final var buttons = new Button[3];
        buttons[0] = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttons[1] = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttons[2] = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        for (final var button : buttons) {
            if (button != null) {
                button.setTextColor(getSecondaryForegroundColorDependingOnTheme(button.getContext(), mainColor));
            }
        }
    }
}
