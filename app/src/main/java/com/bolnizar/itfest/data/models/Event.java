package com.bolnizar.itfest.data.models;

/**
 * Created by Musafir on 11/26/2016.
 */
public class Event {
    public int id;
    public String name;
    public long date;
    public Integer repeatingInterval;
    public int userId;
    public int roomId;
    public Room[] rooms;
    public User[] users;
}
