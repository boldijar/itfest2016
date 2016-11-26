package com.bolnizar.itfest.classes;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.di.InjectionHelper;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class AddClassPresenter extends Presenter<AddClassView> {

    @Inject
    AddClassService mAddClassService;

    public AddClassPresenter(AddClassView addClassView, Context context) {
        super(addClassView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void loadSchools() {
        mAddClassService.getSchools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SchoolsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(SchoolsResponse schoolsResponse) {
                        getView().showSchools(schoolsResponse.schools);
                    }
                });
    }

    public void addClass(int schoolId, String name) {
        mAddClassService.addClass(name, schoolId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        getView().showSuccess(integer);
                    }
                });
    }
}
