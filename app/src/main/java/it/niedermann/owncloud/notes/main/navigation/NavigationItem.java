package it.niedermann.owncloud.notes.main.navigation;

import static it.niedermann.owncloud.notes.shared.model.ENavigationCategoryType.UNCATEGORIZED;

import android.text.TextUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.niedermann.owncloud.notes.shared.model.ENavigationCategoryType;

public class NavigationItem {
    @NonNull
    public String id;
    @NonNull
    public String label;
    @DrawableRes
    public int icon;
    @Nullable
    public Integer count;
    @Nullable
    public ENavigationCategoryType type;

    public NavigationItem(@NonNull String id, @NonNull String label, @Nullable Integer count, @DrawableRes int icon) {
        this.id = id;
        this.label = label;
        this.type = TextUtils.isEmpty(label) ? UNCATEGORIZED : null;
        this.count = count;
        this.icon = icon;
    }

    public NavigationItem(@NonNull String id, @NonNull String label, @Nullable Integer count, @DrawableRes int icon, @NonNull ENavigationCategoryType type) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.count = count;
        this.icon = icon;
    }

    public static class CategoryNavigationItem extends NavigationItem {
        public long accountId;
        @NonNull
        public String category;

        public CategoryNavigationItem(@NonNull String id, @NonNull String label, @Nullable Integer count, @DrawableRes int icon, long accountId, @NonNull String category) {
            super(id, label, count, icon, ENavigationCategoryType.DEFAULT_CATEGORY);
            this.accountId = accountId;
            this.category = category;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CategoryNavigationItem)) return false;
            if (!super.equals(o)) return false;

            CategoryNavigationItem that = (CategoryNavigationItem) o;

            if (accountId != that.accountId) return false;
            return category.equals(that.category);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (int) (accountId ^ (accountId >>> 32));
            result = 31 * result + category.hashCode();
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationItem)) return false;

        final var that = (NavigationItem) o;

        if (icon != that.icon) return false;
        if (!id.equals(that.id)) return false;
        if (!label.equals(that.label)) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + icon;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        return "NavigationItem{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", icon=" + icon +
                ", count=" + count +
                ", type=" + type +
                '}';
    }
}