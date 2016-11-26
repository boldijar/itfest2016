package com.bolnizar.itfest.events;

import com.bolnizar.itfest.data.models.Event;

import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface EventsView {
    void showEvent(List<Event> events);

    void showMessage(int messageId);
}
