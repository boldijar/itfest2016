package com.bolnizar.itfest.test;

import android.content.Context;

import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.models.TestResponse;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Utils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/25/2016.
 */
public class TestPresenter extends Presenter<TestView> {

    @Inject
    TestService mTestService;

    public TestPresenter(TestView testView, Context context) {
        super(testView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void load() {
        mTestService.getTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showError(e);
                    }

                    @Override
                    public void onNext(TestResponse testResponse) {
                        getView().showTest(testResponse);
                    }
                });
    }


}
