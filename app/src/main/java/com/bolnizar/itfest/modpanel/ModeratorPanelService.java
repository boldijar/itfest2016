package com.bolnizar.itfest.modpanel;

import com.bolnizar.itfest.loginregister.LoginRegisterResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface ModeratorPanelService {

    @GET("users")
    Observable<LoginRegisterResponse> getUsers(@Query("filter") String filter);

    @PUT("users/{id}")
    @FormUrlEncoded
    Observable<Integer> changeUserType(@Path("id") int id, @Field("type") String type);
}
