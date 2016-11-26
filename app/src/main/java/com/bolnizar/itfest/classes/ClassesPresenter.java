package com.bolnizar.itfest.classes;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.di.InjectionHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class ClassesPresenter extends Presenter<ClassesView> {

    @Inject
    ClassesService mClassesService;

    private Subscription mTimerSubscription;

    public ClassesPresenter(ClassesView classesView, Context context) {
        super(classesView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void inputChanged(final String text) {
        if (mTimerSubscription != null) {
            mTimerSubscription.unsubscribe();
        }
        mTimerSubscription = Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        loadClasses(text);
                    }
                });

    }

    public void loadClasses(String name) {
        mClassesService.getClassesByNameFilter("name,cs," + name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassesResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(ClassesResponse classesResponse) {
                        getView().showClasses(classesResponse.classes);
                    }
                });


    }
}
