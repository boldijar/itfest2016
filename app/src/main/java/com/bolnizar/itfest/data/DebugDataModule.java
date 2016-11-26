package com.bolnizar.itfest.data;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class DebugDataModule {

    @Provides
    Timber.Tree provideTimberTree() {
        return new Timber.DebugTree();
    }
}
