package com.bolnizar.itfest.classes;

import com.bolnizar.itfest.data.models.School;

import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface AddClassView {
    void showSchools(List<School> schools);

    void showSuccess(Integer id);

    void showMessage(int message);
}
