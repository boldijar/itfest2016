package com.bolnizar.itfest.classes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface ClassesService {

    @GET("classes?include=schools")
    Observable<ClassesResponse> getClassesByNameFilter(@Query("filter") String filter);
}
