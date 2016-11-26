package com.bolnizar.itfest.classes;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface AddClassService {

    @GET("schools?order=name,asc")
    Observable<SchoolsResponse> getSchools();

    @POST("classes")
    @FormUrlEncoded
    Observable<Integer> addClass(@Field("name") String name, @Field("schoolId") int schoolId);


}
