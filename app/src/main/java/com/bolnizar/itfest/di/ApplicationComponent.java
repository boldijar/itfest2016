package com.bolnizar.itfest.di;

import com.bolnizar.itfest.BaseApp;
import com.bolnizar.itfest.MainActivity;
import com.bolnizar.itfest.activities.HomeActivity;
import com.bolnizar.itfest.api.ApiModule;
import com.bolnizar.itfest.data.DebugDataModule;
import com.bolnizar.itfest.di.scopes.ApplicationScope;
import com.bolnizar.itfest.loginregister.LoginRegisterPresenter;
import com.bolnizar.itfest.modpanel.ModeratorPanelPresenter;
import com.bolnizar.itfest.test.TestPresenter;

import javax.inject.Named;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Rares on 12/09/16.
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class, AppPrefsModule.class, DebugDataModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(BaseApp app);

    Retrofit provideRetrofit();

    void inject(MainActivity mainActivity);

    void inject(TestPresenter testPresenter);

    void inject(LoginRegisterPresenter loginRegisterPresenter);

    void inject(HomeActivity homeActivity);

    void inject(ModeratorPanelPresenter moderatorPanelPresenter);
}