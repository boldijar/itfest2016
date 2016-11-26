package com.bolnizar.itfest.subscriptionnextevent;

import android.content.Context;

import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.classes.SubscriptionsResponse;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.data.models.Event;
import com.bolnizar.itfest.data.models.SubscriptionModel;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.events.EventsResponse;
import com.bolnizar.itfest.events.SubscribedEventRecord;
import com.bolnizar.itfest.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Musafir on 11/26/2016.
 */
public class NextEventPresenter extends Presenter<NextEventView> {

    @Inject
    NextEventService mNextEventService;

    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    private List<Event> mEvents = new ArrayList<>();
    private List<SubscriptionModel> mSubscriptionModels = new ArrayList<>();
    private int mSubscriptionsLoaded = 0;

    private boolean mLoading = false;

    public NextEventPresenter(NextEventView nextEventView, Context context) {
        super(nextEventView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void load() {
        if (mLoading) {
            return;
        }
        mLoading = true;
        mEvents.clear();
        mSubscriptionModels.clear();
        mSubscriptionsLoaded = 0;
        mNextEventService.getUserSubs("userId,eq," + mUserId.get())
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
                        gotSubs(subscriptionsResponse.subscriptions);
                    }
                });

    }

    private void gotSubs(List<SubscriptionModel> subscriptions) {
        Timber.d("Got " + subscriptions.size() + " subs");
        if (subscriptions.size() == 0) {
            // no subs
            SubscribedEventRecord.deleteAll(SubscribedEventRecord.class);
            mLoading = false;
            return;
        }
        mSubscriptionModels = subscriptions;
        for (SubscriptionModel subscriptionModel : subscriptions) {
            mNextEventService.getEvents("classId,eq," + subscriptionModel.classId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EventsResponse>() {
                        @Override
                        public void onCompleted() {
                            mSubscriptionsLoaded++;
                            checkIfDoneWithEvents();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(EventsResponse eventsResponse) {
                            Timber.d("Got " + eventsResponse.events.size() + " events for this sub");
                            mEvents.addAll(eventsResponse.events);
                        }
                    });
        }
    }

    private void checkIfDoneWithEvents() {
        if (mSubscriptionsLoaded == mSubscriptionModels.size()) {
            // done with loading..now sort
            for (Event event : mEvents) {
                if (event.repeatingInterval != null) {
                    while (event.date < System.currentTimeMillis()) {
                        event.date += 60 * 60 * 24 * event.repeatingInterval;
                    }
                }
                event.practicDate = event.date;
            }
            Collections.sort(mEvents, new Comparator<Event>() {
                @Override
                public int compare(Event event, Event t1) {
                    return (int) (event.practicDate - t1.practicDate);
                }
            });
            mLoading = false;

            SubscribedEventRecord.deleteAll(SubscribedEventRecord.class);
            for (Event event : mEvents) {
                if (event.practicDate > System.currentTimeMillis())
                    new SubscribedEventRecord(event.id, event.name, event.date, event.repeatingInterval, event.userId, event.roomId,
                            event.rooms, event.users, event.practicDate).save();
            }
            getView().eventsSaved();
        }
    }
}
