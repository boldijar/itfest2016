package com.bolnizar.itfest.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.bolnizar.itfest.BaseApp;
import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.BooleanPreference;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.utils.Constants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppPrefsModule {

    @Provides
    SharedPreferences provideSharedPreferences(BaseApp app) {
        return app.getSharedPreferences(app.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    @Provides
    IntegerPreference provideInt(SharedPreferences sharedPreferences) {
        return new IntegerPreference(sharedPreferences, "asd");
    }

    @Provides
    @Named(Constants.PREF_USER_ID)
    IntegerPreference provideUserId(SharedPreferences sharedPreferences) {
        return new IntegerPreference(sharedPreferences, Constants.PREF_USER_ID, -1);
    }

    @Provides
    @Named(Constants.PREF_USER_MODERATOR)
    BooleanPreference provideUserModerator(SharedPreferences sharedPreferences) {
        return new BooleanPreference(sharedPreferences, Constants.PREF_USER_MODERATOR);
    }

}
