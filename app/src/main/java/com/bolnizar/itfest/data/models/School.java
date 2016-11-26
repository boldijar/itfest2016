package com.bolnizar.itfest.data.models;

/**
 * Created by Musafir on 11/26/2016.
 */
public class School {
    public int id;
    public String name;

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
