package com.bolnizar.itfest.comments;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface CommentsService {

    @GET("comments?include=users")
    Observable<CommentResponse> getComments(@Query("filter") String filter);

    @POST("comments")
    @FormUrlEncoded
    Observable<Integer> addComment(@Field("userId") int userId, @Field("eventId") int eventId, @Field("text") String text);
}
