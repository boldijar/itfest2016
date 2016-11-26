package com.bolnizar.itfest.subscriptionnextevent;

import com.bolnizar.itfest.classes.SubscriptionsResponse;
import com.bolnizar.itfest.events.EventsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface NextEventService {

    @GET("subscriptions")
    Observable<SubscriptionsResponse> getUserSubs(@Query("filter") String filter);

    @GET("events")
    Observable<EventsResponse> getEvents(@Query("filter") String filter);
}
