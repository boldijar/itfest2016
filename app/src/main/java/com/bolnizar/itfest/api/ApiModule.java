package com.bolnizar.itfest.api;

import com.bolnizar.itfest.BaseApp;
import com.bolnizar.itfest.classes.AddClassService;
import com.bolnizar.itfest.classes.ClassesService;
import com.bolnizar.itfest.classes.SubscriptionsService;
import com.bolnizar.itfest.events.AddEventService;
import com.bolnizar.itfest.events.EventsService;
import com.bolnizar.itfest.loginregister.LoginRegisterService;
import com.bolnizar.itfest.modpanel.ModeratorPanelService;
import com.bolnizar.itfest.test.TestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class ApiModule {

    private static final String API_ENDPOINT = "http://192.168.0.170/php/itfest/api.php/itfest/";
    private static final int DISK_CACHE_SIZE = 10 * 1024;

    @Provides
    Cache provideCache(BaseApp app) {
        File httpCacheDirectory = new File(app.getCacheDir().getAbsolutePath(), "HttpCache");
        return new Cache(httpCacheDirectory, DISK_CACHE_SIZE);
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache,
                                     HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    /**
     * Provides a Call Adapter Factor dependency to be used by Retrofit to set the {@link
     * rx.Scheduler} on which the API calls are made
     *
     * @return The custom Call Adapter
     */
    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    @Provides
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    Retrofit provideRetrofit(final OkHttpClient okHttpClient,
                             final Converter.Factory converter,
                             final CallAdapter.Factory adapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addCallAdapterFactory(adapterFactory)
                .addConverterFactory(converter)
                .client(okHttpClient)
                .build();
    }

    @Provides
    TestService provideTestService(final Retrofit retrofit) {
        return retrofit.create(TestService.class);
    }

    @Provides
    LoginRegisterService provideLoginRegisterService(final Retrofit retrofit) {
        return retrofit.create(LoginRegisterService.class);
    }

    @Provides
    ModeratorPanelService provideModeratorPanelService(final Retrofit retrofit) {
        return retrofit.create(ModeratorPanelService.class);
    }

    @Provides
    ClassesService provideClassesService(final Retrofit retrofit) {
        return retrofit.create(ClassesService.class);
    }

    @Provides
    SubscriptionsService provideSubscriptionsService(final Retrofit retrofit) {
        return retrofit.create(SubscriptionsService.class);
    }

    @Provides
    EventsService provideEventsService(final Retrofit retrofit) {
        return retrofit.create(EventsService.class);
    }

    @Provides
    AddClassService providAddClassService(final Retrofit retrofit) {
        return retrofit.create(AddClassService.class);
    }

    @Provides
    AddEventService provideAddEventService(final Retrofit retrofit) {
        return retrofit.create(AddEventService.class);
    }
}
