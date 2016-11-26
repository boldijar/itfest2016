package com.bolnizar.itfest.events;

import com.bolnizar.itfest.events.EventsResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface EventsService {
    @GET("events?include=rooms")
    Observable<EventsResponse> getEvents(@Query("filter") String filter);

}
