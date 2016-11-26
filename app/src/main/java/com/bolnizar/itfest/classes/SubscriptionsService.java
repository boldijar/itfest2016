package com.bolnizar.itfest.classes;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface SubscriptionsService {

    @GET("subscriptions")
    Observable<SubscriptionsResponse> getSubs(@Query("filter") String filter);

    @POST("subscriptions")
    @FormUrlEncoded
    Observable<Integer> addSubscription(@Field("userId") int userId, @Field("classId") int classId);

    @DELETE("subscriptions/{id}")
    Observable<Integer> deleteSub(@Path("id") int id);
}
