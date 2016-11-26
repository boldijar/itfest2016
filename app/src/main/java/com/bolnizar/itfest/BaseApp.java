package com.bolnizar.itfest;

import android.app.Application;

import com.bolnizar.itfest.di.ApplicationComponent;
import com.bolnizar.itfest.di.ApplicationModule;
import com.bolnizar.itfest.di.DaggerApplicationComponent;
import com.orm.SugarApp;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Musafir on 11/25/2016.
 */
public class BaseApp extends SugarApp {

    @Inject
    Timber.Tree mTimberTree;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraphAndInject();
        Timber.plant(mTimberTree);
    }

    private void buildGraphAndInject() {
        final ApplicationModule applicationModule = new ApplicationModule(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
