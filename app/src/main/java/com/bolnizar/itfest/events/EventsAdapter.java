package com.bolnizar.itfest.events;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.data.models.Class;
import com.bolnizar.itfest.data.models.Event;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Constants;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Musafir on 11/26/2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    private List<Event> mEvents = new ArrayList<>();
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.getDefault());

    private final EventsListener mEventListener;

    public EventsAdapter(EventsListener eventListener) {
        mEventListener = eventListener;
    }

    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    public void setEvents(List<Event> events) {
        for (Event event : events) {
            if (event.repeatingInterval != null) {
                while (event.date < System.currentTimeMillis()) {
                    event.date += 60 * 60 * 24 * event.repeatingInterval;
                }
            }
        }
        mEvents = events;
        notifyDataSetChanged();

    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InjectionHelper.getApplicationComponent(parent.getContext()).inject(this);
        return new EventsHolder(parent);
    }

    @Override
    public void onBindViewHolder(EventsHolder holder, final int position) {
        final Event event = mEvents.get(position);
        holder.name.setText(event.name);
        holder.address.setText(event.rooms[0].address);
        holder.room.setText(holder.room.getContext().getString(R.string.room_room) + " " + event.rooms[0].name);
        if (event.repeatingInterval == null) {
            String niceDate = mSimpleDateFormat.format(new Date(event.date));
            holder.date.setText(niceDate);
        } else {
            String niceDate = mSimpleDateFormat.format(new Date(event.date));
            String secondPart = holder.date.getContext().getString(R.string.repeating_every, event.repeatingInterval);
            holder.date.setText(niceDate + " " + secondPart);
        }
        holder.navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventListener.eventClicked(event);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventListener.deleteEvent(event);
            }
        });
        holder.delete.setVisibility(mUserId.get() == event.userId ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return mEvents.size();
    }


    public interface EventsListener {
        void eventClicked(Event event);

        void deleteEvent(Event event);
    }

    public static class EventsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_name)
        TextView name;
        @BindView(R.id.event_address)
        TextView address;
        @BindView(R.id.event_room)
        TextView room;
        @BindView(R.id.event_navigation)
        View navigation;
        @BindView(R.id.event_date)
        TextView date;
        @BindView(R.id.item_event_delete)
        View delete;

        public EventsHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
            ButterKnife.bind(this, itemView);
        }

    }
}
