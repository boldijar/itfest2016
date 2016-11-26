package com.bolnizar.itfest.di;

import android.content.Context;
import android.net.ConnectivityManager;

import com.bolnizar.itfest.BaseApp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context mAppContext;

    public ApplicationModule(Context appContext) {
        mAppContext = appContext.getApplicationContext();
    }

    @Provides
    Context provideApplicationContext() {
        return mAppContext;
    }

    @Provides
    BaseApp provideMrBringsApplication() {
        return (BaseApp) mAppContext;
    }

    @Provides
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
