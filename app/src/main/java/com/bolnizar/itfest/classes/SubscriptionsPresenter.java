package com.bolnizar.itfest.classes;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.data.models.SubscriptionModel;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.persistance.SubscriptionRecord;
import com.bolnizar.itfest.utils.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class SubscriptionsPresenter extends Presenter<SubscriptionsView> {


    @Inject
    SubscriptionsService mSubscriptionsService;

    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    public SubscriptionsPresenter(SubscriptionsView subscriptionsView, Context context) {
        super(subscriptionsView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void subscribe(int classId) {
        mSubscriptionsService.addSubscription(mUserId.get(), classId)
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
                        getView().showMessage(R.string.subscribed_success);
                        updateSubs();
                    }
                });

    }

    public void unsubscribe(int classId) {
        List<SubscriptionRecord> subs = SubscriptionRecord.find(SubscriptionRecord.class, "class_id=?", classId + "");
        mSubscriptionsService.deleteSub(subs.get(0).subId)
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
                        getView().showMessage(R.string.unsubscribed_success);
                        updateSubs();
                    }
                });

    }

    private void updateSubs() {
        mSubscriptionsService.getSubs("userId,eq," + mUserId.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubscriptionsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SubscriptionsResponse subscriptionsResponse) {
                        loadSubs();
                    }
                });
    }

    public void loadSubs() {
        mSubscriptionsService.getSubs("userId,eq," + mUserId.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubscriptionsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SubscriptionsResponse subscriptionsResponse) {
                        loadedSubs(subscriptionsResponse);
                        getView().subsWereUpdated();
                    }
                });
    }

    private void loadedSubs(SubscriptionsResponse subscriptionsResponse) {
        SubscriptionRecord.deleteAll(SubscriptionRecord.class);
        for (SubscriptionModel subscriptionModel : subscriptionsResponse.subscriptions) {
            SubscriptionRecord subscriptionRecord = new SubscriptionRecord(subscriptionModel.userId, subscriptionModel.classId, subscriptionModel.id);
            subscriptionRecord.save();
        }
    }
}
