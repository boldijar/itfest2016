package com.bolnizar.itfest.test;

import com.bolnizar.itfest.data.models.TestResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Musafir on 11/25/2016.
 */
public interface TestService {
    @GET("test")
    Observable<TestResponse> getTest();
}
