package com.bolnizar.itfest.events;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface AddEventService {

    @GET("schoolroom?include=rooms")
    Observable<SchoolRoomsResponse> getRooms(@Query("filter") String filter);

    @POST("events")
    @FormUrlEncoded
    Observable<Integer> addEvent(@Field("name") String name,
                                 @Field("date") long date,
                                 @Field("userId") int userId,
                                 @Field("roomId") int roomId,
                                 @Field("classId") int classId);

    @POST("events")
    @FormUrlEncoded
    Observable<Integer> addEvent(@Field("name") String name,
                                 @Field("date") long date,
                                 @Field("userId") int userId,
                                 @Field("roomId") int roomId,
                                 @Field("classId") int classId,
                                 @Field("repeatingInterval") int interval);
}
