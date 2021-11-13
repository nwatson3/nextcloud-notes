package it.niedermann.owncloud.notes.preferences;

import android.content.Context;

import it.niedermann.owncloud.notes.R;

public enum ViewModeSetting {
    GRID,
    LIST,
    COMPACT_LIST;

    public static ViewModeSetting createFromId(Context context, String id)
    {
            if(context.getString(R.string.pref_view_mode_grid).equals(id))
                return GRID;
            else if(context.getString(R.string.pref_view_mode_list).equals(id))
                return LIST;
            else
                return COMPACT_LIST;
    }
}
