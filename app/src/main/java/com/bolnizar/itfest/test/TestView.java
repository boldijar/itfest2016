package com.bolnizar.itfest.test;

import com.bolnizar.itfest.data.models.TestResponse;

/**
 * Created by Musafir on 11/25/2016.
 */
public interface TestView {
    void showTest(TestResponse testResponse);

    void showError(Throwable e);
}
