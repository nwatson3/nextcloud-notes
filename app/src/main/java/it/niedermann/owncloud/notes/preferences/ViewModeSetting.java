package it.niedermann.owncloud.notes.preferences;

import android.content.Context;

import it.niedermann.owncloud.notes.R;

/**
 * Represents one of three modes the user can view their notes list in:
 * Grid, List, or Compact list
 */
public enum ViewModeSetting {
    GRID,
    LIST,
    COMPACT_LIST;

    /**
     * Converts string from preferences menu into appropriate enum value
     * @param context
     * @param str
     * @return
     */
    public static ViewModeSetting createFromString(Context context, String str)
    {
            if(context.getString(R.string.pref_view_mode_grid).equals(str)) {
                return GRID;
            }
            else if(context.getString(R.string.pref_view_mode_list).equals(str)) {
                return LIST;
            }
            else {
                return COMPACT_LIST;
            }
    }
}
