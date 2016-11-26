package com.bolnizar.itfest.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class IntegerPreference extends BasePreference<Integer> {

    public IntegerPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
        this(preferences, key, 0);
    }

    public IntegerPreference(@NonNull SharedPreferences preferences, @NonNull String key, @Nullable Integer defaultValue) {
        super(preferences, key, defaultValue);
    }

    @Override
    public Integer get() {
        return mPreferences.getInt(mKey, mDefaultValue);
    }

    @Override
    public void set(Integer value) {
        mPreferences.edit().putInt(mKey, value).apply();
    }
}
