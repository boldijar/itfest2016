package com.bolnizar.itfest.events;

import com.bolnizar.itfest.data.models.Room;
import com.bolnizar.itfest.data.models.User;
import com.orm.SugarRecord;

import java.util.Arrays;

/**
 * Created by Musafir on 11/26/2016.
 */
public class SubscribedEventRecord extends SugarRecord {

    public int id;
    public String name;
    public long date;
    public Integer repeatingInterval;
    public int userId;
    public int roomId;
    public Room[] rooms;
    public User[] users;

    public long practicDate;

    public SubscribedEventRecord(int id, String name, long date, Integer repeatingInterval, int userId, int roomId, Room[] rooms, User[] users, long practicDate) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.repeatingInterval = repeatingInterval;
        this.userId = userId;
        this.roomId = roomId;
        this.rooms = rooms;
        this.users = users;
        this.practicDate = practicDate;
    }

    public SubscribedEventRecord() {
    }

    @Override
    public String toString() {
        return "SubscribedEventRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", repeatingInterval=" + repeatingInterval +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", rooms=" + Arrays.toString(rooms) +
                ", users=" + Arrays.toString(users) +
                ", practicDate=" + practicDate +
                '}';
    }
}
