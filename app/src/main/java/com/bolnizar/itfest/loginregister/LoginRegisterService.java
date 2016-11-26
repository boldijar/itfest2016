package com.bolnizar.itfest.loginregister;

import com.bolnizar.itfest.data.models.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface LoginRegisterService {
    @GET("users")
    Observable<LoginRegisterResponse> login(@Query("filter[]") String filter);

    @POST("users")
    @FormUrlEncoded
    Observable<Integer> addUser(@Field("email") String email, @Field("password") String password, @Field("type") String type);
}
