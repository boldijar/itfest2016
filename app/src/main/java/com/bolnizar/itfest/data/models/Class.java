package com.bolnizar.itfest.data.models;

import java.util.Arrays;

/**
 * Created by Musafir on 11/26/2016.
 */
public class Class {
    public int id;
    public String name;
    public int schoolId;
    public School[] schools;

    public boolean subscribed;

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", schools=" + Arrays.toString(schools) +
                '}';
    }
}
