package com.bolnizar.itfest.events;

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
public class EventsPresenter extends Presenter<EventsView> {

    @Inject
    EventsService mEventsService;

    public EventsPresenter(EventsView eventsView, Context context) {
        super(eventsView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void load(int classId) {
        mEventsService.getEvents("classId,eq," + classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(EventsResponse eventsResponse) {
                        getView().showEvent(eventsResponse.events);
                    }
                });

    }

    public void delete(int id) {
        mEventsService.deleteEvent(id).subscribeOn(Schedulers.io())
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
                        getView().eventDeleted();
                        getView().showMessage(R.string.event_deleted);
                    }
                });
    }
}
