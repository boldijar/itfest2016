package com.bolnizar.itfest.di;

import android.content.Context;

import com.bolnizar.itfest.BaseApp;


/**
 * Created by Rares on 12/09/16.
 */
public final class InjectionHelper {

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((BaseApp) context.getApplicationContext()).getApplicationComponent();
    }

}
