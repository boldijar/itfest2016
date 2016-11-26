package com.bolnizar.itfest.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class StringPreference extends BasePreference<String> {

    public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
        this(preferences, key, null);
    }

    public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key, @Nullable String defaultValue) {
        super(preferences, key, defaultValue);
    }

    @Override
    public String get() {
        return mPreferences.getString(mKey, mDefaultValue);
    }

    @Override
    public void set(String value) {
        if (!TextUtils.isEmpty(value)) {
            mPreferences.edit().putString(mKey, value).apply();
        }
    }
}
