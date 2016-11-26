package com.bolnizar.itfest.events;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.data.models.Room;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class AddEventPresenter extends Presenter<AddEventView> {

    @Inject
    AddEventService mAddEventService;

    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    public AddEventPresenter(AddEventView addEventView, Context context) {
        super(addEventView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void addEvent(String name, long date, Integer repeatingInterval, int roomId, int classId) {
        int userId = mUserId.get();
        if (repeatingInterval == null) {
            doEventCall(mAddEventService.addEvent(name, date, userId, roomId, classId));
        } else {
            doEventCall(mAddEventService.addEvent(name, date, userId, roomId, classId, repeatingInterval));
        }
    }


    private void doEventCall(Observable<Integer> integerObservable) {
        integerObservable.subscribeOn(Schedulers.io())
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
                        getView().showMessage(R.string.event_added);
                        getView().showSuccess();
                    }
                });

    }

    public void loadRooms(int schoolId) {
        mAddEventService.getRooms("schoolId,eq," + schoolId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SchoolRoomsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);

                    }

                    @Override
                    public void onNext(SchoolRoomsResponse schoolRoomsResponse) {
                        List<Room> rooms = new ArrayList<>();
                        for (SchoolRoom schoolRoom : schoolRoomsResponse.schoolroom) {
                            rooms.add(schoolRoom.rooms[0]);
                        }
                        getView().showRooms(rooms);
                    }
                });
    }
}
