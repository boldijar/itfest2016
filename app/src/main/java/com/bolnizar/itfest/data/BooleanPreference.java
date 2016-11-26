package com.bolnizar.itfest.data;

import android.content.SharedPreferences;

public class BooleanPreference extends BasePreference<Boolean> {

    public BooleanPreference(SharedPreferences preferences, String key) {
        this(preferences, key, false);
    }

    public BooleanPreference(SharedPreferences preferences, String key, boolean defaultValue) {
        super(preferences, key, defaultValue);
    }

    @Override
    public Boolean get() {
        return mPreferences.getBoolean(mKey, mDefaultValue);
    }

    @Override
    public void set(Boolean value) {
        mPreferences.edit().putBoolean(mKey, value).apply();
    }
}
