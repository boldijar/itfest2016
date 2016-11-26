package com.bolnizar.itfest.events;

import com.bolnizar.itfest.data.models.Room;

import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface AddEventView {
    void showMessage(int message);

    void showRooms(List<Room> rooms);

    void showSuccess();
}
